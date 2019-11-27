package com.treehole.train.controller;

import com.treehole.api.train.CostControllerApi;
import com.treehole.framework.domain.train.Cost;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.train.service.CostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/train/cost")
public class CostController implements CostControllerApi {

    @Autowired
    CostService costService;

    @Override
    @PostMapping("/pay")
    public ResponseResult pay(@RequestBody Cost cost) {
        return costService.pay(cost);
    }

    @Override
    @GetMapping("/find/{studentId}")
    public Cost findTuition(@PathVariable("studentId") String studentId) {
        return costService.findTuition(studentId);
    }


}
