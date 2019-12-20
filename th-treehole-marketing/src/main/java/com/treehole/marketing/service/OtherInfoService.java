package com.treehole.marketing.service;

import com.treehole.framework.domain.marketing.response.MarketingCode;
import com.treehole.framework.domain.member.User;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.member.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * @author wanglu
 */
@Service
public class OtherInfoService {

/*
    public QueryResult findUserByTime(Date beforeTime, Date afterTime){
        Example example = new Example(User.class);
        if(beforeTime == null && afterTime == null){
            ExceptionCast.cast(MarketingCode.DATA_ERROR);
        }
        if(beforeTime == null){
            example.createCriteria().andBetween("user_createtime", afterTime, new Date());
        } else if (afterTime == null ){
            example.createCriteria().andNotBetween("user_createtime", beforeTime, new Date());
        } else {
            example.createCriteria().andBetween("user_createtime", afterTime, beforeTime);
        }

        List<User> users = this.userMapper.selectByExample(example);
        return new QueryResult(users, users.size());
    }*/
}
