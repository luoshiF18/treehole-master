package com.treehole.onlineCourse.service;

import com.treehole.framework.domain.onlineCourse.Dictionary;
import com.treehole.framework.domain.onlineCourse.ext.CategoryNode;
import com.treehole.onlineCourse.dao.CategoryMapper;
import com.treehole.onlineCourse.dao.DictionaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author cc
 * @date 2019/10/29 19:33
 */
@Service
public class DictionaryService {

    @Autowired
    private DictionaryRepository dictionaryRepository;

    //根据dtype查询字典数据
    public List<Dictionary> findCategoryList(String dType) {
        return dictionaryRepository.findByDType(dType);
    }
}
