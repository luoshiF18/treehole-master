package com.treehole.api.marketing;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.treehole.framework.domain.marketing.UserExtension;
import com.treehole.framework.domain.marketing.request.UserCouponRequest;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Api(value = "用户的推广管理", description = "对用户的推广信息进行管理")
public interface UserExtensionControllerApi {
    @ApiOperation("查询用户的推广信息列表")
    public QueryResponseResult queryUserExtensions(@PathVariable("userId") String userId);

    @ApiOperation("查询用户新消息个数")
    public int queryUserExtensionCount(@PathVariable("userId") String userId);
   /* @ApiOperation("添加用户优惠券信息")
    public ResponseResult saveUserExtension(UserExtension userExtension);*/

    @ApiOperation("根据id删除用户通知")
    public ResponseResult deleteExtensionById(@PathVariable("id") String id);


    @ApiOperation("修改信息已读状态")
    public ResponseResult updateResolve(@PathVariable("userId") String userId);
}
