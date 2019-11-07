package com.treehole.train.service;

import com.treehole.framework.domain.train.ClassCourse;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.train.dao.ClassCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClassCourseService {

    @Autowired
    ClassCourseRepository classCourseRepository;


    public ResponseResult AddClassCourse(ClassCourse classCourse) {

       ClassCourse save =  classCourseRepository.save(classCourse);
        if(save!=null){
            return new ResponseResult(CommonCode.SUCCESS);
        }
        return new ResponseResult(CommonCode.FAIL);
    }

    public ResponseResult deleteClassCourse(String classId, String courseId) {
      Optional<ClassCourse> optional =  classCourseRepository.findByClassIdAndCourseId(classId,courseId);
      ClassCourse classCourse = null;
      if(optional.isPresent()){
            classCourse = optional.get();
        }
      if(classCourse == null){
          return new ResponseResult(CommonCode.FAIL);

      }
        classCourseRepository.deleteById(classCourse.getId());
        return new ResponseResult(CommonCode.SUCCESS);
    }



}
