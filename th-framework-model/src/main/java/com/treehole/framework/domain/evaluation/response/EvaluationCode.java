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


    DATA_ERROR(false, 401, "前台数据有误！"),
    DESC_DATA_ERROR(false, 401, "描述信息有误，请检查！"),
    SELECT_NULL(false, 401, "查询失败！"),
    INSERT_FAIL(false, 401, "插入数据库失败！"),
    SCALE_DATA_NULL(false, 401, "量表数据为空！"),
    SCALE_FIND_ERROR(false, 401, "没有找到该量表！"),
    TEST_ERROR(false,401,"抱歉，测评出现问题！"),
    GET_QUESTION_ERROR(false,401,"抱歉，获取题目失败！")



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
