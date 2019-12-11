package com.treehole.member.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.treehole.framework.domain.member.Checkin;
import com.treehole.framework.domain.member.Points;
import com.treehole.framework.domain.member.User;
import com.treehole.framework.domain.member.Vo.UserVo;
import com.treehole.framework.domain.member.result.MemberCode;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.member.mapper.CheckinMapper;
import com.treehole.member.myUtil.MyNumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;


import java.util.*;

/**
 * @author shanhuijie
 * @Description: 签到服务
 * @Date
 */
@Service
@Cacheable(value="MemberCheck")
public class CheckinService {
    @Autowired
    private CheckinMapper checkinMapper;
    @Autowired
    private PointService pointService;
    @Autowired
    private UserVoService userVoService;
    @Autowired
    private UserService userService;

    /*
    * 查询
    * */
   /* public QueryResponseResult findAllCheckins(Integer page,
                                       Integer size,
                                       String nickname) {
        List<Checkin> checkins = new ArrayList<Checkin>();
        //分页
        Page pag =PageHelper.startPage(page,size);
        if(StringUtils.isNotEmpty(nickname)){
            User user = userService.findUserByNickname(nickname);
            Checkin ch = new Checkin();
            ch.setUser_id(user.getUser_id());
            checkins = checkinMapper.select(ch);
        }else{
            checkins = checkinMapper.selectAll();
        }

        if (CollectionUtils.isEmpty(checkins)) {
            ExceptionCast.cast(MemberCode.DATA_IS_NULL);
        }

        List<CheckinVo> checkinVos = new ArrayList<CheckinVo>();
        for(Checkin check : checkins){
            CheckinVo cv = new CheckinVo();
            User user = userService.getUserById(check.getUser_id());
            cv.setNickname(user.getUser_nickname());
            cv.setCheckin_id(check.getCheckin_id());
            cv.setCheckin_time(check.getCheckin_time());
            checkinVos.add(cv);
        }
        //解析分页结果
        PageInfo<CheckinVo> pageInfo = new PageInfo<CheckinVo>(pag.getResult());
        QueryResult queryResult = new QueryResult();
        queryResult.setList(checkinVos);
        queryResult.setTotal(pageInfo.getTotal());
        return new QueryResponseResult(CommonCode.SUCCESS,queryResult);
    }*/

    /*
    * 增
    * */
    @Transactional
    @CacheEvict(value="MemberCheck",allEntries=true)
    public void insertCheckin(Checkin checkin) {
        checkin.setCheckin_id(MyNumberUtils.getUUID());
        checkin.setCheckin_time(new Date());
        int ins= checkinMapper.insert(checkin);
        if(ins != 1){
            ExceptionCast.cast(MemberCode.INSERT_FAIL);
        }
        /*//调用insert积分功能
        Points points = new Points();
        points.setPoints_num();//从active表中获取签到值
        pointService.insertPoint(points);*/
    }

    /*
    * 根据user_id删除签到记录
    * */
    @Transactional
    @CacheEvict(value="MemberCheck",allEntries=true)
    public void deleteCheckinByUserId(String user_id) {
        //id不为空
        if(org.apache.commons.lang3.StringUtils.isBlank(user_id)){
            ExceptionCast.cast(MemberCode.DATA_ERROR);
        }
        //1.先找
        Checkin checkin = new Checkin();
        checkin.setUser_id(user_id);
        List<Checkin> select = checkinMapper.select(checkin);
        /*if(CollectionUtils.isEmpty(select)){
            ExceptionCast.cast(MemberCode.DATA_IS_NULL);
        }*/
        //2.后删
        int i= 0;
        int sum = select.size();
        //if(sum !=0) {
            for (Checkin ch : select) {
                int del = checkinMapper.delete(ch);
                if (del == 1) {
                    i++;
                }
            }
            if(i != sum){
                ExceptionCast.cast(MemberCode.DELETE_FAIL);
            }
        //}

    }

    /*
    *
    * */
    @Transactional
    @CacheEvict(value="MemberCheck",allEntries=true)
    public void deleteByCheckId(String id){
        //1.先找
        Checkin checkin = new Checkin();
        checkin.setCheckin_id(id);
        Checkin select = checkinMapper.selectOne(checkin);
        if(select == null){
            ExceptionCast.cast(MemberCode.DATA_IS_NULL);
        }
        //2.后删
        int del= checkinMapper.delete(select);
        if(del != 1){
            ExceptionCast.cast(MemberCode.DELETE_FAIL);
        }
    }
    /*
    *根据user-id 查询签到记录
    */
    public QueryResult getCheckinByUserId(String user_id, Integer page, Integer size) {
        if (StringUtils.isBlank(user_id)) {
            ExceptionCast.cast(MemberCode.SELECT_NULL);
            return null;
        }
            Page pag =PageHelper.startPage(page,size);
            Checkin checkin = new Checkin();
            checkin.setUser_id(user_id);
            List<Checkin> checkins = checkinMapper.select(checkin);
            //System.out.println("++++++++++++++++++check:" + checkins);
            if (CollectionUtils.isEmpty(checkins)) {
                ExceptionCast.cast(MemberCode.DATA_IS_NULL);
            }
            //解析分页结果
            PageInfo<Checkin> pageInfo = new PageInfo<>(pag.getResult());
            //获取总条数
            //Long sizer = Long.valueOf(points.size());
            // System.out.println("+++++++++++++++++size:" +size);
            return new QueryResult(checkins, pageInfo.getTotal());

    }
    /*不报错版根据userid查签到对象*/
    public QueryResult getCheckinByUserId1(String user_id, Integer page, Integer size) {
        Page pag =PageHelper.startPage(page,size);
        Checkin checkin = new Checkin();
        checkin.setUser_id(user_id);
        List<Checkin> checkins = checkinMapper.select(checkin);
        //解析分页结果
        PageInfo<Checkin> pageInfo = new PageInfo<>(pag.getResult());
        return new QueryResult(checkins, pageInfo.getTotal());
    }

    /*按时间删除  未实现*/
}
