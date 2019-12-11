package com.treehole.psychologist.service;

import com.treehole.framework.domain.psychologist.Profile;
import com.treehole.framework.domain.psychologist.Suggestion;
import com.treehole.framework.domain.psychologist.ext.SuggestionExt;
import com.treehole.framework.domain.psychologist.result.PsychologistCode;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.psychologist.dao.ProfileMapper;
import com.treehole.psychologist.dao.SuggestionMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Helay
 * @date 2019/11/19 14:51
 */
@Service
public class SuggestionService {

    @Autowired
    private SuggestionMapper suggestionMapper;

    @Autowired
    private ProfileMapper profileMapper;

    public SuggestionExt getSuggestionExtById(String suggestion_id) {
        //判断前台传入数据是否为空
        if (StringUtils.isBlank(suggestion_id)) {
            ExceptionCast.cast(PsychologistCode.DATA_NULL);
        }
        //获取建议信息
        Suggestion suggestion = this.suggestionMapper.selectByPrimaryKey(suggestion_id);
        if (suggestion == null) {
            ExceptionCast.cast(PsychologistCode.SUGGESTION_NOT_EXIST);
        }
        //获取咨询师id
        String psychologist_id = suggestion.getPsychologist_id();
        //通过id获取咨询师个人信息
        Profile profile = this.profileMapper.selectByPrimaryKey(psychologist_id);
        //开始设置数据
        SuggestionExt suggestionExt = new SuggestionExt();
        //咨询师给出的建议id
        suggestionExt.setSuggestion_id(suggestion.getSuggestion_id());
        //咨询师id
        suggestionExt.setPsychologist_id(suggestion.getPsychologist_id());
        //咨询师姓名
        suggestionExt.setPsychologist_name(profile.getName());
        //咨询师性别
        suggestionExt.setPsychologist_sex(profile.getSex());
        //咨询师资质
        suggestionExt.setPsychologist_qualification(profile.getQualification());
        //咨询师工作室
        suggestionExt.setPsychologist_studio(profile.getStudio());
        //咨询师联系方式
        suggestionExt.setPsychologist_phone(profile.getPhone());
        //病情描述信息
        suggestionExt.setDescription(suggestion.getDescription());
        //咨询师给出的建议信息
        suggestionExt.setSuggestion_info(suggestion.getSuggestion_info());
        //心理治疗描述信息
        suggestionExt.setPsychotherapy(suggestion.getPsychotherapy());
        //物理治疗描述信息
        suggestionExt.setPhysicotherapy(suggestion.getPhysicotherapy());
        //咨询师给出的预警信息
        suggestionExt.setWarning(suggestion.getWarning());
        //该条建议生成时间
        suggestionExt.setCreate_time(suggestion.getCreate_time());
        return suggestionExt;
    }
}
