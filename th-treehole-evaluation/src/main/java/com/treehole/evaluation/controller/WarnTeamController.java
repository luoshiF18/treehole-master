package com.treehole.evaluation.controller;

import com.alibaba.fastjson.JSON;
import com.treehole.api.evaluation.WarnTeamControllerApi;
import com.treehole.evaluation.MyUtils.MyCookieUtils;
import com.treehole.evaluation.service.WarnTeamService;
import com.treehole.framework.domain.evaluation.request.InterveneRequest;
import com.treehole.framework.domain.evaluation.request.WarnRequest;
import com.treehole.framework.domain.evaluation.response.EvaluationCode;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.QueryResponseResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author: Qbl
 * Created by 10:40 on 2019/12/24.
 * Version: 1.0
 */
@RestController
@RequestMapping("/warnTeam")
public class WarnTeamController implements WarnTeamControllerApi{

    @Autowired
    private WarnTeamService warnTeamService;
    @Autowired
    private StringRedisTemplate redisTemplate;

   /* 根据条件查询出该机构下人员产生的预警信息*/
    @Override
    @GetMapping("/findAllTWarn/{page}/{size}")
    public QueryResponseResult findAllTWarn(@PathVariable("size") Integer size,
                                            @PathVariable("page") Integer page,
                                            WarnRequest warnRequest) {
        Map<String, String> userInfo = this.getUserInfo();
        //得到用户id和机构id
        warnRequest.setUserId(userInfo.get("id" ));
        warnRequest.setCompanyId(userInfo.get("companyId"));
        if(StringUtils.isBlank(warnRequest.getUserId())||StringUtils.isBlank(warnRequest.getCompanyId())){
            ExceptionCast.cast( EvaluationCode.DATA_ERROR);
        }
        return warnTeamService.findAllTWarn(size, page, warnRequest );
    }

    @Override
    @GetMapping("/findHighWarn/{size}/{page}")
    public QueryResponseResult findHighWarn(@PathVariable("size") int size,
                                            @PathVariable("page") int page,
                                            @RequestParam("userNickName") String userNickName) {
        Map<String, String> userInfo = this.getUserInfo();
        return warnTeamService.findHighWarn(size,page, userInfo.get("companyId"),userNickName);
    }

    @Override
    @GetMapping("/teamIntereven/{size}/{page}")
    public QueryResponseResult teamIntereven(int size, int page, InterveneRequest interveneRequest) {
        Map<String, String> userInfo = this.getUserInfo();
        return warnTeamService.teamIntereven(size,page,userInfo.get("companyId"),interveneRequest);
    }


    /**
     * 获取登录用户详情
     */
    private Map<String, String> getUserInfo() {
        //        获取用户id
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //      获取信息
        String cookieValue = MyCookieUtils.getCookieValue(request, "uid");
        String token = redisTemplate.opsForValue().get("user_token:"+cookieValue);
        String between = StringUtils.substringBetween(token, "\"jwt_token\":\"", "\",");
        Jwt decode = JwtHelper.decode(between);
        //得到 jwt中的用户信息
        String claims = decode.getClaims();
        //将jwt转为Map
        Map<String, String> map = null;
        map = JSON.parseObject(claims, Map.class);
        return map;
    }

}
