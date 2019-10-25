package com.treehole.api.psychologist;

import com.treehole.framework.domain.psychologist.Psychologist;
import com.treehole.framework.model.response.QueryResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author Helay
 * @date 2019/10/25 9:24
 */
@Api(value = "心理咨询师信息管理", description = "对心理咨询师信息进行增、删、改、查")
public interface PsychologistControllerApi {

    @ApiOperation("分页查询所有心理咨询师信息")
    QueryResponseResult findAllPsychologist(Integer page, Integer size);

    @ApiOperation("根据id查询心理咨询师信息")
    Psychologist findPsychologistById(String id);

}
