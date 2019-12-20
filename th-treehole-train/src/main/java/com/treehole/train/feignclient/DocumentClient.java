package com.treehole.train.feignclient;
import com.treehole.framework.domain.train.Class;
import com.treehole.framework.domain.train.ext.ClassHeadmaster;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.train.config.MultipartSupportConfig;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

@FeignClient(value ="TH-DOCUMENT-MANAGE",configuration = MultipartSupportConfig.class)
public interface DocumentClient {


    //上传文件
    @RequestMapping(value = "document/uploadPort/{describe}/{folderId}/{userId}",method = RequestMethod.POST,produces = {MediaType.APPLICATION_JSON_UTF8_VALUE},consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadPort(@RequestPart("file") MultipartFile file, @PathVariable("describe") String describe, @PathVariable("folderId") String folderId, @PathVariable("userId") String userId);

    //下载文件
    @RequestMapping(value ="document/downloadPort/{docId}/{userId}",method = RequestMethod.POST)
    public String downloadPort(@PathVariable("docId") String docId, @PathVariable("userId") String userId,HttpServletResponse response);


}
