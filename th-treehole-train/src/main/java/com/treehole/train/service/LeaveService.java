package com.treehole.train.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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

        //修改状态
        if(leave.getLeavePeopleType().equals("学生")){
            if (leave.getLeavePeopleId()!=null){
                //修改学生状态
                Optional<Student> optional = studentRepository.findById(leave.getLeavePeopleId());
                Student student = null;
                if(optional.isPresent()){
                    student = optional.get();
                }
                peopleName = student.getStudentName();
                student.setStudentState("请假中");
                student.setStudentId(leave.getLeavePeopleId());
                studentRepository.save(student);
            }
        }else if(leave.getLeavePeopleType().equals("老师")){
            if (leave.getLeavePeopleId()!=null ){
                //修改老师状态
                Optional<Teacher> optional = teacherRepository.findById(leave.getLeavePeopleId());
                Teacher teacher = null;
                if(optional.isPresent()){
                    teacher = optional.get();
                }
                peopleName = teacher.getTeacherName();
                teacher.setTeacherState("请假中");
                teacher.setTeacherId(leave.getLeavePeopleId());
                teacherRepository.save(teacher);
            }
        }


        leave.setLeaveState("请假中");
        if(leave.getLeavePeopleName()==null ||leave.getLeavePeopleName().equals("") ){
            leave.setLeavePeopleName(peopleName);
        }
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
        leave.setLeaveState("已消假");
        Date date = new Date();
        leave.setLeaveRemoveTime(date);
        leaveRepository.save(leave);
        //修改状态
        //修改状态
        if(leave.getLeavePeopleType().equals("学生")){
            if (leave.getLeavePeopleId()!=null){
                //修改学生状态
                Optional<Student> studentOptional= studentRepository.findById(leave.getLeavePeopleId());
                Student student = null;
                if(studentOptional.isPresent()){
                    student = studentOptional.get();
                }
                student.setStudentState("正常");
                student.setStudentId(leave.getLeavePeopleId());
                studentRepository.save(student);
            }
        }else if(leave.getLeavePeopleType().equals("老师")){
            if (leave.getLeavePeopleId()!=null){
                //修改老师状态
                Optional<Teacher> teacherOptional = teacherRepository.findById(leave.getLeavePeopleId());
                Teacher teacher = null;
                if(teacherOptional.isPresent()){
                    teacher = teacherOptional.get();
                }
                teacher.setTeacherState("正常");
                teacher.setTeacherId(leave.getLeavePeopleId());
                teacherRepository.save(teacher);
            }
        }
       return new ResponseResult(CommonCode.SUCCESS);
    }

    //请假信息统计
    @Transactional
    public QueryResponseResult LeaveStatistics(int page,String type ,String id){
        if(page<=0){
            page=1;
        }
     //   page = page - 1;
        Page<Leave> leavePage = PageHelper.startPage(page, rootPropeties.getPageSize());
        QueryResult<Leave> queryResult = new QueryResult<>();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("peopleType",type);
        hashMap.put("peopleId",id);
        List<Leave> list = leaveMapper.findByLeavePeopleIdAndLeavePeopleType(hashMap);
        PageInfo<Leave> info = new PageInfo<>(leavePage.getResult());
        long total = info.getTotal();
        queryResult.setTotal(total);
        queryResult.setList(list);
        return  new QueryResponseResult(CommonCode.SUCCESS,queryResult);

    }

}
