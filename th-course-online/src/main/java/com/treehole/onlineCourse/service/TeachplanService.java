package com.treehole.onlineCourse.service;

import com.treehole.framework.domain.onlineCourse.CourseBase;
import com.treehole.framework.domain.onlineCourse.Teachplan;
import com.treehole.framework.domain.onlineCourse.ext.TeachplanNode;
import com.treehole.framework.domain.onlineCourse.response.TeachplanResult;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.onlineCourse.dao.CourseBaseRepository;
import com.treehole.onlineCourse.dao.TeachplanMapper;
import com.treehole.onlineCourse.dao.TeachplanRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author cc
 * @date 2019/10/29 19:33
 */
@Service
public class TeachplanService {

    @Autowired
    private TeachplanMapper teachplanMapper;

    @Autowired
    private CourseBaseRepository courseBaseRepository;

    @Autowired
    private TeachplanRepository teachplanRepository;

    //查询课程计划
    public TeachplanNode findTeachplanList(String courseId){
        return teachplanMapper.selectList(courseId);
    }

    //添加课程计划，
    public ResponseResult addTeachplan(Teachplan teachplan) {
        //判断课程数据
        if(StringUtils.isEmpty(teachplan.getCourseid())||StringUtils.isEmpty(teachplan.getPname())){
            //抛出非法参数异常
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        //获取课程id
        String courseid = teachplan.getCourseid();
        //获取课程父节点id
        String parentid = teachplan.getParentid();
        //判断父节点id是否为空
        if(StringUtils.isEmpty(parentid)){
            //获取父节点，若为空则获取跟节点
            parentid = this.getTeachplanRoot(courseid);
        }
        //取出父节点信息
        Optional<Teachplan> teachplanOptional = teachplanRepository.findById(parentid);
        if(!teachplanOptional.isPresent()){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        //父节点信息
        Teachplan teachplan1 = teachplanOptional.get();
        //获取父节点级别
        String parentGrade = teachplan1.getGrade();
        //设置父节点
        teachplan.setParentid(parentid);
        teachplan.setStatus("0");
        //子节点级别，根据父节点来判断
        if(parentGrade.equals("1")){
            teachplan.setGrade("2");
        }else if(parentGrade.equals("2")){
            teachplan.setGrade("3");
        }
        //设置课程id
        teachplan.setCourseid(teachplan1.getCourseid());
        teachplanRepository.save(teachplan);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    //获取根节点，没有则创建根节点
    private String getTeachplanRoot(String courseid) {
        //判断是否有该课程
        //查询课程
        Optional<CourseBase> optional = courseBaseRepository.findById(courseid);
        if(!optional.isPresent()){
            //若为空直接返回null，说明没有该课程
            return null;
        }
        //获取课程基本信息
        CourseBase courseBase = optional.get();
        //不为空则判断是否有根节点
        List<Teachplan> teachplans = teachplanRepository.findByCourseidAndParentid(courseid, "0");
        //判断是否有根节点
        if(teachplans == null || teachplans.size() <= 0){
            //没有根节点，创建根节点
            Teachplan teachplan = new Teachplan();
            //封装数据
            teachplan.setCourseid(courseid);
            teachplan.setPname(courseBase.getName());
            teachplan.setParentid("0");
            teachplan.setGrade("1");
            teachplan.setStatus("0");
            teachplanRepository.save(teachplan);
            return teachplan.getId();
        }
        Teachplan teachplan = teachplans.get(0);
        return teachplan.getId();
    }

    //根据课程计划id查询课程计划
    public TeachplanResult getTeachplanById(String teachplanId) {
        //判断
        if(StringUtils.isEmpty(teachplanId)){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        //查询数据库
        Optional<Teachplan> optional = teachplanRepository.findById(teachplanId);
        if(!optional.isPresent()){
            ExceptionCast.cast(CommonCode.FAIL);
        }
        Teachplan teachplan = optional.get();
        return new TeachplanResult(CommonCode.SUCCESS,teachplan);
    }

    //删除课程计划
    public ResponseResult delTeachplan(String teachplanId) {
        TeachplanResult teachplanResult = this.getTeachplanById(teachplanId);
        Teachplan teachplan = teachplanResult.getTeachplan();
        if(teachplan == null){
            ExceptionCast.cast(CommonCode.FAIL);
        }
        //取出节点等级
        String grade = teachplan.getGrade();
        //判断选中节点
        //若为二级节点
        if(grade !=null && "2".equals(grade)){
            //查询当前节点所有的子节点
            List<Teachplan> teachplans = teachplanRepository.findByParentid(teachplanId);
            //删除子节点
            teachplanRepository.deleteAll(teachplans);
            //删除当前父节点
            teachplanRepository.delete(teachplan);
            return new ResponseResult(CommonCode.SUCCESS);
        }
        //若为三节节点
        if(grade !=null && "3".equals(grade)){
            teachplanRepository.delete(teachplan);
            return new ResponseResult(CommonCode.SUCCESS);
        }
        return new ResponseResult(CommonCode.FAIL);
    }
}
