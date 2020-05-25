package com.treehole.framework.domain.marketing.response;

import com.treehole.framework.model.response.ResultCode;

public enum MarketingCode implements ResultCode {

    DATA_ERROR(false, 401, "前台数据有误！"),
    DESC_DATA_ERROR(false, 401, "描述信息有误，请检查！"),
    SELECT_NULL(false, 401, "查询失败！"),
    INSERT_FAILURE(false, 401, "插入数据库失败！"),
    UPDATE_ERROR(false, 404, "抱歉，更新失败！"),
    UPDATE_FORBIDDEN(false, 403, "抱歉，不可以修改，请进行其他操作！"),
    UPDATE_DATA_ERROR(false,403, "抱歉，数据有误，请重新填写"),
    DELETE_ERROR(false, 404, "抱歉，删除失败！"),
    DELETE_FORBIDDEN(false, 403, "抱歉，正在进行中不可删除，请下架！"),
    INSERT_DATA_ERROR(false, 401, "数据错误，添加失败"),
    DATA_NULL(false, 401, "优惠券数据不存在"),
    STOCK_NULL(false, 401, "优惠券已领完"),
    COUPON_NUM_TOMUCH(false, 401, "未结束发放的优惠券还有很多，不要着急添加"),
    COUPON_VALID_DATE_ERROR(false, 401,"优惠券的使用时间不存在，请重新填写" ),
    COUPONTYPE_DEL_FORBIDDEN(false, 401, "存在该类型的优惠券，删除失败"),
    COUPON_INVALID(false, 401, "优惠券无效"),
    RECEIVE_ERROR(false, 401, "您已领够该优惠券");




    ;
    //操作代码
    boolean success;
    //操作代码
    int code;
    //提示信息
    String message;

    private MarketingCode(boolean success, int code, String message) {
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
