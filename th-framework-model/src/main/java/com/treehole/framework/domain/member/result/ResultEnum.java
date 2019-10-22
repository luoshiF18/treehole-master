package com.treehole.framework.domain.member.result;

/**
 * @author shanhuijie
 */

public enum ResultEnum {
    //可自行定义，与前端交互
    UNKNOWN_ERROR(-1,"未知错误"),
    SUCCESS(200,"成功"),
    USER_NOT_EXIST(1,"用户不存在"),
    USER_IS_EXISTS(2,"用户已存在"),
    DATA_IS_NULL(3,"数据为空"),
    DELETE_FAIL(5,"删除失败"),
    UPDATE_FAIL(6,"更新失败"),
    VERIFY_FAIL(7,"验证失败"),
    INSERT_FAIL(11,"添加失败"),
    POINT_NOT_EXIST(10,"积分信息不存在"),
    Phone_IS_EXISTS(12,"手机号已绑定")
    //POINT_IS_EXISTS(2,"用户已存在"),
    ;
    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}