package com.treehole.framework.domain.archives.response;

import com.treehole.framework.model.response.ResultCode;
import lombok.ToString;

/**
 * @author 任志强
 * @version 1.0
 * @date 2019/10/22 9:10
 * @description: 测评档案管理系统的状态码
 */
@ToString
public enum ArchivesCode implements ResultCode {
    ARCHIVES_RESULT_ISNULL(false,31001,"档案为空！"),
    ARCHIVES_RESULTVO_ISNULL(false,31002,"个体报告为空！"),
    ARCHIVES_PERSONRESULTVO_ISNULL(false,31003,"个体档案为空！"),

    ;

    //操作代码
    boolean success;
    //操作代码
    int code;
    //提示信息
    String message;
    private ArchivesCode(boolean success, int code, String message){
        this.success = success;
        this.code = code;
        this.message = message;
    }

    @Override
    public boolean success() {
        return success;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }
}
