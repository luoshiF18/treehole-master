package com.treehole.framework.domain.psychologist.ext;

import com.treehole.framework.domain.psychologist.Suggestion;
import lombok.Data;

/**
 * 建议信息扩展类
 *
 * @author Helay
 * @date 2019/11/19 10:25
 */
@Data
public class SuggestionExt extends Suggestion {

    //咨询师姓名
    private String psychologist_name;

    //心理咨询师性别
    private String psychologist_sex;

    //心理咨询师资质
    private String psychologist_qualification;

    //心理咨询师工作室
    private String psychologist_studio;

    //心理咨询师联系方式
    private String psychologist_phone;

}
