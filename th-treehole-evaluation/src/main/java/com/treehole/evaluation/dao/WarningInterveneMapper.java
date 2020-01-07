package com.treehole.evaluation.dao;

import com.treehole.framework.domain.evaluation.WarningIntervene;
import com.treehole.framework.domain.evaluation.request.InterveneRequest;
import com.treehole.framework.domain.evaluation.vo.WarnInterveneVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @Author: Qbl
 * Created by 17:37 on 2019/11/23.`
 * Version: 1.0
 */
@Repository
public interface WarningInterveneMapper extends Mapper<WarningIntervene> {

    //按条件查询干预记录表
    public List<WarnInterveneVo> getAllByInterveneRequest(@Param("interveneRequest") InterveneRequest interveneRequest);

    //根据用户名查询
    public List<WarningIntervene> findWarnInterveneByName(String userNickName);
    }
