package com.treehole.api.member;

import com.treehole.framework.domain.member.User;
import com.treehole.framework.domain.member.Vo.UserVo;
import com.treehole.framework.domain.member.resquest.UserListRequest;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
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
                                              UserListRequest userListRequest);
   /* @ApiOperation("根据用户user_ID查询用户Vo信息")
    public UserVo getUserVoByUserId(String user_id);*/
    /*@ApiOperation("查询所有用户")
    public QueryResponseResult getAllUser(Integer page, Integer size);
    @ApiOperation("通过id查询用户")
    public User getUserById(@PathVariable("id") String id)  ;*/
   @ApiOperation("根据nickname集合得到对象的集合")
   public List<UserVo> findUserByNicknames( List<String> names);
    @ApiOperation("根据user_id得到user对象")
    public UserVo findUserById(String user_id);
    @ApiOperation("创建一条用户信息")
    public ResponseResult insertUser(User user);

    @ApiOperation("通过id删除用户")
    public ResponseResult deleteUserById(String user_id);

    @ApiOperation("更新用户基本信息")
    public ResponseResult update(UserVo userVo);

    @ApiOperation("更新用户手机号")
    public ResponseResult updateUserPhone(UserVo userVo);



}
