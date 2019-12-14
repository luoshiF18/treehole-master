package com.treehole.framework.domain.appointment.response;

import com.treehole.framework.model.response.ResultCode;
import lombok.ToString;

@ToString
public enum AppOrderCode implements ResultCode {
    CMS_ADDPAGE_EXISTSNAME(false,24001,"页面名称已存在！"),
    CMS_ADDPAGE1_EXISTSNAME(false,24002,"页面名称已存在！");
    //...

    //操作代码
    boolean success;
    //操作代码
    int code;
    //提示信息
    String message;
    private AppOrderCode(boolean success, int code, String message){
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
