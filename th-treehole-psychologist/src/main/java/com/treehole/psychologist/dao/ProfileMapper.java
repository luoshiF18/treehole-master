package com.treehole.psychologist.dao;

import com.treehole.framework.domain.psychologist.Profile;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author Helay
 * @date 2019/10/25 9:42
 */
public interface ProfileMapper extends Mapper<Profile> {

    //按照id自增查询所有简介信息
    @Select("select * from profile order by id+0")
    List<Profile> getAllProfiles();
}
