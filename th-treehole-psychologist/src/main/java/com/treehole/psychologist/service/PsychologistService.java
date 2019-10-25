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
            //如果数据为空页面，抛出异常，异常内容就是查询数据为空
            ExceptionCast.cast(PsychologistCode.DATA_IS_NULL);
        }
        // 包装成pageInfo
        PageInfo<Psychologist> pageInfo = new PageInfo<>(psychologists);
        // 包装成分页结果集返回
        return new QueryResult(pageInfo.getList(), pageInfo.getTotal());
    }
}
