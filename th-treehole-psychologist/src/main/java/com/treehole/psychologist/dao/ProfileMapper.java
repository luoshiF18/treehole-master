package com.treehole.psychologist.dao;

import com.treehole.framework.domain.psychologist.Profile;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author Helay
 * @date 2019/10/25 9:42
 */
public interface ProfileMapper extends Mapper<Profile> {

    //查询咨询师资质占比情况
    @Select("select p.qualification name,count(p.id) value from profile p group by p.qualification")
    List<Map<String, Object>> getQualificationCount();
}
