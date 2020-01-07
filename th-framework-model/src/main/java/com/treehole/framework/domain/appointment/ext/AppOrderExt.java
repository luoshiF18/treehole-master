package com.treehole.framework.domain.appointment.ext;

import com.treehole.framework.domain.appointment.AppOrder;
import lombok.Data;

/**
 * @ClassName 预约订单扩展类
 * @Description: TODO
 * @Author XDD
 * @Date 2019/12/22 13:50
 **/
@Data
public class AppOrderExt extends AppOrder {
    //咨询师姓名
    private String cltName;
    //咨询师头像
    private String image;
}
