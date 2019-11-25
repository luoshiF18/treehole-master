package com.treehole.evaluation.dao;

import com.treehole.framework.domain.evaluation.WarningIntervene;
import com.treehole.framework.domain.evaluation.request.InterveneRequest;
import org.springframework.data.repository.query.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @Author: Qbl
 * Created by 17:37 on 2019/11/23.`
 * Version: 1.0
 */
public interface WarningInterveneMapper extends Mapper<WarningIntervene> {

    //按条件查询干预记录表
    public List<WarningIntervene> findAllByInterveneRequest(@Param("interveneRequest")InterveneRequest interveneRequest);
}
