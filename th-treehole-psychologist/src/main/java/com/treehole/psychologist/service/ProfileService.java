package com.treehole.psychologist.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.treehole.framework.domain.psychologist.Detail;
import com.treehole.framework.domain.psychologist.Profile;
import com.treehole.framework.domain.psychologist.State;
import com.treehole.framework.domain.psychologist.ext.ProfileExt;
import com.treehole.framework.domain.psychologist.result.PsychologistCode;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.psychologist.dao.DetailMapper;
import com.treehole.psychologist.dao.ProfileMapper;
import com.treehole.psychologist.dao.StateMapper;
import com.treehole.psychologist.util.MyUtils;
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
 * @date 2019/10/25 9:41
 */
@Service
public class ProfileService {

    @Autowired
    private ProfileMapper profileMapper;

    @Autowired
    private StateMapper stateMapper;

    @Autowired
    private DetailMapper detailMapper;

    /**
     * 根据条件分页查询心理咨询师简介信息
     *
     * @param page          当前页码
     * @param size          每页记录数
     * @param name          心理咨询师姓名
     * @param sex           心理咨询师性别
     * @param qualification 心理咨询师资质
     * @return
     */
    public QueryResponseResult findAllProfiles(Integer page, Integer size, String name, String sex, String qualification) {
        //通用mapper中的example用于条件查询，criteria用于添加条件
        Example example = new Example(Profile.class);
        Example.Criteria criteria = example.createCriteria();
        //添加姓名查询条件，实现模糊查询
        if (name != null) {
            criteria.andLike("name", "%" + name + "%");
        }
        //添加性别查询条件，实现精准查询
        if (StringUtils.isNotBlank(sex)) {
            criteria.andEqualTo("sex", sex);
        }
        //添加资质查询条件，实现模糊查询
        if (qualification != null) {
            criteria.andLike("qualification", "%" + qualification + "%");
        }
        //分页参数
        PageHelper.startPage(page, size);
        //进行查询
        List<Profile> profiles = this.profileMapper.selectByExample(example);
        //判断profiles集合是否为空
        if (CollectionUtils.isEmpty(profiles)) {
            //如果数据为空页面，抛出异常，异常内容为查询数据为空！
            ExceptionCast.cast(PsychologistCode.DATA_IS_NULL);
        }
        //包装成pageInfo
        PageInfo<Profile> pageInfo = new PageInfo<>(profiles);
        //包装成分页结果集返回
        QueryResult queryResult = new QueryResult();
        queryResult.setList(pageInfo.getList());
        queryResult.setTotal(pageInfo.getTotal());
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
    }

    /**
     * 根据id查询心理咨询师简介信息
     *
     * @param id 心理咨询师id
     * @return
     */
    public Profile findProfileById(String id) {
        Profile profile = new Profile();
        profile.setId(id);
        Profile one = this.profileMapper.selectOne(profile);
        //判断结果是否为空
        if (one == null) {
            //如果为空，抛出异常，异常内容为该心理咨询师不存在！
            ExceptionCast.cast(PsychologistCode.PSYCHOLOGIST_NOT_EXIST);
        }
        //如果不为空，则直接返回查询到的对象信息
        return one;
    }

    /**
     * 根据id删除心理咨询师简介信息
     *
     * @param id 心理咨询师id
     * @return
     */
    @Transactional
    public ResponseResult delProfileById(String id) {
        //首先判断id是否为空
        if (StringUtils.isBlank(id)) {
            //如果id为空，抛出异常，异常内容为前台数据有误！
            ExceptionCast.cast(PsychologistCode.DATA_ERROR);
        }
        //先查询要删除的心理咨询师
        Profile profile = this.findProfileById(id);
        //判断查询结果是否为空
        if (profile == null) {
            //如果为空，抛出异常，异常内容为该心理咨询师不存在！
            ExceptionCast.cast(PsychologistCode.PSYCHOLOGIST_NOT_EXIST);
        }
        //删除
        int i = this.profileMapper.delete(profile);
        //判断受影响行数是否为1
        if (i != 1) {
            //如果受影响行数不为1，抛出异常，异常内容为删除失败！
            ExceptionCast.cast(PsychologistCode.DELETE_FAIL);
        }
        //删除成功，响应操作成功状态码
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 添加心理咨询师信息（包括简介、状态、详情）
     *
     * @param profileExt 心理咨询师信息扩展类
     * @return
     */
    @Transactional
    public ResponseResult addProfileExt(ProfileExt profileExt) {
        //判断传入的数据是否为空
        if (profileExt == null) {
            //如果为空，抛出异常，异常信息为插入数据为空!
            ExceptionCast.cast(PsychologistCode.INSERT_DATA_NULL);
        }
        //新增简介信息
        String id = MyUtils.getId();//生成32位随机id
        profileExt.setId(id);
        profileExt.setCreate_time(new Date());
        profileExt.setUpdate_time(profileExt.getCreate_time());
        int i1 = this.profileMapper.insertSelective(profileExt);
        if (i1 != 1) {
            ExceptionCast.cast(PsychologistCode.INSERT_FAIL);
        }
        //新增状态信息
        State state = profileExt.getState();
        state.setId(profileExt.getId());
        state.setName(profileExt.getName());
        state.setCreate_time(profileExt.getCreate_time());
        state.setUpdate_time(profileExt.getCreate_time());
        int i2 = this.stateMapper.insertSelective(state);
        if (i2 != 1) {
            ExceptionCast.cast(PsychologistCode.INSERT_FAIL);
        }
        //新增详情信息
        Detail detail = profileExt.getDetail();
        detail.setPsychologist_id(profileExt.getId());
        detail.setPsychologist_name(profileExt.getName());
        detail.setCreate_time(profileExt.getCreate_time());
        detail.setUpdate_time(profileExt.getCreate_time());
        int i3 = this.detailMapper.insertSelective(detail);
        if (i3 != 1) {
            ExceptionCast.cast(PsychologistCode.INSERT_FAIL);
        }
        //添加成功，响应成功状态码
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 根据id更新心理咨询师简介信息
     *
     * @param profile 心理咨询师简介
     * @return
     */
    @Transactional
    public ResponseResult updateProfile(Profile profile) {
        //通用mapper中的example用于条件查询，criteria用于添加条件
        Example example = new Example(Profile.class);
        Example.Criteria criteria = example.createCriteria();
        //先查询要更新的心理咨询师是否存在
        Profile one = this.findProfileById(profile.getId());
        //判断是否为空
        if (one == null) {
            ExceptionCast.cast(PsychologistCode.PSYCHOLOGIST_NOT_EXIST);
        }
        //根据id更新
        criteria.andEqualTo("id", profile.getId());
        int key = this.profileMapper.updateByExample(profile, example);
        if (key != 1) {
            ExceptionCast.cast(PsychologistCode.UPDATE_FAIL);
        }
        //更新成功，响应成功状态码
        return new ResponseResult(CommonCode.SUCCESS);
    }

}
