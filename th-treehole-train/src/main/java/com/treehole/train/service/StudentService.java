package com.treehole.train.service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.treehole.framework.domain.train.Cost;
import com.treehole.framework.domain.train.Student;
import com.treehole.framework.domain.train.ext.StudentCourse;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.train.config.RootPropeties;
import com.treehole.train.dao.CostRepository;
import com.treehole.train.dao.StudentMapper;
import com.treehole.train.dao.StudentRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
@Transactional
public class StudentService {

    @Autowired
    CostRepository costRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    StudentMapper studentMapper;
    @Autowired
    RootPropeties rootPropeties;

//信息管理
   //学员管理
       //基本信息管理

    //添加学生信息
    public ResponseResult addStudent(Student student){
        Date date = new Date();
        student.setStudentEnrollmentTime(date);
        Student save = studentRepository.save(student);
        if(save!=null){
            return new ResponseResult(CommonCode.SUCCESS);
        }
        return new ResponseResult(CommonCode.FAIL);
    }
    //删除学生信息
    public ResponseResult deleteStudent(String studentId){
        studentRepository.deleteById(studentId);
        return new ResponseResult(CommonCode.SUCCESS);
    }
    //修改学生信息
    public ResponseResult updateStudent(String studentId,Student student){
        Optional<Student> optional = studentRepository.findById(studentId);
        Student returnStudent = null;
        if(optional.isPresent()){
            returnStudent = optional.get();
        }
        //拷贝
        BeanUtils.copyProperties(student,returnStudent);
        returnStudent.setStudentId(studentId);
        studentRepository.save(returnStudent);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    //通过id查询学生信息
    public QueryResponseResult<Student> findStudentByFuzzyQuery(int page ,Student student){

        if(student.getStudentId()==null){

        }else  if(student.getStudentId().equals("") ){
            student.setStudentId(null);
        }

        //定义适配器
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("studentName", ExampleMatcher.GenericPropertyMatchers.contains());
        Example<Student> example = Example.of(student, exampleMatcher);

        if(page<=0){
            page = 1;
        }
        page = page - 1;
        //分页
        PageRequest pageRequest = new PageRequest(page,rootPropeties.getPageSize());
        Page<Student> all = studentRepository.findAll(example, pageRequest);

        QueryResult queryResult = new QueryResult();
        queryResult.setList(all.getContent());
        queryResult.setTotal(all.getTotalElements());
        return new QueryResponseResult<Student>(CommonCode.SUCCESS, queryResult);
    }


    //查询所有学生信息
    public QueryResponseResult<Student> findAllStudent(int page) {
        if(page<=0){
            page=1;
        }
       // page = page - 1;
        com.github.pagehelper.Page<Student> studentPage = PageHelper.startPage(page, rootPropeties.getPageSize());
        List<Student> list = studentMapper.findAllStudent();
        PageInfo<Student> info = new PageInfo<>(studentPage.getResult());
        long total = info.getTotal();
        QueryResult queryResult = new QueryResult();
        queryResult.setList(list);
        queryResult.setTotal(total);
        if(list!=null){
            return new QueryResponseResult<Student>(CommonCode.SUCCESS,queryResult);
        }else {

            return new QueryResponseResult<Student>(CommonCode.FAIL,null);
        }
    }
//==============================
    //统计报表
        //学员信息统计

    //查询学生课程
    public QueryResponseResult<StudentCourse> findStudentCourse(int page,String studentId) {
        if(page<=0){
            page=1;
        }
       // page = page - 1;
        com.github.pagehelper.Page<StudentCourse> studentCoursePage = PageHelper.startPage(page, rootPropeties.getPageSize());
        List<StudentCourse>  studentCourses = studentMapper.findStudentCourse(studentId);
        PageInfo<StudentCourse> info = new PageInfo<>(studentCoursePage.getResult());
        long total = info.getTotal();
        QueryResult queryResult = new QueryResult();
        queryResult.setList(studentCourses);
        queryResult.setTotal(total);
        return new QueryResponseResult<StudentCourse>(CommonCode.SUCCESS,queryResult);
    }

//=================================
//统计报表
    //学员信息统计
        //交费统计

    //学生交费记录
    public QueryResponseResult<Cost> findPay(int page,String studentId){
        if(page<=0){
            page=1;
        }
      //  page = page - 1;
        com.github.pagehelper.Page<HashMap<String, Object>> hashMapPage = PageHelper.startPage(page, rootPropeties.getPageSize());
        List<HashMap<String, Object>> costs = studentMapper.findPay(studentId);
        PageInfo<HashMap<String, Object>> info = new PageInfo<>(hashMapPage.getResult());
        long total = info.getTotal();
        QueryResult queryResult = new QueryResult();
        queryResult.setList(costs);
        queryResult.setTotal(total);
        return new QueryResponseResult<Cost>(CommonCode.SUCCESS,queryResult);
    }

    //学生欠费记录
    public QueryResponseResult<Cost> findArrears(int page,String studentId){
        if(page<=0){
            page=1;
        }
     //   page = page - 1;
        com.github.pagehelper.Page<HashMap<String, Object>> hashMapPage = PageHelper.startPage(page, rootPropeties.getPageSize());
        List<HashMap<String, Object>> costs =  studentMapper.findArrears(studentId);
        PageInfo<HashMap<String, Object>> info = new PageInfo<>(hashMapPage.getResult());
        long total = info.getTotal();
        QueryResult queryResult = new QueryResult();
        queryResult.setList(costs);
        queryResult.setTotal(total);
        return new QueryResponseResult<Cost>(CommonCode.SUCCESS,queryResult);
    }

}
