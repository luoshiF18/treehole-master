package com.treehole.psychologist.controller;

import com.treehole.api.psychologist.PsychologistControllerApi;
import com.treehole.framework.domain.psychologist.Psychologist;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.psychologist.service.PsychologistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Helay
 * @date 2019/10/25 9:33
 */
@RestController
@RequestMapping("psychologist")
public class PsychologistController implements PsychologistControllerApi {

    @Autowired
    private PsychologistService psychologistService;

    /**
     * 分页查询心理咨询师信息
     *
     * @param page 当前页码
     * @param size 每页记录数
     * @return
     */
    @Override
    @GetMapping("find/all")
    public QueryResponseResult findAllPsychologist(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "5") Integer size
    ) {
        QueryResult queryResult = this.psychologistService.findAllPsychologist(page, size);
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
    }

    /**
     * 根据id查询心理咨询师信息
     *
     * @param id 心理咨询师id
     * @return
     */
    @Override
    @GetMapping("find/{id}")
    public Psychologist findPsychologistById(@PathVariable("id") String id) {
        return this.psychologistService.findPsychologistById(id);
    }

    /**
     * 根据id删除心理咨询师信息
     *
     * @param id 心理咨询师id
     * @return
     */
    @Override
    @DeleteMapping("del/{id}")
    public ResponseResult delPsychologistById(@PathVariable("id") String id) {
        this.psychologistService.delPsychologistById(id);
        //删除成功，响应成功状态码
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 添加心理咨询师信息
     *
     * @param psychologist 心理咨询师对象
     * @return
     */
    @Override
    @PostMapping("add")
    public ResponseResult addPsychologist(@RequestBody Psychologist psychologist) {
        this.psychologistService.addPsychologist(psychologist);
        //添加成功，响应成功状态码
        return new ResponseResult(CommonCode.SUCCESS);
    }
}
