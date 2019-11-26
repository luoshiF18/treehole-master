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

    WARNING_IS_ERROR(false, 401, "预警信息插入失败！"),
    DESCRIPTION_IS_ERROR(false, 401, "描述信息插入失败！"),
    DATA_ERROR(false, 401, "前台数据有误！"),
    DESC_DATA_ERROR(false, 401, "描述信息有误，请检查！"),
    SELECT_NULL(false, 401, "查询失败！"),
    DATA_MISS(false, 401, "保存失败！信息错误或缺少！"),
    SCORE_ERROR(false, 401, "分数设置有误！请检查！"),
    INSERT_FAIL(false, 401, "插入数据库失败！"),
    TYPE_HAD(false, 401, "已存在该类型！"),
    SCALE_DATA_NULL(false, 401, "量表数据为空！"),
    SCALE_DESC_NULL(false, 401, "描述表id为空！"),
    SCALE_FIND_ERROR(false, 401, "没有找到该量表！"),
    TEST_ERROR(false, 401, "抱歉，测评出现问题！"),
    GET_QUESTION_ERROR(false, 401, "抱歉，获取题目失败！"),
    UPDATE_ERROR(false, 404, "抱歉，更新失败！"),
    DELETE_ERROR(false, 404, "抱歉，删除失败！"),
    SCALE_NAME_SAME(false, 404, "量表名称已存在，请更换！"),
    SKIP_TO_QiD_ERROR(false, 404, "跳题所在的问题未设置，请处理！"),
    SORT_ERROR(false, 404, "序号已存在！"),
    QUESTION_USED(false, 404, "该问题已被绑定跳题，请先解除绑定！")




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
