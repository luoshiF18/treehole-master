package com.treehole.train.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.treehole.framework.domain.train.Document;
import com.treehole.framework.domain.train.ext.CourseTeacher;
import com.treehole.framework.domain.train.ext.DocumentExt;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.train.dao.DocumentMapper;
import com.treehole.train.feignclient.DocumentClient;
import com.treehole.train.dao.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.util.Date;
import java.util.List;

@Service
public class DocumentService {

    @Autowired
    DocumentClient documentClient;

    @Autowired
    DocumentRepository documentRepository;

    @Autowired
    DocumentMapper documentMapper;

    //上传
    @Transactional
    public ResponseResult uploadFile(MultipartFile file, DocumentExt documentExt){
        System.out.println(file.getOriginalFilename());
        System.out.println(documentExt);
        String documentId = documentClient.uploadPort(file, documentExt.getDescribe(), documentExt.getFolderId(),documentExt.getUserId());
        Document document = new Document();
        document.setDocumentId(documentId);
        document.setDocumentName(file.getOriginalFilename());
        document.setUploadTime(new Date());
        document.setClassCourseId(documentExt.getClassCourseId());
        if( documentExt.getStudentId() != null && documentExt.getStudentId() != ""){
            document.setStudentId(documentExt.getStudentId());
        }else {
            document.setStudentId("0");
        }
        Document save = documentRepository.save(document);
        if(save != null){
            return new ResponseResult(CommonCode.SUCCESS);
        }else {
            return new ResponseResult(CommonCode.FAIL);
        }

    }

    //老师查询学生的作业
    public QueryResponseResult<DocumentExt> findStudentTask(int page,int size,DocumentExt documentExt){
        if(page<=0){
            page=1;
        }
        com.github.pagehelper.Page<CourseTeacher> studentCoursePage = PageHelper.startPage(page, size);
        List<DocumentExt> list = documentMapper.findStudentTask(documentExt);
        PageInfo<CourseTeacher> info = new PageInfo<>(studentCoursePage.getResult());
        long total = info.getTotal();
        QueryResult queryResult = new QueryResult();
        queryResult.setList(list);
        queryResult.setTotal(total);
        return new QueryResponseResult<DocumentExt>(CommonCode.SUCCESS,queryResult);
    }

    //学生查询老师的资料
    public QueryResponseResult<DocumentExt> findTeacherData(int page,int size,DocumentExt documentExt){
        if(page<=0){
            page=1;
        }
        com.github.pagehelper.Page<CourseTeacher> studentCoursePage = PageHelper.startPage(page, size);
        List<DocumentExt> list = documentMapper.findTeacherData(documentExt);
        PageInfo<CourseTeacher> info = new PageInfo<>(studentCoursePage.getResult());
        long total = info.getTotal();
        QueryResult queryResult = new QueryResult();
        queryResult.setList(list);
        queryResult.setTotal(total);
        return new QueryResponseResult<DocumentExt>(CommonCode.SUCCESS,queryResult);
    }
}
