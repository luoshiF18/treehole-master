package com.treehole.evaluation.controller;

import com.alibaba.fastjson.JSON;
import com.treehole.api.evaluation.ScaleInsertControllerApi;
import com.treehole.evaluation.MyUtils.MyCookieUtils;
import com.treehole.evaluation.service.ScaleInsertService;
import com.treehole.framework.domain.evaluation.Description;
import com.treehole.framework.domain.evaluation.Scale;
import com.treehole.framework.domain.evaluation.dto.QuestionDTO;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.framework.utils.Oauth2Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 量表增删改查等
 *
 * @auther: Yan Hao
 * @date: 2019/10/14
 */
@RestController
@RequestMapping("/scale/insert")
public class ScaleInsertController implements ScaleInsertControllerApi {
    @Autowired
    private ScaleInsertService scaleInsertService;
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 添加新量表
     *
     * @return null
     */
    @Override
    @PostMapping("scale")
    public ResponseResult insertScale(@RequestBody Scale scale) {

//        String userId = userInfo.get("id");
        scaleInsertService.insertScale(scale, getUserId());
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 添加问题和选项
     *
     * @return
     */
    @Override
    @PostMapping("question")
    public ResponseResult insertQuestionAndOption(@RequestBody QuestionDTO questionDTO) {
        scaleInsertService.insertQuestionAndOption(questionDTO);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 添加得分描述
     *
     * @param description
     * @return
     */
    @Override
    @PostMapping("description")
    public ResponseResult insertDescription(
            @RequestBody Description description) {
        scaleInsertService.insertDescription(description);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 添加量表分类
     *
     * @param type
     * @return
     */
    @Override
    @PostMapping("type")
    public ResponseResult insertScaleType(@RequestParam("type") String type) {
        scaleInsertService.insertScaleType(type);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 处理跳题，把问题序号转为问题id
     *
     * @param scaleId
     * @return
     */
    @Override
    @PostMapping("handle/skip")
    public ResponseResult handleOptionSkip(@RequestParam("scaleId") String scaleId) {
        scaleInsertService.handleOptionSkip(scaleId);
        return new ResponseResult(CommonCode.SUCCESS);
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
