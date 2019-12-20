package com.treehole.api.train;

import com.treehole.framework.domain.train.ext.DocumentExt;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@Api(value="文件管理",description = "上传下载文件")
public interface DocumentControllerApi {

    @ApiOperation("文件上传")
    public ResponseResult uploadDoc( MultipartFile file, DocumentExt documentExt);
    @ApiOperation("老师查看学生作业")
    public QueryResponseResult<DocumentExt> findStudentTask(int page,int size,DocumentExt documentExt);
    @ApiOperation("学生查询老师的资料")
    public QueryResponseResult<DocumentExt> findTeacherData(int page,int size,DocumentExt documentExt);



}
