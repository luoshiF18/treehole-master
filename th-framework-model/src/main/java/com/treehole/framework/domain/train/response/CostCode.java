package com.treehole.framework.domain.train.response;

import com.treehole.framework.model.response.ResultCode;
import lombok.ToString;


@ToString
public enum CostCode implements ResultCode{

    NoPAYMENTISREQUIRED(false,10004,"学费已经交齐"),
    MOREMONEY(false,10005,"钱交多了");
    //操作是否成功
    boolean success;
    //操作代码
    int code;
    //提示信息
    String message;
    private CostCode(boolean success,int code, String message){
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