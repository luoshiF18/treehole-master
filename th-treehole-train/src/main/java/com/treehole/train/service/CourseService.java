package com.treehole.train.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.treehole.framework.domain.train.Course;
import com.treehole.framework.domain.train.Student;
import com.treehole.framework.domain.train.Teacher;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.train.config.RootPropeties;
import com.treehole.train.dao.CourseMapper;
import com.treehole.train.dao.CourseRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class CourseService {

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    RootPropeties rootPropeties;

    @Autowired
    CourseMapper courseMapper;

    //添加课程信息
    public ResponseResult addCourse(Course course){
        Course save = courseRepository.save(course);
        if(save!=null){
            return new ResponseResult(CommonCode.SUCCESS);
        }
        return new ResponseResult(CommonCode.FAIL);
    }

    //删除课程信息
    public ResponseResult deleteCourse(String courseId){
        courseRepository.deleteById(courseId);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    //修改课程信息
    public ResponseResult updateCourse(String courseId,Course course){
        Optional<Course> optional = courseRepository.findById(courseId);
        Course returnCourse = null;
        if(optional.isPresent()){
            returnCourse = optional.get();
        }
        //拷贝
        BeanUtils.copyProperties(course,returnCourse);
        returnCourse.setCourseId(courseId);
        courseRepository.save(returnCourse);
        return new ResponseResult(CommonCode.SUCCESS);
    }
    //通过id查询课程信息
    public QueryResponseResult<Course> findCourseByFuzzyQuery(int page,Course course){
        if(page<=0){
            page = 1;
        }
        Page<Course> coursePage = PageHelper.startPage(page, rootPropeties.getPageSize());
        List<Course> courses = courseMapper.findCourseByFuzzyQuery(course);
        PageInfo<Course> info = new PageInfo<>(coursePage.getResult());
        long total = info.getTotal();
        QueryResult queryResult = new QueryResult();
        queryResult.setList(courses);
        queryResult.setTotal(total);
        return new QueryResponseResult<Course>(CommonCode.SUCCESS, queryResult);
    }




    //查询所有课程信息
    public QueryResponseResult<Course> findAllCourse(int page) {
        if(page<=0){
            page=1;
        }
      //  page = page - 1;
        Page<Course> coursePage = PageHelper.startPage(page, rootPropeties.getPageSize());
        List<Course> list = courseMapper.findAllCourse();
        PageInfo<Course> info = new PageInfo<>(coursePage.getResult());
        long total = info.getTotal();
        QueryResult queryResult = new QueryResult();
        queryResult.setList(list);
        queryResult.setTotal(total);
        if(list!=null){
            return new QueryResponseResult<Course>(CommonCode.SUCCESS,queryResult);
        }else {
            return new QueryResponseResult<Course>(CommonCode.FAIL,null);
        }
    }
}
