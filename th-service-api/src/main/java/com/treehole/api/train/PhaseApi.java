package com.treehole.api.train;

import com.treehole.framework.domain.train.Phase;
import com.treehole.framework.domain.train.ext.CourseExt;
import com.treehole.framework.domain.train.ext.CourseTeacher;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

@Api(value="期数管理",description = "期数信息管理页面管理接口，提供页面的增、删、改、查")
public interface PhaseApi {

    @ApiOperation("添加期数信息")
    public ResponseResult addPhase(Phase phase);

    @ApiOperation("修改期数信息")
    public ResponseResult updatePhase(String phaseId,Phase phase);

    @ApiOperation("查询期数信息")
    public QueryResponseResult<Phase> findPhase(int page , int size , Phase phase);

    @ApiOperation("自动生成名称")
    public String generation_phaseName();

    @ApiOperation("查询期数课程")
    public QueryResponseResult<CourseTeacher> findPhaseCourse(int page, int size, CourseExt courseExt);


}
