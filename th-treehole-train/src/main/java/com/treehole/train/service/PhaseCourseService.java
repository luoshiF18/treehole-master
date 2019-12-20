package com.treehole.train.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.treehole.framework.domain.train.*;
import com.treehole.framework.domain.train.ext.CourseList;
import com.treehole.framework.domain.train.ext.CourseTeacher;
import com.treehole.framework.domain.train.ext.SelectCourses;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.train.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PhaseCourseService {

    @Autowired
    PhaseCourseRepository phaseCourseRepository;
    @Autowired
    PhaseCourseMapper phaseCourseMapper;
    @Autowired
    ClassRepository classRepository;
    @Autowired
    PhaseRepository phaseRepository;
    @Autowired
    PhaseMapper phaseMapper;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    ClassService classService;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    CostMapper costMapper;
    @Autowired
    CostRepository costRepository;
    //选课
    @Transactional
    public ResponseResult addClassCourse(String phaseId , CourseList courseList) {
        List<Course> courseList1 = courseList.getCourseList();
        for(int i=0;i<courseList1.size();i++){

            Course course = courseList1.get(i);
            String courseId = course.getCourseId();

            PhaseCourse phaseCourse = new PhaseCourse();
            phaseCourse.setPhaseId(phaseId);
            phaseCourse.setCourseId(courseId);
            PhaseCourse save =  phaseCourseRepository.save(phaseCourse);
            //期数课程数 +1
            Optional<Phase> phaseOptional = phaseRepository.findById(phaseId);
            Phase phase = null;
            if(phaseOptional.isPresent()){
                phase  = phaseOptional.get();
            }
            //得到课程数hase
            int phaseCourseNumber = phase.getPhaseCourseNumber();
            //课程数+1
            phaseCourseNumber++;
            phase.setPhaseCourseNumber(phaseCourseNumber);
            //学费相加
            double coursePrice = course.getCoursePrice();
            double phaseTuition = phase.getPhaseTuition();
            phase.setPhaseTuition(phaseTuition + coursePrice);
            phaseRepository.save(phase);
        }
        //更新班级课程
        classService.updateCourseNumberSelect(phaseId,courseList);
        //更新学生的欠费情况
        this.updateArrearsSituationOfCourseSelection(phaseId,courseList);
        return new ResponseResult(CommonCode.SUCCESS);

    }

    //退课
    @Transactional
    public ResponseResult deleteClassCourse(String phaseId, String courseId) {
         phaseCourseMapper.deletePhaseCourse(phaseId, courseId);

        //课程数-1
        Optional<Phase> classOptional = phaseRepository.findById(phaseId);
        Phase phase = null;
        if(classOptional.isPresent()){
            phase = classOptional.get();
        }
        //得到课程数
        Integer classCourseNumber = phase.getPhaseCourseNumber();
        //课程数*-1
        classCourseNumber--;
        phase.setPhaseCourseNumber(classCourseNumber);
        //学费相减
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        Course course = null;
        if(optionalCourse.isPresent()){
            course = optionalCourse.get();
        }
        double coursePrice = course.getCoursePrice();
        double phaseTuition = phase.getPhaseTuition();
        phase.setPhaseTuition(phaseTuition - coursePrice);
        phaseRepository.save(phase);
        //更新班级课程
        classService.updateCourseNumberRetire(phaseId,courseId);
        //更新学生欠费情况
        this.updateArrearsSituationOfCourseRetire(phaseId,courseId);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    //查询需要进行选择的课程
    public QueryResponseResult<CourseTeacher> findCourseToBeSelect(int page , int size , String phaseId, SelectCourses selectCourses) {
        if(page<=0){
            page = 1;
        }
        //查询选课表里的课程id
        List<String> courseId = phaseCourseMapper.findSelectCourseId(phaseId);
        if(courseId.size()==0){
            courseId.add("");
        }
        selectCourses.setCourseList(courseId);
        //查询需要进行选择的课程
        Page<CourseTeacher> coursePage = PageHelper.startPage(page, size);

        List<CourseTeacher> list = phaseCourseMapper.findCourseToBeSelect(selectCourses);
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

    //（选课时）更新学生的欠费情况
    public void updateArrearsSituationOfCourseSelection(String phaseId , CourseList courseList){
        //查询这一期的学生
        List<Student> studentList = studentRepository.findByStudentPhase(phaseId);
        for(Student student:studentList){
            String studentId = student.getStudentId();
            //从中拿到最新的交费情况
            List<Cost> costList = costMapper.findCostByStudentId(studentId);
            if(costList.size() != 0){
                Cost cost = costList.get(0);
                //老的欠费金额
                double costArrears = cost.getCostArrears();
                //计算新添加课程的总价格
                List<Course> courseList1 = courseList.getCourseList();
                double price = 0;
                for(Course course:courseList1){
                    double coursePrice = course.getCoursePrice();
                    price+=coursePrice;
                }
                //新的欠费金额
                cost.setCostArrears(costArrears+price);
                cost.setCostOther("按要求又添加课程，学费进行增加");
                costRepository.save(cost);
                //修改学生的欠费状态
                student.setStudentArrears(1);
                studentRepository.save(student);
            }
        }
    }

    //（退课时）更新学生的欠费情况
    public void updateArrearsSituationOfCourseRetire(String phaseId , String courseId){
        //查询这一期的学生
        List<Student> studentList = studentRepository.findByStudentPhase(phaseId);
        for(Student student:studentList){
            String studentId = student.getStudentId();
            //从中拿到最新的交费情况
            List<Cost> costList = costMapper.findCostByStudentId(studentId);
            if(costList.size() != 0){
                Cost cost = costList.get(0);
                //老的欠费金额
                double costArrears = cost.getCostArrears();

                //计算新添加课程的总价格
                Optional<Course> optional = courseRepository.findById(courseId);
                Course course = null;
                if(optional.isPresent()){
                    course = optional.get();
                    double price = course.getCoursePrice();
                    //新的欠费金额
                    cost.setCostArrears(costArrears - price);
                    cost.setCostOther("按要求又减少课程，学费进行减少");
                    costRepository.save(cost);

                    //修改学生的欠费状态
                    if(costArrears - price>0){
                        student.setStudentArrears(1);
                        studentRepository.save(student);
                    }else {
                        student.setStudentArrears(2);
                        studentRepository.save(student);
                    }
                }
            }


        }
    }

}
