package com.treehole.learning.service;

import com.treehole.framework.domain.onlineCourse.TeachplanMediaPub;
import com.treehole.framework.domain.onlineCourse.response.GetMediaResult;
import com.treehole.framework.domain.onlineCourse.response.LearningCode;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.learning.client.CourseSearchClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author cc
 * @date 2019/11/14 17:22
 */
@Service
public class LearningService {

    @Autowired
    private CourseSearchClient courseSearchClient;

    //获取课程地址
    public GetMediaResult getMedia(String courseId, String teachplanId){
        //判断学生的学习的权限

        //调用搜索服务接口
        TeachplanMediaPub teachplanMediaPub = courseSearchClient.getmedia(teachplanId);
        //判断
        if(teachplanMediaPub == null && StringUtils.isEmpty(teachplanMediaPub.getMediaUrl())){
            //获取视频播放地址出错
            ExceptionCast.cast(LearningCode.LEARNING_GETMEDIA_ERROR);
        }
        return new GetMediaResult(CommonCode.SUCCESS,teachplanMediaPub.getMediaUrl());
    }
}
