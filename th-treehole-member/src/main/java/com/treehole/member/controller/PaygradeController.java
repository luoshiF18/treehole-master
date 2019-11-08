package com.treehole.member.controller;

import com.treehole.api.member.PayGradeControllerApi;
import com.treehole.framework.domain.member.PayGrade;
import com.treehole.framework.domain.member.User;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.member.service.PaygradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Shan HuiJie
 * @Description:
 * @Date 2019.11.07 16:31
 */
@RestController
@RequestMapping("paygrade")
public class PaygradeController implements PayGradeControllerApi {

    @Autowired
    private PaygradeService paygradeService;

    @Override
    @GetMapping("/find/all")
    public QueryResponseResult findAllPayGrade(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                               @RequestParam(value = "size", defaultValue = "5") Integer size) {
        QueryResult queryResult = paygradeService.findAll(page,size);
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
    }

    @Override
    @GetMapping("/find/id/{id}")
    public PayGrade findPayGradeById(@PathVariable("id") String id) {
        return paygradeService.getById(id);
    }

    @Override
    @PostMapping ("/insert")
    public ResponseResult insertPayGrade(@RequestBody @Valid PayGrade payGrade) {

        return null;
    }

    @Override
    @DeleteMapping("/delete/{id}")
    public ResponseResult deletePayGrade(@PathVariable("id") String paygrade_id) {
        return null;
    }


    /*接收到的数据为前端update后的*/
    @Override
    @PutMapping("/update")
    public ResponseResult update(@RequestBody @Valid PayGrade payGrade) {
        paygradeService.updateGrade(payGrade);
        return new ResponseResult(CommonCode.SUCCESS);
    }
}
