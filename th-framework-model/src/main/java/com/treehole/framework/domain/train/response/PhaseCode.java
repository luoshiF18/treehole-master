package com.treehole.framework.domain.train.response;

import com.treehole.framework.model.response.ResultCode;

public enum  PhaseCode implements ResultCode {

    PHASEFULL(false,10005,"今年期数已满");

    //操作是否成功
    boolean success;
    //操作代码
    int code;
    //提示信息
    String message;
    private PhaseCode(boolean success,int code, String message){
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
