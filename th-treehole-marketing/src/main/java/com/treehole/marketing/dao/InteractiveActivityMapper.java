package com.treehole.marketing.dao;

import com.treehole.framework.domain.marketing.InteractiveActivity;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface InteractiveActivityMapper extends Mapper<InteractiveActivity> {
    List<InteractiveActivity> queryActivityByReleaseTime(String today);
}
