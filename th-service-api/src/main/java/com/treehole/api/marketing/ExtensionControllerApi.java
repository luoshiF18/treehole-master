package com.treehole.api.marketing;

import com.treehole.framework.domain.marketing.Extension;
import com.treehole.framework.domain.marketing.request.ExtensionRequest;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Api(value = "推广管理", description = "对推广进行管理")
public interface ExtensionControllerApi {


    @ApiOperation("发送推广邮件")
    public ResponseResult addEmailExtension(@RequestBody ExtensionRequest extensionRequest);

    @ApiOperation("发送推广")
    public ResponseResult addExtension(@RequestBody Extension extension);

    @ApiOperation("查询所有推广消息")
    public QueryResponseResult queryExtension(@RequestParam(value = "key", required = false)String key,
                                              @RequestParam(value = "page", defaultValue = "1")Integer page,
                                              @RequestParam(value = "size", defaultValue = "5")Integer size,
                                              @RequestParam(value = "sortBy", required = false)String sortBy,
                                              @RequestParam(value = "desc", defaultValue = "true")Boolean desc,
                                              @RequestParam(value = "mode", required = false)Integer mode,
                                              @RequestParam(value = "usedFor", required = false)Boolean usedFor);

    @ApiOperation("删除推广信息")
    public ResponseResult deleteExtension(String id);
}
