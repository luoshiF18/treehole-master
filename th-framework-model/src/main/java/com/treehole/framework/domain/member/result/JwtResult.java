package com.treehole.framework.domain.member.result;

import com.treehole.framework.model.response.ResponseResult;
import com.treehole.framework.model.response.ResultCode;

/**
 * @Author: Qbl
 * Created by 15:50 on 2019/11/15.
 * Version: 1.0
 */
public class JwtResult extends ResponseResult {
    public JwtResult(ResultCode resultCode,String jwt){
        super(resultCode);
        this.jwt=jwt;
    }
    private  String jwt;
}
