package com.treehole.train.controller;

import com.treehole.api.train.PhaseApi;
import com.treehole.framework.domain.train.Phase;
import com.treehole.framework.domain.train.ext.CourseExt;
import com.treehole.framework.domain.train.ext.CourseTeacher;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.train.service.PhaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/train/phase")
public class PhaseController implements PhaseApi {

    @Autowired
    PhaseService phaseService;

    @Override
    @PostMapping("/add")
    public ResponseResult addPhase(@RequestBody Phase phase) {
       return phaseService.addPhase(phase);
    }

    @Override
    @PutMapping("/update/{phaseId}")
    public ResponseResult updatePhase(@PathVariable("phaseId") String phaseId ,@RequestBody Phase phase) {
        return phaseService.updatePhase(phaseId,phase);
    }

    @Override
    @PostMapping("/find/{page}/{size}")
    public QueryResponseResult<Phase> findPhase(@PathVariable("page") int page , @PathVariable("size") int size , @RequestBody Phase phase) {
        return phaseService.findPhase(page, size, phase);
    }

    @Override
    @GetMapping("/findname")
    public String generation_phaseName() {
        return phaseService.generation_phaseName();
    }

    @Override
    @PostMapping("/findPhaseCourse/{page}/{size}")
    public QueryResponseResult<CourseTeacher> findPhaseCourse(@PathVariable("page") int page,@PathVariable("size") int size,@RequestBody CourseExt courseExt) {
        return phaseService.findPhaseCourse(page,size,courseExt);
    }

}
