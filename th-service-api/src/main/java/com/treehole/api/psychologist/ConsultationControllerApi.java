package com.treehole.api.psychologist;

import com.treehole.framework.domain.psychologist.Consultation;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author Helay
 * @date 2019/11/19 10:33
 */
@Api(value = "用户咨询记录相关接口", description = "对用户咨询记录进行增、删、改、查")
public interface ConsultationControllerApi {

    @ApiOperation("根据用户id查询用户咨询记录列表")
    QueryResponseResult getConsultationsByUser(Integer page, Integer size, String user_id);

    @ApiOperation("查询所有用户咨询记录")
    QueryResponseResult getAllConsultations(Integer page, Integer size);

    @ApiOperation("根据咨询师id查询用户咨询记录列表")
    QueryResponseResult getConsultationsByPsy(Integer page, Integer size, String psychologist_id);

    @ApiOperation("根据咨询记录id删除咨询记录")
    ResponseResult delConsultationById(String consultation_id);

    @ApiOperation("添加一条咨询记录")
    ResponseResult addConsultation(Consultation consultation);
}
