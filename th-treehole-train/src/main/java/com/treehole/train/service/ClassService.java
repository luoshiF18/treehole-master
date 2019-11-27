package com.treehole.train.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.treehole.framework.domain.train.Class;
import com.treehole.framework.domain.train.Student;
import com.treehole.framework.domain.train.Teacher;
import com.treehole.framework.domain.train.ext.*;
import com.treehole.framework.domain.train.response.TrainCode;
import com.treehole.framework.model.response.*;
import com.treehole.train.config.RootPropeties;
import com.treehole.train.dao.ClassMapper;
import com.treehole.train.dao.ClassRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;
@Service
@Transactional
public class ClassService {

    @Autowired
    ClassRepository classRepository;

    @Autowired
    ClassMapper classMapper;

    @Autowired
    RootPropeties rootPropeties;

    @Autowired
    GenerateNumberService generateNumberService;
    //添加班级信息
    public ResponseResult addClass(Class class1) {
        Date date = new Date();
        class1.setClassCreatTime(date);
        class1.setClassNumber(0);
        class1.setClassCourseNumber(0);
        //添加生成的Id
        String cId = generateNumberService.GenerateNumber("5");
        class1.setClassId(cId);
        Class save = classRepository.save(class1);
        if (save != null) {
            return new ResponseResult(CommonCode.SUCCESS);
        }
        return new ResponseResult(CommonCode.FAIL);
    }

    //删除班级信息
    @Transactional
    public ResponseResult deleteClass(String classId) {
        //查看班级学生人数
        Optional<Class> optionalClass = classRepository.findById(classId);
        Class class1 = null;
        if(optionalClass.isPresent()){
             class1 = optionalClass.get();
        }
        Integer classNumber = class1.getClassNumber();
        if(classNumber == 0){
            classRepository.deleteById(classId);
            return new ResponseResult(CommonCode.SUCCESS);
        }else {
            return new ResponseResult(TrainCode.STUDENT_NUMBER_NOTNULL);
        }
    }

    //修改班级信息
    public ResponseResult updateClass(String classId, Class class1) {

        class1.setClassId(classId);
        classRepository.save(class1);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    //通过id查询班级信息
    public QueryResponseResult<ClassHeadmaster> findClassByFuzzyQuery(int page,int size, Class class1) {
        if(page<=0){
            page=1;
        }
        Page<ClassHeadmaster> classPage = PageHelper.startPage(page, size);
        List<ClassHeadmaster> list = classMapper.findClassByFuzzyQuery(class1);
        PageInfo<ClassHeadmaster> info = new PageInfo<>(classPage.getResult());
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


    //查询班级学生
    public QueryResponseResult<Student> findClassStudent(int page, int size, StudentExt studentExt){
        if(page<=0){
            page=1;
        }
        Page<Student> studentPage = PageHelper.startPage(page, size);
        List<Student>  list = classMapper.findClassStudent(studentExt);
        PageInfo<Student> info = new PageInfo<>(studentPage.getResult());
        long total = info.getTotal();
        QueryResult queryResult = new QueryResult();
        queryResult.setTotal(total);
        queryResult.setList(list);
        return new QueryResponseResult<Student>(CommonCode.SUCCESS,queryResult);
    }

    //查询班级课程
    public QueryResponseResult<CourseTeacher> findClassCourse(int page, int size, CourseExt courseExt) {
        if(page<=0){
            page=1;
        }
        Page<CourseTeacher> studentCoursePage = PageHelper.startPage(page, size);
        List<CourseTeacher> list = classMapper.findClassCourse(courseExt);
        PageInfo info = new PageInfo<>(studentCoursePage.getResult());
        int pageNum = info.getPageNum();//总页数
        long total = info.getTotal();//数据总个数
        QueryResult queryResult = new QueryResult();
        queryResult.setTotal(total);
        queryResult.setList(list);
        return new QueryResponseResult<CourseTeacher>(CommonCode.SUCCESS,queryResult);
    }

    //查询班级老师
    public QueryResponseResult<Teacher> findClassTeacher(int page,int size,ClassTeacher classTeacher) {
        if(page<=0){
            page=1;
        }
        Page<Teacher> teacherPage = PageHelper.startPage(page,size);
        List<Teacher> list = classMapper.findClassTeacher(classTeacher);
        PageInfo info = new PageInfo<>(teacherPage.getResult());
        long total = info.getTotal();//数据总个数
        QueryResult queryResult = new QueryResult();
        queryResult.setTotal(total);
        queryResult.setList(list);
        return new QueryResponseResult<Teacher>(CommonCode.SUCCESS,queryResult);
    }

}
