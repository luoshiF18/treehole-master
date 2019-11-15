package com.treehole.auth.controller;

import com.treehole.api.auth.AuthControllerApi;
import com.treehole.auth.service.AuthService;
import com.treehole.framework.domain.member.ext.AuthToken;
import com.treehole.framework.domain.member.request.LoginRequest;
import com.treehole.framework.domain.member.result.AuthCode;
import com.treehole.framework.domain.member.result.JwtResult;
import com.treehole.framework.domain.member.result.LoginResult;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.framework.utils.CookieUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Author: Qbl
 * Created by 19:18 on 2019/11/12.
 * Version: 1.0
 */
@RestController
public class AuthController implements AuthControllerApi {
    @Autowired
    private AuthService authService;
    @Value( "${auth.clientId}" )
    String clientId;
    @Value( "${auth.clientSecret}" )
    String clientSecret;
    @Value( "${auth.cookieDomain}" )
    String cookieDomain;
    @Value("${auth.cookieMaxAge}")
    int cookieMaxAge;

    @Override
    @RequestMapping("/userlogin")
    public LoginResult login(LoginRequest loginRequest) {
        if(loginRequest==null|| StringUtils.isEmpty(loginRequest.getUserNickName())){
            ExceptionCast.cast( AuthCode.AUTH_USERNAME_NONE);
        }
        if(loginRequest==null|| StringUtils.isEmpty(loginRequest.getPassword())){
            ExceptionCast.cast( AuthCode.AUTH_PASSWORD_NONE);
        }
        //得到账号密码
        String userNickName = loginRequest.getUserNickName();
        String password = loginRequest.getPassword();
        //申请令牌
        AuthToken authToken =  authService.login(userNickName,password,clientId,clientSecret);
        //用户身份令牌
        String access_token = authToken.getAccess_token();
        //将令牌存储到cookie
       try {
           this.saveCookie(access_token);
       }
        catch (Exception e){
           e.printStackTrace();
           return null;
        }
        return new LoginResult( CommonCode.SUCCESS,access_token);
    }
    //将令牌存储到cookie
    private void saveCookie(String token){
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        //HttpServletResponse response,String domain,String path, String name, String value, int maxAge,boolean httpOnly
        CookieUtil.addCookie(response,cookieDomain,"/","uid",token,cookieMaxAge,false);
    }

    @Override
    @PostMapping("userlogout")
    public ResponseResult logout() {
        //取出身份令牌
        String uid = getTokenFormCookie();
        //删除redis的token
        authService.delToken(uid );
        //清除cookie中token
        clearCookie(uid);
        return new ResponseResult(CommonCode.SUCCESS);
    }
    //清除cookie
    private void clearCookie(String token){
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        CookieUtil.addCookie(response, cookieDomain, "/", "uid", token, 0, false);
    }
    @Override
    @GetMapping("/userjwt")
    public JwtResult userjwt() {
        String uid = getTokenFormCookie();
        if(uid == null){
            return new JwtResult(CommonCode.FAIL,null);
        }

        //拿身份令牌从redis中查询jwt令牌
        AuthToken userToken = authService.getUserToken(uid);
        if(userToken!=null){
            //将jwt令牌返回给用户
            String jwt_token = userToken.getJwt_token();
            return new JwtResult(CommonCode.SUCCESS,jwt_token);
        }
        return null;

    }

    //取出cookie中的身份令牌
    private String getTokenFormCookie(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Map<String, String> map = CookieUtil.readCookie(request, "uid");
        if(map!=null && map.get("uid")!=null){
            String uid = map.get("uid");
            return uid;
        }
        return null;
    }
}
