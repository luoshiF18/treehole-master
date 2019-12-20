package com.treehole.train.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.treehole.framework.domain.train.Class;
import com.treehole.framework.domain.train.Teacher;
import com.treehole.framework.domain.train.User;
import com.treehole.framework.domain.train.ext.ClassHeadmaster;
import com.treehole.framework.domain.train.ext.TeacherCourseOfTeach;
import com.treehole.framework.domain.train.response.TrainCode;
import com.treehole.framework.model.response.*;
import com.treehole.train.config.RootPropeties;
import com.treehole.train.dao.TeacherMapper;
import com.treehole.train.dao.TeacherRepository;
import com.treehole.train.dao.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
public class TeacherService {

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    RootPropeties rootPropeties;

    @Autowired
    TeacherMapper teacherMapper;

    @Autowired
    GenerateNumberService generateNumberService;

    @Autowired
    UserRepository userRepository;
    //添加老师信息
    @Transactional
    public ResponseResult addTeacher(Teacher teacher){
        teacher.setTeacherState(1);
        //添加老师自动生成的Id
        String tId = generateNumberService.GenerateNumber("2");
        teacher.setTeacherId(tId);
        Teacher save = teacherRepository.save(teacher);
        //添加老师user
        User user = new User();
        user.setUserName(tId);
        user.setUserPassword(tId);
        user.setUserType(2);
        userRepository.save(user);

        if(save!=null){
            return new ResponseResult(CommonCode.SUCCESS);
        }
        return new ResponseResult(CommonCode.FAIL);
    }
    //删除老师信息
    @Transactional
    public ResponseResult deleteTeacher(String teacherId){
        //查询老师没有没带课
        int courseNum = teacherMapper.findCourseByTeacherId(teacherId);
        //查询老师没有没带班级
        int classNum = teacherMapper.findClassByTeacherId(teacherId);
        if(courseNum != 0){
            return new ResponseResult(TrainCode.TEACHER_HAVING_COURSE);
        }else if (classNum != 0){
            return new ResponseResult(TrainCode.TEACHER_HAVING_CLASS);
        }else {
            teacherRepository.deleteById(teacherId);
            return new ResponseResult(CommonCode.SUCCESS);
        }
    }

    //修改老师信息
    public ResponseResult updateTeacher(String teacherId,Teacher teacher){

        teacher.setTeacherId(teacherId);
        teacherRepository.save(teacher);
        return new ResponseResult(CommonCode.SUCCESS);

    }

    //通过id和模糊查询查询老师信息
    public QueryResponseResult<Teacher> findTeacherByFuzzyQuery(int page,int size,Teacher teacher) {

            if(page<=0){
            page = 1;
            }

        com.github.pagehelper.Page<Teacher> teacherPage = PageHelper.startPage(page, size);
        List<Teacher> list = teacherMapper.findTeacherByFuzzyQuery(teacher);
        PageInfo<Teacher> info = new PageInfo<>(teacherPage.getResult());
        long total = info.getTotal();
        QueryResult queryResult = new QueryResult();
        queryResult.setList(list);
        queryResult.setTotal(total);

        if(list!=null){
            return new QueryResponseResult<Teacher>(CommonCode.SUCCESS,queryResult);
        }else {
            return new QueryResponseResult<Teacher>(CommonCode.FAIL,null);
        }
    }

    //查询老师所教的班级
    public QueryResponseResult<ClassHeadmaster> findTeacherClass(int page, int size, ClassHeadmaster classHeadmaster) {

        if(page<=0){
            page = 1;
        }

        com.github.pagehelper.Page<ClassHeadmaster> teacherPage = PageHelper.startPage(page, size);
        List<ClassHeadmaster> list = teacherMapper.findTeacherClass(classHeadmaster);
        PageInfo<ClassHeadmaster> info = new PageInfo<>(teacherPage.getResult());
        long total = info.getTotal();
        QueryResult queryResult = new QueryResult();
        queryResult.setList(list);
        queryResult.setTotal(total);

        if(list!=null){
            return new QueryResponseResult<ClassHeadmaster>(CommonCode.SUCCESS,queryResult);
        }else {
            return new QueryResponseResult<ClassHeadmaster>(CommonCode.FAIL,null);
        }

    }

    //查询老师所教的课程
    public QueryResponseResult<TeacherCourseOfTeach> findTeacherCourseOfTeaching(int page, int size, TeacherCourseOfTeach teacherCourseOfTeach) {

        if(page<=0){
            page = 1;
        }

        com.github.pagehelper.Page<TeacherCourseOfTeach> teacherPage = PageHelper.startPage(page, size);
        List<TeacherCourseOfTeach> list = teacherMapper.findTeacherCourseOfTeaching(teacherCourseOfTeach);
        PageInfo<TeacherCourseOfTeach> info = new PageInfo<>(teacherPage.getResult());
        long total = info.getTotal();
        QueryResult queryResult = new QueryResult();
        queryResult.setList(list);
        queryResult.setTotal(total);

        if(list!=null){
            return new QueryResponseResult<TeacherCourseOfTeach>(CommonCode.SUCCESS,queryResult);
        }else {
            return new QueryResponseResult<TeacherCourseOfTeach>(CommonCode.FAIL,null);
        }

    }

    //查询班主任所带的班级
    public QueryResponseResult<Class>findClassOfHeadTeacher(int page, int size, ClassHeadmaster classHeadmaster) {

        if(page<=0){
            page = 1;
        }

        com.github.pagehelper.Page<Class> teacherPage = PageHelper.startPage(page, size);
        List<Class> list = teacherMapper.findClassOfHeadTeacher(classHeadmaster);
        PageInfo<Class> info = new PageInfo<>(teacherPage.getResult());
        long total = info.getTotal();
        QueryResult queryResult = new QueryResult();
        queryResult.setList(list);
        queryResult.setTotal(total);

        if(list!=null){
            return new QueryResponseResult<Class>(CommonCode.SUCCESS,queryResult);
        }else {
            return new QueryResponseResult<Class>(CommonCode.FAIL,null);
        }

    }
}
