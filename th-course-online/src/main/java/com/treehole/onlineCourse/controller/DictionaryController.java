package com.treehole.onlineCourse.controller;

import com.treehole.api.onlineCourse.CategoryControllerApi;
import com.treehole.api.onlineCourse.DictionaryControllerApi;
import com.treehole.framework.domain.onlineCourse.Dictionary;
import com.treehole.framework.domain.onlineCourse.ext.CategoryNode;
import com.treehole.onlineCourse.service.CategoryService;
import com.treehole.onlineCourse.service.DictionaryService;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author cc
 * @date 2019/10/29 19:38
 */
@RestController
@RequestMapping("/course")
public class DictionaryController implements DictionaryControllerApi {

    @Autowired
    private DictionaryService dictionaryService;

    //根据dtype查询字典数据
    @Override
    @GetMapping("/dictionary/finddictionarybydtype/{dType}")
    public List<Dictionary> findDictionaryByDType(@PathVariable("dType") String dType) {
        return dictionaryService.findCategoryList(dType);
    }
}
