package com.treehole.evaluation.controller;

import com.treehole.api.evaluation.WarningInterveneControllerApi;
import com.treehole.evaluation.service.WarningInterveneService;
import com.treehole.framework.domain.evaluation.WarningIntervene;
import com.treehole.framework.domain.evaluation.request.InterveneRequest;
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

/**
 * @Author: Qbl
 * Created by 17:35 on 2019/11/23.
 * Version: 1.0
 */
@RestController
@RequestMapping("/intervene")
public class WarningInterveneController implements WarningInterveneControllerApi
{
    @Autowired
    private WarningInterveneService warningInterveneService;


    @Override
    @PostMapping("/addRecord")
    public ResponseResult addRecord(@RequestBody WarningIntervene warningIntervene) {
        return warningInterveneService.addRecord(warningIntervene);
    }

    @Override
    @GetMapping("/getAllByCondition/{page}/{size}")
    public QueryResponseResult getInterveneCondition(@PathVariable("page") int page,
                                                     @PathVariable("size") int size,
                                                     InterveneRequest interveneRequest) {
        return warningInterveneService.getInterveneCondition(page, size, interveneRequest );
    }

    @Override
    @GetMapping("/getInterveneById")
    public WarningIntervene getInterveneById(@RequestParam("interveneId") String interveneId){
        return warningInterveneService.getInterveneById(interveneId);
    }

    @Override
    @DeleteMapping("/delIntervene")
    public ResponseResult delIntervene(@RequestParam("interveneId")String InterveneId){
        return warningInterveneService.delIntervene(InterveneId);
    }

}
