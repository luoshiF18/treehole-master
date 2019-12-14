package com.treehole.psychologist.api;

import com.treehole.framework.domain.psychologist.ext.SuggestionExt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Helay
 * @date 2019/11/19 15:52
 */
public interface SuggestionApi {

    /**
     * 根据建议id查询建议信息
     *
     * @param suggestion_id 建议id
     * @return
     */
    @GetMapping("/psychologist/suggestion/get/{suggestion_id}")
    SuggestionExt getSuggestionExtById(@PathVariable("suggestion_id") String suggestion_id);
}
