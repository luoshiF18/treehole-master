package com.treehole.member.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.treehole.framework.domain.evaluation.Description;
import com.treehole.framework.domain.evaluation.response.EvaluationCode;
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
            ExceptionCast.cast(EvaluationCode.SELECT_NULL);
            return null;
        }
    }
    /**
     * 插入一条积分信息
     * 会员注册后立即调用(会员)
     * 积分插入的步骤：
     *   先查询user表中的pointnow付给before，后修改
     *   记录表需要4个参数： 1.before ：从user表里通过userid获取
     *            2.num  ：从前端获取
     *            3.after    ：before+num
     *            4.userid   ：从前端获取
     *   最后:user表内的pointnow = after;   updateUserById() //
     *
     *
     *
     * @param
     * @return int
     */

    public void insertPoint(Points points)  {
        points.setPoints_id(MyNumberUtils.getUUID());
        points.setPoints_time(new Date());
        User user = userService.getUserById(points.getUser_id());
        //当前用户的积分值
        points.setPoints_before(user.getPoints_now());
        //System.out.println("++++++++++++++++before:" +user.getPoints_now());
        //获取本次操作的值
        points.setPoints_num(points.getPoints_num());
        //System.out.println("++++++++++++++++benci:" +points.getPoints_num());
        //later的值
        int later = points.getPoints_before() + points.getPoints_num();
        if(later < 0){
            ExceptionCast.cast(MemberCode.POINT_NOT_FULL);
        }
        points.setPoints_later(later);
        //与user表保持同步
        user.setPoints_now(later);
        //System.out.println("++++++++++++++++later:" + later);
        userService.updateUser(user);

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
        Points point = new Points();
        point.setPoints_id(points_id);
        int del = pointsMapper.delete(point);
        if(del !=1){
            ExceptionCast.cast(MemberCode. DELETE_FAIL);
        }
    }


}
