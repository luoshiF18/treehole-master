package com.treehole.evaluation.controller;

import com.treehole.api.evaluation.WarningControllerApi;
import com.treehole.evaluation.service.WarningService;
import com.treehole.framework.model.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping("/getWarning")
    public ResponseResult getWarning(String descriptionId) {
        return warningService.getWarning(descriptionId);
    }
}
