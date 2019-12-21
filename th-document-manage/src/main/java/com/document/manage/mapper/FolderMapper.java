package com.document.manage.mapper;

import com.document.manage.pojo.Folder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Auther: lee
 * @Date: 2019/10/28
 * @Description: com.lee.springboot.mapper
 * @version: 1.0
 */
public interface FolderMapper extends JpaRepository<Folder,String> {
    List<Folder> findAllByParentIdAndStatus(String parentId, Integer status);
    List<Folder> findFolderByStatus(Integer status);
    Folder findFolderByIdAndStatus(String id, Integer status);
    Folder findFolderByParentIdAndNameAndStatus(String parentId, String name, Integer status);
}
