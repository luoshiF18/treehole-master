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
    DATA_ERROR(false, 401, "前台数据有误!"),
    PSYCHOLOGIST_NOT_EXIST(false, 402, "该心理咨询师不存在！"),
    STATE_NOT_EXIST(false, 403, "该状态信息不存在!"),
    DATA_IS_NULL(false, 404, "查询数据为空!"),
    INSERT_DATA_NULL(false, 405, "插入数据为空!"),
    DELETE_FAIL(false, 406, "删除失败!"),
    INSERT_FAIL(false, 407, "添加失败!"),
    UPDATE_FAIL(false, 408, "更新失败!"),
    DATA_NULL(false, 409, "前台数据为空!"),
    SUGGESTION_NOT_EXIST(false, 410, "该建议信息不存在！"),
    CONSULTATION_NOT_EXIST(false, 411, "该咨询记录不存在！");

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
