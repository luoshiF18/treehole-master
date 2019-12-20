package com.treehole.framework.domain.member.ext;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author: Qbl
 * Created by 19:20 on 2019/11/12.
 * Version: 1.0
 * 存储令牌信息
 */
@Data
@ToString
@NoArgsConstructor
public class AuthToken {
    String access_token;//访问token 短令牌 就是用户的身份令牌
    String refresh_token;//刷新token
    String jwt_token;//jwt令牌
}
