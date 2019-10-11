package com.treehole.api.cms;

import com.treehole.framework.domain.cms.CmsConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author 任志强
 * @version 1.0
 * @date 2019/10/11 11:26
 * @description:
 */
@Api(value="cms配置管理接口",description = "cms配置管理接口，提供数据模型的管理、查询接口")
public interface CmsConfigControllerApi {

    @ApiOperation("根据id查询CMS配置信息")
    public CmsConfig getmodel(String id);

}

