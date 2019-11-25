package com.treehole.psychologist.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.treehole.framework.domain.psychologist.Detail;
import com.treehole.framework.domain.psychologist.result.PsychologistCode;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.psychologist.dao.DetailMapper;
import com.treehole.psychologist.dao.DetailsMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author Helay
 * @date 2019/11/16 9:20
 */
@Service
public class DetailService {

    @Autowired
    private DetailsMapper detailsMapper;

    @Autowired
    private DetailMapper detailMapper;

    /**
     * 按照id自增分页查询所有咨询师详情信息
     *
     * @param page 当前页
     * @param size 每页记录数
     * @return
     */
    public QueryResult<Detail> getAllByPage(Integer page, Integer size) {
        //分页参数
        PageHelper.startPage(page, size);
        List<Detail> all = this.detailsMapper.getAllByPage();
        if (CollectionUtils.isEmpty(all)) {
            ExceptionCast.cast(PsychologistCode.DATA_IS_NULL);
        }
        //包装成pageInfo
        PageInfo<Detail> pageInfo = new PageInfo<>(all);
        //包装成分页结果集返回
        List<Detail> list = pageInfo.getList();
        long total = pageInfo.getTotal();
        return new QueryResult(list, total);
    }

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
     * 根据咨询师id删除咨询师详情信息
     *
     * @param psychologist_id
     */
    @Transactional
    public void delDetailById(String psychologist_id) {
        if (StringUtils.isBlank(psychologist_id)) {
            ExceptionCast.cast(PsychologistCode.DATA_NULL);
        }
        Detail detail = this.getDetailById(psychologist_id);
        if (detail == null) {
            ExceptionCast.cast(PsychologistCode.PSYCHOLOGIST_NOT_EXIST);
        }
        int i = this.detailMapper.deleteByPrimaryKey(psychologist_id);
        if (i != 1) {
            ExceptionCast.cast(PsychologistCode.DELETE_FAIL);
        }
    }

    /**
     * 添加咨询师详情信息
     *
     * @param detail 详情信息
     * @return
     */
    @Transactional
    public void addDetail(Detail detail) {
        if (detail == null) {
            ExceptionCast.cast(PsychologistCode.INSERT_DATA_NULL);
        }
        int i = this.detailMapper.insert(detail);
        if (i != 1) {
            ExceptionCast.cast(PsychologistCode.INSERT_FAIL);
        }
    }

    /**
     * 根据咨询师id更新其详情信息
     *
     * @param detail 详情信息
     * @return
     */
    @Transactional
    public void updateDetail(Detail detail) {
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
    }

    /**
     * 根据咨询师姓名查询咨询师详情信息
     *
     * @param psychologist_name 咨询师姓名
     * @return
     */
    public QueryResult getDetailByName(Integer page, Integer size, String psychologist_name) {
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
        return new QueryResult(pageInfo.getList(), pageInfo.getTotal());
    }
}
