package com.treehole.psychologist.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.treehole.framework.domain.psychologist.Psychologist;
import com.treehole.framework.domain.psychologist.result.PsychologistCode;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.psychologist.dao.PsychologistMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author Helay
 * @date 2019/10/25 9:41
 */
@Service
public class PsychologistService {

    @Autowired
    private PsychologistMapper psychologistMapper;

    /**
     * 分页查询心理咨询师信息
     *
     * @param page 当前页码
     * @param size 每页记录数
     * @return
     */
    public QueryResult findAllPsychologist(Integer page, Integer size) {
        //分页参数
        PageHelper.startPage(page, size);
        //进行查询
        List<Psychologist> psychologists = psychologistMapper.selectAll();
        //判断psychologists集合是否为空
        if (CollectionUtils.isEmpty(psychologists)) {
            //如果数据为空页面，抛出异常，异常内容为查询数据为空
            ExceptionCast.cast(PsychologistCode.DATA_IS_NULL);
        }
        //包装成pageInfo
        PageInfo<Psychologist> pageInfo = new PageInfo<>(psychologists);
        //包装成分页结果集返回
        return new QueryResult(pageInfo.getList(), pageInfo.getTotal());
    }

    /**
     * 根据id查询心理咨询师信息
     *
     * @param id 心理咨询师id
     * @return
     */
    public Psychologist findPsychologistById(String id) {
        Psychologist psychologist = new Psychologist();
        psychologist.setId(id);
        Psychologist one = this.psychologistMapper.selectOne(psychologist);
        //判断结果是否为空
        if (one == null) {
            //如果为空，抛出异常，异常内容为该心理咨询师不存在
            ExceptionCast.cast(PsychologistCode.PSYCHOLOGIST_NOT_EXIST);
        }
        //如果不为空，则直接返回查询到的对象信息
        return one;
    }

    /**
     * 根据id删除心理咨询师信息
     *
     * @param id 心理咨询师id
     * @return
     */
    @Transactional
    public void delPsychologistById(String id) {
        Psychologist psychologist = new Psychologist();
        psychologist.setId(id);
        //先查询要删除的心理咨询师
        Psychologist psychologist1 = this.findPsychologistById(id);
        //判断结果是否为空
        if (psychologist1 == null) {
            //如果为空，抛出异常，异常内容为该心理咨询师不存在
            ExceptionCast.cast(PsychologistCode.PSYCHOLOGIST_NOT_EXIST);
        }
        //删除
        int i = this.psychologistMapper.delete(psychologist);
        //如果受影响行数不为1，则删除失败
        if (i != 1) {
            ExceptionCast.cast(PsychologistCode.DELETE_FAIL);
        }
    }
}
