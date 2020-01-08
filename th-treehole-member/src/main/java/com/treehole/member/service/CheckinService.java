package com.treehole.member.service;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.treehole.framework.domain.member.Checkin;
import com.treehole.framework.domain.member.Points;
import com.treehole.framework.domain.member.User;
import com.treehole.framework.domain.member.Vo.CheckinVo;
import com.treehole.framework.domain.member.result.MemberCode;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.member.mapper.CheckinMapper;
import com.treehole.member.myUtil.MyNumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.sql.Timestamp;
import java.util.ArrayList;
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
    @Autowired
    private UserVoService userVoService;
    @Autowired
    private UserService userService;

    /*
    * 查询
    * */
    //@Cacheable(value="MemberCheck")
    public QueryResponseResult findAllCheckins(Integer page,
                                       Integer size,
                                       String nickname) {
        List<Checkin> checkins = new ArrayList<Checkin>();
            Page pag = new Page();
        if(StringUtils.isNotEmpty(nickname)){
            User user = userService.findUserByNickname(nickname);
            pag =PageHelper.startPage(page,size);
            Example example = new Example(Checkin.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("user_id",user.getUser_id()); //参数为 属性名+值
            example.orderBy("checkin_time").desc();//排序
            checkins = checkinMapper.selectByExample(example);
        }else{
             pag =PageHelper.startPage(page,size);
            Example example = new Example(Checkin.class);
            Example.Criteria criteria = example.createCriteria();
            example.orderBy("checkin_time").desc();//排序
            checkins = checkinMapper.selectByExample(example);
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
    }

    /*
    * 增
    * */
    @Transactional
    //@CacheEvict(value="MemberCheck",allEntries=true)
    public void insertCheckin(Checkin checkin) {
        //false 不在同一天
    if(!this.OneDayCheckin(checkin.getUser_id())){

        checkin.setCheckin_id(MyNumberUtils.getUUID());
        checkin.setCheckin_time(new Date());
        int ins= checkinMapper.insert(checkin);
        if(ins != 1){
            ExceptionCast.cast(MemberCode.INSERT_FAIL);
        }
        }else{
            ExceptionCast.cast(MemberCode.CHECKIN_REPEAT);
        }

    }
    //判断一天只能签一次到
    public boolean OneDayCheckin(String user_id){
        Checkin check = new Checkin();
        check.setUser_id(user_id);
        List<Checkin> select = checkinMapper.select(check);
        Boolean inOneday = false;  //不在一天
        for(Checkin ch : select){
            //现在的时间
//            Date now = new Date();
//            Timestamp  timenow  = new Timestamp(now.getTime());
            //get到的签到时间
//            Date checkTime = ch.getCheckin_time();
//            Timestamp timecheck =new Timestamp(checkTime.getTime());
//            boolean day = DateUtil.isSameDay(timenow,timecheck);
            Date now = new Date();
            Date checkTime = ch.getCheckin_time();
            boolean day = DateUtil.isSameDay(now,checkTime);
            if(day){
                inOneday = true;
            }
        }
        return inOneday;

    }

    /*
    * 根据user_id删除签到记录
    * */
    @Transactional
    //@CacheEvict(value="MemberCheck",allEntries=true)
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
    //@CacheEvict(value="MemberCheck",allEntries=true)
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
            if (CollectionUtils.isEmpty(checkins)) {
                ExceptionCast.cast(MemberCode.DATA_IS_NULL);
            }
            //解析分页结果
            PageInfo<Checkin> pageInfo = new PageInfo<>(pag.getResult());
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
