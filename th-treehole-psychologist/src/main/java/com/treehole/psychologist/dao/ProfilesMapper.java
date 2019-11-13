package com.treehole.psychologist.dao;

import com.treehole.framework.domain.psychologist.Profile;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Helay
 * @date 2019/11/13 14:42
 */
@Mapper
@Repository
public interface ProfilesMapper {

    /**
     * 按照id自增查询所有简介信息
     *
     * @return
     */
    List<Profile> findAll();
}
