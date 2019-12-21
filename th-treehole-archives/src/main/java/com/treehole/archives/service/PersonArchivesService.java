package com.treehole.archives.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.treehole.archives.client.ScaleSelectClient;
import com.treehole.archives.client.UserVoClient;
import com.treehole.archives.dao.ArchivesResultMapper;
import com.treehole.framework.domain.archives.ext.ArchivesExt;
import com.treehole.framework.domain.archives.ext.ArchivesList;
import com.treehole.framework.domain.archives.ext.ResultTiny;
import com.treehole.framework.domain.archives.response.ArchivesCode;
import com.treehole.framework.domain.archives.response.ArchivesCountResult;
import com.treehole.framework.domain.archives.resquest.ArchivesListRequest;
import com.treehole.framework.domain.evaluation.response.DetailResult;
import com.treehole.framework.domain.evaluation.vo.ScaleDetailVO2;
import com.treehole.framework.domain.member.Vo.UserVo;
import com.treehole.framework.domain.member.resquest.UserListRequest;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 任志强
 * @version 1.0
 * @date 2019/10/24 14:16
 * @description: 个体档案service
 */
@Service
public class PersonArchivesService {

    @Autowired
    private UserVoClient userVoClient;

    @Autowired
    private ArchivesResultMapper archivesResultMapper;

    @Autowired
    private ScaleSelectClient scaleSelectClient;

    public QueryResponseResult findArchivesList(
            Integer page,
            Integer size,
            ArchivesListRequest archivesListRequest) {
        //判断请求条件的合法性
        if (archivesListRequest == null || StringUtils.isEmpty(archivesListRequest.getUserId())){
           ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        //判断页码是否合法
        if (page < 0){
            page = 0;
        }
        //判断size是否合法,默认值为8页
        if (size <= 0){
            size = 8;
        }
        //设置分页
        PageHelper.startPage(page,size);
        //查询用户做的量表档案
        Page<ArchivesList> pageInfo = archivesResultMapper.findArchivesListByUserId(archivesListRequest.getUserId());
        if (pageInfo == null){
            ExceptionCast.cast(ArchivesCode.ARCHIVES_PERSONRESULTVO_ISNULL);
        }
        //暂时先不设置用户数据
        //UserVo userVo = findUserById(archivesListRequest.getUserId());
        //设置用户数据

        //构建返回查询对象
        QueryResult<ArchivesList> queryResult = new QueryResult<>();
        queryResult.setList(pageInfo.getResult());
        queryResult.setTotal(pageInfo.getTotal());
        return new QueryResponseResult(CommonCode.SUCCESS,queryResult);
    }

    /**
     * 根据用户id查询用户信息
     * @param userId
     * @return
     */
    private UserVo findUserById(String userId){
        UserVo userVoByUserId = userVoClient.getUserVoByUserId(userId);
        return userVoByUserId;  //上层接口已做了空值判定
    }

    /**
     * 根据用户id和量表名查询个人详细档案记录
     * @param archivesListRequest
     * @return
     */
    public ArchivesExt findArchivesExt(ArchivesListRequest archivesListRequest) {
        ArchivesExt archivesExt = new ArchivesExt();
        //设置量表名称
        archivesExt.setScaleName(archivesListRequest.getScaleName());
        //设置用户id
        archivesExt.setUserId(archivesListRequest.getUserId());
        //得到历次作答记录信息
        List<ResultTiny> resultTinies = archivesResultMapper.finArchivesByUserIdAndScaleName(archivesListRequest);
        //设置历次作答记录
        archivesExt.setResultTinies(resultTinies);
        //设置答题次数
        archivesExt.setScaleCount(resultTinies.size());
        //根据id获取用户信息
        UserVo userVo = findUserById(archivesListRequest.getUserId());
        if (userVo == null){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        //将用户信息拷贝到archivesExt中
        BeanUtils.copyProperties(userVo,archivesExt);
        //根据量表名称获取量表信息

        //根据量表id(名称)查询量表的基本信息
        DetailResult scaleDetailRes = scaleSelectClient.findScaleDetail(null, archivesListRequest.getScaleName());
        if (scaleDetailRes == null){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        ScaleDetailVO2 scaleDetail = scaleDetailRes.getScaleDetail();
        //设置主题描述
        archivesExt.setTopicBackground(scaleDetail.getTopicBackground());
        return archivesExt;
    }
    /**
     * 查询所有用户档案
     * @param page
     * @param size
     * @param userListRequest
     * @return
     */
    public QueryResponseResult findAllUserArchivesList(
            Integer page,
            Integer size,
            UserListRequest userListRequest) {
        //判断请求条件的合法性
        if (userListRequest == null){
            userListRequest = new UserListRequest();
        }
        //判断页码是否合法
        if (page < 0){
            page = 0;
        }
        //判断size是否合法,默认值为8页
        if (size <= 0){
            size = 8;
        }
        //远程调用用户查询接口
        return userVoClient.findAllUserVo(page,size,userListRequest);
    }

    /**
     * 根据用户id查询用户是否有作答记录
     * @param userId
     * @return
     */
    public ArchivesCountResult findArchivesCount(String userId) {
        if (StringUtils.isEmpty(userId)){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        Integer count = archivesResultMapper.queryResultByUserId(userId);
        return new ArchivesCountResult(CommonCode.SUCCESS,count>0 ? true : false);
    }
}
