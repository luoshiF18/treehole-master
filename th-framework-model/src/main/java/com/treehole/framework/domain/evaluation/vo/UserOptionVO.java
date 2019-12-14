package com.treehole.framework.domain.evaluation.vo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Map;

/**
 * @auther: Yan Hao
 * @date: 2019/10/17
 */
@Data
public class UserOptionVO {

    private String id;
    private String scaleName;
    private String userName;
    private Map result;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private String time;
}
