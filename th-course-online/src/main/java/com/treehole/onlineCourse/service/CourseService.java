package com.treehole.onlineCourse.service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.treehole.framework.domain.onlineCourse.*;
import com.treehole.framework.domain.onlineCourse.ext.CourseInfo;
import com.treehole.framework.domain.onlineCourse.ext.CourseView;
import com.treehole.framework.domain.onlineCourse.ext.TeachplanNode;
import com.treehole.framework.domain.onlineCourse.request.QueryPageRequest;
import com.treehole.framework.domain.onlineCourse.response.CourseCode;
import com.treehole.framework.domain.onlineCourse.response.CoursePublishResult;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.onlineCourse.dao.*;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.print.DocFlavor;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author cc
 * @date 2019/10/29 19:33
 */
@Service
public class CourseService {

    @Autowired
    private CourseBaseRepository courseBaseRepository;

    @Autowired
    private CourseMarketRepository courseMarketRepository;

    @Autowired
    private CoursePicRepository coursePicRepository;

    @Autowired
    private TeachplanMapper teachplanMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private CoursePubRepository coursePubRepository;

    @Autowired
    private TeachplanRepository teachplanRepository;

    @Autowired
    private TeachplanMediaRepository teachplanMediaRepository;

    @Autowired
    private TeachplanMediaPubRepository teachplanMediaPubRepository;

    //获取课程视图数据
    public CourseView findCourseView(String courseId) {
        CourseView courseView = new CourseView();
        //查询课程基本信息
        Optional<CourseBase> optional = courseBaseRepository.findById(courseId);
        if(optional.isPresent()){
            CourseBase courseBase = optional.get();
            courseView.setCourseBase(courseBase);
        }
        //查询课程营销信息
        Optional<CourseMarket> courseMarketOptional = courseMarketRepository.findById(courseId);
        if(courseMarketOptional.isPresent()){
            CourseMarket courseMarket = courseMarketOptional.get();
            courseView.setCourseMarket(courseMarket);
        }
        //查询课程图片信息
        Optional<CoursePic> picOptional = coursePicRepository.findById(courseId);
        if(picOptional.isPresent()){
            CoursePic coursePic = picOptional.get();
            courseView.setCoursePic(picOptional.get());
        }
        //查询课程计划信息
        TeachplanNode teachplanNode = teachplanMapper.selectList(courseId);
        courseView.setTeachplanNode(teachplanNode);
        return courseView;
    }

