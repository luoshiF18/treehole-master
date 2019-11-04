package com.treehole.archives.controller;

import com.treehole.api.archives.PersonArchivesApi;
import com.treehole.archives.service.PersonArchivesService;
import com.treehole.framework.domain.archives.ext.ArchivesExt;
import com.treehole.framework.domain.archives.resquest.ArchivesListRequest;
import com.treehole.framework.domain.member.resquest.UserListRequest;
import com.treehole.framework.model.response.QueryResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 任志强
 * @version 1.0
 * @date 2019/10/24 14:08
 * @description: 个人档案具体接口
 */
@RestController
@RequestMapping("/archives")
public class PersonArchivesController implements PersonArchivesApi {

    @Autowired
    private PersonArchivesService personArchivesService;

    /**
     * 查询个人档案列表接口
     * @param page
     * @param size
     * @param archivesListRequest
     * @return
     */
    @Override
    @GetMapping("/person/list/{page}/{size}")
    public QueryResponseResult findArchivesList(
            @PathVariable("page") Integer page,
            @PathVariable("size") Integer size,
            ArchivesListRequest archivesListRequest) {
        return personArchivesService.findArchivesList(page,size,archivesListRequest);
    }

    /**
     * 根据用户id和量表名查询个人详细档案记录
     * @param archivesListRequest
     * @return
     */
    @Override
    @GetMapping("/person/detail")
    public ArchivesExt findArchivesExt(
            ArchivesListRequest archivesListRequest) {
        return personArchivesService.findArchivesExt(archivesListRequest);
    }

    /**
     * 查询所有用户的档案接口
     * @param page
     * @param size
     * @param userListRequest
     * @return
     */
    @Override
    @GetMapping("/person/allUser/{page}/{size}")
    public QueryResponseResult findAllUserArchivesList(
            @PathVariable("page") Integer page,
            @PathVariable("size") Integer size,
            UserListRequest userListRequest) {
        return personArchivesService.findAllUserArchivesList(page,size,userListRequest);
    }
}
