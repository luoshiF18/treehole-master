package com.treehole.member.mapper;

import com.treehole.framework.domain.member.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface UserSQL {
    List<User> selectUserbydate(@Param(value = "before")Date before,
                                @Param(value = "after") Date after);
}
