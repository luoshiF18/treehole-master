package com.treehole.member.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.treehole.framework.domain.evaluation.Description;
import com.treehole.framework.domain.evaluation.response.EvaluationCode;
import com.treehole.framework.domain.member.Cards;
import com.treehole.framework.domain.member.FreeGrade;
import com.treehole.framework.domain.member.Points;
import com.treehole.framework.domain.member.User;
import com.treehole.framework.domain.member.Vo.UserVo;
import com.treehole.framework.domain.member.result.MemberCode;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.member.mapper.PointsMapper;
import com.treehole.member.mapper.UserMapper;
import com.treehole.member.myUtil.MyNumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * @author shanhuijie
 * @Description: 积分信息服务
 * @Date
 */
@Service
public class PointService {
    @Autowired
    private PointsMapper pointsMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private CardsService cardsService;
    @Autowired
    private FreegradeService freegradeService;


    /**
     * 查询所有积分信息
     *
     * @param
     * @return List<Points>
     */
    public QueryResult findAllPoints(Integer page, Integer size){
//        分页
        PageHelper.startPage(page, size);
        //查询
        List<Points> points = pointsMapper.selectAll();
        if (CollectionUtils.isEmpty(points)) {
            ExceptionCast.cast(MemberCode.DATA_IS_NULL);
        }
        //        解析分页结果
        PageInfo<Points> pageInfo = new PageInfo<>(points);

        return new QueryResult(points, pageInfo.getTotal());
    }

    /**
     * 根据id查询积分信息
     *
     * @param user_id
     * @return List<Points>
     */

    public QueryResult getPointById(String user_id,Integer page, Integer size){
        if (StringUtils.isNotBlank(user_id)) {
            Page pag =PageHelper.startPage(page,size);
            Points point = new Points();
            point.setUser_id(user_id);
            List<Points> points = pointsMapper.select(point);
            //解析分页结果
            PageInfo<Points> pageInfo = new PageInfo<>(pag.getResult());
            //            获取总条数
            //Long sizer = Long.valueOf(points.size());
           // System.out.println("+++++++++++++++++size:" +size);
            return new QueryResult(points, pageInfo.getTotal());
        } else {
            ExceptionCast.cast(MemberCode.SELECT_NULL);
            return null;
        }
    }
    /**
     * 插入一条积分信息
     * 前端需要传入本次操作num值、user_id
     * 会员注册后立即调用(会员)
     * 积分插入的步骤：
     *   先查询user表中的pointnow付给before，后修改
     *   记录表需要4个参数： 1.before ：从user表里通过userid获取
     *            2.num  ：从前端获取
     *            3.after    ：before+num
     *            4.userid   ：从前端获取
     *   最后:user表内的pointnow = after;   updateUserById() //
     * @param
     * @return int
     */

    public void insertPoint(Points points)  {
        points.setPoints_id(MyNumberUtils.getUUID());
        points.setPoints_time(new Date());
        //改变cards  目的：累计积分变化/等级变化
        if(points.getPoints_num()>0){
            Cards cards = cardsService.findCardsByUserId(points.getUser_id());

            Integer sum =cards.getPoints_sum()+ points.getPoints_num();

            //判断cars累计积分值是否大于现有等级的积分值,此功能在freegradeservice中，gradeChange()
            String freegradeId = cardsService.findCardsByUserId(points.getUser_id()).getFreegrade_id();//通过user_id 查找用户的freegrade_id

            freegradeService.gradeChange(points.getUser_id(),freegradeId,sum);

            //cardsService.updateCard(cards);
        }


        //User user = userService.getUserById(points.getUser_id());
        Cards cards = cardsService.findCardsByUserId(points.getUser_id());
        //当前用户的积分值
        points.setPoints_before(cards.getPoints_now());
        //System.out.println("++++++++++++++++before:" +user.getPoints_now());
        //获取本次操作的值
        points.setPoints_num(points.getPoints_num());  //手写代码，后期需完善
        //points.setPoints_num("跨服务调用接口,从活动表里获取值");
        //System.out.println("++++++++++++++++benci:" +points.getPoints_num());

        points.setAct_id(points.getAct_id());
        //需要接口：根据活动id查询活动名称及活动积分值，从营销活动表内获取活动名称
        points.setDescription("签到");
        //later的值
        int later = points.getPoints_before() + points.getPoints_num();
        if(later < 0){  //积分值小于0 ，不可操作
            ExceptionCast.cast(MemberCode.POINT_NOT_FULL);
        }
        points.setPoints_later(later);
        //与cards表保持同步
        cards.setPoints_now(later);

        //System.out.println("++++++++++++++++later:" + later);
        //userService.updateUser(cards);

        cardsService.updateCard(cards);//更新积分变化
        int ins = pointsMapper.insert(points);
        if( ins != 1){
            ExceptionCast.cast(MemberCode.INSERT_FAIL);
        }
    }

    /**
     * 根据id删除积分信息
     * 暂时不可行
     * 暂时不用
     * 可考虑--》按时间定时删除老旧数据 未实现
     *
     * @param points_id
     * @return int
     */
    public void deletePointById(String points_id) {
        //List<Points> points = this.getPointById(points_id);
        //id不为空
        if(org.apache.commons.lang3.StringUtils.isBlank(points_id)){
            ExceptionCast.cast(MemberCode.DATA_ERROR);
        }
        Points point = new Points();
        point.setPoints_id(points_id);
        int del = pointsMapper.delete(point);
        if(del != 1){
            ExceptionCast.cast(MemberCode. DELETE_FAIL);
        }
    }

    public void deletePointByUserId(String user_id){
        //id不为空
        if(org.apache.commons.lang3.StringUtils.isBlank(user_id)){
            ExceptionCast.cast(MemberCode.DATA_ERROR);
        }
        Points points = new Points();
        points.setUser_id(user_id);
        int del = pointsMapper.delete(points);
        if(del <0 ||del==0){
            ExceptionCast.cast(MemberCode. DELETE_FAIL);
        }
    }


}
