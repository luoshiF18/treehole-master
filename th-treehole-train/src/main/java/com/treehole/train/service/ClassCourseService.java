package com.treehole.train.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.treehole.framework.domain.train.Class;
import com.treehole.framework.domain.train.ClassCourse;
import com.treehole.framework.domain.train.Course;
import com.treehole.framework.domain.train.ext.CourseList;
import com.treehole.framework.domain.train.ext.CourseTeacher;
import com.treehole.framework.domain.train.ext.SelectCourses;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.train.dao.ClassCourseMapper;
import com.treehole.train.dao.ClassCourseRepository;
import com.treehole.train.dao.ClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClassCourseService {

    @Autowired
    ClassCourseRepository classCourseRepository;
    @Autowired
    ClassCourseMapper classCourseMapper;
    @Autowired
    ClassRepository classRepository;

    //选课
    public ResponseResult addClassCourse(String classId , CourseList courseList) {
        List<Course> courseList1 = courseList.getCourseList();
        for(int i=0;i<courseList1.size();i++){

            Course course = courseList1.get(i);
            String courseId = course.getCourseId();

            ClassCourse classCourse = new ClassCourse();
            classCourse.setClassId(classId);
            classCourse.setCourseId(courseId);
            ClassCourse save =  classCourseRepository.save(classCourse);
            //班级课程数 +1
            Optional<Class> classOptional = classRepository.findById(classId);
            Class class1 = null;
            if(classOptional.isPresent()){
                class1 = classOptional.get();
            }
            //得到课程数
            Integer classCourseNumber = class1.getClassCourseNumber();
            //课程数+1
            classCourseNumber++;
            class1.setClassCourseNumber(classCourseNumber);
            classRepository.save(class1);
        }

        return new ResponseResult(CommonCode.SUCCESS);

    }

    //退课
    public ResponseResult deleteClassCourse(String classId, String courseId) {
         classCourseMapper.deleteClassCourse(classId, courseId);

        //课程数-1
        Optional<Class> classOptional = classRepository.findById(classId);
        Class class1 = null;
        if(classOptional.isPresent()){
            class1 = classOptional.get();
        }
        //得到课程数
        Integer classCourseNumber = class1.getClassCourseNumber();
        //课程数*-1
        classCourseNumber--;
        class1.setClassCourseNumber(classCourseNumber);
        classRepository.save(class1);

        return new ResponseResult(CommonCode.SUCCESS);
    }

    //查询需要进行选择的课程
    public QueryResponseResult<CourseTeacher> findCourseToBeSelect(int page , int size , String classId, SelectCourses selectCourses) {
        if(page<=0){
            page = 1;
        }
        //查询选课表里的课程id
        List<String> courseId = classCourseMapper.findSelectCourseId(classId);
        if(courseId.size()==0){
            courseId.add("");
        }
        selectCourses.setCourseList(courseId);
        //查询需要进行选择的课程
        Page<CourseTeacher> coursePage = PageHelper.startPage(page, size);

        List<CourseTeacher> list = classCourseMapper.findCourseToBeSelect(selectCourses);
        PageInfo<CourseTeacher> info = new PageInfo<>(coursePage.getResult());
        long total = info.getTotal();
        QueryResult queryResult = new QueryResult();
        queryResult.setList(list);
        queryResult.setTotal(total);
        if(list!=null){
            return new QueryResponseResult<CourseTeacher>(CommonCode.SUCCESS,queryResult);
        }else {
            return new QueryResponseResult<CourseTeacher>(CommonCode.FAIL,null);
        }
    }

}
