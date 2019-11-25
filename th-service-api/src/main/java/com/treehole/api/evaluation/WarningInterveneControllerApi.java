package com.treehole.api.evaluation;

import com.treehole.framework.domain.evaluation.WarningIntervene;
import com.treehole.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @Author: Qbl
 * Created by 17:27 on 2019/11/23.
 * Version: 1.0
 */
@Api(value = "预警干预管理", description = "对干预信息的添加，修改，查询")
public interface WarningInterveneControllerApi {

    @ApiOperation( "添加一条预警干预的记录" )
    public ResponseResult addRecord(WarningIntervene warningIntervene);
}
