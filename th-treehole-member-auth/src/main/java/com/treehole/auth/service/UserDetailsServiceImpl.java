package com.treehole.auth.service;

import com.treehole.auth.client.UserClient;
import com.treehole.framework.domain.member.ThMenu;
import com.treehole.framework.domain.member.ext.UserExt;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Qbl
 * Created by 19:30 on 2019/11/12.
 * Version: 1.0
 * SpringSecurity默认实现的方法，用来比对输入密码和数据库密码是否一致
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserClient userClient;
    @Autowired
    ClientDetailsService clientDetailsService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //取出身份，如果身份为空说明没有认证
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //没有认证统一采用httpbasic认证，httpbasic中存储了client_id和client_secret，开始认证client_id和client_secret
        if (authentication == null) {
            ClientDetails clientDetails = clientDetailsService.loadClientByClientId(username);
            if (clientDetails != null) {
                //密码
                String clientSecret = clientDetails.getClientSecret();
                return new User(username, clientSecret, AuthorityUtils.commaSeparatedStringToAuthorityList(""));
            }
        }
        if (StringUtils.isEmpty(username)) {
            return null;
        }

        //远程调用用户中心根据账号查询用户信息
        //测试时得到对象的字段全部为null，但是在member可以得到全部字段
        UserExt userExt = userClient.getUserExt(username);
        if (userExt == null) {
            return null;
        }
        String password = userExt.getPassword();
        List<ThMenu> perimissions = userExt.getPerimissions();
        if (perimissions == null) {

            perimissions = new ArrayList<>();
        }
        List<String> user_permission = new ArrayList<>();
        for (ThMenu item : perimissions){
            user_permission.add(item.getCode());
        }

        String user_permission_string = org.apache.commons.lang3.StringUtils.join(user_permission.toArray(), ",");
        UserJwt userDetails = new UserJwt(username,
                password,
                AuthorityUtils.commaSeparatedStringToAuthorityList(user_permission_string));
        userDetails.setId(userExt.getUser_id());
        /* userDetails.setUtype(userExt.getUser_type());//用户类型*/
        userDetails.setCompanyId(userExt.getCompanyId());//所属企业
        userDetails.setName(userExt.getUser_nickname());//用户名称
        userDetails.setUserpic(userExt.getUser_image());//用户头像
        System.out.println("1111"+userDetails);
        return userDetails;
    }
}
