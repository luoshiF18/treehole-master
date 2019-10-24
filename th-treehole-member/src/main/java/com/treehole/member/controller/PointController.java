package com.treehole.member.controller;

import com.treehole.api.member.PointControllerApi;
import com.treehole.framework.domain.member.Points;
import com.treehole.framework.domain.member.User;
import com.treehole.framework.domain.member.result.Result;
import com.treehole.framework.domain.member.result.ResultEnum;
import com.treehole.framework.domain.member.result.ResultUtil;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.member.service.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author shanhuijie
 * @Description: 积分Controller
 * @Date 2019.10.19 19:26
 */

@RestController
@RequestMapping("point")
public class PointController implements PointControllerApi {

    @Autowired
    private PointService pointService;

    @Override
    @GetMapping("/getAllPoints")
    public QueryResponseResult findAllPoint(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                            @RequestParam(value = "size", defaultValue = "5") Integer size)
    {
        QueryResult queryResult = pointService.findAllPoints(page,size);
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
    }


    //http://localhost:40300/point/find/id/2?page=2
    @Override
    @GetMapping("/find/id/{user_id}")
    public QueryResponseResult getPointById(@PathVariable("user_id") String user_id,
                                            @RequestParam(value = "page", defaultValue = "1") Integer page,
                                            @RequestParam(value = "size", defaultValue = "5") Integer size)  {
        QueryResult pointById= pointService.getPointById(user_id,page,size);
        return new QueryResponseResult(CommonCode.SUCCESS, pointById);
    }


    @Override
    @PostMapping("/insert")
    public ResponseResult insertPoint(@RequestBody @Valid Points points)  {
        pointService.insertPoint(points);
        return new ResponseResult(CommonCode.SUCCESS);
    }
    /*该删除方法保留，暂时不用！！*/
    //@Override
    //@RequestMapping(value = "/delete/id/{points_id}")
    public ResponseResult deletePointById(@PathVariable("points_id") String points_id) {
        pointService.deletePointById(points_id);
        return new ResponseResult(CommonCode.SUCCESS);

    }


}
