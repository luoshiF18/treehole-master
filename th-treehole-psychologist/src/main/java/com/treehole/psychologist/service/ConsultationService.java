package com.treehole.psychologist.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.treehole.framework.domain.member.User;
import com.treehole.framework.domain.psychologist.Consultation;
import com.treehole.framework.domain.psychologist.Profile;
import com.treehole.framework.domain.psychologist.ext.ConsultationExt;
import com.treehole.framework.domain.psychologist.result.PsychologistCode;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.psychologist.client.UserClient;
import com.treehole.psychologist.dao.ConsultationMapper;
import com.treehole.psychologist.dao.ProfileMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Helay
 * @date 2019/11/19 10:46
 */
@Service
public class ConsultationService {

    @Autowired
    private ConsultationMapper consultationMapper;

    @Autowired
    private ProfileMapper profileMapper;

    @Autowired
    private UserClient userClient;

    /**
     * 根据用户id查询用户咨询记录列表
     *
     * @param page    当前页
     * @param size    每页记录数
     * @param user_id 用户id
     * @return
     */
    public QueryResponseResult getConsultationsByUser(Integer page, Integer size, String user_id) {
        //判断传入的数据是否为空
        if (StringUtils.isBlank(user_id)) {
            ExceptionCast.cast(PsychologistCode.DATA_NULL);
        }
        //设置分页参数
        PageHelper.startPage(page, size);
        //开始查询，获取consultation集合
        List<Consultation> consultations = this.consultationMapper.getConsultationsByUser(user_id);
        //判断集合是否为空
        if (CollectionUtils.isEmpty(consultations)) {
            ExceptionCast.cast(PsychologistCode.CONSULTATION_NOT_EXIST);
        }
        PageInfo<Consultation> pageInfo = new PageInfo<>(consultations);
        //遍历consultations，将consultations集合转换为consultationExts集合
        List<ConsultationExt> consultationExts = consultations.stream().map(consultation -> {
            //创建Consultation扩展类实例对象
            ConsultationExt consultationExt = new ConsultationExt();
            BeanUtils.copyProperties(consultation, consultationExt);  //copy共同属性的值到新的对象
            //设置扩展字段数据
            Profile psy = this.profileMapper.selectByPrimaryKey(consultation.getPsychologist_id());
            if (psy == null) {
                ExceptionCast.cast(PsychologistCode.PSYCHOLOGIST_NOT_EXIST);
            }
            consultationExt.setPsychologist_name(psy.getName());//咨询师姓名
            consultationExt.setQualification(psy.getQualification());//咨询师资质
            return consultationExt;
        }).collect(Collectors.toList());
        QueryResult queryResult = new QueryResult();
        queryResult.setList(consultationExts);
        queryResult.setTotal(pageInfo.getTotal());
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
    }

    /**
     * 查询所有用户咨询记录
     *
     * @param page 当前页
     * @param size 每页记录数
     * @return
     */
    public QueryResponseResult getAllConsultations(Integer page, Integer size) {
        //设置分页参数
        PageHelper.startPage(page, size);
        //查询数据库，获取consultation集合
        List<Consultation> consultations = this.consultationMapper.getAllConsultations();
        PageInfo<Consultation> pageInfo = new PageInfo<>(consultations);
        //判断集合是否为空
        if (CollectionUtils.isEmpty(consultations)) {
            ExceptionCast.cast(PsychologistCode.COMMENT_NOT_EXIST);
        }
        //consultation集合转换为consultationExt集合
        List<ConsultationExt> consultationExts = consultations.stream().map(consultation -> {
            ConsultationExt consultationExt = new ConsultationExt();
            BeanUtils.copyProperties(consultation, consultationExt);//copy共同属性的值到新的对象
            //查询心理咨询师信息
            Profile psy = this.profileMapper.selectByPrimaryKey(consultation.getPsychologist_id());
            if (psy == null) {
                ExceptionCast.cast(PsychologistCode.PSYCHOLOGIST_NOT_EXIST);
            }
            //通过用户id查询用户信息
            User user = this.userClient.getUserById(consultation.getUser_id());
            if (user == null) {
                ExceptionCast.cast(PsychologistCode.USER_NOT_EXIST);
            }
            //设置扩展字段数据
            consultationExt.setPsychologist_name(psy.getName());
            consultationExt.setUser_nickname(user.getUser_nickname());
            return consultationExt;
        }).collect(Collectors.toList());
        QueryResult queryResult = new QueryResult();
        queryResult.setList(consultationExts);
        queryResult.setTotal(pageInfo.getTotal());
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
    }

    /**
     * @param page            当前页
     * @param size            每页记录数
     * @param psychologist_id 咨询师id
     * @return
     */
    public QueryResponseResult getConsultationsByPsy(Integer page, Integer size, String psychologist_id) {
        if (StringUtils.isBlank(psychologist_id)) {
            ExceptionCast.cast(PsychologistCode.DATA_NULL);
        }
        PageHelper.startPage(page, size);
        List<Consultation> consultations = this.consultationMapper.getConsultationsByPsy(psychologist_id);
        if (CollectionUtils.isEmpty(consultations)) {
            ExceptionCast.cast(PsychologistCode.CONSULTATION_NOT_EXIST);
        }
        PageInfo<Consultation> pageInfo = new PageInfo<>(consultations);
        List<ConsultationExt> consultationExts = consultations.stream().map(consultation -> {
            ConsultationExt consultationExt = new ConsultationExt();
            BeanUtils.copyProperties(consultation, consultationExt);
            Profile psy = this.profileMapper.selectByPrimaryKey(consultation.getPsychologist_id());
            if (psy == null) {
                ExceptionCast.cast(PsychologistCode.PSYCHOLOGIST_NOT_EXIST);
            }
            User user = this.userClient.getUserById(consultation.getUser_id());
            if (user == null) {
                ExceptionCast.cast(PsychologistCode.USER_NOT_EXIST);
            }
            consultationExt.setPsychologist_name(psy.getName());
            consultationExt.setUser_nickname(user.getUser_nickname());
            return consultationExt;
        }).collect(Collectors.toList());
        QueryResult queryResult = new QueryResult();
        queryResult.setList(consultationExts);
        queryResult.setTotal(pageInfo.getTotal());
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
    }

    /**
     * 根据咨询记录id删除咨询记录
     *
     * @param consultation_id 咨询记录id
     * @return
     */
    @Transactional
    public ResponseResult delConsultationById(String consultation_id) {
        if (StringUtils.isBlank(consultation_id)) {
            ExceptionCast.cast(PsychologistCode.DATA_NULL);
        }
        int i = this.consultationMapper.deleteByPrimaryKey(consultation_id);
        if (i != 1) {
            ExceptionCast.cast(PsychologistCode.DELETE_FAIL);
        }
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 添加一条咨询记录
     *
     * @param consultation 咨询记录
     * @return
     */
    public ResponseResult addConsultation(Consultation consultation) {
        if (consultation == null) {
            ExceptionCast.cast(PsychologistCode.DATA_NULL);
        }
        int i = this.consultationMapper.insert(consultation);
        if (i != 1) {
            ExceptionCast.cast(PsychologistCode.INSERT_FAIL);
        }
        return new ResponseResult(CommonCode.SUCCESS);
    }
}
