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
import org.springframework.security.access.prepost.PreAuthorize;
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
    @GetMapping("/find/all/{page}/{size}")
    @PreAuthorize("hasAuthority('member_freegrade_find_all')")
    public QueryResponseResult findAllFreeGrade(@PathVariable("page") Integer page,
                                                @PathVariable("size") Integer size,
                                                 GradeListRequest gradeListRequest) {
        return  freegradeService.findAll1(page,size,gradeListRequest);

    }
    @Override
    @GetMapping("/find/all")
    //@PreAuthorize("hasAuthority('member_freegrade_find_nopage')")
    public QueryResponseResult findAll() {
        QueryResult result = freegradeService.findAll2();
        return new QueryResponseResult(CommonCode.SUCCESS,result);
    }

    @Override
    @GetMapping("/getGradeById/{id}")
    //@PreAuthorize("hasAuthority('member_freegrade_find_id')")
    public FreeGrade findGradeById(@PathVariable("id") String id){
        return freegradeService.getById(id);
    }
   /* @Override
    @GetMapping("/find/id/{id}")
    public FreeGrade findPayGradeById(@PathVariable("id") String id) {
        return freegradeService.getById(id);
    }*/

    @Override
    @PostMapping("/insert")
    @PreAuthorize("hasAuthority('member_freegrade_insert')")
    public ResponseResult insertFreeGrade(@RequestBody @Valid FreeGrade freeGrade) {
        freegradeService.insert(freeGrade);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    @Override
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('member_freegrade_delete_id')")
    public ResponseResult deleteFreeGrade(@PathVariable("id") String id) {
        freegradeService.deleteGrade(id);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    @Override
    @PutMapping("/update")
    @PreAuthorize("hasAuthority('member_freegrade_update')")
    public ResponseResult update(@RequestBody FreeGrade freeGrade) {

        freegradeService.updateGrade(freeGrade);
        return new ResponseResult(CommonCode.SUCCESS);
    }
}
