package com.treehole.psychologist.controller;

import com.treehole.api.psychologist.SuggestionControllerApi;
import com.treehole.framework.domain.psychologist.ext.SuggestionExt;
import com.treehole.psychologist.service.SuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public SuggestionExt getSuggestionExtById(@PathVariable("suggestion_id") String suggestion_id) {
        return this.suggestionService.getSuggestionExtById(suggestion_id);
    }
}
