package com.treehole.member.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.treehole.framework.domain.member.PayGrade;
import com.treehole.framework.domain.member.User;
import com.treehole.framework.domain.member.resquest.GradeListRequest;
import com.treehole.framework.domain.member.resquest.UserListRequest;
import com.treehole.framework.domain.member.result.MemberCode;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.member.mapper.PaygradeMapper;
import com.treehole.member.myUtil.MyNumberUtils;
import org.apache.commons.lang.StringUtils;
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
    /*
     * 根据rank,id,name查询所有付费会员等级信息
     * */
    public QueryResult findAll(Integer page,
                               Integer size,
                               GradeListRequest gradeListRequest) {
        //        分页
        PageHelper.startPage(page, size);

        //判断请求条件的合法性
        if (gradeListRequest == null){
            gradeListRequest = new GradeListRequest();
        }
        PayGrade payGrade = new PayGrade();
        //判断不为空字符串
        if(org.apache.commons.lang3.StringUtils.isNotEmpty(gradeListRequest.getGrade_id())){
            payGrade.setPaygrade_id(gradeListRequest.getGrade_id());
        }
        if(org.apache.commons.lang3.StringUtils.isNotEmpty(gradeListRequest.getGrade_name())){
            payGrade.setPaygrade_name(gradeListRequest.getGrade_name());
        }
        if(org.apache.commons.lang3.StringUtils.isNotEmpty(String.valueOf(gradeListRequest.getRank()))){
            payGrade.setRank(gradeListRequest.getRank());
        }


        List<PayGrade> grades = paygradeMapper.select(payGrade);
        if (CollectionUtils.isEmpty(grades)) {
            ExceptionCast.cast(MemberCode.DATA_IS_NULL);
        }
        //        解析分页结果
        PageInfo<PayGrade> pageInfo = new PageInfo<>(grades);

        return new QueryResult(grades, pageInfo.getTotal());
    }
        /*删除*/
    public void deleteGrade(String id) {
        //id不为空
        if(org.apache.commons.lang3.StringUtils.isBlank(id)){
            ExceptionCast.cast(MemberCode.DATA_ERROR);
        }
        //等级存在
        if(this.getById(id) != null){
            PayGrade payGrade = new PayGrade();
            payGrade.setPaygrade_id(id);
            int del = paygradeMapper.delete(payGrade);
            if(del != 1){
                ExceptionCast.cast(MemberCode.DELETE_FAIL);
            }
        }else{
            ExceptionCast.cast(MemberCode.GRADE_NOT_EXIST);
        }


    }

    public void insert(PayGrade payGrade) {
        if(payGrade == null){
            //抛出异常，非法参数异常。指定异常信息的内容
            ExceptionCast.cast(MemberCode.DATA_IS_NULL);
        }
        payGrade.setPaygrade_id(MyNumberUtils.getUUID());
        if(payGrade.getDiscount() == null ){
            payGrade.setDiscount(1.00);
        }
        //判断rank存在
        PayGrade payGrade1 = new PayGrade();
        payGrade1.setRank(payGrade.getRank());
        List<PayGrade> payGrades = paygradeMapper.select(payGrade1);
        //rank不存在即可插入
        if(CollectionUtils.isEmpty(payGrades)){
            int ins = paygradeMapper.insert(payGrade);
            if(ins != 1){
                ExceptionCast.cast(MemberCode.UPDATE_FAIL);
            }
        }else{
            ExceptionCast.cast(MemberCode.GRADE_RANK_EXIST);
        }
    }

    /*
    *
    * 等级变化*/

}
