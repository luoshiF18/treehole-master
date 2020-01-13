package com.treehole.member.controller;

import com.treehole.api.member.CheckinControllerApi;
import com.treehole.framework.domain.member.Checkin;
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
@RequestMapping("member/checkin")
public class CheckinController implements CheckinControllerApi {
    @Autowired
    private CheckinService checkinService;

    @Override
    @GetMapping("/getAllCheckin/{page}/{size}")
    public QueryResponseResult findAllCheckin(@PathVariable("page") Integer page,
                                              @PathVariable("size") Integer size,
                                             @RequestParam(value = "nickname") String nickname) {
        return checkinService.findAllCheckins(page,size,nickname);

    }

   @Override
    @GetMapping("/find/id/{page}/{size}/{user_id}")
    public QueryResponseResult findCheckinByUserId(@PathVariable("user_id") String user_id,
                                                   @PathVariable("page") Integer page,
                                                  @PathVariable("size") Integer size) {
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
    @DeleteMapping(value = "/deleteByUserId/{user_id}")
    public ResponseResult deleteCheckinById(@PathVariable("user_id") String user_id) {
        checkinService.deleteCheckinByUserId(user_id);
        return new ResponseResult(CommonCode.SUCCESS);
    }
    @Override
    @DeleteMapping(value = "/deleteByCheckId/{check_id}")
    public ResponseResult deleteByCheckId(@PathVariable("check_id") String check_id){
        checkinService.deleteByCheckId(check_id);
        return new ResponseResult(CommonCode.SUCCESS);
    }
}
