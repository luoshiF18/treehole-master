package com.document.manage.service;

import com.document.manage.pojo.Folder;

import java.util.List;

/**
 * @Auther: lee
 * @Date: 2019/10/28
 * @Description: com.lee.springboot.service
 * @version: 1.0
 */
public interface FolderService {
    /**
     * 新建文件夹
     * @return
     */
    int addFolder(String name, String parentId, String userId);

    /**
     * 根据父id查询所有文件夹
     * @return
     */
    List<Folder> findAllFolder(String parentId);

    /**
     * 根据id和状态查询此文件夹
     * @param id
     * @return
     */
    Folder findFolderById(String id, Integer status);

    /**
     * 重命名文件夹
     * @param id
     * @param name
     * @return
     */
    int updateName(String id, String name, String userId);

    //物理删除文件夹以及此文件夹下的文档
    int destroyFolder(String id, String userId);

    /**
     * 根据id逻辑删除文件夹
     * @param id
     * @return
     */
    int deleteFolder(String id, String userId);

    /**
     * 还原回收站中的文件夹
     * @param id
     * @return
     */
    int recoverFolder(String id, String userId);

    /**
     * 查询回收站中的文件夹
     * @return
     */
    List<Folder> showRecycledFolder();
}
