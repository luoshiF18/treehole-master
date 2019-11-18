package com.treehole.evaluation.controller;

import com.treehole.api.evaluation.WarningControllerApi;
import com.treehole.evaluation.service.WarningService;
import com.treehole.framework.domain.evaluation.Warning;
import com.treehole.framework.domain.evaluation.request.WarnRequest;
import com.treehole.framework.domain.evaluation.vo.WarnReportVo;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: Qbl
 * Created by 10:23 on 2019/10/17.
 * Version: 1.0
 */
@RestController
@RequestMapping("/warning")
public class WarningController implements WarningControllerApi
{
    @Autowired
    private WarningService warningService;




    @Override
    @GetMapping("/getWarning/{page}/{size}")
    public QueryResponseResult findWarningByUserId(@RequestParam("userId") String userId,
                                                   @PathVariable("page") int page,
                                                   @PathVariable("size") int size)
    {
        return warningService.selectWarningByUserId(userId,page,size);
    }

    @Override
    @GetMapping("/findAll/{page}/{size}")
    public QueryResponseResult findAll(@PathVariable("page") int page,
                                       @PathVariable("size") int size) {
        return warningService.findAll(page,size);
    }

    @Override
    @DeleteMapping("/delWarning")
    public ResponseResult deleteWarning(@RequestParam("warningId") String warningId) {
        return warningService.deleteWarning(warningId);
    }

    @Override
    @DeleteMapping("/delMoreWarning")
    public ResponseResult deleteMoreWarning(@RequestParam("ids[]") List<String> ids) {
        return warningService.deleteMoreWarning(ids);
    }

    @Override
    @GetMapping("/lookWarning/{warningId}")
    public WarnReportVo lookWaring(@PathVariable("warningId") String warningId) {
        return warningService.lookWaring( warningId );
    }
    @Override
    @PostMapping("addWarningByPsy")
    public ResponseResult addWarningByPsy(@RequestBody Warning warning) {
        return warningService.addWarningByPsy( warning);
    }
    @Override
    @GetMapping("/getWarningCondition/{page}/{size}")
    public QueryResponseResult findWarningCondition(@PathVariable("page") int page,
                                                    @PathVariable("size") int size,
                                                    WarnRequest warnRequest) {
        return warningService.findWarningCondition(page, size, warnRequest );
    }
}
