package com.treehole.member.controller;

import com.treehole.api.member.FreeGradeControllerApi;
import com.treehole.framework.domain.member.FreeGrade;
import com.treehole.framework.domain.member.resquest.GradeListRequest;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.member.service.FreegradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Shan HuiJie
 * @Description:
 * @Date 2019.11.07 19:35
 */
@RestController
@RequestMapping("member/freegrade")
public class FreeGradeController implements FreeGradeControllerApi {

    @Autowired
    private FreegradeService freegradeService;

    @Override
    @GetMapping("/find/all")
    public QueryResponseResult findAllFreeGrade(@PathVariable("page") Integer page,
                                                @PathVariable("size") Integer size,
                                                GradeListRequest gradeListRequest) {
        QueryResult queryResult = freegradeService.findAll(page,size,gradeListRequest);
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
    }

    @Override
    @GetMapping("/find/id/{id}")
    public FreeGrade findPayGradeById(@PathVariable("id") String id) {
        return freegradeService.getById(id);
    }

    @Override
    @PostMapping("/insert")
    public ResponseResult insertFreeGrade(@RequestBody @Valid FreeGrade freeGrade) {
        freegradeService.insert(freeGrade);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    @Override
    @DeleteMapping("/delete/{id}")
    public ResponseResult deleteFreeGrade(@PathVariable("id") String id) {
        freegradeService.deleteGrade(id);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    @Override
    @PutMapping("/update")
    public ResponseResult update(@RequestBody @Valid FreeGrade freeGrade) {

        freegradeService.updateGrade(freeGrade);
        return new ResponseResult(CommonCode.SUCCESS);
    }
}
