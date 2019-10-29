package com.treehole.evaluation.dao;

import com.treehole.framework.domain.evaluation.Warning;
import com.treehole.framework.domain.evaluation.vo.WarningVo;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @Author: Qbl
 * Created by 14:24 on 2019/10/14.
 * Version: 1.0
 */
public interface WarningMapper extends Mapper<Warning> {

    void deleteMoreWarning(List<String> ids);
    List<WarningVo> findScaleWarning();
    List<WarningVo> getWaning(List listScaleId);
}
