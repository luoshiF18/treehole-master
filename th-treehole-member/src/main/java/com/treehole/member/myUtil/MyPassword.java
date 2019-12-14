package com.treehole.member.myUtil;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Shan HuiJie
 * @Description: 密码加密加盐
 * @Date 2019.12.02 9:38
 */
public class MyPassword {

    public static String PasswrodEncoder(String password) throws Exception{
        String pass = password;
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //使用bcrypt加密,每次加密使用一个随机盐
        //for(int i=0;i<10;i++) {
            //每个计算出的Hash值都不一样
            String hashPass = passwordEncoder.encode(pass);
            //System.out.println(hashPass);
            //虽然每次计算的密码Hash值不一样但是校验是通过的
           // boolean f = passwordEncoder.matches(pass, hashPass);
           // System.out.println(f);
        //}
        return hashPass;
    }
}
