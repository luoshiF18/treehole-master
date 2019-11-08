package com.treehole.member.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.treehole.framework.domain.member.PayGrade;
import com.treehole.framework.domain.member.User;
import com.treehole.framework.domain.member.result.MemberCode;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.member.mapper.PaygradeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author shanhuijie
 * @Description: 付费等级信息服务
 * @Date
 */

@Service
public class PaygradeService {

    @Autowired
    private PaygradeMapper paygradeMapper;
    //根据id查询等级
    public PayGrade getById(String id){
        PayGrade payGrade = new PayGrade();
        payGrade.setPaygrade_id(id);
        PayGrade grade = paygradeMapper.selectOne(payGrade);
        if(grade == null){
            ExceptionCast.cast(MemberCode.GRADE_NOT_EXIST);
        }
        return grade;

    }
    //修改等级信息
    public void updateGrade(PayGrade payGrade){
        Example example =new Example(PayGrade.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("paygrade_id",payGrade.getPaygrade_id());
        //根据id从数据库中查询等级信息

        int upd = paygradeMapper.updateByExampleSelective(payGrade, example);
        if(upd != 1){
            ExceptionCast.cast(MemberCode.UPDATE_FAIL);
        }

    }

    public QueryResult findAll(Integer page, Integer size) {
        //        分页
        PageHelper.startPage(page, size);

        List<PayGrade> grades = paygradeMapper.selectAll();
        if (CollectionUtils.isEmpty(grades)) {
            ExceptionCast.cast(MemberCode.DATA_IS_NULL);
        }
        //        解析分页结果
        PageInfo<PayGrade> pageInfo = new PageInfo<>(grades);

        return new QueryResult(grades, pageInfo.getTotal());
    }
}
