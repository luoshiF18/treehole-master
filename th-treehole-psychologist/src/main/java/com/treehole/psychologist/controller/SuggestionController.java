package com.treehole.psychologist.controller;

import com.treehole.api.psychologist.SuggestionControllerApi;
import com.treehole.framework.domain.psychologist.Suggestion;
import com.treehole.framework.domain.psychologist.ext.SuggestionExt;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.psychologist.service.SuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Helay
 * @date 2019/11/19 14:48
 */
@RestController
@RequestMapping("/psychologist/suggestion")
public class SuggestionController implements SuggestionControllerApi {

    @Autowired
    private SuggestionService suggestionService;

    /**
     * 根据建议id查询建议信息
     *
     * @param suggestion_id 建议id
     * @return
     */
    @Override
    @GetMapping("/get/{suggestion_id}")
    public QueryResponseResult getSuggestionById(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "5") Integer size,
            @PathVariable("suggestion_id") String suggestion_id
    ) {
        return this.suggestionService.getSuggestionById(page, size, suggestion_id);
    }

    /**
     * 分页查询所有建议信息
     *
     * @param page 当前页
     * @param size 每页记录数
     * @return
     */
    @Override
    @GetMapping("/get/all")
    public QueryResponseResult getAllSuggestions(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "5") Integer size
    ) {
        return this.suggestionService.getAllSuggestions(page, size);
    }

    /**
     * 根据id删除建议信息
     *
     * @param suggestion_id 建议id
     * @return
     */
    @Override
    @DeleteMapping("/del/{suggestion_id}")
    public ResponseResult delSuggestionById(@PathVariable("suggestion_id") String suggestion_id) {
        return this.suggestionService.delSuggestionById(suggestion_id);
    }

    /**
     * 添加一条建议信息
     *
     * @param suggestion 建议信息
     * @return
     */
    @Override
    @PostMapping("/add")
    public ResponseResult addSuggestion(@RequestBody Suggestion suggestion) {
        return this.suggestionService.addSuggestion(suggestion);
    }

    /**
     * 更新建议信息
     *
     * @param suggestion 建议信息
     * @return
     */
    @Override
    @PutMapping("/update")
    public ResponseResult updateSuggestion(@RequestBody Suggestion suggestion) {
        return this.suggestionService.updateSuggestion(suggestion);
    }

    /**
     * 根据主键查询
     *
     * @param suggestion_id 主键
     * @return
     */
    @Override
    @GetMapping("/find/{suggestion_id}")
    public SuggestionExt findSuggestionExtById(@PathVariable("suggestion_id") String suggestion_id) {
        return this.suggestionService.findSuggestionExtById(suggestion_id);
    }
}
