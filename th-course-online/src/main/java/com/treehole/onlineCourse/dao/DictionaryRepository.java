package com.treehole.onlineCourse.dao;

import com.treehole.framework.domain.onlineCourse.CourseBase;
import com.treehole.framework.domain.onlineCourse.Dictionary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Administrator.
 */
public interface DictionaryRepository extends JpaRepository<Dictionary,String> {

    //根据dtype查询字典
    public List<Dictionary> findByDType(String dType);
}
