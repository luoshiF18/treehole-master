package com.treehole.framework.domain.cms.request;

import com.treehole.framework.model.request.RequestData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 任志强
 * @version 1.0
 * @date 2019/10/11 11:10
 * @description: 定义请求模型  cms
 */
@Data
public class QueryPageRequest extends RequestData {
    //站点id
    @ApiModelProperty("站点id")
    private String siteId;

    //页面ID
    @ApiModelProperty("页面ID")
    private String pageId;

    //页面名称
    @ApiModelProperty("页面名称")
    private String pageName;

    //别名
    @ApiModelProperty("页面别名")
    private String pageAliase;

    //模版id
    @ApiModelProperty("模版id")
    private String templateId;
}

