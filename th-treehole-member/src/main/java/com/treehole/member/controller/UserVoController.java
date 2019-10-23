package com.treehole.member.controller;

import com.treehole.api.member.UserVoControllerApi;
import com.treehole.framework.domain.member.Vo.UserVo;
import com.treehole.framework.domain.member.result.Result;
import com.treehole.framework.domain.member.result.ResultEnum;
import com.treehole.framework.domain.member.result.ResultUtil;
import com.treehole.member.service.UserService;
import com.treehole.member.service.UserVoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author shanhuijie
 * @Description:
 * @Date 2019.10.21 19:46
 */
@RestController
@RequestMapping("uservo")
public class UserVoController implements UserVoControllerApi {
    @Autowired
    private UserService userService;

    @Autowired
    UserVoService userVoService;

    @GetMapping("/getAllUserVos")
    public Result findAllUserVo()  {
        List<UserVo> userVos = userVoService.findAllUserVos();
        if(userVos != null){
            //System.out.println("users:==============" +users);
            return ResultUtil.success(userVos);
        }else{
            return ResultUtil.error(ResultEnum.DATA_IS_NULL.getCode(),ResultEnum.DATA_IS_NULL.getMsg());
        }

    }

    @GetMapping("/find/uniqId/{uniq_id}")
    public UserVo getUserVoByUniqId(@PathVariable("uniq_id") String uniq_id)  {
        //System.out.println("==========+++++++++11111   "+id);
         return userVoService.getUserByUniqId(uniq_id);

    }

    @GetMapping("/find/userId/{user_id}")
    public UserVo getUserVoByUserId(@PathVariable("user_id") String user_id)  {
        //System.out.println("==========+++++++++11111   "+id);
         return userVoService.getUserByUserId(user_id);
        //System.out.println("==========+++++++++      "+res);

    }

    @GetMapping("/find/userPhone/{user_phone}")
    public Result getUserVoByUserPhone(@PathVariable("user_phone") String user_phone)  {
        //System.out.println("==========+++++++++11111   "+user_phone);
        UserVo res = userVoService.getUserByUserPhone(user_phone);
        //System.out.println("==========+++++++++      "+res);
        if(res != null){
            return ResultUtil.success(res);
        }else {
            return ResultUtil.error(ResultEnum.USER_NOT_EXIST.getCode(),ResultEnum.USER_NOT_EXIST.getMsg());
        }
    }
}
