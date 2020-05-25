package com.treehole.marketing.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.treehole.framework.domain.marketing.Extension;
import com.treehole.framework.domain.marketing.UserExtension;
import com.treehole.framework.domain.marketing.response.MarketingCode;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.marketing.dao.ExtensionMapper;
import com.treehole.marketing.dao.UserExtensionMapper;
import com.treehole.marketing.utils.MyNumberUtils;
import com.treehole.marketing.utils.WebSocketServer;
import com.treehole.marketing.utils.WebSocketUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wanglu
 */
@Service
public class ExtensionService {

    @Autowired
    private ExtensionMapper extensionMapper;

    @Autowired
    private UserExtensionMapper userExtensionMapper;
    private static final ObjectMapper MAPPER = new ObjectMapper();
    @Autowired
    private AmqpTemplate amqpTemplate;

    /*public void addExtension(ExtensionRequest extensionRequest) {

        if(extensionRequest == null){
            ExceptionCast.cast(MarketingCode.DATA_ERROR);
        }
        if(extensionRequest.getType() == 0){
            sendEmail(extensionRequest);
        }


    }

    private void sendEmail(ExtensionRequest extensionRequest){
        List<String> emails = extensionRequest.getTo();
        String title = extensionRequest.getTitle();
        String content = extensionRequest.getContent();

        for(String email : emails){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            MyMailUtils.sendMail(USER, PASSWORD, email, content, title);
        }
    }*/

