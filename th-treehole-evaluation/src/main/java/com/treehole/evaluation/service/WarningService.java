package com.treehole.evaluation.service;

import com.treehole.evaluation.MyUtils.MyNumberUtils;
import com.treehole.evaluation.dao.DescriptionMapper;
import com.treehole.evaluation.dao.WarningMapper;
import com.treehole.framework.domain.evaluation.Description;
import com.treehole.framework.domain.evaluation.Warning;
import com.treehole.framework.domain.evaluation.response.EvaluationCode;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.ResponseResult;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;

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

    /**
    *@param  descriptionId
    *@return ResponseResult
    */
        //根据描述表得到预警等级和预警信息存入Warning表
    public ResponseResult getWarning(String descriptionId ){

        if(descriptionId==null|| StringUtils.isEmpty(descriptionId)){
            ExceptionCast.cast( EvaluationCode.SCALE_DERC_NULL);
        }
        Description description = descriptionMapper.selectByPrimaryKey( descriptionId );
        if(description==null){
            return  new ResponseResult(EvaluationCode.SCALE_FIND_ERROR);
        }
        try {
        Warning warning = new Warning();
        Integer warningLevel = description.getWarningLevel();
        //测试 硬编码存入预警信息
        HashMap<Integer, String> map = new HashMap<>();
        map.put(2,"轻微");
        map.put(3,"严重");
        map.put(4,"非常严重");
        for (Integer integer : map.keySet()) {
            if(integer.equals(warningLevel)){
                warning.setWMessage(map.get(integer));
                break;
            }
        }
        warning.setId(MyNumberUtils.getUUID());
        warning.setUserId("1"); //硬编码测试用
        warning.setScaleId(description.getScaleId());
        warning.setWarningLevel(description.getWarningLevel());
        warning.setCreateTime(new Date());
        warningMapper.insert(warning);
        return ResponseResult.SUCCESS();
        }
        catch (RuntimeException e){
            return  new ResponseResult(EvaluationCode.WARNING_IS_ERROR);
        }
    }

}