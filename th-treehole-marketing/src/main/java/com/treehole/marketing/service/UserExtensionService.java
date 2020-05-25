package com.treehole.marketing.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.treehole.framework.domain.marketing.Coupon;
import com.treehole.framework.domain.marketing.UserCoupon;
import com.treehole.framework.domain.marketing.UserExtension;
import com.treehole.framework.domain.marketing.bo.UserCouponBo;
import com.treehole.framework.domain.marketing.response.MarketingCode;
import com.treehole.framework.domain.marketing.utils.MyStatusCode;
import com.treehole.framework.domain.member.ext.UserExt;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.marketing.dao.UserExtensionMapper;
import com.treehole.marketing.utils.MyNumberUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wanglu
 */
@Service
public class UserExtensionService {

    @Autowired
    private UserExtensionMapper userExtensionMapper;

    /**
     * 查询用户新消息个数
     * @param userId
     * @return
     */
    public int queryUserExtensionCount(String userId) {
        if(StringUtils.isBlank(userId)){
            ExceptionCast.cast(MarketingCode.DATA_ERROR);
        }
        UserExtension userExtension = new UserExtension();
        userExtension.setUserId(userId);
        userExtension.setResolve(false);//查询新消息个数，则该字段值应为0
        return this.userExtensionMapper.selectCount(userExtension);

    }

    /**
     * 查询用户站内信通知列表
     * @param userId
     * @return
     */
    public QueryResult queryUserExtensions(String userId) {
        Example example = new Example(UserExtension.class);
        Example.Criteria criteria = example.createCriteria();
        //条件不为空，则添加查询条件
        if(StringUtils.isNotBlank(userId)){
            criteria.andEqualTo("userId",userId);
        }
        criteria.andNotBetween("mode", 0,1);
        criteria.andLessThanOrEqualTo("releaseTime", new Date());

        // 添加排序条件

        example.setOrderByClause("release_time" + " " + "desc" );


        //查询
        List<UserExtension> userExtensions = this.userExtensionMapper.selectByExample(example);

        if(CollectionUtils.isEmpty(userExtensions)){
            ExceptionCast.cast(MarketingCode.SELECT_NULL);
        }

        PageInfo<UserExtension> pageInfo = new PageInfo<>(userExtensions);

        return new QueryResult(userExtensions, pageInfo.getTotal());


    }



    public void deleteExtensionById(String id) {
        try {
            if(StringUtils.isNotBlank(id)){
                this.userExtensionMapper.deleteByPrimaryKey(id);
            }


        } catch (Exception e) {

            ExceptionCast.cast(MarketingCode.DELETE_ERROR);
            e.printStackTrace();
        }
    }


    /**
     * 修改已读状态
     * @param userId
     */
    public void updateResolve(String userId) {
        if(StringUtils.isBlank(userId)){
            ExceptionCast.cast(MarketingCode.DATA_ERROR);
        }
        UserExtension userExtension = new UserExtension();
        userExtension.setUserId(userId);
        List<UserExtension> select = this.userExtensionMapper.select(userExtension);

        for(UserExtension userExtension1: select){
            userExtension1.setResolve(true);
            if(this.userExtensionMapper.updateByPrimaryKey(userExtension1)!=1){
                ExceptionCast.cast(MarketingCode.UPDATE_ERROR);
            }
        }


    }
}
