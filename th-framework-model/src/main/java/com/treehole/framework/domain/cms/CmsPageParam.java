package com.treehole.framework.domain.cms;

import lombok.Data;
import lombok.ToString;

/**
 * @author 任志强
 * @version 1.0
 * @date 2019/10/11 11:06
 * @description:
 */
@Data
@ToString
public class CmsPageParam {
    //参数名称
    private String pageParamName;
    //参数值
    private String pageParamValue;

}

