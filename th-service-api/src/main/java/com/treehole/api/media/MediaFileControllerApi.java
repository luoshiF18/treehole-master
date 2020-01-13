package com.treehole.api.media;

import com.treehole.framework.domain.onlineCourse.MediaFile;
import com.treehole.framework.domain.onlineCourse.request.QueryMediaFileRequest;
import com.treehole.framework.model.response.QueryResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author cc
 * @date 2019/11/13 9:17
 */
@Api(value = "媒体文件管理",description = "媒体文件管理")
public interface MediaFileControllerApi {

    @ApiOperation("查询文件接口")
    public QueryResponseResult<MediaFile> findList(int page, int size, QueryMediaFileRequest queryMediaFileRequest);
}
