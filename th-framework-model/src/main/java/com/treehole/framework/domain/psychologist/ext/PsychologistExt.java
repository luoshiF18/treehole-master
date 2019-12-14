package com.treehole.framework.domain.psychologist.ext;

import lombok.Data;

/**
 * @author Helay
 * @date 2019/12/11 16:50
 */
@Data
public class PsychologistExt {

    //咨询师id
    private String id;

    //心理咨询师姓名
    private String name;

    //心理咨询师性别
    private String sex;

    //心理咨询师年龄
    private String age;

    //心理咨询师所在城市
    private String region;

    //心理咨询师工作室
    private String studio;

    //心理咨询师好评数
    private String praise_number;

    //心理咨询师入住平台时长
    private String platform_year;

    //心理咨询师收费标准
    private String price;

}
