package com.treehole.member.controller;

import com.treehole.api.member.CheckinControllerApi;
import com.treehole.framework.domain.member.Checkin;
import com.treehole.framework.domain.member.Role;
import com.treehole.framework.domain.member.result.Result;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.member.service.CheckinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Shan HuiJie
 * @Description:
 * @Date 2019.10.25 10:00
 */
@RestController
@RequestMapping("checkin")
public class CheckinController implements CheckinControllerApi {
    @Autowired
    private CheckinService checkinService;

    @Override
    @GetMapping("/getAllCheckin")
    public QueryResponseResult findAllCheckin(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                 @RequestParam(value = "size", defaultValue = "5") Integer size) {
        QueryResult queryResult = checkinService.findAllCheckins(page,size);
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
    }

    @Override
    @GetMapping("/find/id/{user_id}")
    public QueryResponseResult findCheckinByUserId(@PathVariable("user_id") String user_id, @RequestParam(value = "page", defaultValue = "1") Integer page,
                                              @RequestParam(value = "size", defaultValue = "5") Integer size) {
        QueryResult checkinById= checkinService.getCheckinByUserId(user_id,page,size);
        return new QueryResponseResult(CommonCode.SUCCESS, checkinById);
    }

    @Override
    @PostMapping("/insert")
    public ResponseResult insertCheckin(@RequestBody  @Valid Checkin checkin) {
        checkinService.insertCheckin(checkin);
        return new ResponseResult(CommonCode.SUCCESS);

    }

    @Override
    @DeleteMapping(value = "delete/user/{user_id}")
    public ResponseResult deleteCheckinById(@PathVariable("user_id") String user_id) {
        checkinService.deleteCheckinByUserId(user_id);
        return new ResponseResult(CommonCode.SUCCESS);
    }
}
