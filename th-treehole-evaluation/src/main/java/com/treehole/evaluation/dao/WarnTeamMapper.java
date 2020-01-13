package com.treehole.evaluation.dao;

import com.treehole.framework.domain.evaluation.request.InterveneRequest;
import com.treehole.framework.domain.evaluation.request.WarnRequest;
import com.treehole.framework.domain.evaluation.vo.WarnInterveneVo;
import com.treehole.framework.domain.evaluation.vo.WarningVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: Qbl
 * Created by 15:02 on 2019/12/24.
 * Version: 1.0
 */
@Repository
public interface WarnTeamMapper{
    //根据userId查询出预警信息
     List<WarningVo> getTwarnsByUids(@Param("request" ) WarnRequest warnRequest);
    List<WarnInterveneVo> getTeamIntereven(@Param("interveneRequest")  InterveneRequest interveneRequest);

}
