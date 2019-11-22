package com.treehole.evaluation.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.treehole.evaluation.MyUtils.MyNumberUtils;
import com.treehole.evaluation.client.UserClient;
import com.treehole.evaluation.dao.DescriptionMapper;
import com.treehole.evaluation.dao.ScaleMapper;
import com.treehole.evaluation.dao.WarningMapper;
import com.treehole.framework.domain.evaluation.Scale;
import com.treehole.framework.domain.evaluation.Warning;
import com.treehole.framework.domain.evaluation.request.WarnRequest;
import com.treehole.framework.domain.evaluation.response.EvaluationCode;
import com.treehole.framework.domain.evaluation.vo.WarnReportVo;
import com.treehole.framework.domain.evaluation.vo.WarningVo;
import com.treehole.framework.domain.member.Vo.UserVo;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.framework.model.response.ResponseResult;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: Qbl
 * Created by 9:30 on 2019/10/17.
 * Version: 1.0
 */
@Service
@AllArgsConstructor
public class WarningService {

    private final DescriptionMapper descriptionMapper;
    private final WarningMapper warningMapper;
    private final ScaleMapper scaleMapper;
    private final UserClient userClient;


    //心理咨询师添加咨询用户的预警信息
    @Transactional
    public ResponseResult addWarningByPsy(Warning warning) {
        //得到预警对象,判断对象里用户
        if (warning == null) {
            ExceptionCast.cast( CommonCode.FAIL );
        }
        try {
            warning.setId( MyNumberUtils.getUUID() );
            warning.setUserId( "null" );//从哪里得到用户id???
            warning.setCreateTime( new Date() );
            warning.setScaleId( "null" );//得到咨询师让用户做的那张量表id
            warning.setStatus( 1 );  //咨询师给出的预警信息状态码为1
            warningMapper.insert( warning );
            return ResponseResult.SUCCESS();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //根据用户id查询用户的预警信息，分页查询
    public QueryResponseResult selectWarningByUserId(String userId, int page, int size) {
        //TODO 只限后台管理查询 ，心理咨询师查看待写
        if (userId == null || StringUtils.isEmpty( userId )) {
            ExceptionCast.cast( EvaluationCode.DATA_ERROR );
        }
        Warning warning = new Warning();
        warning.setUserId(userId);
        //设置分页参数
        if (page <= 0) {
            page = 1;
        }
        page = page - 1;
        if (size <= 0) {
            size = 10;
        }
        try {
            PageHelper.startPage( page, size );
            List<Warning> warnings = warningMapper.select( warning );
            PageInfo<Warning> warningPageInfo = new PageInfo<Warning>( warnings );
            QueryResult queryResult = new QueryResult<>();
            queryResult.setList( warningPageInfo.getList() ); //数据列表
            queryResult.setTotal( warningPageInfo.getTotal() ); //总记录数
            QueryResponseResult queryResponseResult = new QueryResponseResult( CommonCode.SUCCESS, queryResult );
            return queryResponseResult;
        } catch (RuntimeException e) {
            ExceptionCast.cast( EvaluationCode.SELECT_NULL );
            return null;
        }
    }

    //分页查询数据库中所有预警信息
    public QueryResponseResult findAll(int page,int size) {
        //设置分页参数
        PageHelper.startPage(page,size);
        //得到量表名称和预警信息
        List<WarningVo> scaleWarning = warningMapper.findScaleWarning();
        //遍历得到userId
        List listUserId = new ArrayList();
        for (WarningVo warningVo : scaleWarning) {
            listUserId.add(warningVo.getUserId());
        }
        //远程调用得到用户信息
        List<UserVo> allUser = userClient.getAllUser( listUserId );
        if (allUser.get(0)!=null||scaleWarning.get(0)!=null) {
            for (UserVo userVo : allUser) {
                for (WarningVo warningVo : scaleWarning) {
                    if (userVo.getUser_id().equals(warningVo.getUserId())) {
                        if(userVo.getGender()==null||userVo.getUser_birth()==null){
                            warningVo.setSex(2);
                            warningVo.setUserBirth(null);
                        }
                        else {
                            warningVo.setSex(userVo.getGender());
                        }
                        warningVo.setUserName( userVo.getUser_name() );
                        warningVo.setUserNickName( userVo.getUser_nickname() );
                    }
                }
            }
        }else {
            ExceptionCast.cast(EvaluationCode.DATA_ERROR);
            return new QueryResponseResult(CommonCode.FAIL,null);
        }
        PageInfo<WarningVo> warningVoPageInfo = new PageInfo<WarningVo>(scaleWarning);
        QueryResult<WarningVo> queryResult = new QueryResult<>();
        queryResult.setTotal(warningVoPageInfo.getTotal());
        queryResult.setList(warningVoPageInfo.getList());
        QueryResponseResult queryResponseResult = new QueryResponseResult( CommonCode.SUCCESS, queryResult );
        return queryResponseResult;
    }

    //后台管理条件查询，根据量表名称、用户名、预警等级综合分页查询预警信息
    public QueryResponseResult findWarningCondition(int page,int size,WarnRequest warnRequest) {
        if(warnRequest==null){
            return  this.findAll(page, size);
        }
        //根据传入的条件查询出用户的预警信息
        if(warnRequest.getUserNickName()!=null
           ||StringUtils.isNotEmpty(warnRequest.getUserNickName())){
            UserVo userVoByNickname = userClient.getUserVoByNickname( warnRequest.getUserNickName());
            warnRequest.setUserId(userVoByNickname.getUser_id());
        }
        System.out.println(warnRequest);
        PageHelper.startPage(page,size);
        List<WarningVo> warning = warningMapper.getWaning(warnRequest);
        List listUserId = new ArrayList();
        for (WarningVo vo : warning) {
            listUserId.add(vo.getUserId());
        }
        List<UserVo> allUser = userClient.getAllUser( listUserId );
        for (WarningVo warningVo : warning) {
            for (UserVo userVo : allUser){
                if(warningVo.getUserId().equals(userVo.getUser_id())){
                    warningVo.setUserName(userVo.getUser_name() );
                    warningVo.setUserNickName(userVo.getUser_nickname());
                    if(userVo.getGender()!=null){
                        warningVo.setSex(userVo.getGender());
                    }
                }
            }
        }
        PageInfo<WarningVo> warningVoPageInfo = new PageInfo<WarningVo>(warning);
        QueryResult<WarningVo> queryResult = new QueryResult<>();
        queryResult.setTotal(warningVoPageInfo.getTotal());
        queryResult.setList(warningVoPageInfo.getList());
        QueryResponseResult queryResponseResult = new QueryResponseResult( CommonCode.SUCCESS, queryResult );
        return queryResponseResult;
    }

    //查看预警详细信息
    public WarnReportVo lookWaring(String warningId) {
        //根据id查询出预警信息
        if (warningId == null || StringUtils.isEmpty( warningId )) {
            ExceptionCast.cast( EvaluationCode.DATA_ERROR );
        }
        try {
            Warning warning = warningMapper.selectByPrimaryKey( warningId );
            Scale scale = scaleMapper.selectByPrimaryKey( warning.getScaleId());
            UserVo userVo = userClient.getUserVoByUserId( warning.getUserId() );
            WarnReportVo warnReportVo = new WarnReportVo();
            //存入用户基本信息
            warnReportVo.setUserNickName(userVo.getUser_nickname() );
            warnReportVo.setUserName(userVo.getUser_name());
            warnReportVo.setScaleName(scale.getScaleName() );
            if(userVo.getUser_birth()!=null){
                warnReportVo.setUserBirth(userVo.getUser_birth() );
            }
            else {
                warnReportVo.setUserBirth(null);
            }
            if(userVo.getGender()!=null){
                warnReportVo.setSex(userVo.getGender());
            }
            warnReportVo.setPhone(userVo.getUser_phone());
            warnReportVo.setUserEmail( userVo.getUser_email() );
            warnReportVo.setUserRegion(userVo.getUser_region() );
            //存入量表信息
            warnReportVo.setScaleName(scale.getScaleName());
            warnReportVo.setTopicDescription( scale.getTopicDescription());
            warnReportVo.setScaleFunction(scale.getScaleFunction());
            //存入预警信息
            warnReportVo.setStatus(warning.getStatus());
            warnReportVo.setWarningLevel(warning.getWarningLevel() );
            warnReportVo.setWMessage(warning.getWMessage());
            warnReportVo.setCreateTime(warning.getCreateTime());
            return warnReportVo;
        }catch (Exception e)
        {
            e.printStackTrace();
            return  null;
        }
    }

    //根据用户id删除用户的预警记录
    public ResponseResult deleteWarning(String warningId) {
        if (warningId == null || StringUtils.isEmpty( warningId )) {
            ExceptionCast.cast( EvaluationCode.DATA_ERROR );
        }
        try {
            warningMapper.deleteByPrimaryKey( warningId );
            return ResponseResult.SUCCESS();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    //批量删除预警记录
    public ResponseResult deleteMoreWarning(List<String> ids) {
        if (ids == null) {
            ExceptionCast.cast( CommonCode.FAIL );
        }
        try {
            warningMapper.deleteMoreWarning(ids);
            return ResponseResult.SUCCESS();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    //根据量表名称得到量表id
    private String getScaleId(String scaleName) {
        if (scaleName==null||StringUtils.isEmpty(scaleName)){
            return null;
        }
        Scale scale = new Scale();
        scale.setScaleName(scaleName);
        Scale scaleOne = scaleMapper.selectOne( scale );
        return scaleOne.getId();
    }
    //根据用户名称得到用户信息
    private UserVo getUsersByName(String userNickNmae)
    {
        if(userNickNmae!=null&&StringUtils.isNotEmpty(userNickNmae )){
            UserVo userVoByNickname = userClient.getUserVoByNickname( userNickNmae );
            return userVoByNickname;
        }
        else {
            return  null;
        }
    }
    //根据用户id的list得到用户信息
    private List<UserVo> getUsersByUserId(List listUserId){
        if(listUserId.get( 0 )!=null){
            List<UserVo> allUser = userClient.getAllUser( listUserId );
            return allUser;
        }
        else return null;
    }
}