    /**
     * 添加推广信息
     * @param extension
     */
    @Transactional
    public void addExtensionInfo(Extension extension) {
        if(extension.getMode() == null ||
                (extension.getMode() == 0 && StringUtils.isBlank(extension.getContent()))  ){
            ExceptionCast.cast(MarketingCode.DATA_ERROR);
        }
        for (Map<String, String> value : extension.getValues()) {
            if(value.containsKey("to") == false && value.containsKey("userId") == false){
                ExceptionCast.cast(MarketingCode.DATA_ERROR);
            }
        }

        extension.setCount(extension.getValues().size());
        extension.setId(MyNumberUtils.getUUID());

        //发送邮件的推广
        if(extension.getMode() == 0){
            this.amqpTemplate.convertAndSend("TREEHOLE.EMAIL.EXCHANGE", "EMAIL.VERIFY.EXTENSION", extension);
            //sendEmails(extension);
            //int succCount = sendEmails(extension);
            //extension.setSuccCount(succCount);
        }else if(extension.getMode() == 1){
            List<Map<String, String>> extensionValues = extension.getValues();
            for (Map<String, String> extensionValue : extensionValues) {
                Map<String, String> msg = new HashMap<>();
                msg.put("phone", extensionValue.get("to"));
                msg.put("code", extension.getUrl());
                this.amqpTemplate.convertAndSend("TREEHOLE.SMS.EXCHANGE", "SMS.VERIFY.URL", msg);
            }
        } else if(extension.getMode() == 2){//站内信
           // sendWs(extension);
        }

        try {
            extension.setInfo(MAPPER.writeValueAsString(extension.getValues()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if(this.extensionMapper.insertSelective(extension)!=1){
            ExceptionCast.cast(MarketingCode.INSERT_FAILURE);
        }
        this.saveUserExtension(extension);

    }
    //添加用户推广

    private void saveUserExtension(Extension extension){
       /* String content = extension.getContent();
        for (Map<String, String> value : extension.getValues()) {
            String id = value.get("to");
            try {
                WebSocketServer.SendMessage(content, id);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }*/
        UserExtension userExtension = new UserExtension();
        userExtension.setMode(extension.getMode());
        userExtension.setTitle(extension.getTitle());
        userExtension.setExtensionId(extension.getId());
        if(StringUtils.isNotBlank(extension.getUrl())){
            String regex = "\\$\\{url\\}";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(extension.getContent());
            userExtension.setContent(matcher.replaceAll(extension.getUrl()));
        }else{
            userExtension.setContent(extension.getContent());
        }
        userExtension.setTitle(extension.getTitle());
        userExtension.setUsedFor(extension.getUsedFor());
        userExtension.setActivityType(extension.getActivityType());
        List<Map<String, String>> extensionValues = extension.getValues();
        for (Map<String, String> extensionValue : extensionValues) {
            userExtension.setUserId(extensionValue.get("userId"));
            userExtension.setId(MyNumberUtils.getUUID());
            if(userExtension.getReleaseTime() == null){
                userExtension.setReleaseTime(new Date());
            }
            userExtension.setResolve(false);
            if(this.userExtensionMapper.insertSelective(userExtension)!= 1 ){
                ExceptionCast.cast(MarketingCode.INSERT_FAILURE);
            }
        }

    }

/*

    private int sendEmails(Extension extension){

        //List<String> emails = extension.getTo();
        String title = extension.getTitle();
       // String content = extension.getContent();
        int succCount = 0;
        //将content中的${key}变量替换为对应的值
        for (Map<String, String> value : extension.getValues()) {
            value.put("url", extension.getUrl());
            try {
                System.out.println(MAPPER.writeValueAsString(value));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            *//*传入的参数为extension.getContent()的原因是，
            传入content104行的content，content第一个${key}已经被修改过了，
            不再有${key},那么发送给用户的所有邮件内容是一样的
             */
    /*
            String content = renderString(extension.getContent(), value);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            MyMailUtils.sendMail(USER, PASSWORD, value.get("to"), content, title);
            succCount++;
        }

        return succCount;
    }

    //
    public String renderString(String content, Map<String, String> map){
        Set<Map.Entry<String, String>> sets = map.entrySet();
        for(Map.Entry<String, String> entry : sets) {
            String regex = "\\$\\{" + entry.getKey() + "\\}";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(content);
            content = matcher.replaceAll(entry.getValue());
        }
        return content;
    }*/

    /**
     * 根据条件分页查询推广信息
     * @param key
     * @param page
     * @param size
     * @param sortBy
     * @param desc
     * @param mode
     * @param usedFor
     * @return
     */
    public QueryResult<Extension> queryExtension(String key, Integer page, Integer size, String sortBy, Boolean desc, Integer mode, Boolean usedFor) {
        Example example = new Example(Extension.class);
        Example.Criteria criteria = example.createCriteria();
        //条件不为空，则添加查询条件
        if(StringUtils.isNotBlank(key)){
            criteria.andLike("url", "%" + key + "%");
        }
        if(mode != null){
            criteria.andEqualTo("mode", mode);
        }
        if(usedFor != null){
            criteria.andEqualTo("usedFor", usedFor);
        }
        // 添加分页条件
        PageHelper.startPage(page, size);
        // 添加排序条件
        if (StringUtils.isNotBlank(sortBy)) {
            example.setOrderByClause(sortBy + " " + (desc ? "desc" : "asc"));
        }

        //查询
        List<Extension> extensions = this.extensionMapper.selectByExample(example);

        if(CollectionUtils.isEmpty(extensions)){
            ExceptionCast.cast(MarketingCode.SELECT_NULL);
        }

        for(Extension extension:extensions){
           /* coupon.setLetter(StringUtils.substring(coupon.getLetter(), 0, 1));
            coupon.setValidTypeName(coupon.getValidType() ? "绝对时效":"相对时效");
            Type type = this.typeMapper.selectByPrimaryKey(coupon.getTypeId());
            coupon.setTypeName(type.getName());*/
           if(extension.getMode() == 0){
               extension.setModeName("邮件");
           } else if(extension.getMode() == 1){
               extension.setModeName("短信");
           } else if(extension.getMode() == 2){
               extension.setModeName("站内信");
           }

           if(extension.getUsedFor()){
               extension.setUsedForStr("活动");
           } else {
               extension.setUsedForStr("优惠券");
           }
        }
        // 包装成pageInfo
        PageInfo<Extension> pageInfo = new PageInfo<>(extensions);

        return new QueryResult(extensions, pageInfo.getTotal());

    }

    /**
     * 删除推广信息
     * @param id
     */
    @Transactional
    public void deleteExtension(String id) {
        try {
            if(StringUtils.isNotBlank(id)){

                this.extensionMapper.deleteByPrimaryKey(id);
            }
        } catch (Exception e) {
            ExceptionCast.cast(MarketingCode.DELETE_ERROR);
            e.printStackTrace();
        }
    }

    public void addPushToClient(Extension extension) {
        if(extension.getMode() == null){
            ExceptionCast.cast(MarketingCode.DATA_ERROR);
        }

        if(extension.getMode() == 3){
            try {
                WebSocketUtil.sendInfo(extension.getContent());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
