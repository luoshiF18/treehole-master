package com.treehole.evaluation.controller;

import com.treehole.api.evaluation.WarningInterveneControllerApi;
import com.treehole.evaluation.service.WarningInterveneService;
import com.treehole.framework.domain.evaluation.WarningIntervene;
import com.treehole.framework.model.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

}
