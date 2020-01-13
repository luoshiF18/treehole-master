package com.treehole.framework.domain.onlineCourse.request;

import com.treehole.framework.model.request.RequestData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author cc
 * @date 2019/10/18 10:08
 */
@Data
public class QueryVideoRequest extends RequestData {
    //站点id
    @ApiModelProperty("视频标题")
    private String title;

    //页面ID
    @ApiModelProperty("课程id")
    private String courseId;

}
