package com.treehole.framework.domain.member.result;

import com.treehole.framework.model.response.ResultCode;
import lombok.ToString;

/**
 * @Author: Qbl
 * Created by 19:25 on 2019/11/12.
 * Version: 1.0
 */
@ToString
public enum  AuthCode implements ResultCode {
    UNKNOWN_ERROR(false,-1,"未知错误"),
    AUTH_LOGIN_APPLYTOKEN_FAI(false,-1,"令牌为空！"),
    AUTH_LOGIN_TOKEN_SAVEFAI(false,-1,"用户令牌保存失败！"),
    AUTH_ACCOUNT_NOTEXISTS(false,-1,"用户不存在！"),
    AUTH_CREDENTIAL_ERROR(false,-1,"申请令牌错误"),
    AUTH_USERNAME_NONE(false,-1,"用户名为空！"),
    AUTH_PASSWORD_NONE(false,-1,"密码为空!")
            ;
    //操作代码
    boolean success;
    //操作代码
    int code;
    //提示信息
    String message;

    private AuthCode(boolean success, int code, String message) {
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
