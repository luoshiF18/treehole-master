package com.treehole.train.service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.treehole.framework.domain.train.ClassCourse;
import com.treehole.framework.domain.train.Course;
import com.treehole.framework.domain.train.CourseType;
import com.treehole.framework.domain.train.ext.CourseTeacher;
import com.treehole.framework.domain.train.response.TrainCode;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.train.config.RootPropeties;
import com.treehole.train.dao.ClassCourseRepository;
import com.treehole.train.dao.CourseMapper;
import com.treehole.train.dao.CourseRepository;
import com.treehole.train.dao.CourseTypeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
public class CourseService {

    @Autowired
    CourseRepository courseRepository;
    @Autowired
    CourseTypeRepository courseTypeRepository;
    @Autowired
    CourseTypeService courseTypeService;

    @Autowired
    CourseMapper courseMapper;

    @Autowired
    GenerateNumberService generateNumberService;

    @Autowired
    ClassCourseRepository classCourseRepository;
    //添加课程信息
    @Transactional
    public ResponseResult addCourse(Course course){
        //生成Id
        String cId = generateNumberService.GenerateNumber("4");
        course.setCourseId(cId);
        //添加课程
         courseRepository.save(course);
         //对应类型课程加1
        String courseId = course.getCourseId();
        String courseType = course.getCourseType();
        Optional<CourseType> optionalCourseType = courseTypeRepository.findById(courseType);
        CourseType courseType1 = null;
        if(optionalCourseType.isPresent()){
             courseType1 = optionalCourseType.get();
        }
        int num = courseType1.getCourseTypeCourseNumber() + 1;
        courseType1.setCourseTypeCourseNumber(num);
        courseTypeRepository.save(courseType1);

        return new ResponseResult(CommonCode.SUCCESS);
    }

    //删除课程信息
    public ResponseResult deleteCourse(String courseId){
        //先判断课程有没有被选中
        List<ClassCourse> courses = classCourseRepository.findByCourseId(courseId);
        if(courses.size() != 0){
            ExceptionCast.cast(TrainCode.COURSE_SELECTED);
        }
        courseRepository.deleteById(courseId);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    //修改课程信息
    public ResponseResult updateCourse(String courseId,Course course){
        course.setCourseId(courseId);
        courseRepository.save(course);
        return new ResponseResult(CommonCode.SUCCESS);
    }
    //通过id查询课程信息
    public QueryResponseResult<CourseTeacher> findCourseByFuzzyQuery(int page,int size , Course course){
        if(page<=0){
            page = 1;
        }
        Page<CourseTeacher> coursePage = PageHelper.startPage(page, size);
        List<CourseTeacher> list = courseMapper.findCourseByFuzzyQuery(course);
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


}
