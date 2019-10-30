package com.treehole.psychologist.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.treehole.framework.domain.psychologist.Profile;
import com.treehole.framework.domain.psychologist.State;
import com.treehole.framework.domain.psychologist.result.PsychologistCode;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.psychologist.dao.StateMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
     * 根据条件分页查询心理咨询师状态信息
     *
     * @param page    当前页码
     * @param size    每页记录数
     * @param id      心理咨询师状态id
     * @param address 心理咨询师详细地址
     * @return
     */
    public QueryResult findStateByPage(Integer page, Integer size, String id, String address) {
        //通用mapper中的example用于条件查询，criteria用于添加条件
        Example example = new Example(State.class);
        Example.Criteria criteria = example.createCriteria();
        //添加搜索条件
        if (StringUtils.isNotBlank(id)) {
            criteria.andEqualTo("id", id);
        }
        //添加地址查询条件,实现模糊查询
        if (address != null) {
            criteria.andLike("address", "%" + address + "%");
        }
        //添加分页条件
        PageHelper.startPage(page, size);
        //执行查询
        List<State> states = this.stateMapper.selectByExample(example);
        //判断states集合是否为空
        if (CollectionUtils.isEmpty(states)) {
            //如果数据为空页面，抛出异常，异常内容为查询数据为空！
            ExceptionCast.cast(PsychologistCode.DATA_IS_NULL);
        }
        //包装成pageInfo
        PageInfo<State> pageInfo = new PageInfo<>(states);
        //包装成分页结果集返回
        return new QueryResult(pageInfo.getList(), pageInfo.getTotal());
    }

    /**
     * 根据id更新心理咨询师状态信息
     *
     * @param state 状态信息
     * @return
     */
    public void updateState(State state) {
        //通用mapper中的example用于条件查询，criteria用于添加条件
        Example example = new Example(Profile.class);
        Example.Criteria criteria = example.createCriteria();
        //先查询要更新的状态信息是否存在
        State one = this.findStateById(state.getId());
        //判断是否为空
        if (one == null) {
            ExceptionCast.cast(PsychologistCode.STATE_NOT_EXIST);
        }
        //根据id更新
        criteria.andEqualTo("id", state.getId());
        int key = this.stateMapper.updateByExample(state, example);
        if (key != 1) {
            ExceptionCast.cast(PsychologistCode.UPDATE_FAIL);
        }
    }

    /**
     * 根据状态id查询状态信息
     *
     * @param id 状态id
     * @return
     */
    public State findStateById(String id) {
        State state = new State();
        state.setId(id);
        State one = this.stateMapper.selectOne(state);
        //判断结果是否为空
        if (one == null) {
            //如果为空，抛出异常，异常内容为该状态信息不存在！
            ExceptionCast.cast(PsychologistCode.STATE_NOT_EXIST);
        }
        return one;
    }
}
