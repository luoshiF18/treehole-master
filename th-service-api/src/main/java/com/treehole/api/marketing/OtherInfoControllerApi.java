package com.treehole.api.marketing;

import com.treehole.framework.model.response.QueryResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestParam;

@Api(value = "其他微服务方法管理", description = "对需要调用的其他微服务的方法进行管理")
public interface OtherInfoControllerApi {

    @ApiOperation("获取所有分类")
    public QueryResponseResult findScaleType();

    @ApiOperation("根据分类id查询量表")
    public QueryResponseResult findScaleByType(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                               @RequestParam(value = "size", defaultValue = "5") Integer size,
                                               @RequestParam(value = "scaleTypeId") String scaleTypeId,
                                               @RequestParam(value = "isFree", defaultValue = "true") Boolean isFree);


}
