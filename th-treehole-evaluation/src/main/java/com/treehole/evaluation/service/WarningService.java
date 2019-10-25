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
import com.treehole.framework.domain.evaluation.response.EvaluationCode;
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
    public QueryResponseResult findAll() {

        //设置分页参数
        PageHelper.startPage( 1, 10 );
        List<Warning> warnings = warningMapper.selectAll();
        PageInfo<Warning> warningPageInfo = new PageInfo<>( warnings );
        QueryResult queryResult = new QueryResult<>();
        queryResult.setList( warningPageInfo.getList() );
        queryResult.setTotal( warningPageInfo.getTotal() );
        QueryResponseResult queryResponseResult = new QueryResponseResult( CommonCode.SUCCESS, queryResult );
        return queryResponseResult;
    }

    //后台管理条件查询，根据量表名称、用户名、预警等级综合分页查询预警信息
    public QueryResponseResult findWarningCondition(WarningVo warningVo) {

        //根据量表名称得到量表id
        String sacleId = this.getSacleId(warningVo.getScaleName());
        //得到空对象，查询所有
        if (warningVo==null) {
            try {
                PageHelper.startPage( 1, 10 );
                List<Warning> warnings = warningMapper.selectAll();
                PageInfo<Warning> warningPageInfo = new PageInfo<>( warnings );
                QueryResult<Warning> queryResult = new QueryResult<>();
                queryResult.setTotal( warningPageInfo.getTotal() );
                queryResult.setList( warningPageInfo.getList() );
                QueryResponseResult queryResponseResult = new QueryResponseResult( CommonCode.SUCCESS, queryResult );
                return queryResponseResult;
            } catch (RuntimeException e) {
                e.getCause();
            }
        }
        Warning warning = new Warning();
        if(sacleId!=null||StringUtils.isNoneEmpty(sacleId)){
            warning.setScaleId(sacleId);
        }
        if (warningVo.getUserName()!=null||StringUtils.isNoneEmpty(warningVo.getUserName())){
            //根据用户名得到用户id
            UserVo userVoByNickname = userClient.getUserVoByNickname(warningVo.getUserName());
            warning.setUserId(userVoByNickname.getUser_id());
        }
        if (warningVo.getWarningLevel()!=0){
            warning.setWarningLevel(warningVo.getWarningLevel());
        }
        //设置分页参数
        PageHelper.startPage( 1, 10 );
        List<Warning> warningList = warningMapper.select( warning );
        PageInfo<Warning> warningPageInfo = new PageInfo<>( warningList );
        QueryResult<Warning> queryResult = new QueryResult<>();
        queryResult.setList( warningPageInfo.getList() );
        queryResult.setTotal( warningPageInfo.getTotal() );
        QueryResponseResult queryResponseResult = new QueryResponseResult( CommonCode.SUCCESS, queryResult );
        return queryResponseResult;
    }

    //查看预警详细信息
    public Warning lookWaring(String warningId) {
        if (warningId == null || StringUtils.isEmpty( warningId )) {
            ExceptionCast.cast( EvaluationCode.DATA_ERROR );
        }
        return warningMapper.selectByPrimaryKey( warningId );
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
    public ResponseResult deleteMoreWarning(String[] ids) {
        if (ids == null) {
            ExceptionCast.cast( CommonCode.FAIL );
        }
        try {
            warningMapper.deleteMoreWarning( ids );
            return ResponseResult.SUCCESS();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //根据量表名称得到量表id
    private String getSacleId(String scaleName) {
        if (scaleName==null||StringUtils.isEmpty(scaleName)){
            return null;
        }
        Scale scale = new Scale();
        scale.setScaleName(scaleName);
        Scale scaleOne = scaleMapper.selectOne( scale );
        return scaleOne.getId();
    }


}
