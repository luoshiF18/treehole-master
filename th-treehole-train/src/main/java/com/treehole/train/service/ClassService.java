package com.treehole.train.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.treehole.framework.domain.train.Class;
import com.treehole.framework.domain.train.Student;
import com.treehole.framework.domain.train.Teacher;
import com.treehole.framework.domain.train.ext.StudentCourse;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.train.config.RootPropeties;
import com.treehole.train.dao.ClassMapper;
import com.treehole.train.dao.ClassRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
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

    //添加班级信息
    public ResponseResult addClass(Class class1) {
        Date date = new Date();
        class1.setClassCreatTime(date);
        Class save = classRepository.save(class1);
        if (save != null) {
            return new ResponseResult(CommonCode.SUCCESS);
        }
        return new ResponseResult(CommonCode.FAIL);
    }

    //删除班级信息
    public ResponseResult deleteClass(String classId) {
        classRepository.deleteById(classId);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    //修改班级信息
    public ResponseResult updateClass(String classId, Class class1) {

        Optional<Class> optional = classRepository.findById(classId);
        Class returnClass = null;
        if (optional.isPresent()) {
            returnClass = optional.get();
        }
        //拷贝
        BeanUtils.copyProperties(class1, returnClass);
        returnClass.setClassId(classId);
        classRepository.save(returnClass);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    //通过id查询班级信息
    public QueryResponseResult<Class> findClassByFuzzyQuery(int page, Class class1) {

        if(class1.getClassId()==null){

        }else  if(class1.getClassId().equals("") ){
            class1.setClassId(null);
        }

        //定义适配器
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("className", ExampleMatcher.GenericPropertyMatchers.contains());
        Example<Class> example = Example.of(class1, exampleMatcher);

        if(page<=0){
            page = 1;
        }
        page = page - 1;
        //分页
        PageRequest pageRequest = new PageRequest(page,rootPropeties.getPageSize());
        org.springframework.data.domain.Page<Class> all = classRepository.findAll(example, pageRequest);

        QueryResult queryResult = new QueryResult();
        queryResult.setList(all.getContent());
        queryResult.setTotal(all.getTotalElements());
        return new QueryResponseResult<Class>(CommonCode.SUCCESS, queryResult);
    }

    //查询所有班级信息
    public QueryResponseResult<Class> findAllClass(int page) {
        if(page<=0){
            page=1;
        }
       // page = page - 1;
        Page<Class> classPage = PageHelper.startPage(page, rootPropeties.getPageSize());
        List<Class> list = classMapper.findAllClass();
        PageInfo<Class> info = new PageInfo<>(classPage.getResult());
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



    //查询班级学生
    public QueryResponseResult<Student> findClassStudent(int page,String classId){
        if(page<=0){
            page=1;
        }
      //  page = page - 1;
        Page<Student> studentPage = PageHelper.startPage(page, rootPropeties.getPageSize());
        List<Student>  list = classMapper.findClassStudent(classId);
        PageInfo<Student> info = new PageInfo<>(studentPage.getResult());
        long total = info.getTotal();
        QueryResult queryResult = new QueryResult();
        queryResult.setTotal(total);
        queryResult.setList(list);
        return new QueryResponseResult<Student>(CommonCode.SUCCESS,queryResult);
    }

    //查询班级课程
    public QueryResponseResult<StudentCourse> findClassCourse(int page,String classId) {
        if(page<=0){
            page=1;
        }
     //   page = page - 1;
        Page<StudentCourse> studentCoursePage = PageHelper.startPage(page, rootPropeties.getPageSize());
        List<StudentCourse> list = classMapper.findClassCourse(classId);
        PageInfo info = new PageInfo<>(studentCoursePage.getResult());
        int pageNum = info.getPageNum();//总页数
        long total = info.getTotal();//数据总个数
        QueryResult queryResult = new QueryResult();
        queryResult.setTotal(total);
        queryResult.setList(list);
        return new QueryResponseResult<StudentCourse>(CommonCode.SUCCESS,queryResult);
    }

    //查询班级老师
    public QueryResponseResult<Teacher> findClassTeacher(int page,String classId) {
        if(page<=0){
            page=1;
        }
     //   page = page - 1;
        Page<Teacher> teacherPage = PageHelper.startPage(page, rootPropeties.getPageSize());
        List<Teacher> list = classMapper.findClassTeacher(classId);
        PageInfo info = new PageInfo<>(teacherPage.getResult());
        long total = info.getTotal();//数据总个数
        QueryResult queryResult = new QueryResult();
        queryResult.setTotal(total);
        queryResult.setList(list);
        return new QueryResponseResult<Teacher>(CommonCode.SUCCESS,queryResult);
    }

}
