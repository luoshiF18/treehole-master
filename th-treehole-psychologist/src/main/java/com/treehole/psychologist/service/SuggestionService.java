package com.treehole.psychologist.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.treehole.framework.domain.psychologist.Suggestion;
import com.treehole.framework.domain.psychologist.result.PsychologistCode;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.psychologist.dao.SuggestionMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * @author Helay
 * @date 2019/11/19 14:51
 */
@Service
public class SuggestionService {

    @Autowired
    private SuggestionMapper suggestionMapper;

    /**
     * 根据建议id查询建议信息
     *
     * @param suggestion_id 建议id
     * @return
     */
    public QueryResponseResult getSuggestionById(Integer page, Integer size, String suggestion_id) {
        Example example = new Example(Suggestion.class);
        Example.Criteria criteria = example.createCriteria();
        //按照精准查询
        if (StringUtils.isNotBlank(suggestion_id)) {
            criteria.andEqualTo("suggestion_id", suggestion_id);
        }
        //分页参数
        PageHelper.startPage(page, size);
        //查询
        List<Suggestion> suggestions = this.suggestionMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(suggestions)) {
            ExceptionCast.cast(PsychologistCode.DATA_IS_NULL);
        }
        //包装成pageInfo
        PageInfo<Suggestion> pageInfo = new PageInfo<>(suggestions);
        //包装成分页结果集返回
        QueryResult queryResult = new QueryResult();
        queryResult.setList(pageInfo.getList());
        queryResult.setTotal(pageInfo.getTotal());
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
    }

    /**
     * 分页查询所有建议信息
     *
     * @param page 当前页
     * @param size 每页记录数
     * @return
     */
    public QueryResponseResult getAllSuggestions(Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<Suggestion> suggestions = this.suggestionMapper.getAllSuggestions();
        if (CollectionUtils.isEmpty(suggestions)) {
            ExceptionCast.cast(PsychologistCode.DATA_IS_NULL);
        }
        PageInfo<Suggestion> pageInfo = new PageInfo<>(suggestions);
        QueryResult queryResult = new QueryResult();
        queryResult.setList(pageInfo.getList());
        queryResult.setTotal(pageInfo.getTotal());
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
    }

    /**
     * 根据id删除建议信息
     *
     * @param suggestion_id 建议id
     * @return
     */
    @Transactional
    public ResponseResult delSuggestionById(String suggestion_id) {
        if (StringUtils.isBlank(suggestion_id)) {
            ExceptionCast.cast(PsychologistCode.DATA_NULL);
        }
        int i = this.suggestionMapper.deleteByPrimaryKey(suggestion_id);
        if (i != 1) {
            ExceptionCast.cast(PsychologistCode.DELETE_FAIL);
        }
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 添加一条建议信息
     *
     * @param suggestion 建议信息
     * @return
     */
    @Transactional
    public ResponseResult addSuggestion(Suggestion suggestion) {
        if (suggestion == null) {
            ExceptionCast.cast(PsychologistCode.DATA_NULL);
        }
        suggestion.setCreate_time(new Date());
        suggestion.setUpdate_time(suggestion.getCreate_time());
        int i = this.suggestionMapper.insert(suggestion);
        if (i != 1) {
            ExceptionCast.cast(PsychologistCode.INSERT_FAIL);
        }
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 更新建议信息
     *
     * @param suggestion 建议信息
     * @return
     */
    @Transactional
    public ResponseResult updateSuggestion(Suggestion suggestion) {
        Example example = new Example(Suggestion.class);
        Example.Criteria criteria = example.createCriteria();
        Suggestion one = this.suggestionMapper.selectByPrimaryKey(suggestion.getSuggestion_id());
        if (one == null) {
            ExceptionCast.cast(PsychologistCode.DATA_IS_NULL);
        }
        criteria.andEqualTo("suggestion_id", suggestion.getSuggestion_id());
        int i = this.suggestionMapper.updateByExample(suggestion, example);
        if (i != 1) {
            ExceptionCast.cast(PsychologistCode.UPDATE_FAIL);
        }
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 根据主键查询
     *
     * @param suggestion_id 主键
     * @return
     */
    public Suggestion findSuggestionById(String suggestion_id) {
        if (StringUtils.isBlank(suggestion_id)) {
            ExceptionCast.cast(PsychologistCode.DATA_NULL);
        }
        Suggestion suggestion = this.suggestionMapper.selectByPrimaryKey(suggestion_id);
        if (suggestion == null) {
            ExceptionCast.cast(PsychologistCode.DATA_IS_NULL);
        }
        return suggestion;
    }
}
