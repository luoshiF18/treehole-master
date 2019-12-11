package com.treehole.train.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.treehole.framework.domain.train.TeacherCourse;
import com.treehole.framework.domain.train.ext.TeacherCourseShow;
import com.treehole.framework.domain.train.ext.TeacherSelectCourseList;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.train.dao.TeacherCourseMapper;
import com.treehole.train.dao.TeacherCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TeacherCourseService {

    @Autowired
    TeacherCourseMapper teacherCourseMapper;
    @Autowired
    TeacherCourseRepository teacherCourseRepository;


    //老师选课
    @Transactional
    public ResponseResult teacherCourseSelection(String teacherId , TeacherSelectCourseList teacherSelectCourseList) {
        List<TeacherCourseShow> list = teacherSelectCourseList.getTeacherSelectCourseList();
        for(TeacherCourseShow course:list){
            String id = course.getClassCourseId();
            TeacherCourse teacherCourse = new TeacherCourse();
            teacherCourse.setTeacherId(teacherId);
            teacherCourse.setClassCourseId(id);
            teacherCourse.setState(3);
            teacherCourse.setTeacherCourseChecked(1);
            Date date = new Date();
            teacherCourse.setSelectCourseTime(date);
            teacherCourseRepository.save(teacherCourse);
            //如果不为空使 其不在显示
            String teacherCourseId = course.getTeacherCourseId();
            if( teacherCourseId != null ){
                Optional<TeacherCourse> optional = teacherCourseRepository.findById(teacherCourseId);
                if(optional.isPresent()){
                    TeacherCourse teacherCourse1 = optional.get();
                    teacherCourse1.setTeacherCourseChecked(2);
                    teacherCourseRepository.save(teacherCourse1);
                }
            }
        }
        return new ResponseResult(CommonCode.SUCCESS);
    }

    //老师选课的课程显示
    public QueryResponseResult<TeacherCourseShow> findTeacherCourse(int page , int size,TeacherCourseShow teacherCourseShow) {
        if(page<=0){
            page=1;
        }
        Page<TeacherCourseShow> teacherCourseShowPage = PageHelper.startPage(page, size);
        List<TeacherCourseShow> list = teacherCourseMapper.findTeacherCourse(teacherCourseShow);
        PageInfo<TeacherCourseShow> info = new PageInfo<>(teacherCourseShowPage.getResult());
        long total = info.getTotal();
        QueryResult queryResult = new QueryResult();
        queryResult.setList(list);
        queryResult.setTotal(total);
        if(list!=null){
            return new QueryResponseResult<TeacherCourseShow>(CommonCode.SUCCESS,queryResult);
        }else {
            return new QueryResponseResult<TeacherCourseShow>(CommonCode.FAIL,null);
        }
    }

    //所审核的课程
    public QueryResponseResult<TeacherCourseShow> findExamineTeacherCourse(int page , int size,TeacherCourseShow teacherCourseShow) {

        if(page<=0){
            page=1;
        }
        Page<TeacherCourseShow> teacherCourseShowPage = PageHelper.startPage(page, size);
        List<TeacherCourseShow> list = teacherCourseMapper.findExamineTeacherCourse(teacherCourseShow);
        PageInfo<TeacherCourseShow> info = new PageInfo<>(teacherCourseShowPage.getResult());
        long total = info.getTotal();
        QueryResult queryResult = new QueryResult();
        queryResult.setList(list);
        queryResult.setTotal(total);
        if(list!=null){
            return new QueryResponseResult<TeacherCourseShow>(CommonCode.SUCCESS,queryResult);
        }else {
            return new QueryResponseResult<TeacherCourseShow>(CommonCode.FAIL,null);
        }
    }

    //审核通过
    @Transactional
    public ResponseResult examinePass(String id) {

        Optional<TeacherCourse> teacherCourseOptional = teacherCourseRepository.findById(id);
        TeacherCourse teacherCourse = null;
        if(teacherCourseOptional.isPresent()){
             teacherCourse = teacherCourseOptional.get();
        }
        teacherCourse.setState(1);
        TeacherCourse save = teacherCourseRepository.save(teacherCourse);
       return new ResponseResult(CommonCode.SUCCESS);
        }


    //审核不通过
    public ResponseResult examineNoPass(String id) {

        Optional<TeacherCourse> teacherCourseOptional = teacherCourseRepository.findById(id);
        TeacherCourse teacherCourse = null;
        if(teacherCourseOptional.isPresent()){
            teacherCourse = teacherCourseOptional.get();
        }
        teacherCourse.setState(2);
        teacherCourse.setTeacherCourseChecked(1);
        teacherCourseRepository.save(teacherCourse);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    //查询审核情况
    public QueryResponseResult<TeacherCourseShow> findExamineSituation(int page , int size,TeacherCourseShow teacherCourseShow) {
        if(page<=0){
            page=1;
        }
        Page<TeacherCourseShow> teacherCourseShowPage = PageHelper.startPage(page, size);
//        //时间格式化
//        Date selectCourseTime = teacherCourseShow.getSelectCourseTime();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        String format = sdf.format(selectCourseTime);
//        System.out.println(format);

        List<TeacherCourseShow> list = teacherCourseMapper.findExamineSituation(teacherCourseShow);
        PageInfo<TeacherCourseShow> info = new PageInfo<>(teacherCourseShowPage.getResult());
        long total = info.getTotal();
        QueryResult queryResult = new QueryResult();
        queryResult.setList(list);
        queryResult.setTotal(total);
        if(list!=null){
            return new QueryResponseResult<TeacherCourseShow>(CommonCode.SUCCESS,queryResult);
        }else {
            return new QueryResponseResult<TeacherCourseShow>(CommonCode.FAIL,null);
        }
    }

}