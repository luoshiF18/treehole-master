package com.treehole.onlineCourse.service;

import com.treehole.framework.domain.onlineCourse.CourseBase;
import com.treehole.framework.domain.onlineCourse.CoursePic;
import com.treehole.framework.domain.onlineCourse.response.CourseBaseResult;
import com.treehole.framework.domain.onlineCourse.response.CoursePicCode;
import com.treehole.framework.domain.onlineCourse.response.CoursePicResult;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.onlineCourse.dao.CourseBaseRepository;
import com.treehole.onlineCourse.dao.CoursePicRepository;
import org.apache.commons.lang3.StringUtils;
import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

/**
 * @author cc
 * @date 2019/10/29 19:33
 */
@Service
public class CoursePicService {

    @Value("${treehole.fastdfs.tracker_servers}")
    String tracker_servers;
    @Value("${treehole.fastdfs.connect_timeout_in_seconds}")
    int connect_timeout_in_seconds;
    @Value("${treehole.fastdfs.network_timeout_in_seconds}")
    int network_timeout_in_seconds;
    @Value("${treehole.fastdfs.charset}")
    String charset;

    @Autowired
    private CoursePicRepository coursePicRepository;

    //上传图片
    public CoursePicResult uploadCoursePic(MultipartFile multipartFile,String courseId) {
        //判断
        if(multipartFile == null){
            //抛出上传文件为空异常
            ExceptionCast.cast(CoursePicCode.FS_UPLOADFILE_FILEISNULL);
        }
        //将文件上传到fastDFS中，得到一个文件id路径
        String fileId = this.fdfs_upload(multipartFile);
        //判断fileId是否为空
        if(StringUtils.isEmpty(fileId)){
            ExceptionCast.cast(CoursePicCode.FS_UPLOADFILE_SERVERFAIL);
        }
        //封装保存/返回结果
        CoursePic coursePic = new CoursePic();
        coursePic.setCourseid(courseId);
        coursePic.setPic(fileId);
        coursePic.setFileName(multipartFile.getOriginalFilename());
        coursePic.setFileType(multipartFile.getContentType());
        //存储到数据库
        coursePicRepository.save(coursePic);
        return new CoursePicResult(CommonCode.SUCCESS,coursePic);
    }

    //上传文件到fastDFS中
    private String fdfs_upload(MultipartFile multipartFile) {
        //初始化fastDFS的环境
        this.initFdfsConfig();
        //创建trackerClient
        TrackerClient trackerClient = new TrackerClient();
        //建立连接
        try {
            TrackerServer trackerServer = trackerClient.getConnection();
            //得到storage服务器
            StorageServer storeStorage = trackerClient.getStoreStorage(trackerServer);
            //创建storageClient来上传文件
            StorageClient1 storageClient1 = new StorageClient1(trackerServer, storeStorage);
            //上传文件
            //得到字节文件
            byte[] bytes = multipartFile.getBytes();
            //得到文件的原始名称
            String originalFilename = multipartFile.getOriginalFilename();
            //得到扩展名
            String ext = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            String fileId = storageClient1.upload_file1(bytes, ext, null);
            return fileId;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //初始化fastDFS环境
    private void initFdfsConfig() {
        //初始化tracker服务地址
        try {
            ClientGlobal.initByTrackers(tracker_servers);
            ClientGlobal.setG_charset(charset);
            ClientGlobal.setG_network_timeout(network_timeout_in_seconds);
            ClientGlobal.setG_connect_timeout(connect_timeout_in_seconds);
        } catch (Exception e) {
            e.printStackTrace();
            //抛出异常
            ExceptionCast.cast(CoursePicCode.FS_INITFDFSERROR);
        }

    }

    //查询图片
    public CoursePicResult findCoursePic(String courseId) {
        if(StringUtils.isEmpty(courseId)){
            //若为空这抛出参数异常
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        Optional<CoursePic> optional = coursePicRepository.findById(courseId);
        if(!optional.isPresent()){
            //返回操作失败
            return new CoursePicResult(CommonCode.FAIL,null);
        }
        //返回查询结果
        return new CoursePicResult(CommonCode.SUCCESS,optional.get());
    }

    //删除图片
    public ResponseResult deletecoursepic(String courseId) {
        if(StringUtils.isEmpty(courseId)){
            ExceptionCast.cast(CoursePicCode.FS_UPLOADFILE_BUSINESSISNULL);
        }
        try {
            //执行删除
            coursePicRepository.deleteById(courseId);
            return new ResponseResult(CommonCode.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult(CommonCode.FAIL);
        }
    }
}
