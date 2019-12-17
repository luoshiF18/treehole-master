package com.document.manage.scheduled;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Auther: lee
 * @Date: 2019/10/18
 * @Description: com.lee.springboot.scheduled
 * @version: 1.0
 */
@Component
public class ScheduledDemo {
    /**
     * 定时任务方法
     * @Scheduled : 设置定时任务
     * corn : corn表达式(定时任务触发时间的一个字符串表达式)
     */
    @Scheduled(cron = "0 0 0 * * ?")//每天晚上的0点0分0秒执行（24小时制）
    public void scheduledMethod(){
        System.out.println("定时器被触发"+new Date());
    }
}
