package com.treehole.api.learning;

import com.treehole.framework.domain.onlineCourse.response.GetMediaResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author cc
 * @date 2019/11/19 15:25
 */
@Api(value = "录播课程学习管理",description = "录播课程学习管理")
public interface CourseLearningControllerApi {

    @ApiOperation("获取录播视频播放路径")
    public GetMediaResult getmedia(String courseId, String teachplanId);
}
