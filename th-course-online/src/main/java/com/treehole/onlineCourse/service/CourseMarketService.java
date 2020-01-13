package com.treehole.onlineCourse.service;

import com.treehole.framework.domain.onlineCourse.CourseBase;
import com.treehole.framework.domain.onlineCourse.CourseMarket;
import com.treehole.framework.domain.onlineCourse.response.CourseBaseResult;
import com.treehole.framework.domain.onlineCourse.response.CourseMarketResult;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.onlineCourse.dao.CourseBaseRepository;
import com.treehole.onlineCourse.dao.CourseMarketRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author cc
 * @date 2019/10/29 19:33
 */
@Service
public class CourseMarketService {

   @Autowired
    private CourseMarketRepository courseMarketRepository;

   //根据id查询课程营销信息
    public CourseMarketResult findCourseMarketById(String courseId) {
        if(StringUtils.isEmpty(courseId)){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        //查询
        Optional<CourseMarket> optional = courseMarketRepository.findById(courseId);
        if(!optional.isPresent()){
            //返回错误
            return new CourseMarketResult(CommonCode.FAIL,null);
        }
        //返回结果
        return new CourseMarketResult(CommonCode.SUCCESS,optional.get());
    }

    //更新课程营销信息，没有则添加
    public ResponseResult updateCourseMarket(String courseId,CourseMarket courseMarket) {
        //判断数据是否符合
        if(StringUtils.isEmpty(courseMarket.getCharge())||StringUtils.isEmpty(courseMarket.getValid())||StringUtils.isEmpty(courseId)){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        //查询数据库
        CourseMarket findcourseMarket = this.findCourseMarketById(courseId).getCourseMarket();
        //判断
        if(findcourseMarket == null){
            //没有则添加
            courseMarketRepository.save(courseMarket);
            //返回结果
            return new ResponseResult(CommonCode.SUCCESS);
        }
        //有则更新
        //封装数据
        findcourseMarket.setCharge(courseMarket.getCharge());
        findcourseMarket.setPrice(courseMarket.getPrice());
        findcourseMarket.setValid(courseMarket.getValid());
        findcourseMarket.setStartTime(courseMarket.getStartTime());
        findcourseMarket.setEndTime(courseMarket.getEndTime());
        findcourseMarket.setQq(courseMarket.getQq());
        //保存
        courseMarketRepository.save(findcourseMarket);
        return new ResponseResult(CommonCode.SUCCESS);
    }
}
