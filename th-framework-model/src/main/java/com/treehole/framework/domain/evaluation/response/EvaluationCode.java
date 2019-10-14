package com.treehole.framework.domain.evaluation.response;

import com.treehole.framework.model.response.ResultCode;
import lombok.ToString;

/**
 * 异常代码
 *
 * @auther: Yan Hao
 * @date: 2019/10/14
 */
@ToString
public enum EvaluationCode implements ResultCode {




    SCALE_INSERT_FAIL(false, 401, "插入数据库失败！"),
    SCALE_DATA_NULL(false, 401, "量表数据为空！")

    ;
    //操作代码
    boolean success;
    //操作代码
    int code;
    //提示信息
    String message;

    private EvaluationCode(boolean success, int code, String message) {
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
