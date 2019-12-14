package com.treehole.evaluation.controller;

import com.treehole.api.evaluation.CheckCenterControllerApi;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author: Qbl
 * Created by 14:54 on 2019/12/6.
 * Version: 1.0
 */
@RestController
@RequestMapping("/checkcenter")
public class CheckCenterController implements CheckCenterControllerApi{

    //页面请求
    @GetMapping("/socket/{cid}")
    public ModelAndView socket(@PathVariable String cid) {
        ModelAndView mav=new ModelAndView("/socket");
        mav.addObject("cid", cid);
        return mav;
    }
    //推送消息


}
