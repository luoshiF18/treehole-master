package com.treehole.framework.exception;

import com.google.common.collect.ImmutableMap;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.framework.model.response.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 任志强
 * @version 1.0
 * @date 2019/10/11 10:52
 * @description: 统一捕获异常类
 */
@ControllerAdvice //控制器增强
public class ExceptionCatch {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionCatch.class);

    //定义map,配置异常类型所对应的错误代码
    private static ImmutableMap<Class<? extends Throwable>,ResultCode> EXCEPTIONS;
    //定义map的builder对象,去构建ImmutableMap
    protected static ImmutableMap.Builder<Class<? extends Throwable>,ResultCode> builder = ImmutableMap.builder();

    @ExceptionHandler(CustomException.class)  //捕获CustomException此类异常
    @ResponseBody //向客户端响应数据为json类型
    public ResponseResult customException(CustomException customException){
        customException.printStackTrace();
        //记录日志
        LOGGER.error("catch exception:{}",customException.getResultCode().message());
        ResultCode resultCode = customException.getResultCode();
        return new ResponseResult(resultCode);
    }


    @ExceptionHandler(Exception.class)  //捕获Exception此类异常
    @ResponseBody //向客户端响应数据为json类型
    public ResponseResult exception(Exception exception){
        exception.printStackTrace();
        //记录日志
        LOGGER.error("catch exception:{}",exception.getMessage());
        if (EXCEPTIONS == null){
            //构建map
            EXCEPTIONS = builder.build();  //EXCEPTIONS构建成功,构建成功后不能更改
        }
        //从EXCEPTIONS中找异常类型所对应的错误代码,如果找到了将错误代码响应给用户,如果找不到给用户响应99999异常
        ResultCode resultCode = EXCEPTIONS.get(exception.getClass());
        if (resultCode != null){  //如果在集合中找到了对应的异常类型
            return new ResponseResult(resultCode);
        }else {
            //返回99999异常
            return new ResponseResult(CommonCode.SERVER_ERROR);
        }
    }

    static {
        //定义异常类型对应的错误代码
        builder.put(HttpMessageNotReadableException.class,CommonCode.INVALID_PARAM);
    }


}
