package com.treehole.framework.domain.evaluation;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @Author: Qbl
 * Created by 19:30 on 2019/12/13.
 * Version: 1.0
 */
@Data
@ToString
public class WarnMsg {


    private String to;  //收件人的地址
    private List<String> warningId; //预警消息id
    private String userId;  //用户id
    private String message;
    private String status; //是否为自动发送
}
