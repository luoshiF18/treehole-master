package com.treehole.evaluation.controller;

import com.alibaba.fastjson.JSON;
import com.treehole.api.evaluation.WarningInterveneControllerApi;
import com.treehole.evaluation.MyUtils.MyCookieUtils;
import com.treehole.evaluation.service.WarningInterveneService;
import com.treehole.framework.domain.evaluation.WarningIntervene;
import com.treehole.framework.domain.evaluation.request.InterveneRequest;
import com.treehole.framework.domain.evaluation.vo.WarningVo;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.ResponseResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


/**
 * @Author: Qbl
 * Created by 17:35 on 2019/11/23.
 * Version: 1.0
 */
@RestController
@RequestMapping("/intervene")
public class WarningInterveneController implements WarningInterveneControllerApi
{
    @Autowired
    private WarningInterveneService warningInterveneService;
    @Autowired
    private StringRedisTemplate redisTemplate;


    @Override
    @PostMapping("/addRecord")
    public ResponseResult addRecord(@RequestBody WarningIntervene warningIntervene) {
        return warningInterveneService.addRecord(warningIntervene);
    }

    @Override
    @GetMapping("/getAllByCondition/{page}/{size}")
    public QueryResponseResult getInterveneCondition(@PathVariable("page") int page,
                                                     @PathVariable("size") int size,
                                                     InterveneRequest interveneRequest) {
        return warningInterveneService.getInterveneCondition(page, size, interveneRequest );
    }

    @Override
    @GetMapping("/getInterveneById")
    public WarningIntervene getInterveneById(@RequestParam("interveneId") String interveneId){
        return warningInterveneService.getInterveneById(interveneId);
    }

    @Override
    @DeleteMapping("/delIntervene")
    public ResponseResult delIntervene(@RequestParam("interveneId")String InterveneId){
        return warningInterveneService.delIntervene(InterveneId);
    }

    @Override
    @GetMapping("/findByPsy")
    public WarningVo findByPsy() {
        return warningInterveneService.findByPsy(this.getUserId());
    }
    /**
     * 获取用户Id
     */
    private String getUserId() {
        //        获取用户id
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //      获取信息
        String cookieValue = MyCookieUtils.getCookieValue(request, "uid");
        String token = redisTemplate.opsForValue().get("user_token:" + cookieValue);
        String between = StringUtils.substringBetween(token, "\"jwt_token\":\"", "\",");
        Jwt decode = JwtHelper.decode(between);
        //得到 jwt中的用户信息
        String claims = decode.getClaims();
        //将jwt转为Map
        Map<String, String> map = null;
        map = JSON.parseObject(claims, Map.class);
        String id = map.get("id");
        return id;
    }
}
