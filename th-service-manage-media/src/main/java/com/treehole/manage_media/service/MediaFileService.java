package com.treehole.manage_media.service;

import com.treehole.framework.domain.onlineCourse.MediaFile;
import com.treehole.framework.domain.onlineCourse.request.QueryMediaFileRequest;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.manage_media.dao.MediaFileRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

/**
 * @author cc
 * @date 2019/11/13 9:21
 */
@Service
public class MediaFileService {

    @Autowired
    private MediaFileRepository mediaFileRepository;

    //分页查询所有媒体文件列表
    public QueryResponseResult<MediaFile> findList(int page, int size, QueryMediaFileRequest queryMediaFileRequest) {
        //判断数据
        if(page <= 0){
            page = 1;
        }
        page = page - 1;
        if(size <= 0){
            size = 10;
        }
        if(queryMediaFileRequest == null){
            queryMediaFileRequest = new QueryMediaFileRequest();
        }
        MediaFile mediaFile = new MediaFile();
        //设置查询参数
        if(StringUtils.isNotEmpty(queryMediaFileRequest.getFileOriginalName())){
            mediaFile.setFileOriginalName(queryMediaFileRequest.getFileOriginalName());
        }
        if(StringUtils.isNotEmpty(queryMediaFileRequest.getProcessStatus())){
            mediaFile.setProcessStatus(queryMediaFileRequest.getProcessStatus());
        }
        if(StringUtils.isNotEmpty(queryMediaFileRequest.getTag())){
            mediaFile.setTag(queryMediaFileRequest.getTag());
        }
        //设置查询方式
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("fileOriginalName",ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("processStatus",ExampleMatcher.GenericPropertyMatchers.exact())//不设置默认就是精确匹配
                .withMatcher("tag",ExampleMatcher.GenericPropertyMatchers.contains());
        //创建查询条件
        Example<MediaFile> example = Example.of(mediaFile,matcher);
        //设置分页条件
        Pageable pageable = new PageRequest(page,size);
        Page<MediaFile> all = mediaFileRepository.findAll(example,pageable);
        //封装返回结果
        QueryResult<MediaFile> queryResult = new QueryResult<MediaFile>();
        queryResult.setList(all.getContent());//数据列表
        queryResult.setTotal(all.getTotalElements());//数据总数
        return new QueryResponseResult(CommonCode.SUCCESS,queryResult);
    }
}
