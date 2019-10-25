package com.treehole.framework.domain.psychologist.result;

import com.treehole.framework.model.response.ResultCode;

/**
 * 异常代码列表
 *
 * @author Helay
 * @date 2019/10/25 10:51
 */
public enum PsychologistCode implements ResultCode {

    SUCCESS(false, 200, "成功"),
    DATA_IS_NULL(false, 304, "查询数据为空");


    //操作是否成功,true为成功，false操作失败
    boolean success;
    //操作代码
    int code;
    //提示信息
    String message;

    PsychologistCode(boolean success, int code, String message) {
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