    //发布课程
    @Transactional
    public CoursePublishResult publishCourse(String courseId) {
        try {
            //创建配置类
            Configuration configuration=new Configuration(Configuration.getVersion());
            String classpath = this.getClass().getResource("/").getPath();
            //设置模板路径
            configuration.setDirectoryForTemplateLoading(new File(classpath + "/templates/"));
            //设置字符集
            configuration.setDefaultEncoding("utf-8");
            //加载模板
            Template template = configuration.getTemplate("course.ftl");
            //数据模型
            CourseView courseView = this.findCourseView(courseId);
            //静态化
            String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, courseView);
            //静态化内容
            System.out.println(content);
            InputStream inputStream = IOUtils.toInputStream(content);
            //输出文件
            FileOutputStream fileOutputStream = new FileOutputStream(new File("E:/Tools/WebStorm/workspace/treehole/static/course/detail/"+courseId+".html"));
            IOUtils.copy(inputStream, fileOutputStream);
            //查询课程基本信息
            Optional<CourseBase> optional = courseBaseRepository.findById(courseId);
            if(optional.isPresent()){
                CourseBase courseBase = optional.get();
                courseBase.setStatus("202002");
                //保存课程状态
                courseBaseRepository.save(courseBase);
            }
            //创建课程索引
            //创建课程索引信息
            CoursePub coursePub = this.createCoursePub(courseId);
            //向数据库保存课程索引信息
            CoursePub newCoursePub = this.saveCoursePub(courseId,coursePub);
            if(newCoursePub == null){
                ExceptionCast.cast(CourseCode.COURSE_PUBLISH_VIEWERROR);
            }
            //保存课程媒资与课程计划关系
            ResponseResult responseResult = this.saveTeachplanMediaPub(courseId);
            if(!responseResult.isSuccess()){
                ExceptionCast.cast(CommonCode.FAIL);
            }
            //拼装url
            String url = "www.treehole.com/course/details/"+courseId+".html";
            return new CoursePublishResult(CommonCode.SUCCESS,url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new CoursePublishResult(CommonCode.FAIL,null);
    }

    //向数据库中保存课程索引信息
    private CoursePub saveCoursePub(String courseId, CoursePub coursePub) {
        if(!StringUtils.isNotEmpty(courseId)){
            ExceptionCast.cast(CourseCode.COURSE_PUBLISH_COURSEIDISNULL);
        }
        CoursePub coursePubNew = null;
        Optional<CoursePub> coursePubOptional = coursePubRepository.findById(courseId);
        if(coursePubOptional.isPresent()){
            coursePubNew = coursePubOptional.get();
        }
        if(coursePubNew == null){
            coursePubNew = new CoursePub();
        }
        BeanUtils.copyProperties(coursePub,coursePubNew);
        //设置主键
        coursePubNew.setId(courseId);
        //更新时间戳为最新时间
        coursePub.setTimestamp(new Date());
        //发布时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY‐MM‐dd HH:mm:ss");
        String date = simpleDateFormat.format(new Date());
        coursePub.setPubTime(date);
        coursePubRepository.save(coursePub);
        return coursePub;
    }

    //创建课程索引信息
    private CoursePub createCoursePub(String courseId) {
        //创建
        CoursePub coursePub = new CoursePub();
        //查询课程基本信息
        Optional<CourseBase> courseBaseOptional = courseBaseRepository.findById(courseId);
        if(courseBaseOptional.isPresent()){
            CourseBase courseBase = courseBaseOptional.get();
            BeanUtils.copyProperties(courseBase,coursePub);
        }
        //查询课程图片
        Optional<CoursePic> picOptional = coursePicRepository.findById(courseId);
        if(picOptional.isPresent()){
            CoursePic coursePic = picOptional.get();
            BeanUtils.copyProperties(coursePic, coursePub);
        }
        //课程营销信息
        Optional<CourseMarket> marketOptional = courseMarketRepository.findById(courseId);
        if(marketOptional.isPresent()){
            CourseMarket courseMarket = marketOptional.get();
            BeanUtils.copyProperties(courseMarket, coursePub);
        }
        //课程计划
        TeachplanNode teachplanNode = teachplanMapper.selectList(courseId);
        //将课程计划转成json数据
        String teachplanString = JSON.toJSONString(teachplanNode);
        coursePub.setTeachplan(teachplanString);
        return coursePub;
    }
    //分页查询课程
    public QueryResponseResult findCourse(int page, int size, QueryPageRequest queryPageRequest) {
        //先判断queryPageRequest是否为空，防止出现空指针异常
        if(queryPageRequest == null){
            queryPageRequest = new QueryPageRequest();
        }
        //分页参数
        if(page <= 0){
            page = 0;
        }
        if(size <= 0){
            size = 8;
        }
        //设置分页参数
        PageHelper.startPage(page,size);
        //分页查询
        Page<CourseInfo> courseListPage = courseMapper.findCourseListPage(queryPageRequest);
        //查询列表
        List<CourseInfo> result = courseListPage.getResult();
        //总记录数
        long total = courseListPage.getTotal();
        //查询结果集
        QueryResult<CourseInfo> courseInfoQueryResult = new QueryResult<>();
        courseInfoQueryResult.setList(result);
        courseInfoQueryResult.setTotal(total);
        return new QueryResponseResult(CommonCode.SUCCESS,courseInfoQueryResult);
    }

    //关联课程计划与媒资信息
    public ResponseResult savemedia(TeachplanMedia teachplanMedia) {
        //判断数据
        if(teachplanMedia == null && StringUtils.isEmpty(teachplanMedia.getTeachplanId())){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        //获取课程计划信息，判断是否为三级节点
        Optional<Teachplan> optional = teachplanRepository.findById(teachplanMedia.getTeachplanId());
        if(!optional.isPresent()){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        //取出数据
        Teachplan teachplan = optional.get();
        //判断是否为第三级节点
        if(StringUtils.isEmpty(teachplan.getGrade()) || !teachplan.getGrade().equals("3")){
            ExceptionCast.cast(CourseCode.COURSE_MEDIA_TEACHPLAN_GRADEERROR);
        }
        //准备保存
        TeachplanMedia one = null;
        //查询数据库
        Optional<TeachplanMedia> mediaOptional = teachplanMediaRepository.findById(teachplanMedia.getTeachplanId());
        if(!mediaOptional.isPresent()){
            one = new TeachplanMedia();
        }else{
            one = mediaOptional.get();
        }
        //封装保存数据
        one.setCourseId(teachplanMedia.getCourseId());
        one.setMediaFileOriginalName(teachplanMedia.getMediaFileOriginalName());
        one.setMediaId(teachplanMedia.getMediaId());
        one.setMediaUrl(teachplanMedia.getMediaUrl());
        one.setTeachplanId(teachplanMedia.getTeachplanId());
        //保存
        teachplanMediaRepository.save(one);
        return new ResponseResult(CommonCode.SUCCESS);
    }
    //保存课程计划媒资信息，先删除关于该课程的所有媒资信息，再进行添加
    private ResponseResult saveTeachplanMediaPub(String courseId){
        //先删除
        teachplanMediaPubRepository.deleteByCourseId(courseId);
        //查询
        List<TeachplanMedia> byCourseId = teachplanMediaRepository.findByCourseId(courseId);
        //通过工具类拷贝实体类数据
        List<TeachplanMediaPub> teachplanMediaPubList = new ArrayList<TeachplanMediaPub>();
        //拷贝
        for (TeachplanMedia teachplanMedia:byCourseId){
            TeachplanMediaPub teachplanMediaPub = new TeachplanMediaPub();
            BeanUtils.copyProperties(teachplanMedia,teachplanMediaPub);
            //再添加上时间戳
            teachplanMediaPub.setTimestamp(new Date());
            //放入集合中
            teachplanMediaPubList.add(teachplanMediaPub);
        }
        //baoc
        teachplanMediaPubRepository.saveAll(teachplanMediaPubList);
        return new ResponseResult(CommonCode.SUCCESS);
    }
}
