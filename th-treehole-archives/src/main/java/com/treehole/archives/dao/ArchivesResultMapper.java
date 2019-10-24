package com.treehole.archives.dao;

import com.treehole.framework.domain.archives.ext.ArchivesList;
import com.treehole.framework.domain.archives.ext.ResultBase;
import com.treehole.framework.domain.archives.ext.ResultTiny;
import com.treehole.framework.domain.archives.resquest.ArchivesListRequest;
import com.treehole.framework.domain.evaluation.Result;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 任志强
 * @version 1.0
 * @date 2019/10/23 16:50
 * @description:
 */
@Mapper
public interface ArchivesResultMapper {

    //根据结果id查询结果
    ResultBase findResultById(String id);

    //查询所有结果
    List<Result> findAll();

    //根据用户id查询用户所答过的量表记录(重复量表去重)
    List<ArchivesList> findArchivesListByUserId(String userId);

    //根据用户id和量表名查询答该量表的历次作答记录
    List<ResultTiny> finArchivesByUserIdAndScaleName(ArchivesListRequest archivesListRequest);
}
