package com.treehole.member.api;

import com.treehole.framework.domain.member.result.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author shanhuijie
 * @Description:暴露UserVo查询接口
 * @Date 2019.10.22 13:11
 */
public interface UserVoApi {
    /*查找所有用户*/
    @GetMapping("/getAllUserVos")
    public Result findAllUserVo() throws Exception;
    /*根据id查找用户*/
    @GetMapping("/uservo/find/userId/{user_id}")
    public Result getUserVoByUserId(@PathVariable String user_id);
    /*根据uniq_id查找用户*/
    @GetMapping("/find/uniqId/{uniq_id}")
    public Result getUserVoByUniqId(@PathVariable String uniq_id) throws Exception;
    /*根据user_phone查找用户*/
    @GetMapping("/find/userPhone/{user_phone}")
    public Result getUserVoByUserPhone(@PathVariable String user_phone) throws Exception;

}
