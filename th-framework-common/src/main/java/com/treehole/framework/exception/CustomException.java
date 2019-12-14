package com.treehole.framework.exception;

import com.treehole.framework.model.response.ResultCode;

/**
 * 自定义异常类型
 * 使用RuntimeException是因为对原有代码没有侵入性
 */
public class CustomException extends RuntimeException{

    //错误代码
    private ResultCode resultCode;

    //通过构造方法进行初始化
    public CustomException(ResultCode resultCode){
        this.resultCode = resultCode;
    }

    //定义resultcode的get方法
    public ResultCode getResultCode(){
        return resultCode;
    }

}

