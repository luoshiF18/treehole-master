package com.treehole.manage_media.controller;

import com.treehole.api.media.MediaFileControllerApi;
import com.treehole.framework.domain.onlineCourse.MediaFile;
import com.treehole.framework.domain.onlineCourse.request.QueryMediaFileRequest;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.manage_media.service.MediaFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cc
 * @date 2019/11/12 9:16
 */
@RestController
@RequestMapping("/media/file")
public class MediaFileController implements MediaFileControllerApi {

    @Autowired
    private MediaFileService mediaFileService;

    //查询媒体文件列表
    @Override
    @GetMapping("/list/{page}/{size}")
    public QueryResponseResult<MediaFile> findList(int page, int size, QueryMediaFileRequest queryMediaFileRequest) {
        return mediaFileService.findList(page,size,queryMediaFileRequest);
    }
}
