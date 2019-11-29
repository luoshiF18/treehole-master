package com.treehole.psychologist.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.treehole.framework.domain.psychologist.State;
import com.treehole.framework.domain.psychologist.result.PsychologistCode;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.psychologist.dao.StateMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author Helay
 * @date 2019/10/29 19:13
 */
@Service
public class StateService {

    @Autowired
    private StateMapper stateMapper;

    /**
     * 查询心理咨询师状态信息列表
     *
     * @return
     */
    public QueryResponseResult findStateList(Integer page, Integer size, String name, String price, String free) {
        //通用mapper中的example用于条件查询，criteria用于添加条件
        Example example = new Example(State.class);
        Example.Criteria criteria = example.createCriteria();
        //添加姓名查询条件，实现模糊查询
        if (name != null) {
            criteria.andLike("name", "%" + name + "%");
        }
        //添加价格查询条件，实现模糊查询
        if (price != null) {
            criteria.andLike("price", "%" + price + "%");
        }
        //添加工作状态查询条件，实现精准查询
        if (StringUtils.isNotBlank(free)) {
            criteria.andEqualTo("free", free);
        }
        //分页参数
        PageHelper.startPage(page, size);
        List<State> list = this.stateMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(list)) {
            ExceptionCast.cast(PsychologistCode.DATA_IS_NULL);
        }
        //包装成pageInfo
        PageInfo<State> pageInfo = new PageInfo<>(list);
        //包装成分页结果集返回
        QueryResult queryResult = new QueryResult();
        queryResult.setList(pageInfo.getList());
        queryResult.setTotal(pageInfo.getTotal());
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
    }

    /**
     * 根据id查询心理咨询师状态信息
     *
     * @param id 心理咨询师id
     * @return
     */
    public State findStateById(String id) {
        State state = new State();
        state.setId(id);
        State one = this.stateMapper.selectOne(state);
        if (one == null) {
            ExceptionCast.cast(PsychologistCode.STATE_NOT_EXIST);
        }
        return one;
    }

    /**
     * 根据id删除心理咨询师状态信息
     *
     * @param id 心理咨询师id
     * @return
     */
    @Transactional
    public ResponseResult delStateById(String id) {
        if (StringUtils.isBlank(id)) {
            ExceptionCast.cast(PsychologistCode.DATA_ERROR);
        }
        State state = new State();
        state.setId(id);
        State state1 = this.findStateById(id);
        if (state1 == null) {
            ExceptionCast.cast(PsychologistCode.STATE_NOT_EXIST);
        }
        int i = this.stateMapper.delete(state);
        if (i != 1) {
            ExceptionCast.cast(PsychologistCode.DELETE_FAIL);
        }
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 添加心理咨询师状态信息
     *
     * @param state 心理咨询师状态信息
     * @return
     */
    @Transactional
    public ResponseResult addState(State state) {
        if (state == null) {
            ExceptionCast.cast(PsychologistCode.INSERT_DATA_NULL);
        }
        int i = this.stateMapper.insert(state);
        if (i != 1) {
            ExceptionCast.cast(PsychologistCode.INSERT_FAIL);
        }
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 根据id更新心理咨询师状态信息
     *
     * @param state 心理咨询师状态
     * @return
     */
    @Transactional
    public ResponseResult updateState(State state) {
        Example example = new Example(State.class);
        Example.Criteria criteria = example.createCriteria();
        State one = this.findStateById(state.getId());
        if (one == null) {
            ExceptionCast.cast(PsychologistCode.STATE_NOT_EXIST);
        }
        criteria.andEqualTo("id", state.getId());
        int key = this.stateMapper.updateByExample(state, example);
        if (key != 1) {
            ExceptionCast.cast(PsychologistCode.UPDATE_FAIL);
        }
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 按照id自增查询所有简介信息
     *
     * @param page 当前页
     * @param size 每页记录数
     * @return
     */
    public QueryResponseResult getAllStates(Integer page, Integer size) {
        //分页参数
        PageHelper.startPage(page, size);
        List<State> states = this.stateMapper.getAllStates();
        if (CollectionUtils.isEmpty(states)) {
            //如果数据为空页面，抛出异常，异常内容为查询数据为空！
            ExceptionCast.cast(PsychologistCode.DATA_IS_NULL);
        }
        //包装成pageInfo
        PageInfo<State> pageInfo = new PageInfo<>(states);
        //包装成分页结果集返回
        QueryResult queryResult = new QueryResult();
        queryResult.setList(pageInfo.getList());
        queryResult.setTotal(pageInfo.getTotal());
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
    }
}
