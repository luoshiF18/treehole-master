package com.treehole.api.evaluation;

import com.treehole.framework.domain.evaluation.WarningIntervene;
import com.treehole.framework.domain.evaluation.request.InterveneRequest;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: Qbl
 * Created by 17:27 on 2019/11/23.
 * Version: 1.0
 */
@Api(value = "预警干预管理", description = "对干预信息的添加，修改，查询")
public interface WarningInterveneControllerApi {

    @ApiOperation( "添加一条预警干预的记录" )
    public ResponseResult addRecord(WarningIntervene warningIntervene);

    @ApiOperation( "根据条件查询干预记录表" )
    public QueryResponseResult getInterveneCondition(int page, int size,
                                                     InterveneRequest interveneRequest);
    @ApiOperation("根据id得到干预记录表详情" )
    public WarningIntervene getInterveneById(@RequestParam("interveneId") String interveneId);

    @ApiOperation("根据id删除" )
    public ResponseResult delIntervene(@RequestParam("interveneId") String InterveneId);
}
