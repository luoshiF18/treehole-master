package com.treehole.train.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.treehole.framework.domain.train.Teacher;
import com.treehole.framework.domain.train.response.TrainCode;
import com.treehole.framework.model.response.*;
import com.treehole.train.config.RootPropeties;
import com.treehole.train.dao.TeacherMapper;
import com.treehole.train.dao.TeacherRepository;
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
    //添加老师信息
    public ResponseResult addTeacher(Teacher teacher){
        teacher.setTeacherState(1);
        //添加老师自动生成的Id
        String tId = generateNumberService.GenerateNumber("2");
        teacher.setTeacherId(tId);
        Teacher save = teacherRepository.save(teacher);
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

}
