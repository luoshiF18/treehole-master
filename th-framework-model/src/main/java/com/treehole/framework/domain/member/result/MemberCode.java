package com.treehole.framework.domain.member.result;

import com.treehole.framework.model.response.ResultCode;
import lombok.ToString;

/**
 * 异常代码
 *
 * @auther: Shan huijie
 * @date: 2019/10/22
 */
@ToString
public enum MemberCode implements ResultCode {

    UNKNOWN_ERROR(false,-1,"未知错误"),
    SUCCESS(false,200,"成功"),
    DATA_ERROR(false, 301, "前台数据有误！"),
    USER_NOT_EXIST(false,302,"用户不存在"),
    USER_IS_EXISTS(false,303,"用户已存在"),
    DATA_IS_NULL(false,304,"数据为空"),
    DELETE_FAIL(false,305,"删除失败"),
    UPDATE_FAIL(false,306,"更新失败"),
    VERIFY_FAIL(false,307,"验证失败"),
    INSERT_FAIL(false,308,"添加失败"),
    POINT_NOT_EXIST(false,309,"积分信息不存在"),
    PHONE_IS_EXIST(false,310,"手机号已绑定"),
    SELECT_NULL(false,311,"查询失败"),
    DELETE_USER_NOT_EXIST(false,312,"删除的用户不存在"),
    NICKNAME_EXIST(false,313,"该昵称已存在"),
    PHONE_NOT_EXIST(false,314,"手机号不存在"),
    POINT_NOT_FULL(false,315,"积分不足")
    ;
    //操作代码
    boolean success;
    //操作代码
    int code;
    //提示信息
    String message;

    private MemberCode(boolean success, int code, String message) {
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
