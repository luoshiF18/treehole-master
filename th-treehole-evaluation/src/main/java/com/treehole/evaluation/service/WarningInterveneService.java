package com.treehole.evaluation.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.treehole.evaluation.MyUtils.MyNumberUtils;
import com.treehole.evaluation.client.UserClient;
import com.treehole.evaluation.dao.WarningInterveneMapper;
import com.treehole.evaluation.dao.WarningMapper;
import com.treehole.framework.domain.evaluation.Warning;
import com.treehole.framework.domain.evaluation.WarningIntervene;
import com.treehole.framework.domain.evaluation.request.InterveneRequest;
import com.treehole.framework.domain.evaluation.response.EvaluationCode;
import com.treehole.framework.domain.evaluation.vo.WarnInterveneVo;
import com.treehole.framework.domain.member.Vo.UserVo;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.framework.model.response.ResponseResult;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: Qbl
 * Created by 17:34 on 2019/11/23.
 * Version: 1.0
 */
@Service
@AllArgsConstructor
public class WarningInterveneService {


    private final WarningInterveneMapper warningInterveneMapper;
    private final UserClient userClient;
    private final WarningMapper warningMapper;

    //新增干预记录表
    public ResponseResult addRecord(WarningIntervene warningIntervene){
        if(warningIntervene==null||StringUtils.isBlank(warningIntervene.getUserName()))
        {
                    ExceptionCast.cast(EvaluationCode.DATA_ERROR);
                    return null;
        }
        //只有在预警表里的用户才能被添加用户信息
        UserVo userVoByNickname = userClient.getUserVoByNickname( warningIntervene.getUserName() );
        if(userVoByNickname==null){
            userVoByNickname=null;
        }
        Warning warning = new Warning();
        warning.setUserId(userVoByNickname.getUser_id());
        List<Warning> select = warningMapper.select( warning );
        if(select.size()==0||select==null){
            return new ResponseResult(CommonCode.NOTEXIST);
        }
        //一个用户只能被添加一个记录表
        List<WarningIntervene> warnInterveneByName = warningInterveneMapper.findWarnInterveneByName( warningIntervene.getUserName());
        if(warnInterveneByName!=null&&warnInterveneByName.size()!=0){
            return new ResponseResult(CommonCode.REPETITION);
        }
        try {
            warningIntervene.setId(MyNumberUtils.getUUID());
            warningIntervene.setCreateTime( new Date());
            warningInterveneMapper.insert( warningIntervene );
            return ResponseResult.SUCCESS();
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //根据id删除
    public ResponseResult delIntervene(String InterveneId){
        if(InterveneId==null|| StringUtils.isBlank(InterveneId)){
            return new ResponseResult(CommonCode.FAIL);
        }

        try {
            warningInterveneMapper.deleteByPrimaryKey(InterveneId);
            return ResponseResult.SUCCESS();
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    //批量删除

    //修改



    //条件查询，根据用户名、咨询人员名(有可能是心理咨询师也有可能是团体负责人，
    // 在前端设置一个单选框来区分)、日期区间、团体名称
    public QueryResponseResult getInterveneCondition(int page,int size,InterveneRequest interveneRequest){

       /* if(interveneRequest==null){
            ExceptionCast.cast( EvaluationCode.DATA_ERROR);
        }*/

        //根据请求条件得到干预表信息
        List<WarnInterveneVo> warnInterveneVos = warningInterveneMapper.getAllByInterveneRequest( interveneRequest );
        if (warnInterveneVos.size()==0||warnInterveneVos==null){
            ExceptionCast.cast( EvaluationCode.SELECT_NULL);
            return null; //没有查询到数据
        }
        //从干预表得到每一条记录的用户名
        List<String> names = new ArrayList<>();
        for (WarnInterveneVo warniInterveneVo : warnInterveneVos) {
            names.add(warniInterveneVo.getUserName());
        }
        //根据用户名远程调用得到用户信息
        List<UserVo> userByNicknames = userClient.findUserByNicknames( names );

        //拼接用户的信息,返回前台展示
        for (WarnInterveneVo warningIntervene : warnInterveneVos) {
            for(UserVo userVo : userByNicknames){
                if(userVo.getUser_nickname().equals(warningIntervene.getUserName())){
                    warningIntervene.setUser_name(userVo.getUser_name() );
                    warningIntervene.setGender(userVo.getGender() );
                    warningIntervene.setUser_phone(userVo.getUser_phone() );
                    warningIntervene.setCompany_id("暂时写死");
                }
            }
        }
        //设置分页参数
        PageHelper.startPage(page,size);
        PageInfo<WarnInterveneVo> warningVoPageInfo = new PageInfo<WarnInterveneVo>(warnInterveneVos);
        QueryResult<WarnInterveneVo> queryResult = new QueryResult<>();
        queryResult.setTotal(warningVoPageInfo.getTotal());
        queryResult.setList(warningVoPageInfo.getList());
        QueryResponseResult queryResponseResult = new QueryResponseResult( CommonCode.SUCCESS, queryResult );
        return queryResponseResult;
    }

    //查看详细干预信息
    public WarningIntervene getInterveneById(String interveneId){

        if(StringUtils.isBlank(interveneId)){
            ExceptionCast.cast(EvaluationCode.DATA_ERROR);
        }
        //根据id得到干预表信息
        try {
            return warningInterveneMapper.selectByPrimaryKey(interveneId);
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }





}
