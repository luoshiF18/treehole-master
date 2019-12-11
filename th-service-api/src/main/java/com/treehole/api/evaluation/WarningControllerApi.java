package com.treehole.api.evaluation;

import com.treehole.framework.domain.evaluation.Warning;
import com.treehole.framework.domain.evaluation.request.WarnRequest;
import com.treehole.framework.domain.evaluation.vo.WarnHUserVo;
import com.treehole.framework.domain.evaluation.vo.WarnReportVo;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

/**
 * @Author: Qbl
 * Created by 8:56 on 2019/10/17.
 * Version: 1.0
 */
@Api(value = "预警管理", description = "对预警信息的添加，修改，查询")
public interface WarningControllerApi {

    @ApiOperation("心理咨询师添加预警信息")
    public ResponseResult addWarningByPsy(Warning warning);
    @ApiOperation("心理咨询师查看用户预警信息")
    public QueryResponseResult findWarningByUserId(String userId,int page,int size);
    @ApiOperation("后台管理，根据条件查询用户预警信息")
    public QueryResponseResult findWarningCondition(int page,int size,WarnRequest warnRequest);
    @ApiOperation( "后台管理，查询所有预警信息")
    public QueryResponseResult findAll(int page,int size);
    @ApiOperation("后台管理，删除预警信息")
    public ResponseResult deleteWarning(String warningId);
    @ApiOperation("后台管理，批量删除预警信息")
    public ResponseResult deleteMoreWarning(List<String> ids);
    @ApiOperation("后台管理，查看详细预警信息")
    public WarnReportVo lookWaring(String warningId);
    @ApiOperation("根据用户名得到该用户饼状图预警信息")
    public String getPieData(String userNickName);
    @ApiOperation("根据量表名得到该量表饼状图信息")
    public String getPieScaData(String scaleName);
    @ApiOperation("根据用户名得到统计后的预警信息")
    public String getUserPieData(String userNickName );
    @ApiOperation("查询出高危人群" )
    public QueryResponseResult findHighRisk(int page,int size,String userNickName);
    @ApiOperation("查看高危人群详情")
    public WarnHUserVo lookDetailHWarn(String warnHUserid);
}
