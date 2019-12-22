package com.treehole.learning.client;

import com.treehole.framework.domain.onlineCourse.TeachplanMediaPub;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author cc
 * @date 2019/11/14 17:19
 */
@FeignClient("th-treehole-search")
public interface CourseSearchClient {

    //调用搜索服务，从es中获取课程计划与媒资信息视频之间关联的数据
    @GetMapping("/search/course/getmedia/{teachplanId}")
    public TeachplanMediaPub getmedia(@PathVariable("teachplanId") String teachplanId);
}
