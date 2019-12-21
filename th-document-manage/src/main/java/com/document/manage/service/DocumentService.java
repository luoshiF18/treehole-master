package com.document.manage.service;

import com.document.manage.pojo.Document;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.List;

/**
 * @Auther: lee
 * @Date: 2019/10/26
 * @Description: com.lee.springboot.service
 * @version: 1.0
 */
public interface DocumentService {
    /**
     * 根据文件夹id查询此文件夹下的所有文档
     * @param folderId
     * @return
     */
    List<Document> showAllDocByFolder(String folderId, Integer status);

    /**
     * 文档上传
     * @param file
     * @return
     */
    List upload(MultipartFile[] file, String folderId, String describe, String userId);

    /**
     * 根据文档id逻辑删除文档
     * @param documentId
     * @return
     */
    int delete(String documentId, String userId);
    /**
     * 根据id彻底物理删除服务器文档
     * @param documentId
     * @return
     */
    int destroy(String documentId, String userId);

    /**
     * 下载文档
     * @param documentId
     */
    void download(String documentId, String userId, HttpServletResponse response);

    /**
     * 根据文档id和文件夹id以及新名称修改文档名
     * @param id
     * @param name
     * @return
     */
    int updateName(String id, String name, String userId);

    /**
     * 查询回收站中的文档
     * @return
     */
    List<Document> showRecycledDocument();

    /**
     * 从回收站中还原文档到原来文件夹下
     * @param docId
     * @return
     */
    int recoverDoc(String docId, String userId);

    /**
     * 获取文档存在服务器的地址
     * @param docId
     * @return
     */
    OutputStream downloadPort(String docId,String userId,HttpServletResponse response);
}
