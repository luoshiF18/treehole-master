package com.treehole.framework.domain.cms;

import lombok.Data;
import lombok.ToString;

import java.util.Map;

/**
 * @author 任志强
 * @version 1.0
 * @date 2019/10/11 11:07
 * @description:
 */
@Data
@ToString
public class CmsConfigModel {
    private String key;
    private String name;
    private String url;
    private Map mapValue;
    private String value;

}

