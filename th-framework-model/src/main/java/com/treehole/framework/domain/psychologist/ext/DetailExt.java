package com.treehole.framework.domain.psychologist.ext;

import lombok.Data;

/**
 * @author Helay
 * @date 2019/12/13 12:09
 */
@Data
public class DetailExt {

    //咨询师id profile表
    private String id;

    //咨询师姓名 profile表
    private String name;

    //咨询师性别 profile表
    private String sex;

    //咨询师头像 profile表
    private String image;

    //咨询师联系方式 profile表
    private String phone;

    //咨询师自我介绍 profile表
    private String introduction;

    //咨询师资质 profile表
    private String qualification;

    //心理咨询师擅长领域 profile表
    private String proficiency;

    //心理咨询师详细地址 state表
    private String address;//详细地址

    //咨询师服务价格 state表
    private String price;

    //咨询师获得好评数 detail表
    private Integer praise_number;
}
