package com.treehole.api.archives;

import com.treehole.framework.domain.archives.ext.ResultExt;
import com.treehole.framework.domain.archives.resquest.ResultListRequest;
import com.treehole.framework.model.response.QueryResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author 任志强
 * @version 1.0
 * @date 2019/10/22 9:04
 * @description: 个体报告相关接口
 */
@Api(value="个体报告相关接口",description = "提供个体报告相关接口")
public interface ArchivesSelectControllerApi {

    @ApiOperation("查询个体报告列表接口")
    public QueryResponseResult findResultList(
            Integer page,
            Integer size,
            ResultListRequest resultListRequest);


    @ApiOperation("根据个体报告id查询个体报告的详细内容")
    public ResultExt findResultExtReport(
            String resultId
    );


}
