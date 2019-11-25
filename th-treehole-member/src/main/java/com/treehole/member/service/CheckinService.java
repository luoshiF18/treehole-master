package com.treehole.member.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.treehole.framework.domain.evaluation.response.EvaluationCode;
import com.treehole.framework.domain.member.Checkin;
import com.treehole.framework.domain.member.Points;
import com.treehole.framework.domain.member.result.MemberCode;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.member.mapper.CheckinMapper;
import com.treehole.member.myUtil.MyNumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;
import java.util.List;

/**
 * @author shanhuijie
 * @Description: 签到服务
 * @Date
 */
@Service
public class CheckinService {
    @Autowired
    private CheckinMapper checkinMapper;
    @Autowired
    private PointService pointService;

    public QueryResult findAllCheckins(Integer page, Integer size) {
//        分页
        PageHelper.startPage(page, size);
        //查询
        List<Checkin> checkins = checkinMapper.selectAll();
        if (CollectionUtils.isEmpty(checkins)) {
            ExceptionCast.cast(MemberCode.DATA_IS_NULL);
        }
        //        解析分页结果
        PageInfo<Checkin> pageInfo = new PageInfo<>(checkins);

        return new QueryResult(checkins, pageInfo.getTotal());
    }


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


    public void deleteCheckinByUserId(String user_id) {

        Checkin checkin = new Checkin();
        checkin.setUser_id(user_id);
        int del = checkinMapper.delete(checkin);
        if(del != 1){
            ExceptionCast.cast(MemberCode.DELETE_FAIL);
        }
    }

    public QueryResult getCheckinByUserId(String user_id, Integer page, Integer size) {
        if (StringUtils.isBlank(user_id)) {
            ExceptionCast.cast(MemberCode.SELECT_NULL);
            return null;
        }
            Page pag =PageHelper.startPage(page,size);
            Checkin checkin = new Checkin();
            checkin.setUser_id(user_id);
            List<Checkin> checkins = checkinMapper.select(checkin);
            System.out.println("++++++++++++++++++check:" + checkins);
            if (CollectionUtils.isEmpty(checkins)) {
                ExceptionCast.cast(MemberCode.DATA_IS_NULL);
            }
            //解析分页结果
            PageInfo<Checkin> pageInfo = new PageInfo<>(pag.getResult());
            //            获取总条数
            //Long sizer = Long.valueOf(points.size());
            // System.out.println("+++++++++++++++++size:" +size);
            return new QueryResult(checkins, pageInfo.getTotal());

    }

    /*按时间删除  未实现*/
}
