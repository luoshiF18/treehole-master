package com.treehole.member.mapper;

import com.treehole.framework.domain.member.User;
import com.treehole.framework.domain.member.Vo.UserVo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;
import java.util.List;

/**
 * @author shanhuijie
 * @Description:
 * @Date 2019.10.21 19:38
 */
public interface UserVoMapper extends Mapper<UserVo> {

    List<UserVo> getAllUser(List listUserId);

    List<User> getUserByTime(@Param(value = "before") Date before,
                                @Param(value = "after") Date after
                              );

}
