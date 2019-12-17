package com.treehole.evaluation.service;

import com.alibaba.fastjson.JSON;
import com.treehole.evaluation.MyUtils.MyNumberUtils;
import com.treehole.evaluation.client.UserClient;
import com.treehole.evaluation.dao.WarningMapper;
import com.treehole.framework.domain.evaluation.WarnMsg;
import com.treehole.framework.domain.evaluation.Warning;
import com.treehole.framework.domain.evaluation.response.EvaluationCode;
import com.treehole.framework.domain.member.Vo.UserVo;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.ResponseResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: Qbl
 * Created by 19:04 on 2019/12/13.
 * Version: 1.0
 */
@Service
public class WarnMsgService {

    @Autowired
    private  WarningMapper warningMapper;
    @Autowired
    private  StringRedisTemplate redisTemplate;
    @Autowired
    private  JavaMailSender mailSender;
    @Autowired
    private UserClient userClient;

    @Value("${spring.mail.username}")
    private String from;

    //发送预警消息
    public ResponseResult sendMeg(WarnMsg warnMsg){
        //预警消息设置默认值
        if(warnMsg.getWarningId()==null||warnMsg.getWarningId().size()==0){
            List<String> wid = new ArrayList<>();
            wid.add("ceshichushizhi");
            warnMsg.setWarningId(wid);
        }
        //根据预警id查询出用户的预警信息
        List<Warning> warnings = warningMapper.findWarnByUserId(warnMsg.getWarningId());
        //如果userid不为空默认为心理咨询师给用户发送预警信息
        Warning warn = new Warning();
        if(warnMsg.getUserId()!=null&&StringUtils.isNotBlank(warnMsg.getUserId())){
            warn=this.sendMsgByPsy(warnMsg);
            warnings.add(warn);
        }
        if(warnings==null){
            ExceptionCast.cast(EvaluationCode.SELECT_NULL);
        }
        //将预警消息存到redis中
        try {
            for (Warning warning : warnings) {
                String data=JSON.toJSONString(warning);
                String key=warning.getId();
                redisTemplate.opsForValue().set(key,data,24*3, TimeUnit.DAYS);
            }
            return new ResponseResult(CommonCode.SUCCESS );
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
    //从redis中查询预警信息
    public List<String> getRedisWarning(String userId){
        if(userId==null||StringUtils.isBlank( userId )){
            ExceptionCast.cast(EvaluationCode.DATA_ERROR );
        }
        Warning warning = new Warning();
        warning.setUserId(userId);
        List<Warning> select = warningMapper.select( warning );
        List<String > warns = new ArrayList<>();
        try {
            for (Warning warnings : select) {
                String key = warnings.getId();
                String value = redisTemplate.opsForValue().get( key );
                value=JSON.parse(value).toString();
                warns.add( value );
            }
            System.out.println(warns);
            return warns;
        }
       catch (Exception e){
            e.printStackTrace();
            return null;
       }
    }
    //心理咨询师给用户发送消息
    private Warning sendMsgByPsy(WarnMsg wMsg ){
           //给用户新增预警信息
            Warning warning = new Warning();
           try {
               warning.setId( MyNumberUtils.getUUID());
               warning.setUserId(wMsg.getUserId());
               warning.setStatus( 0 );
               warning.setWMessage( wMsg.getMessage());
               warning.setCreateTime(new Date() );
               System.out.println(warning.getId());
               warningMapper.insert( warning);
               return warningMapper.selectByPrimaryKey(warning.getId());
           }
            catch (Exception e){
               e.printStackTrace();
               return null;
            }
    }

    //邮件发送
    public ResponseResult sendEamil(WarnMsg warnMsg){
        if(warnMsg==null){
            ExceptionCast.cast(EvaluationCode.DATA_ERROR);
        }
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(warnMsg.getTo());
        message.setSubject("树洞网预警消息通知");
        message.setText(warningMapper.selectByPrimaryKey(warnMsg.getWarningId().get(0)).getWMessage());
        try {
            mailSender.send(message);
            return new ResponseResult(CommonCode.SUCCESS);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseResult(CommonCode.FAIL);
        }
    }
    //用户是否存在,返回正确的邮箱
    public String emilFormat(WarnMsg warnMsg){
        //查询该用户
        UserVo userVoByUserId = userClient.getUserVoByUserId( warnMsg.getUserId());
        if(userVoByUserId==null){
            ExceptionCast.cast(EvaluationCode.DATA_ERROR );
        }
        //校验邮箱格式
        final String format = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        final Pattern pattern = Pattern.compile(format);
        final Matcher mat = pattern.matcher(userVoByUserId.getUser_email());
        if(!mat.find()){
            //格式错误
            return null;
        }
        return userVoByUserId.getUser_email();
    }



}
