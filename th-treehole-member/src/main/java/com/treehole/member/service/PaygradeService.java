package com.treehole.member.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.treehole.framework.domain.member.Cards;
import com.treehole.framework.domain.member.PayGrade;
import com.treehole.framework.domain.member.resquest.GradeListRequest;
import com.treehole.framework.domain.member.result.MemberCode;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.member.mapper.PaygradeMapper;
import com.treehole.member.myUtil.AddDateUtil;
import com.treehole.member.myUtil.MyNumberUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * @author shanhuijie
 * @Description: 付费等级信息服务
 * @Date
 */

@Service
@Cacheable(value="MemberPayGrade")
public class PaygradeService {

    @Autowired
    private PaygradeMapper paygradeMapper;

    @Autowired
    private CardsService cardsService;

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
    /*通过等级名称查询等级对象*/
    public PayGrade getByName(String name) {
        PayGrade payGrade = new PayGrade();
        payGrade.setPaygrade_name(name);
        PayGrade grade = paygradeMapper.selectOne(payGrade);
        if(grade == null){
            ExceptionCast.cast(MemberCode.GRADE_NAME_NOT_EXIST);
        }
        return grade;
    }
    //修改等级信息
    @Transactional
    @CacheEvict(value="MemberPayGrade",allEntries=true)
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
    public QueryResponseResult findAll1(Integer page,
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
        QueryResult queryResult = new QueryResult();
        queryResult.setList(grades);
        queryResult.setTotal(pageInfo.getTotal());
        return new QueryResponseResult(CommonCode.SUCCESS,queryResult);
    }

    /*
    * 查询所有VIP等级信息
    * */
    public QueryResult findAll2(){

        List<PayGrade> grades = paygradeMapper.selectAll();
        if (CollectionUtils.isEmpty(grades)) {
            ExceptionCast.cast(MemberCode.DATA_IS_NULL);
        }
        //        解析分页结果
        PageInfo<PayGrade> pageInfo = new PageInfo<>(grades);
        return new QueryResult(grades,pageInfo.getTotal());
    }

        /*删除*/
    @Transactional
    @CacheEvict(value="MemberPayGrade",allEntries=true)
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

    @Transactional
    @CacheEvict(value="MemberPayGrade",allEntries=true)
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
    * 等级变化
    * */
    @Transactional
    @CacheEvict(value="MemberPayGrade",allEntries=true)
    public void gradeChange(String user_id,String  paygrade_id){
        //通过paygrade_id得到等级对象
        PayGrade payGrade1 = this.getById(paygrade_id);
        Integer day =payGrade1.getCard_legality(); //天数
        long day1 = day.longValue();
        Cards cards = cardsService.findCardsByUserId(user_id);
        Date dateStart;
        Date end = new Date();
        if(cards.getPaygrade_start() == null){
            dateStart = new Date();
            cards.setPaygrade_start(dateStart);
        }else{
            dateStart = cards.getPaygrade_end();  //续费情况
        }
        try {
            Date dateEnd = AddDateUtil.addDate(dateStart,day1);
            end = dateEnd;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        cards.setPaygrade_end(end);

        //修改cards
        cardsService.updateCard(cards);

    }

}
