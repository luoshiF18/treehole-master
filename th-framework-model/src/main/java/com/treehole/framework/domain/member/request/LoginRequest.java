package com.treehole.framework.domain.member.request;

import com.treehole.framework.model.request.RequestData;
import lombok.Data;
import lombok.ToString;

/**
 * @Author: Qbl
 * Created by 19:21 on 2019/11/12.
 * Version: 1.0
 */
@Data
@ToString
public class LoginRequest extends RequestData{

    String userNickName;
    String password;
    String verifycode;

}
