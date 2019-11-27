package com.treehole.api.psychologist;

import com.treehole.framework.domain.psychologist.ext.SuggestionExt;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author Helay
 * @date 2019/11/19 14:42
 */
@Api(value = "咨询师建议相关接口", description = "对咨询师给出的建议进行增、删、改、查")
public interface SuggestionControllerApi {

    @ApiOperation("根据建议id查询建议信息")
    SuggestionExt getSuggestionExtById(String suggestion_id);
}
