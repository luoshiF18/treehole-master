package com.treehole.online.controller;

import com.treehole.api.onlinetalk.LeaveControllerApi;
import com.treehole.framework.domain.onlinetalk.Leave;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.online.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author hewenze
 * @Description:
 * @Date
 */
@RestController
@RequestMapping("online/leave")
public class LeaveController implements LeaveControllerApi {
    @Autowired
    private LeaveService leaveService;


    /**
     * 获取所有留言
     * @param page
     * @param size
     * @param name
     * @return
     */
    @Override
    @GetMapping ("/getAllLeave")
    public QueryResponseResult getAllLeave( @RequestParam(value = "page", defaultValue = "1") int page,
                                            @RequestParam(value = "size", defaultValue = "5") int size,
                                            @RequestParam(value = "name", defaultValue = "") String name
                                            )  {
        QueryResult queryResult = leaveService.findAllLeave(page, size,name);
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);

    }


    /**
     * 新增留言
     * @param leave
     * @return
     */
    @Override
    @PostMapping ("/insert")
    public ResponseResult insertLeave(@RequestBody @Valid Leave leave) {


        leaveService.insertLeave(leave);
        return new ResponseResult(CommonCode.SUCCESS);


    }




}
