package com.treehole.marketing.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.treehole.framework.domain.marketing.Extension;
import com.treehole.marketing.conf.EmailProperties;
import com.treehole.marketing.utils.MyMailUtils;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wanglu
 */
@Component

public class EmailListener {

    @Autowired
    private EmailProperties prop;
    private static final ObjectMapper MAPPER = new ObjectMapper();
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "TREEHOLE.EMAIL.QUEUE", durable = "true"),
            exchange = @Exchange(value = "TREEHOLE.EMAIL.EXCHANGE",
                    ignoreDeclarationExceptions = "true"),
            key = {"EMAIL.VERIFY.EXTENSION"}))
    public void sendEmails(Extension extension){

        //List<String> emails = extension.getTo();
        String title = extension.getTitle();
        // String content = extension.getContent();
        //int succCount = 0;
        //将content中的${key}变量替换为对应的值
        for (Map<String, String> value : extension.getValues()) {
            value.put("url", extension.getUrl());
            try {
                System.out.println(MAPPER.writeValueAsString(value));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            /*传入的参数为extension.getContent()的原因是，
            传入content104行的content，content第一个${key}已经被修改过了，
            不再有${key},那么发送给用户的所有邮件内容是一样的
             */
            String content = renderString(extension.getContent(), value);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            MyMailUtils.sendMail(prop.getUSER(), prop.getPASSWORD(), value.get("to"), content, title);
            //succCount++;
        }

        //return succCount;
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
    }
}
