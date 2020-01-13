package com.treehole.onlineCourse.service;

import com.treehole.framework.domain.onlineCourse.CourseBase;
import com.treehole.framework.domain.onlineCourse.CourseMarket;
import com.treehole.framework.domain.onlineCourse.CoursePic;
import com.treehole.framework.domain.onlineCourse.ext.CourseView;
import com.treehole.framework.domain.onlineCourse.ext.TeachplanNode;
import com.treehole.framework.domain.onlineCourse.response.CourseBaseResult;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.onlineCourse.dao.CourseBaseRepository;
import com.treehole.onlineCourse.dao.CourseMarketRepository;
import com.treehole.onlineCourse.dao.CoursePicRepository;
import com.treehole.onlineCourse.dao.TeachplanMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.activation.CommandMap;
import java.util.Optional;

/**
 * @author cc
 * @date 2019/10/29 19:33
 */
@Service
public class CourseBaseService {

    @Autowired
    private CourseBaseRepository courseBaseRepository;

    public CourseBaseResult findCourseBaseById(String courseId) {
        //判断courseId是否为空
        if(StringUtils.isEmpty(courseId)){
            return new CourseBaseResult(CommonCode.INVALID_PARAM,null);
        }
        //获取数据
        Optional<CourseBase> optional = courseBaseRepository.findById(courseId);
        if(!optional.isPresent()){
            return new CourseBaseResult(CommonCode.FAIL,null);
        }
        return new CourseBaseResult(CommonCode.SUCCESS,optional.get());
    }

    //更新课程基本信息，没有则添加
    public ResponseResult updateCourseBase(String courseId, CourseBase courseBase) {
        //判断courseid是否为空
        if(StringUtils.isEmpty(courseId)){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        //判断数据
        if(StringUtils.isEmpty(courseBase.getName())||StringUtils.isEmpty(courseBase.getMt())||StringUtils.isEmpty(courseBase.getSt())||StringUtils.isEmpty(courseBase.getGrade())||StringUtils.isEmpty(courseBase.getStudymodel())){
            //若有不符合规则的数据,则抛出非法参数异常
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        //先查询数据库
        CourseBase courseBase1 = this.findCourseBaseById(courseId).getCourseBase();
        //判断是否为空
        if(courseBase1 == null){
            //若为空，则添加课程基本信息,保存
            courseBaseRepository.save(courseBase);
            return new ResponseResult(CommonCode.SUCCESS);
        }
        //将数据更新到数据库中
        courseBase1.setName(courseBase.getName());
        courseBase1.setUsers(courseBase.getUsers());
        courseBase1.setMt(courseBase.getMt());
        courseBase1.setSt(courseBase.getSt());
        courseBase1.setGrade(courseBase.getGrade());
        courseBase1.setStudymodel(courseBase.getStudymodel());
        courseBase1.setDescription(courseBase.getDescription());
        courseBaseRepository.save(courseBase1);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    //添加课程基本信息
    public ResponseResult addCourseBase(CourseBase courseBase) {
        //判断数据是否合法
        if(StringUtils.isEmpty(courseBase.getName())||StringUtils.isEmpty(courseBase.getGrade())||StringUtils.isEmpty(courseBase.getStudymodel())||StringUtils.isEmpty(courseBase.getSt())){
            return new ResponseResult(CommonCode.INVALID_PARAM);
        }
        //设置当前课程状态
        courseBase.setStatus("202001");
        try {
            //保存课程
            courseBaseRepository.save(courseBase);
            //返回结果
            return new ResponseResult(CommonCode.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //返回结果
        return new ResponseResult(CommonCode.FAIL);
    }
}
