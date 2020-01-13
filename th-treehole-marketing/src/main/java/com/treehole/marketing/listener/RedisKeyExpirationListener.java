package com.treehole.marketing.listener;

import com.treehole.framework.domain.marketing.InteractiveActivity;
import com.treehole.marketing.service.ActivityService;
import com.treehole.marketing.service.CouponService;
import com.treehole.marketing.service.InteractiveActivityService;
import com.treehole.marketing.service.UserCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

@Component
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private CouponService couponService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private InteractiveActivityService interactiveActivityService;

    @Autowired
    private UserCouponService userCouponService;

    public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    /**
     * 针对redis数据失效事件，进行数据处理
     * @param message
     * @param pattern
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        // 用户做自己的业务处理即可,注意message.toString()可以获取失效的key
        String expiredKey = message.toString();

        String [] info = expiredKey.split("_");
        if(info.length == 3){
            String id = info[0];
            String type = info[1];
            String status = info[2];
            if("coupon".equals(type)){
                this.couponService.changeStatus(status, id);
            }else if("activity".equals(type)){
                this.activityService.changeStatus(status, id);
            } else if("interactiveActivity".equals(type)){
                this.interactiveActivityService.changeStatus(status, id);
            }

        } else if(info.length == 4) {
            String id = info[0];
            String userCoupon = info[1];
            String ifValid = info[2];
            String ifUsed = info[3];
            if("userCoupon".equals(userCoupon)){
                this.userCouponService.changeStatus(id, ifValid, ifUsed);
            }

        }
        System.out.println(expiredKey);
    }
}