package com.treehole.member.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.treehole.framework.domain.member.Points;
import com.treehole.framework.domain.member.User;
import com.treehole.framework.domain.member.resquest.PointListRequest;
import com.treehole.framework.domain.member.result.MemberCode;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.member.client.MemberClient;
import com.treehole.member.mapper.PointsMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author shanhuijie
 * @Description: 积分信息服务
 * @Date
 */
@Service
@Cacheable(value="MemberPoint")
public class PointService {
    @Autowired
    private PointsMapper pointsMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private UserVoService userVoService;
    @Autowired
    private CardsService cardsService;
    @Autowired
    private FreegradeService freegradeService;
    @Autowired
    MemberClient memberClient;


    /**
     * 查询所有积分信息
     *
     * @param
     * @return List<Points>
     */

    /**
     * 根据id查询积分信息
     *
     * @param pointListRequest
     * @return List<Points>
     */

    public QueryResponseResult findAllPoints(Integer page,
                                             Integer size,
                                             PointListRequest pointListRequest){


        if(pointListRequest == null){
            pointListRequest =new PointListRequest();
        }
        Points points1 = new Points();
        if(StringUtils.isNotEmpty(pointListRequest.getUser_nickname())){
            User user = userService.findUserByNickname(pointListRequest.getUser_nickname());
            points1.setUser_id(user.getUser_id());
        }
        //pagehelper需要放在离查询最近的地方，中间不能隔着查询去查询，数据会一直为1
        Page pag =PageHelper.startPage(page,size);
        if(StringUtils.isNotEmpty(pointListRequest.getUser_id())){
            points1.setUser_id(pointListRequest.getUser_id());
        }
        //查询
        List<Points> pointsList = pointsMapper.select(points1);
        if (CollectionUtils.isEmpty(pointsList)) {
            ExceptionCast.cast(MemberCode.DATA_IS_NULL);
        }
        //时间倒序排  最近的时间的放在最前面
        Collections.sort(pointsList, new Comparator<Points>() {
            @Override
            public int compare(Points p1, Points p2) {
                return p2.getPoints_time().compareTo(p1.getPoints_time());  //大于返回1；小于返回-1；等于返回0
            }
        });
        //解析分页结果
        PageInfo<Points> pageInfo = new PageInfo<Points>(pag.getResult());
        QueryResult queryResult = new QueryResult();
        queryResult.setList(pointsList);
        queryResult.setTotal(pageInfo.getTotal());
        return new QueryResponseResult(CommonCode.SUCCESS,queryResult);
    }


    /**
     * 插入一条积分信息  已完成
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
    @Transactional
    @CacheEvict(value="MemberPoint",allEntries=true)
    public void insertPoint(Points points)  {

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
    @Transactional
    @CacheEvict(value="MemberPoint",allEntries=true)
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
    @Transactional
    @CacheEvict(value="MemberPoint",allEntries=true)
    public void deletePointByUserId(String user_id){
        //id不为空
        if(org.apache.commons.lang3.StringUtils.isBlank(user_id)){
            ExceptionCast.cast(MemberCode.DATA_ERROR);
        }
        //1.先找
        Points points = new Points();
        points.setUser_id(user_id);
        List<Points> select = pointsMapper.select(points);
        //2.后删
        int i= 0;
        int sum = select.size();
        //if(sum !=0) {
            for (Points po : select) {
                int del = pointsMapper.delete(po);
                if (del == 1) {
                    i++;
                }
            }
            if(i != sum){
                ExceptionCast.cast(MemberCode.DELETE_FAIL);
            }
        //}

    }
    /*不报错版根据user——id查询*/
    public QueryResult findAllPoints1(Integer page,
                                      Integer size,
                                      String user_id){
        Page pag =PageHelper.startPage(page,size);
        Points point = new Points();
        point.setUser_id(user_id);
        List<Points> points = pointsMapper.select(point);
        //解析分页结果
        PageInfo<Points> pageInfo = new PageInfo<Points>(pag.getResult());
        return new QueryResult(points, pageInfo.getTotal());
    }


}
