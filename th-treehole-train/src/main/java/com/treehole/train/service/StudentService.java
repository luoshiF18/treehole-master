package com.treehole.train.service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.treehole.framework.domain.train.*;
import com.treehole.framework.domain.train.Class;
import com.treehole.framework.domain.train.ext.CourseTeacher;
import com.treehole.framework.domain.train.ext.StudentCourseParams;
import com.treehole.framework.domain.train.ext.StudentExt;
import com.treehole.framework.domain.train.ext.TeacherExt;
import com.treehole.framework.domain.train.response.TrainCode;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.train.config.RootPropeties;
import com.treehole.train.dao.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    ClassRepository classRepository;
    @Autowired
    StudentMapper studentMapper;
    @Autowired
    RootPropeties rootPropeties;
    @Autowired
    CostService costService;
    @Autowired
    PhaseRepository phaseRepository;
    @Autowired
    GenerateNumberService generateNumberService;
    @Autowired
    UserRepository userRepository;
//信息管理
   //学员管理
       //基本信息管理

    //添加学生信息
    /**
     * 自动生成学生id，添加学生信息 ，班级人数加1
     * @param student
     * @return
     */
    @Transactional
    public ResponseResult addStudent(Student student){
        Date date = new Date();
        student.setStudentState(1);
        student.setStudentType(1);
        student.setStudentEnrollmentTime(date);
        student.setStudentGraduation(1);
        student.setStudentArrears(1);
        //添加生成的Id
        String sId = generateNumberService.GenerateNumber("1");
        student.setStudentId(sId);

        //添加学生信息
        Student save = studentRepository.save(student);

        //先交费0元生成欠费信息
        this.payZero(sId,student);

        //班级人数加1
        String studentClass = student.getStudentClass();
        Optional<Class> optionalClass = classRepository.findById(studentClass);
        Class newClass = null;
        if(optionalClass.isPresent()){
             newClass = optionalClass.get();
        }
        Integer classPlannedNumber = newClass.getClassPlannedNumber();
        Integer classNumber = newClass.getClassNumber();
        if(classNumber >= classPlannedNumber){
            ExceptionCast.cast(TrainCode.CLASS_IS_FULL);
        }
        classNumber++;
        newClass.setClassNumber(classNumber);
        classRepository.save(newClass);

        //添加学生user
        User user = new User();
        user.setUserName(sId);
        user.setUserPassword(sId);
        user.setUserType(1);
        userRepository.save(user);

        if(save != null){
            return new ResponseResult(CommonCode.SUCCESS);
        }else {
            return new ResponseResult(CommonCode.FAIL);
        }

    }

    //先交费0元生成欠费信息
    public void payZero(String sId , Student student){
        String studentPhase = student.getStudentPhase();
        Optional<Phase> optionalPhase = phaseRepository.findById(studentPhase);
        Phase phase = null;
        if(optionalPhase.isPresent()){
            phase = optionalPhase.get();
        }

        Cost cost = new Cost();
        cost.setCostStudentId(sId);
        //应付金额
        cost.setCostAmountPayable(phase.getPhaseTuition());
        //优惠金额
        cost.setCostPreferentialAmount( phase.getPhasePreferentialAmount());
        cost.setCostOther("新生信息录入系统,自动欠费");
        ResponseResult pay = costService.pay(cost);
    }

    //删除学生信息
    @Transactional
    public ResponseResult deleteStudent(String studentId){
        //如果学生有欠费 不能删除
        Student student1 = new Student();
        student1.setStudentId(studentId);
        QueryResponseResult<Cost> pay = this.findPay(1, 0, student1);
        QueryResult<Cost> queryResult = pay.getQueryResult();
        List<Cost> list = queryResult.getList();
        if(list.size() == 0){
            ExceptionCast.cast(TrainCode.NO_TUITION_FEES);
        }
         //得到欠费金额
        Cost cost = list.get(list.size()-1);
        double costArrears = cost.getCostArrears();
        if(costArrears == 0 || costArrears == 0.0 || costArrears == 0.00){
            //班级人数减1
            Optional<Student> optionalStudent = studentRepository.findById(studentId);
            Student student =null;
            if(optionalStudent.isPresent()){
                student = optionalStudent.get();
            }
            String studentClass = student.getStudentClass();
            Optional<Class> optionalClass = classRepository.findById(studentClass);
            Class newClass = null;
            if(optionalClass.isPresent()){
                newClass = optionalClass.get();
            }
            Integer classNumber = newClass.getClassNumber() - 1;
            newClass.setClassNumber(classNumber);
            classRepository.save(newClass);
            //删除学生
            studentRepository.deleteById(studentId);
            return new ResponseResult(CommonCode.SUCCESS);
        }else {
            ExceptionCast.cast(TrainCode.TUITION_IS_NOT_PAID);
        }
        return null;
    }

    //修改学生信息
    public ResponseResult updateStudent(String studentId,Student student){
        student.setStudentId(studentId);
        studentRepository.save(student);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    //通过id查询学生信息
    public QueryResponseResult<StudentExt> findStudentByFuzzyQuery(int page ,int size,Student student){

        com.github.pagehelper.Page<StudentExt> studentPage = PageHelper.startPage(page, size);
        List<StudentExt> studentByFuzzyQuery = studentMapper.findStudentByFuzzyQuery(student);
        PageInfo<StudentExt> info = new PageInfo<>(studentPage.getResult());
        long total = info.getTotal();
        QueryResult queryResult = new QueryResult();
        queryResult.setList(studentByFuzzyQuery);
        queryResult.setTotal(total);
        if(studentByFuzzyQuery!=null){
            return new QueryResponseResult<StudentExt>(CommonCode.SUCCESS,queryResult);
        }else {

            return new QueryResponseResult<StudentExt>(CommonCode.FAIL,null);
        }
    }


    //学生毕业(个人）
    @Transactional
    public ResponseResult studentGraduation(String studentId){
        Optional<Student> studentOptional = studentRepository.findById(studentId);
        Student student = null;
        if(studentOptional.isPresent()) {
             student = studentOptional.get();
        }
        student.setStudentGraduation(2);
        Date date = new Date();
        student.setStudentGraduationTime(date);
        studentRepository.save(student);

        return new ResponseResult(CommonCode.SUCCESS);
    }


    //学生毕业(全班）
    @Transactional
    public ResponseResult studentGraduationAllClass(String classId){
        //查询该班目前的学生
        List<Student> students = studentRepository.findByStudentClassAndStudentGraduation(classId, 1);

        for(int i=0;i<students.size();i++){
            Student student = students.get(i);
            student.setStudentGraduation(2);
            Date date = new Date();
            student.setStudentGraduationTime(date);
            studentRepository.save(student);
        }

        //班级毕业
        Optional<Class> optionalClass = classRepository.findById(classId);
        Class newClass = null;
        if(optionalClass.isPresent()){
            newClass = optionalClass.get();
        }
        newClass.setClassGraduation(2);
        Date date = new Date();
        newClass.setClassEndTime(date);
        classRepository.save(newClass);

        return new ResponseResult(CommonCode.SUCCESS);
    }

//==============================
    //统计报表
        //学员信息统计

    //查询学生课程
    public QueryResponseResult<CourseTeacher> findStudentCourse(int page, int size, StudentCourseParams studentCourseParams) {
        if(page<=0){
            page=1;
        }
        com.github.pagehelper.Page<CourseTeacher> studentCoursePage = PageHelper.startPage(page, size);
        List<CourseTeacher>  studentCourses = studentMapper.findStudentCourse(studentCourseParams);
        PageInfo<CourseTeacher> info = new PageInfo<>(studentCoursePage.getResult());
        long total = info.getTotal();
        QueryResult queryResult = new QueryResult();
        queryResult.setList(studentCourses);
        queryResult.setTotal(total);
        return new QueryResponseResult<CourseTeacher>(CommonCode.SUCCESS,queryResult);
    }

    //查询学生老师
    public QueryResponseResult<Teacher> findStudentTeacher(int page, int size, TeacherExt teacherExt) {
        if(page<=0){
            page=1;
        }
        com.github.pagehelper.Page<Teacher> studentCoursePage = PageHelper.startPage(page, size);
        List<Teacher> studentTeacher = studentMapper.findStudentTeacher(teacherExt);
        PageInfo<Teacher> info = new PageInfo<>(studentCoursePage.getResult());
        long total = info.getTotal();
        QueryResult queryResult = new QueryResult();
        queryResult.setList(studentTeacher);
        queryResult.setTotal(total);
        return new QueryResponseResult<Teacher>(CommonCode.SUCCESS,queryResult);
    }

//=================================
//统计报表
    //学员信息统计
        //交费统计(欠费记录)

    //学生交费记录(欠费记录)
    public QueryResponseResult<Cost> findPay(int page,int size,Student student){
        if(page<=0){
            page=1;
        }
        com.github.pagehelper.Page<Cost> hashMapPage = PageHelper.startPage(page, size);
        List<Cost> costs = studentMapper.findPay(student);
        PageInfo<Cost> info = new PageInfo<>(hashMapPage.getResult());
        long total = info.getTotal();
        QueryResult queryResult = new QueryResult();
        queryResult.setList(costs);
        queryResult.setTotal(total);
        return new QueryResponseResult<Cost>(CommonCode.SUCCESS,queryResult);
    }

}
