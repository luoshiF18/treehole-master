package com.treehole.member.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.treehole.framework.domain.member.Cards;
import com.treehole.framework.domain.member.FreeGrade;
import com.treehole.framework.domain.member.PayGrade;
import com.treehole.framework.domain.member.resquest.GradeListRequest;
import com.treehole.framework.domain.member.result.MemberCode;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.member.mapper.FreegradeMapper;
import com.treehole.member.myUtil.MyNumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author shanhuijie
 * @Description: 非付费等级信息服务
 * @Date
 */

@Service
@Cacheable(value="MemberFreeGrade")
public class FreegradeService {
    @Autowired
    private FreegradeMapper freegradeMapper;
    @Autowired
    private CardsService cardsService;
    @Autowired
    private CardsVoService cardsVoService;

/*
* 根据rank,id,name查询所有用户等级信息
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
        FreeGrade freeGrade = new FreeGrade();
        //判断不为空字符串
        if(StringUtils.isNotEmpty(gradeListRequest.getGrade_id())){
            freeGrade.setFreegrade_id(gradeListRequest.getGrade_id());
        }
        if(StringUtils.isNotEmpty(gradeListRequest.getGrade_name())){
            freeGrade.setFreegrade_name(gradeListRequest.getGrade_name());
        }
        if(StringUtils.isNotEmpty(String.valueOf(gradeListRequest.getRank()))){
            freeGrade.setRank(gradeListRequest.getRank());
        }

        List<FreeGrade> grades = freegradeMapper.select(freeGrade);
        if (CollectionUtils.isEmpty(grades)) {
            ExceptionCast.cast(MemberCode.DATA_IS_NULL);
        }
        //        解析分页结果
        PageInfo<FreeGrade> pageInfo = new PageInfo<>(grades);
        QueryResult queryResult = new QueryResult();
        queryResult.setList(grades);
        queryResult.setTotal(pageInfo.getTotal());
        return new QueryResponseResult(CommonCode.SUCCESS,queryResult);

    }

    /*
     * 查询所有用户等级信息
     * */
    public QueryResult findAll2(){

        List<FreeGrade> grades = freegradeMapper.selectAll();
        if (CollectionUtils.isEmpty(grades)) {
            ExceptionCast.cast(MemberCode.DATA_IS_NULL);
        }
        //        解析分页结果
        PageInfo<FreeGrade> pageInfo = new PageInfo<>(grades);
        return new QueryResult(grades,pageInfo.getTotal());
    }
    /*通过等级id查询等级对象*/
    public FreeGrade getById(String id) {
        FreeGrade freeGrade = new FreeGrade();
        freeGrade.setFreegrade_id(id);
        FreeGrade grade = freegradeMapper.selectOne(freeGrade);
        if(grade == null){
            ExceptionCast.cast(MemberCode.GRADE_NOT_EXIST);
        }
        return grade;
    }

    /*通过等级名称查询等级对象*/
    public FreeGrade getByName(String name) {
        FreeGrade freeGrade = new FreeGrade();
        freeGrade.setFreegrade_name(name);
        FreeGrade grade = freegradeMapper.selectOne(freeGrade);
        if(grade == null){
            ExceptionCast.cast(MemberCode.GRADE_NAME_NOT_EXIST);
        }
        return grade;
    }


    @Transactional
    @CacheEvict(value="MemberFreeGrade",allEntries=true)
    public void updateGrade(FreeGrade freeGrade) {
        //freeGrade.setFreegrade_id();
        Example example =new Example(FreeGrade.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("freegrade_id",freeGrade.getFreegrade_id());
        //根据id从数据库中查询等级信息

        int upd = freegradeMapper.updateByExampleSelective(freeGrade, example);
        if(upd != 1){
            ExceptionCast.cast(MemberCode.UPDATE_FAIL);
        }
    }

/*根据Freegrade_id删除*/
    @Transactional
    @CacheEvict(value="MemberFreeGrade",allEntries=true)
    public void deleteGrade(String id) {
        //id不为空
        if(org.apache.commons.lang3.StringUtils.isBlank(id)){
            ExceptionCast.cast(MemberCode.DATA_ERROR);
        }
        //等级存在
        if(this.getById(id) != null){
            FreeGrade freeGrade = new FreeGrade();
            freeGrade.setFreegrade_id(id);
            int del = freegradeMapper.delete(freeGrade);
            if(del != 1){
                ExceptionCast.cast(MemberCode.DELETE_FAIL);
            }
        }else{
            ExceptionCast.cast(MemberCode.GRADE_NOT_EXIST);
        }
    }

    @Transactional
    @CacheEvict(value="MemberFreeGrade",allEntries=true)
    public void insert(FreeGrade freeGrade) {
        if(freeGrade == null){
            ExceptionCast.cast(MemberCode.DATA_IS_NULL);
        }
        freeGrade.setFreegrade_id(MyNumberUtils.getUUID());
        if(freeGrade.getPoints_judge() == null){
            freeGrade.setPoints_judge(0);
        }
        if(freeGrade.getConsum_judge() == null){
            int judge = 0;
            freeGrade.setConsum_judge(BigDecimal.valueOf((int)judge));
        }
        //判断rank是否存在
        FreeGrade freeGrade1 = new FreeGrade();
        freeGrade1.setRank(freeGrade.getRank());
        List<FreeGrade> freeGrades = freegradeMapper.select(freeGrade1);
        if(CollectionUtils.isEmpty(freeGrades)){
            int ins = freegradeMapper.insert(freeGrade);
            if(ins != 1){
                ExceptionCast.cast(MemberCode.UPDATE_FAIL);
            }
        }else {
            ExceptionCast.cast(MemberCode.GRADE_RANK_EXIST);
        }


    }

    //注册用户等级变化
    @Transactional
    @CacheEvict(value="MemberFreeGrade",allEntries=true)
    public void gradeChange(String user_id,String freegrade_id,Integer sum){

        FreeGrade freeGrade = new FreeGrade();
        freeGrade.setFreegrade_id(freegrade_id);
        FreeGrade fg = freegradeMapper.selectOne(freeGrade);

        FreeGrade fg2 = new FreeGrade();
        fg2.setRank(fg.getRank()+1);

        FreeGrade fg1 = freegradeMapper.selectOne(fg2);

        Cards card1= cardsService.findCardsByUserId(user_id);

        card1.setPoints_sum(sum);
        //card1.setPoints_sum(sum);
        //bigdimical比较大小 1大于 0等于 -1小于
        int concum = (card1.getConsum_all()).compareTo(fg1.getConsum_judge());
        //积分比较  总记录积分的比较
        if(sum>fg1.getPoints_judge()||sum==fg1.getPoints_judge()){
            //总消费额比较
            if(concum ==1||concum ==0){
                //根据user_id获取cards对象
                //card1.setPoints_sum(sum);
                card1.setFreegrade_id(fg1.getFreegrade_id());
                /*//通过card等级id 获取freegrade对象，再获取对象rank值
                FreeGrade fgbyId = this.getById(card1.getFreegrade_id());
                //获取rank+1的gradeid值
                FreeGrade freeGrade1 = new FreeGrade();
                freeGrade1.setRank(fgbyId.getRank()+1);
                FreeGrade freeGrade2 = freegradeMapper.selectOne(freeGrade1);
                card1.setFreegrade_id(freeGrade2.getFreegrade_id());*/
                cardsService.updateCard(card1);
            }
        }

    }
}

