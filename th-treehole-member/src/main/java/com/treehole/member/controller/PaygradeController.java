package com.treehole.member.controller;

import com.treehole.api.member.PayGradeControllerApi;
import com.treehole.framework.domain.member.PayGrade;
import com.treehole.framework.domain.member.User;
import com.treehole.framework.domain.member.resquest.GradeListRequest;
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
@RequestMapping("member/paygrade")
public class PaygradeController implements PayGradeControllerApi {

    @Autowired
    private PaygradeService paygradeService;

    @Override
    @GetMapping("/find/all/{page}/{size}")
    public QueryResponseResult findAllPayGrade(@PathVariable("page") Integer page,
                                               @PathVariable("size") Integer size,
                                               GradeListRequest gradeListRequest) {
        return paygradeService.findAll(page,size,gradeListRequest);

    }

   /* @Override
    @GetMapping("/find/id/{id}")
    public PayGrade findPayGradeById(@PathVariable("id") String id) {
        return paygradeService.getById(id);
    }*/

    @Override
    @PostMapping ("/insert")
    public ResponseResult insertPayGrade(@RequestBody @Valid PayGrade payGrade) {
        paygradeService.insert(payGrade);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    @Override
    @DeleteMapping("/delete/{id}")
    public ResponseResult deletePayGrade(@PathVariable("id") String paygrade_id) {
        paygradeService.deleteGrade(paygrade_id);
        return new ResponseResult(CommonCode.SUCCESS);
    }


    /*接收到的数据为前端update后的*/
    @Override
    @PutMapping("/update")
    public ResponseResult update(@RequestBody @Valid PayGrade payGrade) {
        paygradeService.updateGrade(payGrade);
        return new ResponseResult(CommonCode.SUCCESS);
    }
}
