package com.treehole.api.member;

import com.treehole.framework.domain.member.User;
import com.treehole.framework.domain.member.Vo.UserVo;
import com.treehole.framework.domain.member.ext.UserExt;
import com.treehole.framework.domain.member.resquest.UserListRequest;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.Date;
import java.util.List;

/**
 * @author shanhuijie
 * @Description:
 * @Date
 */
@Api(value = "用户信息管理", description = "对用户信息进行增删改查")
public interface UserControllerApi {
    @ApiOperation("根据user_id,user_nickname,user_phone，查询所有Vo用户信息")
    public QueryResponseResult findAllUserVo( Integer page,
                                              Integer size,
                                              String sortBy,
                                              Boolean desc,
                                              UserListRequest userListRequest);
    @ApiOperation("根据nickname集合得到对象的集合")
    public List<UserVo> findUserByNicknames( List<String> names);
    @ApiOperation("创建一条用户信息")
    public ResponseResult insertUser(User user);
    @ApiOperation("通过id删除用户")
    public ResponseResult deleteUserById(String user_id);
    @ApiOperation("更新用户基本信息")
    public ResponseResult update(UserVo userVo);
    @ApiOperation("更新用户手机号")
    public ResponseResult updateUserPhone(String user_id,
                                          String role_id, String user_phone);
    @ApiOperation("更新用户密码")
    public ResponseResult updateUserPass(String id,String OldPass,String NewPass);
    @ApiOperation("用户验证根据昵称查询userExt")
    public UserExt getUserExt( String userNickName);
    @ApiOperation("根据user_id得到user对象")
    public UserVo getUserVoByUserId(String user_id);
    @ApiOperation("根据时间得到userlist对象")
    public QueryResult findUserByTime(Date beforeTime,
                                      Date afterTime);
    @ApiOperation("根据List Id得到所有user对象")
    public List<UserVo> getAllUser(List listUserId);
    /* @ApiOperation("根据用户user_ID查询用户Vo信息")
    public UserVo getUserVoByUserId(String user_id);*/
    /*@ApiOperation("查询所有用户")
    public QueryResponseResult getAllUser(Integer page, Integer size);
    */
    @ApiOperation("根据nickname得到对象")
    public UserVo getUserVoByNickname( String nickname);

}
