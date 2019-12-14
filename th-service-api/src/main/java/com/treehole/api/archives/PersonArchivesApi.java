package com.treehole.api.archives;

import com.treehole.framework.domain.archives.response.ArchivesCountResult;
import com.treehole.framework.domain.archives.resquest.ArchivesListRequest;
import com.treehole.framework.domain.member.resquest.UserListRequest;
import com.treehole.framework.model.response.QueryResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author 任志强
 * @version 1.0
 * @date 2019/10/24 13:58
 * @description: 个人档案相关接口
 */
@Api(value="个人档案相关接口",description = "提供个人档案相关的接口")
public interface PersonArchivesApi {

    @ApiOperation("查询userId查询个人档案列表接口")
    public QueryResponseResult findArchivesList(
            Integer page,
            Integer size,
            ArchivesListRequest archivesListRequest
    );

/*    @ApiOperation("根据用户id和量表名查询个人详细档案记录")
    public ArchivesExt findArchivesExt(
            ArchivesListRequest archivesListRequest
    );*/

    @ApiOperation("查询所有用户的档案记录")
    public QueryResponseResult findAllUserArchivesList(
            Integer page,
            Integer size,
            UserListRequest userListRequest
    );

    @ApiOperation("根据用户id查询用户是否有作答记录")
    public ArchivesCountResult findArchivesCount(
            String userId
    );

}
