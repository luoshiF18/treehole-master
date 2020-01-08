package com.treehole.member.mapper;

import com.treehole.framework.domain.member.ThMenu;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;
import java.util.List;

/**
 * @author shanhuijie
 * @Description:
 * @Date
 */
public interface ThMenuMapper extends Mapper<ThMenu> {
    //根据用户id查询用户的权限
    List<ThMenu> selectPermissionByUserId(@Param(value = "id") String id);
}
