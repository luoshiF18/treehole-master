package com.treehole.api.evaluation;

import com.treehole.framework.domain.evaluation.request.InterveneRequest;
import com.treehole.framework.domain.evaluation.request.WarnRequest;
import com.treehole.framework.model.response.QueryResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @Author: Qbl
 * Created by 10:41 on 2019/12/24.
 * Version: 1.0
 */
@Api(value = "团体预警管理",description = "团体预警管理")
public interface WarnTeamControllerApi {

        @ApiOperation( "按条件查询团体用户预警人群" )
        public QueryResponseResult findAllTWarn(Integer size, Integer page, WarnRequest warnRequest);
        @ApiOperation("按条件筛选高危人群" )
        public QueryResponseResult findHighWarn(int size,int page,String userNickName);
        @ApiOperation("条件查询干预记录" )
        public QueryResponseResult teamIntereven(int size, int page, InterveneRequest interveneRequest);
}
