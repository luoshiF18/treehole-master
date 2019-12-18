package com.treehole.api.evaluation;

import com.treehole.framework.domain.evaluation.Description;
import com.treehole.framework.domain.evaluation.dto.OptionsDTO;
import com.treehole.framework.domain.evaluation.response.*;
import com.treehole.framework.model.response.QueryResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @auther: Yan Hao
 * @date: 2019/10/15
 */
@Api(value = "量表查询管理", description = "对量表进行查询")
public interface ScaleSelectControllerApi {

    @ApiOperation("搜索量表")
    public QueryResponseResult findScale(Integer page, Integer size, String sortBy, Boolean desc, String key, String typeId);

    @ApiOperation("得到量表详细信息")
    public DetailResult findScaleDetail(String scaleId, String scaleName);

    @ApiOperation("搜索所有量表描述")
    public QueryResponseResult findScaleDesc(String scaleId);

    @ApiOperation("开始测试,多选类型")
    public StartTestResult startTestType1(String scaleId, Integer nextQuestionSort);

    @ApiOperation("开始测试,跳题类型")
    public StartTestResult2 startTestType2(String scaleId, String nextQuestionId, Integer questionSort, String optionId);

    @ApiOperation("根据选项得出测试结果")
    public ResultRequest testResult(OptionsDTO optionsDTO);

    @ApiOperation("根据量表名和用户id查询用户选项")
    public UserOptionResult findUserOption(String scaleName, String userId);

    @ApiOperation("根据量表名或用户id查询用户结果")
    public QueryResponseResult findResult(Integer page, Integer size, String scaleName, String userId);

    @ApiOperation("获取要更改的问题和选项")
    public UpdateQuestionResult findUpdateQuestion(String scaleId);

    @ApiOperation("获取所有分类")
    public QueryResponseResult findScaleType();

    @ApiOperation("获取所有分数计算方法")
    public QueryResponseResult findScoreMethod();

    @ApiOperation("根据分类id查询量表")
    public QueryResponseResult findScaleByType(Integer page, Integer size, String scaleTypeId, Boolean isFree);

    @ApiOperation("根据量表id查询下一个要添加的问题id")
    public Integer findNextQuestionSort(String scaleId);

    @ApiOperation("获取一个问题的信息")
    public UpdateOneQuestionResult findOneQuestion(String questionId);

    @ApiOperation("获取一个得分描述的信息")
    public Description findOneDescription(String descId);

}
