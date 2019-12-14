package com.treehole.framework.model.response;

/**
 * @author 任志强
 * @version 1.0
 * @date 2019/10/11 10:43
 * @description: 10000-- 通用错误代码
 * 22000-- 媒资错误代码
 * 23000-- 用户中心错误代码
 * 24000-- cms错误代码
 * 25000-- 文件系统
 */
public interface ResultCode {
    //操作是否成功,true为成功，false操作失败
    boolean success();

    //操作代码
    int code();

    //提示信息
    String message();

}