package com.treehole.psychologist.service;

import com.github.pagehelper.PageHelper;
import com.treehole.framework.domain.psychologist.Consultation;
import com.treehole.framework.domain.psychologist.Profile;
import com.treehole.framework.domain.psychologist.ext.ConsultationExt;
import com.treehole.framework.domain.psychologist.result.PsychologistCode;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.psychologist.dao.ConsultationMapper;
import com.treehole.psychologist.dao.ProfileMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

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

    /**
     * 根据用户id查询用户咨询记录列表
     *
     * @param page    当前页
     * @param size    每页记录数
     * @param user_id 用户id
     * @return
     */
    public QueryResponseResult getConsultationListById(Integer page, Integer size, String user_id) {
        //判断传入的数据是否为空
        if (StringUtils.isBlank(user_id)) {
            ExceptionCast.cast(PsychologistCode.DATA_NULL);
        }
        //设置分页参数
        PageHelper.startPage(page, size);
        //开始查询
        List<Consultation> consultationList = this.consultationMapper.getConsultationListById(user_id);
        if (consultationList == null) {
            ExceptionCast.cast(PsychologistCode.CONSULTATION_NOT_EXIST);
        }
        //将List<Consultation>转成List<ConsultationExt>
        List<ConsultationExt> consultationExtList = new LinkedList<>();
        //循环遍历consultationList集合
        for (Consultation consultations : consultationList) {
            ConsultationExt consultationExt = new ConsultationExt();
            //获取咨询师id
            String psychologist_id = consultations.getPsychologist_id();
            //根据id查询咨询师信息
            Profile profile = this.profileMapper.selectByPrimaryKey(psychologist_id);
            if (profile == null) {
                ExceptionCast.cast(PsychologistCode.PSYCHOLOGIST_NOT_EXIST);
            }
            //开始设置数据
            //咨询记录id
            consultationExt.setConsultation_id(consultations.getConsultation_id());
            //设置咨询师id
            consultationExt.setPsychologist_id(consultations.getPsychologist_id());
            //咨询师姓名
            consultationExt.setPsychologist_name(profile.getName());
            //用户id
            consultationExt.setUser_id(consultations.getUser_id());
            //咨询师给出的建议id
            consultationExt.setSuggestion_id(consultations.getSuggestion_id());
            //咨询时间
            consultationExt.setConsultation_time(consultations.getConsultation_time());
            //将consultationExt对象添加到集合中
            consultationExtList.add(consultationExt);
        }
        QueryResult queryResult = new QueryResult();
        queryResult.setList(consultationExtList);
        queryResult.setTotal(consultationList.size());
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
    }
}
