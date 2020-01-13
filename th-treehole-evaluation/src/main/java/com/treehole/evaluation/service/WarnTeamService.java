package com.treehole.evaluation.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.treehole.evaluation.client.UserClient;
import com.treehole.evaluation.dao.WarnTeamMapper;
import com.treehole.evaluation.dao.WarningInterveneMapper;
import com.treehole.evaluation.dao.WarningMapper;
import com.treehole.framework.domain.evaluation.WarningIntervene;
import com.treehole.framework.domain.evaluation.request.InterveneRequest;
import com.treehole.framework.domain.evaluation.request.WarnRequest;
import com.treehole.framework.domain.evaluation.response.EvaluationCode;
import com.treehole.framework.domain.evaluation.vo.WarnHUserVo;
import com.treehole.framework.domain.evaluation.vo.WarnInterveneVo;
import com.treehole.framework.domain.evaluation.vo.WarningVo;
import com.treehole.framework.domain.member.Vo.UserVo;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.framework.model.response.ResponseResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Qbl
 * Created by 10:40 on 2019/12/24.
 * Version: 1.0
 */
@Service
public class WarnTeamService {

    @Autowired
    private WarningMapper warningMapper;
    @Autowired
    private WarnTeamMapper warnTeamMapper;
    @Autowired
    private WarningInterveneMapper warningInterveneMapper;
    @Autowired
    private UserClient userClient;
    /* 根据条件查询出该机构下人员产生的预警信息*/
    public QueryResponseResult findAllTWarn(int size,int page,WarnRequest warnRequest) {
        if(StringUtils.isBlank(warnRequest.getCompanyId())){
            ExceptionCast.cast( EvaluationCode.DATA_ERROR);
            return null;
        }
        //先查询出该机构下的用户和机构信息
        List<UserVo> userByCid = userClient.getUserByCid( warnRequest.getCompanyId() );
        if(userByCid==null){
            ExceptionCast.cast( EvaluationCode.DATA_ERROR);
        }
        List<String> userIds = new ArrayList<>();
        for (UserVo userVo : userByCid) {
            userIds.add(userVo.getUser_id());
        }
        warnRequest.setUserIds(userIds);
        //根据用户id查询出对应的预警信息
        List<WarningVo> twarnsByUids = warnTeamMapper.getTwarnsByUids( warnRequest );
        //拼接预警信息，返回前台
        for (WarningVo twarnsByUid : twarnsByUids) {
            for(UserVo userVos : userByCid){
                if(twarnsByUid.getUserId().equals(userVos.getUser_id())){
                    twarnsByUid.setUserName(userVos.getUser_name());
                    twarnsByUid.setCompany(userVos.getCompanyName());
                    twarnsByUid.setUserNickName(userVos.getUser_nickname() );
                    twarnsByUid.setPhone(userVos.getUser_phone() );
                    twarnsByUid.setSex(userVos.getGender());
                }
            }
        }
        //设置分页，展示数据
        PageInfo<WarningVo> warningVoPageInfo = new PageInfo<WarningVo>(twarnsByUids);
        QueryResult<WarningVo> queryResult = new QueryResult<>();
        queryResult.setTotal(warningVoPageInfo.getTotal());
        queryResult.setList(warningVoPageInfo.getList());
        QueryResponseResult queryResponseResult = new QueryResponseResult( CommonCode.SUCCESS, queryResult );
        return queryResponseResult;
    }

