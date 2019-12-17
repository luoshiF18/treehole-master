package com.treehole.evaluation.controller;

import com.treehole.api.evaluation.WarnMsgControllerApi;
import com.treehole.evaluation.service.WarnMsgService;
import com.treehole.framework.domain.evaluation.WarnMsg;
import com.treehole.framework.model.response.ResponseResult;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: Qbl
 * Created by 19:04 on 2019/12/13.
 * Version: 1.0
 */
@RestController
@RequestMapping("/warnMsg")
@AllArgsConstructor
public class WarnMsgController implements WarnMsgControllerApi {

    private final WarnMsgService warnMsgService;


    @Override
    @GetMapping("/sendMeg")
    public ResponseResult sendMeg(WarnMsg warnMsg) {
        return warnMsgService.sendMeg(warnMsg);
    }

    @Override
    @GetMapping("/sendEamil")
    public ResponseResult sendEamil(WarnMsg warnMsg) {
        return warnMsgService.sendEamil(warnMsg);
    }

    @Override
    @GetMapping("/getRedisWarning")
    public List<String> getRedisWarning(String userId) {
        return warnMsgService.getRedisWarning(userId);
    }

    @Override
    @GetMapping("/emilFormat")
    public String emilFormat(WarnMsg warnMsg) {
        return warnMsgService.emilFormat(warnMsg);
    }


}
