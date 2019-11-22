package com.treehole.framework.domain.member.result;

import com.treehole.framework.model.response.ResponseResult;
import com.treehole.framework.model.response.ResultCode;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author: Qbl
 * Created by 19:33 on 2019/11/12.
 * Version: 1.0
 */
@Data
@ToString
@NoArgsConstructor
public class LoginResult extends ResponseResult{

    public LoginResult(ResultCode resultCode, String token) {
        super(resultCode);
        this.token = token;
    }
    private String token;

}
