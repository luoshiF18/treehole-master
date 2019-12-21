package com.document.manage.mapper;

import com.document.manage.pojo.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Auther: lee
 * @Date: 2019/10/26
 * @Description: com.lee.springboot.mapper
 * @version: 1.0
 */
public interface DocumentMapper extends JpaRepository<Document,String> {
    List<Document> findByStatusAndFolderId(int status, String folderId);
    List<Document> findDocumentByStatus(Integer status);
    Document findDocumentById(String id);
    Document findDocumentByNameAndStatusAndFolderIdAndTypeId(String name, Integer status, String folderId, String typeId);
}
