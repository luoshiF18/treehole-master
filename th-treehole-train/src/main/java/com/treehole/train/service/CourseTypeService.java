package com.treehole.train.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.treehole.framework.domain.train.CourseType;
import com.treehole.framework.domain.train.response.TrainCode;
import com.treehole.framework.model.response.*;
import com.treehole.train.dao.CourseTypeMapper;
import com.treehole.train.dao.CourseTypeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseTypeService {

    @Autowired
    CourseTypeRepository courseTypeRepository;

    @Autowired
    CourseTypeMapper courseTypeMapper;
    @Autowired
    GenerateNumberService generateNumberService;

    //添加类型
    public ResponseResult addCourseType(CourseType courseType){
        courseType.setCourseTypeCourseNumber(0);
        //生成Id
        String ctId = generateNumberService.GenerateNumber("3");
        courseType.setCourseTypeId(ctId);
        courseTypeRepository.save(courseType);
        return new ResponseResult(CommonCode.SUCCESS);
    }
    //删除类型
    public ResponseResult deleteCourseType(String courseTypeId){
        Optional<CourseType> optionalCourseType = courseTypeRepository.findById(courseTypeId);
        CourseType courseType = null;
        if (optionalCourseType.isPresent()){
             courseType = optionalCourseType.get();
        }
        Integer number = courseType.getCourseTypeCourseNumber();
        if(number == 0){
            courseTypeRepository.deleteById(courseTypeId);
            return new ResponseResult(CommonCode.SUCCESS);
        }else {
            return new ResponseResult(TrainCode.COURSE_NUMBER_NOTNULL);
        }

    }
    //修改类型
    public ResponseResult updateCourseType(String courseTypeId , CourseType courseType){
        //查询
        Optional<CourseType> courseTypeOptional = courseTypeRepository.findById(courseTypeId);
        CourseType cpurseType1 = null;
        if(courseTypeOptional.isPresent()){
            cpurseType1 = courseTypeOptional.get();

        }
        //拷贝
        BeanUtils.copyProperties(courseType,cpurseType1);
        //保存
        cpurseType1.setCourseTypeId(courseTypeId);
        courseTypeRepository.save(cpurseType1);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    //模糊查询类型
    public QueryResponseResult<CourseType> findCourseTypeByFuzzyQuery(int page , int size , CourseType courseType){
        if(page<=0){
            page=1;
        }
        Page<CourseType> classPage = PageHelper.startPage(page, size);
        List<CourseType> list = courseTypeMapper.findCourseTypeByFuzzyQuery(courseType);
        PageInfo<CourseType> info = new PageInfo<>(classPage.getResult());
        long total = info.getTotal();
        QueryResult queryResult = new QueryResult();
        queryResult.setList(list);
        queryResult.setTotal(total);
        if(list!=null){
            return new QueryResponseResult<CourseType>(CommonCode.SUCCESS,queryResult);
        }else {
            return new QueryResponseResult<CourseType>(CommonCode.FAIL,null);
        }
    }
}
