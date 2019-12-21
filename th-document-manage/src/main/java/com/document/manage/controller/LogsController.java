package com.document.manage.controller;

import com.document.manage.pojo.EasyUIResult;
import com.document.manage.service.LogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Auther: lee
 * @Date: 2019/12/16
 * @Description: com.lee.springboot.controller
 * @version: 1.0
 */
@Controller
@RequestMapping("logs")
public class LogsController {

    @Autowired
    private LogsService logsServiceImpl;

    @RequestMapping("show.html")
    public String show(){
        return "logs";
    }

    @RequestMapping("show")
    @ResponseBody
    public EasyUIResult logsShow(int page, int rows){
        EasyUIResult result = logsServiceImpl.showLogs(page, rows);
        return logsServiceImpl.showLogs(page,rows);
    }

}
