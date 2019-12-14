package com.treehole.api.evaluation;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author: Qbl
 * Created by 15:26 on 2019/12/6.
 * Version: 1.0
 */
@Api(value = "预警消息推送",description = "预警消息的推送")
public interface CheckCenterControllerApi {

    public ModelAndView socket(@PathVariable String cid);
}