    //筛选高危人群
    public QueryResponseResult findHighWarn(int size,int page, String companyId,String userNickName){

        if(StringUtils.isBlank(companyId)){
            ExceptionCast.cast(EvaluationCode.DATA_ERROR);
        }
        //先查询出该机构下的用户和机构信息
        List<UserVo> userByCid = userClient.getUserByCid(companyId );
        if(userByCid==null){
            ExceptionCast.cast( EvaluationCode.DATA_ERROR);
        }
        List<String> userIds = new ArrayList<>();
        for (UserVo userVo : userByCid) {
            userIds.add(userVo.getUser_id());
        }
        //根据用户id查询出对应的预警信息
        List<WarnHUserVo> highRisk = warningMapper.findHighRisk( userNickName, userIds );
        System.out.println(highRisk);
        //循环拼接用户信息
        for (WarnHUserVo user : highRisk) {
            for (UserVo userVo : userByCid){
                if(user.getUserId().equals( userVo.getUser_id())){
                    user.setUserName(userVo.getUser_name() );
                    user.setUserNickName(userVo.getUser_nickname() );
                    user.setSex(userVo.getGender() );
                    user.setPhone( userVo.getUser_phone() );
                }
            }
        }
        //设置分页，展示数据
        PageHelper.startPage(page,size);
        PageInfo<WarnHUserVo> highRisks = new PageInfo<WarnHUserVo>(highRisk);
        QueryResult<WarnHUserVo> queryResult = new QueryResult<>();
        queryResult.setTotal(highRisks.getTotal());
        queryResult.setList(highRisks.getList());
        QueryResponseResult queryResponseResult = new QueryResponseResult( CommonCode.SUCCESS, queryResult );
        return queryResponseResult;
    }

    //干预记录表查询
    public QueryResponseResult teamIntereven(int size, int page,
                                             String companyId,
                                             InterveneRequest interveneRequest){
        //根据机构信息得到该机构下用户
        if(StringUtils.isBlank(companyId)){
            ExceptionCast.cast(EvaluationCode.DATA_ERROR);
        }
        //先查询出该机构下的用户和机构信息
        List<UserVo> userByCid = userClient.getUserByCid(companyId );
        if(userByCid==null){
            ExceptionCast.cast( EvaluationCode.DATA_ERROR);
        }
        List<String> usernames = new ArrayList<>();
        for (UserVo userVo : userByCid) {
            usernames.add(userVo.getUser_nickname());
        }
        interveneRequest.setUsernames(usernames);
        //根据用户姓名查询出对应的干预表信息
        List<WarnInterveneVo> teamIntereven = warnTeamMapper.getTeamIntereven( interveneRequest );
        for(WarnInterveneVo warnInterveneVo: teamIntereven){
            for (UserVo userVo :userByCid){
                if(warnInterveneVo.getUserName().equals(userVo.getUser_nickname())){
                    warnInterveneVo.setUser_name(userVo.getUser_name() );
                    warnInterveneVo.setGender(userVo.getGender() );
                    warnInterveneVo.setUser_phone(userVo.getUser_phone() );
                    warnInterveneVo.setCompany_id(userVo.getCompanyName());
                }
            }
        }
        //设置分页参数
        PageHelper.startPage(page,size);
        PageInfo<WarnInterveneVo> warningVoPageInfo = new PageInfo<WarnInterveneVo>(teamIntereven);
        QueryResult<WarnInterveneVo> queryResult = new QueryResult<>();
        queryResult.setTotal(warningVoPageInfo.getTotal());
        queryResult.setList(warningVoPageInfo.getList());
        QueryResponseResult queryResponseResult = new QueryResponseResult( CommonCode.SUCCESS, queryResult );
        return queryResponseResult;
    }
    //修改干预记录表信息
    public ResponseResult  updateIntervene(WarningIntervene warningIntervene){
        if(warningIntervene==null){
            ExceptionCast.cast(EvaluationCode.DATA_ERROR);
        }
        try {
            warningInterveneMapper.updateByPrimaryKey( warningIntervene );
            return new ResponseResult(CommonCode.SUCCESS);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseResult( CommonCode.FAIL);
        }
    }



    private List<String> userIds(String companyId){
        //先查询出该机构下的用户和机构信息
        List<UserVo> userByCid = userClient.getUserByCid(companyId);
        if(userByCid==null){
            ExceptionCast.cast( EvaluationCode.DATA_ERROR);
        }
        List<String> userIds = new ArrayList<>();
        for (UserVo userVo : userByCid) {
            userIds.add(userVo.getUser_id());
        }
        return userIds;
    }




}
