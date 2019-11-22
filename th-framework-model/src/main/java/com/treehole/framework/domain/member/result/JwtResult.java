package com.treehole.framework.domain.member.result;

import com.treehole.framework.model.response.ResponseResult;
import com.treehole.framework.model.response.ResultCode;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Qbl
 * Created by 15:50 on 2019/11/15.
 * Version: 1.0
 */
@Data
@NoArgsConstructor
public class JwtResult extends ResponseResult {
    private  String jwt;

    public JwtResult(ResultCode resultCode,String jwt){
        super(resultCode);
        this.jwt=jwt;
    }
}
