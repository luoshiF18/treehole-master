package com.treehole.evaluation.controller;

import com.alibaba.fastjson.JSON;
import com.treehole.api.evaluation.WarningControllerApi;
import com.treehole.evaluation.MyUtils.MyCookieUtils;
import com.treehole.evaluation.dao.ScaleMapper;
import com.treehole.evaluation.service.WarningService;
import com.treehole.framework.domain.evaluation.Warning;
import com.treehole.framework.domain.evaluation.request.WarnRequest;
import com.treehole.framework.domain.evaluation.vo.WarnHUserVo;
import com.treehole.framework.domain.evaluation.vo.WarnReportVo;
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
import java.util.List;
import java.util.Map;

/**
 * @Author: Qbl
 * Created by 10:23 on 2019/10/17.
 * Version: 1.0
 */
@RestController
@RequestMapping("/warning")
public class WarningController implements WarningControllerApi

{
    @Autowired
    private WarningService warningService;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private ScaleMapper scaleMapper;

    @Override
    @GetMapping("/getWarning/{page}/{size}")
    public QueryResponseResult findWarningByUserId(
                                                   @PathVariable("page") int page,
                                                   @PathVariable("size") int size)
    {
        return warningService.selectWarningByUserId(this.getUserId(),page,size);
    }
    @Override
    @PostMapping("/addWarningByPsy")
    public ResponseResult addWarningByPsy(@RequestBody Warning warning) {
        //根据量表名称得到量表id
        String scaleId=scaleMapper.findScaleIdByName(warning.getScaleId());
        warning.setScaleId( scaleId );
        warning.setUserId(this.getUserId());
        return warningService.addWarningByPsy( warning);
    }
    @Override
    @GetMapping("/findAll/{page}/{size}")
    public QueryResponseResult findAll(@PathVariable("page") int page,
                                       @PathVariable("size") int size) {
        return warningService.findAll(page,size);
    }

    @Override
    @DeleteMapping("/delWarning")
    public ResponseResult deleteWarning(@RequestParam("warningId") String warningId) {
        return warningService.deleteWarning(warningId);
    }

    @Override
    @DeleteMapping("/delMoreWarning")
    public ResponseResult deleteMoreWarning(@RequestParam("ids[]") List<String> ids) {
        return warningService.deleteMoreWarning(ids);
    }

    @Override
    @GetMapping("/lookWarning/{warningId}")
    public WarnReportVo lookWaring(@PathVariable("warningId") String warningId) {
        return warningService.lookWaring( warningId );
    }

    @Override
    @GetMapping("/getPieDate")
    public String getPieData(@RequestParam("userNickName") String userNickName) {
        return warningService.getPieData(userNickName);
    }

    @Override
    @GetMapping("/getPieScaData")
    public String getPieScaData(@RequestParam("scaleName") String scaleName) {
        return warningService.getPieScaData(scaleName);
    }

    @Override
    @GetMapping("/getUserPieData")
    public String getUserPieData(@RequestParam("userNickName") String userNickName) {
        return warningService.getUserPieData( userNickName );
    }


    @Override
    @GetMapping("/getWarningCondition/{page}/{size}")
    public QueryResponseResult findWarningCondition(@PathVariable("page") int page,
                                                    @PathVariable("size") int size,
                                                    WarnRequest warnRequest) {
        return warningService.findWarningCondition(page, size, warnRequest );
    }
    @Override
    @GetMapping("/findHighRisk/{page}/{size}")
    public QueryResponseResult findHighRisk(@PathVariable("page") int page,
                                            @PathVariable("size") int size,
                                            @RequestParam("userNickName") String userNickName){
        return warningService.findHighRisk(page, size,userNickName);
    }
    @Override
    @GetMapping("/lookDetailHWarn")
    public WarnHUserVo lookDetailHWarn(@RequestParam("warnHUserid") String warnHUserid) {
        return warningService.lookDetailHWarn(warnHUserid);
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
