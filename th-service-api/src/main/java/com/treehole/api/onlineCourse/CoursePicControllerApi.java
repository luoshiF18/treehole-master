package com.treehole.api.onlineCourse;

import com.treehole.framework.domain.onlineCourse.CourseBase;
import com.treehole.framework.domain.onlineCourse.response.CourseBaseResult;
import com.treehole.framework.domain.onlineCourse.response.CoursePicResult;
import com.treehole.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author cc
 * @date 2019/10/29 19:12
 */
@Api(value="在线课程接口管理",description = "提供在线课程图片操作")
public interface CoursePicControllerApi {

    @ApiOperation("图片上传")
    public CoursePicResult uploadCoursePic(MultipartFile multipartFile,String courseId);

    @ApiOperation("查询图片")
    public CoursePicResult findCoursePic(String courseId);

    @ApiOperation("删除图片")
    public ResponseResult deleteCoursePic(String courseId);

}
