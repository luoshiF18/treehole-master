package com.treehole.evaluation.service;

import com.treehole.evaluation.dao.WarningInterveneMapper;
import com.treehole.framework.domain.evaluation.WarningIntervene;
import com.treehole.framework.domain.evaluation.request.InterveneRequest;
import com.treehole.framework.domain.evaluation.response.EvaluationCode;
import com.treehole.framework.domain.evaluation.vo.WarnInterveneVo;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.ResponseResult;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    //新增干预记录表
    public ResponseResult addRecord(WarningIntervene warningIntervene){
        //只有在预警表里的用户才能被添加用户信息

        if(warningIntervene==null){
            return new ResponseResult(CommonCode.FAIL);
        }
        try {
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
    public WarnInterveneVo find(InterveneRequest interveneRequest){

        if(interveneRequest==null){
            ExceptionCast.cast( EvaluationCode.DATA_ERROR);
        }
        //根据请求条件得到干预表信息
        List<WarningIntervene> warningIntervenes = warningInterveneMapper.findAllByInterveneRequest( interveneRequest );
        if (warningIntervenes.size()==0){
            ExceptionCast.cast( EvaluationCode.SELECT_NULL);
            return null; //没有查询到数据
        }
        //从干预表得到每一条记录的用户名
        List<String> usersName = new ArrayList<>();
        for (WarningIntervene warningIntervene : warningIntervenes) {
           usersName.add(warningIntervene.getUserName());
        }
        //根据用户名远程调用得到用户信息

        //拼接用户的信息



        return null;
    }
    //只有在预警表里的用户才能被添加用户信息





}
