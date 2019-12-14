package com.treehole.archives.controller;

import com.treehole.api.archives.ArchivesSelectControllerApi;
import com.treehole.archives.service.ArchivesSelectService;
import com.treehole.framework.domain.archives.ext.ResultExt;
import com.treehole.framework.domain.archives.resquest.ResultListRequest;
import com.treehole.framework.model.response.QueryResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 任志强
 * @version 1.0
 * @date 2019/10/22 9:00
 * @description: 个体报告Controller
 */
@RestController
@RequestMapping("/archives")
public class ArchivesSelectController implements ArchivesSelectControllerApi {

    @Autowired
    private ArchivesSelectService archivesSelectService;

    /**
     * 查询个体报告列表接口
     * @param page
     * @param size
     * @param resultListRequest
     * @return
     */
    @Override
    @GetMapping("/ResultList/{page}/{size}")
    public QueryResponseResult findResultList(
            @PathVariable("page") Integer page,
            @PathVariable("size") Integer size,
            ResultListRequest resultListRequest) {
        return archivesSelectService.findArchivesList(page,size,resultListRequest);
    }


    /**
     * 根据个体报告id查询个体报告的详细内容
     * @param resultId
     * @return
     */
    @Override
    @GetMapping("/resultExt/{resultId}")
    public ResultExt findResultExtReport(
            @PathVariable("resultId") String resultId) {
        return archivesSelectService.findResultExtReport(resultId);
    }

}
