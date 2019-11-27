package com.treehole.train.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.treehole.framework.domain.train.CourseType;
import com.treehole.framework.domain.train.Leave;
import com.treehole.framework.domain.train.Student;
import com.treehole.framework.domain.train.Teacher;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.train.config.RootPropeties;
import com.treehole.train.dao.LeaveMapper;
import com.treehole.train.dao.LeaveRepository;
import com.treehole.train.dao.StudentRepository;
import com.treehole.train.dao.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
@Service
public class LeaveService {
    @Autowired
    LeaveRepository leaveRepository;

    @Autowired
    RootPropeties rootPropeties;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    LeaveMapper leaveMapper;


    //请假
    @Transactional
    public ResponseResult addLeave(Leave leave){

        //得到请假人的姓名
       String peopleName = null;

        //修改状态  1 代表学生
        if(leave.getLeavePeopleType() == 1){
            if (leave.getLeavePeopleId()!=null){
                //修改学生状态
                Optional<Student> optional = studentRepository.findById(leave.getLeavePeopleId());
                Student student = null;
                if(optional.isPresent()){
                    student = optional.get();
                }
                peopleName = student.getStudentName();
                student.setStudentState(2);
                student.setStudentId(leave.getLeavePeopleId());
                studentRepository.save(student);
            }
        }else if(leave.getLeavePeopleType() == 2){ // 2 代表老师
            if (leave.getLeavePeopleId()!=null ){
                //修改老师状态
                Optional<Teacher> optional = teacherRepository.findById(leave.getLeavePeopleId());
                Teacher teacher = null;
                if(optional.isPresent()){
                    teacher = optional.get();
                }
                peopleName = teacher.getTeacherName();
                teacher.setTeacherState(2);
                teacher.setTeacherId(leave.getLeavePeopleId());
                teacherRepository.save(teacher);
            }
        }


        leave.setLeaveState(2);
        if(leave.getLeavePeopleName()==null ||leave.getLeavePeopleName().equals("") ){
            leave.setLeavePeopleName(peopleName);
        }
        //添加请假时间
        Date date = new Date();
        leave.setLeaveTime(date);
        Leave save = leaveRepository.save(leave);


        if(save!=null){
            return new ResponseResult(CommonCode.SUCCESS);
        }
        return new ResponseResult(CommonCode.FAIL);
    }

    //消假
    @Transactional
    public ResponseResult removeLeave(String leaveId){
       Optional<Leave> optional = leaveRepository.findById(leaveId);
       Leave leave = null;
       if(optional.isPresent()){
           leave = optional.get();
       }
       //消假
        leave.setLeaveState(1);
        Date date = new Date();
        leave.setLeaveRemoveTime(date);
        leaveRepository.save(leave);
        //修改状态 1 代表老师
        if(leave.getLeavePeopleType() == 1){
            if (leave.getLeavePeopleId()!=null){
                //修改学生状态
                Optional<Student> studentOptional= studentRepository.findById(leave.getLeavePeopleId());
                Student student = null;
                if(studentOptional.isPresent()){
                    student = studentOptional.get();
                }
                student.setStudentState(1);
                student.setStudentId(leave.getLeavePeopleId());
                studentRepository.save(student);
            }
        }else if(leave.getLeavePeopleType() == 2){ // 2 代表老师
            if (leave.getLeavePeopleId()!=null){
                //修改老师状态
                Optional<Teacher> teacherOptional = teacherRepository.findById(leave.getLeavePeopleId());
                Teacher teacher = null;
                if(teacherOptional.isPresent()){
                    teacher = teacherOptional.get();
                }
                teacher.setTeacherState(1);
                teacher.setTeacherId(leave.getLeavePeopleId());
                teacherRepository.save(teacher);
            }
        }
       return new ResponseResult(CommonCode.SUCCESS);
    }

    //请假信息统计(请假中的人员信息统计)
    @Transactional
    public QueryResponseResult<Leave> LeaveStatistics(int page,int size ,Leave leave){
        if(page<=0){
            page=1;
        }
        Page<Leave> leavePage = PageHelper.startPage(page, size);
        List<Leave> list = leaveMapper.findLeaveByFuzzyQuery(leave);
        PageInfo<Leave> info = new PageInfo<>(leavePage.getResult());
        long total = info.getTotal();
        QueryResult queryResult = new QueryResult();
        queryResult.setList(list);
        queryResult.setTotal(total);
        if(list!=null){
            return new QueryResponseResult<Leave>(CommonCode.SUCCESS,queryResult);
        }else {
            return new QueryResponseResult<Leave>(CommonCode.FAIL,null);
        }

    }

    //请假信息统计(所有人员信息统计)
    public QueryResponseResult<Leave> LeaveAllStatistics(int page,int size ,Leave leave){
        if(page<=0){
            page=1;
        }
        Page<Leave> leavePage = PageHelper.startPage(page, size);
        List<Leave> list = leaveMapper.findLeaveAll(leave);
        PageInfo<Leave> info = new PageInfo<>(leavePage.getResult());
        long total = info.getTotal();
        QueryResult queryResult = new QueryResult();
        queryResult.setList(list);
        queryResult.setTotal(total);
        if(list!=null){
            return new QueryResponseResult<Leave>(CommonCode.SUCCESS,queryResult);
        }else {
            return new QueryResponseResult<Leave>(CommonCode.FAIL,null);
        }

    }

    //根据Id得到请假人信息
    public Leave findInfo(String id){
        String typeId = id.substring(0, 1);
        int type = Integer.parseInt(typeId);

        if(typeId .equals("1")){
            //
            Student student = null;
            Optional<Student> optionalStudent = studentRepository.findById(id);
            if(optionalStudent.isPresent()){
             student = optionalStudent.get();
            }
            String studentName = student.getStudentName();
            Leave leave = new Leave();
            leave.setLeavePeopleName(studentName);
            leave.setLeavePeopleType(type);
            return leave;

        }else if(typeId .equals("2")){
            //
            Teacher teacher = null;
            Optional<Teacher> optionalTeacher = teacherRepository.findById(id);
            if(optionalTeacher.isPresent()){
                teacher = optionalTeacher.get();
            }
            String teacherName = teacher.getTeacherName();
            Leave leave = new Leave();
            leave.setLeavePeopleName(teacherName);
            leave.setLeavePeopleType(type);
            return leave;
        }
    return null;

    }




}
