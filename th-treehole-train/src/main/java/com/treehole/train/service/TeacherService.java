package com.treehole.train.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.treehole.framework.domain.train.Student;
import com.treehole.framework.domain.train.Teacher;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.train.config.RootPropeties;
import com.treehole.train.dao.TeacherMapper;
import com.treehole.train.dao.TeacherRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    //添加老师信息
    public ResponseResult addTeacher(Teacher teacher){

        Teacher save = teacherRepository.save(teacher);
        if(save!=null){
            return new ResponseResult(CommonCode.SUCCESS);
        }
        return new ResponseResult(CommonCode.FAIL);
    }
    //删除老师信息
    public ResponseResult deleteTeacher(String teacherId){
        teacherRepository.deleteById(teacherId);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    //修改老师信息
    public ResponseResult updateTeacher(String teacherId,Teacher teacher){

        Optional<Teacher> optional = teacherRepository.findById(teacherId);
        Teacher returnTeacher = null;
        if(optional.isPresent()){
            returnTeacher = optional.get();
        }
        //拷贝
        BeanUtils.copyProperties(teacher,returnTeacher);
        returnTeacher.setTeacherId(teacherId);
        teacherRepository.save(returnTeacher);
        return new ResponseResult(CommonCode.SUCCESS);

    }

    //通过id和模糊查询查询老师信息
    public QueryResponseResult<Teacher> findTeacherByFuzzyQuery(int page,Teacher teacher) {

       if(teacher.getTeacherId()==null){

        }else  if(teacher.getTeacherId().equals("") ){
            teacher.setTeacherId(null);
        }

        //定义适配器
            ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                    .withMatcher("teacherName", ExampleMatcher.GenericPropertyMatchers.contains());
            Example<Teacher> example = Example.of(teacher, exampleMatcher);

            if(page<=0){
            page = 1;
            }
        page = page - 1;
        //分页
        PageRequest pageRequest = new PageRequest(page,rootPropeties.getPageSize());
        Page<Teacher> all = teacherRepository.findAll(example, pageRequest);

        QueryResult queryResult = new QueryResult();
        queryResult.setList(all.getContent());
        queryResult.setTotal(all.getTotalElements());
        return new QueryResponseResult<Teacher>(CommonCode.SUCCESS, queryResult);
    }


    //查询所有老师信息
    public QueryResponseResult<Teacher> findAllTeacher(int page) {
        if(page<=0){
            page=1;
        }
       // page = page - 1;
        com.github.pagehelper.Page<Teacher> teacherPage = PageHelper.startPage(page, rootPropeties.getPageSize());
        List<Teacher> list = teacherMapper.findAllTeacher();
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
