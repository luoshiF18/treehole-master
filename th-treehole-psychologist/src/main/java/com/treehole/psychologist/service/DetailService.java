package com.treehole.psychologist.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.treehole.framework.domain.psychologist.Detail;
import com.treehole.framework.domain.psychologist.result.PsychologistCode;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.psychologist.dao.DetailMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Helay
 * @date 2019/11/16 9:20
 */
@Service
public class DetailService {

    @Autowired
    private DetailMapper detailMapper;

    /**
     * 根据咨询师id查询详情信息
     *
     * @param psychologist_id 咨询师id
     * @return
     */
    public Detail getDetailById(String psychologist_id) {
        if (StringUtils.isBlank(psychologist_id)) {
            ExceptionCast.cast(PsychologistCode.DATA_NULL);
        }
        Detail detail = this.detailMapper.selectByPrimaryKey(psychologist_id);
        return detail;
    }

    /**
     * 根据咨询师id更新其详情信息
     *
     * @param detail 详情信息
     * @return
     */
    @Transactional
    public ResponseResult updateDetail(Detail detail) {
        //通用mapper中的example用于条件查询，criteria用于添加条件
        Example example = new Example(Detail.class);
        Example.Criteria criteria = example.createCriteria();
        //先查询要更新的咨询师详情信息是否存在
        Detail one = this.getDetailById(detail.getPsychologist_id());
        //判断是否为空
        if (one == null) {
            ExceptionCast.cast(PsychologistCode.PSYCHOLOGIST_NOT_EXIST);
        }
        //根据id更新
        criteria.andEqualTo("psychologist_id", detail.getPsychologist_id());
        int key = this.detailMapper.updateByExample(detail, example);
        if (key != 1) {
            ExceptionCast.cast(PsychologistCode.UPDATE_FAIL);
        }
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 根据咨询师姓名查询咨询师详情信息
     *
     * @param psychologist_name 咨询师姓名
     * @return
     */
    public QueryResponseResult getDetailByName(Integer page, Integer size, String psychologist_name) {
        //通用mapper中的example用于条件查询，criteria用于添加条件
        Example example = new Example(Detail.class);
        Example.Criteria criteria = example.createCriteria();
        //添加姓名查询条件，实现模糊查询
        if (psychologist_name != null) {
            criteria.andLike("psychologist_name", "%" + psychologist_name + "%");
        }
        //分页参数
        PageHelper.startPage(page, size);
        List<Detail> details = this.detailMapper.selectByExample(example);
        //判断details集合是否为空
        if (CollectionUtils.isEmpty(details)) {
            //如果数据为空页面，抛出异常，异常内容为查询数据为空！
            ExceptionCast.cast(PsychologistCode.DATA_IS_NULL);
        }
        //包装成pageInfo
        PageInfo<Detail> pageInfo = new PageInfo<>(details);
        //包装成分页结果集返回
        QueryResult queryResult = new QueryResult();
        queryResult.setList(pageInfo.getList());
        queryResult.setTotal(pageInfo.getTotal());
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
    }

    /**
     * 查询所有咨询师的姓名
     *
     * @return
     */
    public QueryResponseResult getPsychologistNames() {
        List<Detail> psychologists = this.detailMapper.getPsychologists();
        if (CollectionUtils.isEmpty(psychologists)) {
            ExceptionCast.cast(PsychologistCode.DATA_IS_NULL);
        }
        //遍历list中对象的某一字段添加到另一个list
        List<String> nameList = psychologists.stream().map(Detail::getPsychologist_name).collect(Collectors.toList());
        QueryResult queryResult = new QueryResult();
        queryResult.setList(nameList);
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);

    }

    /**
     * 查询所有咨询师的好评数
     *
     * @return
     */
    public QueryResponseResult getPraiseNumber() {
        List<Detail> psychologists = this.detailMapper.getPsychologists();
        if (CollectionUtils.isEmpty(psychologists)) {
            ExceptionCast.cast(PsychologistCode.DATA_IS_NULL);
        }
        //遍历list中对象的某一字段添加到另一个list
        List<String> praiseList = psychologists.stream().map(Detail::getPraise_number).collect(Collectors.toList());
        QueryResult queryResult = new QueryResult();
        queryResult.setList(praiseList);
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);

    }
}
