package com.treehole.member.api;

import com.treehole.framework.domain.member.Vo.UserVo;
import com.treehole.framework.domain.member.ext.UserExt;
import com.treehole.framework.domain.member.result.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author shanhuijie
 * @Description:暴露UserVo查询接口
 * @Date 2019.10.22 13:11
 */
public interface UserVoApi {
    /*查找所有用户*/
    @GetMapping("/uservo/getAllUserVos")
    public Result findAllUserVo();
    /*根据id查找用户*/
    @GetMapping("/uservo/find/userId/{user_id}")
    public UserVo getUserVoByUserId(@PathVariable("user_id") String user_id);
    /*根据uniq_id查找用户*/
    /*@GetMapping("/uservo/find/uniqId/{uniq_id}")
    public UserVo getUserVoByUniqId(@PathVariable("uniq_id") String uniq_id);*/
    /*根据user_phone查找用户*/
    @GetMapping("/uservo/find/userPhone/{user_phone}")
    public Result getUserVoByUserPhone(@PathVariable("user_phone") String user_phone);

    @GetMapping("/uservo/find/nickname")
    public UserVo getUserVoByNickname(@RequestParam("nickname") String nickname);
    @GetMapping("/user/getUserExt")
    public UserExt getUserExt(@RequestParam("userNickName") String userNickName);
}
