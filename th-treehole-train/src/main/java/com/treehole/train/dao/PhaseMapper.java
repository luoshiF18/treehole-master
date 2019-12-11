package com.treehole.train.dao;

import com.treehole.framework.domain.train.Phase;
import com.treehole.framework.domain.train.ext.CourseExt;
import com.treehole.framework.domain.train.ext.CourseTeacher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface PhaseMapper {

    //查询期数学费信息
    List<Phase> findPhase(Phase phase);

    //查询最大id
    @Select("SELECT MAX(p.`phase_id`) FROM phase p")
    String findMaxPhaseId();

    //查看期数课程
    List<CourseTeacher> findPhaseCourse(CourseExt courseExt);

}
