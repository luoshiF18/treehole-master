package com.treehole.train.dao;

import com.treehole.framework.domain.train.Teacher;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface TeacherMapper {

    //查询所有老师信息
     List<Teacher> findAllTeacher();





}
