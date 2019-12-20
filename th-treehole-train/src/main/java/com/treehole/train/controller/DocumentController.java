package com.treehole.train.controller;

import com.treehole.api.train.DocumentControllerApi;
import com.treehole.framework.domain.train.ext.DocumentExt;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.train.feignclient.DocumentClient;
import com.treehole.train.service.DocumentService;
import com.treehole.train.utils.FtpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

@RestController
@RequestMapping("/train/document")
public class DocumentController implements DocumentControllerApi {

    @Autowired
    DocumentService documentService;


    @PostMapping("/uploadDoc")
    public ResponseResult uploadDoc(@RequestPart("file") MultipartFile file,  DocumentExt documentExt) {
       return documentService.uploadFile(file,documentExt);
    }

    @Override
    @PostMapping("/findStudentTask/{page}/{size}")
    public QueryResponseResult<DocumentExt> findStudentTask(@PathVariable("page") int page,@PathVariable("size") int size,@RequestBody  DocumentExt documentExt) {
        return documentService.findStudentTask(page, size, documentExt);
    }

    @Override
    @PostMapping("/findTeacherData/{page}/{size}")
    public QueryResponseResult<DocumentExt> findTeacherData(@PathVariable("page") int page,@PathVariable("size") int size,@RequestBody DocumentExt documentExt) {
        return documentService.findTeacherData(page, size, documentExt);
    }


}
