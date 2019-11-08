package com.treehole.member.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.treehole.framework.domain.member.FreeGrade;
import com.treehole.framework.domain.member.PayGrade;
import com.treehole.framework.domain.member.result.MemberCode;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.member.mapper.FreegradeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author shanhuijie
 * @Description: 非付费等级信息服务
 * @Date
 */

@Service
public class FreegradeService {
    @Autowired
    private FreegradeMapper freegradeMapper;

    public QueryResult findAll(Integer page, Integer size) {
        //        分页
        PageHelper.startPage(page, size);

        List<FreeGrade> grades = freegradeMapper.selectAll();
        if (CollectionUtils.isEmpty(grades)) {
            ExceptionCast.cast(MemberCode.DATA_IS_NULL);
        }
        //        解析分页结果
        PageInfo<FreeGrade> pageInfo = new PageInfo<>(grades);

        return new QueryResult(grades, pageInfo.getTotal());
    }

    public FreeGrade getById(String id) {
        FreeGrade freeGrade = new FreeGrade();
        freeGrade.setFreegrade_id(id);
        FreeGrade grade = freegradeMapper.selectOne(freeGrade);
        if(grade == null){
            ExceptionCast.cast(MemberCode.GRADE_NOT_EXIST);
        }
        return grade;
    }

    public void updateGrade(FreeGrade freeGrade) {
        Example example =new Example(PayGrade.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("freegrade_id",freeGrade.getFreegrade_id());
        //根据id从数据库中查询等级信息

        int upd = freegradeMapper.updateByExampleSelective(freeGrade, example);
        if(upd != 1){
            ExceptionCast.cast(MemberCode.UPDATE_FAIL);
        }
    }


    }

