package com.treehole.api.psychologist;

import com.treehole.framework.model.response.QueryResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author Helay
 * @date 2019/11/19 10:33
 */
@Api(value = "用户咨询记录相关接口", description = "对用户咨询记录进行增、删、改、查")
public interface ConsultationControllerApi {

    @ApiOperation("根据用户id查询用户咨询记录列表")
    QueryResponseResult getConsultationListById(Integer page, Integer size, String user_id);
}
