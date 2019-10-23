package com.treehole.member.controller;

import com.treehole.api.member.PointControllerApi;
import com.treehole.framework.domain.member.Points;
import com.treehole.framework.domain.member.User;
import com.treehole.framework.domain.member.result.Result;
import com.treehole.framework.domain.member.result.ResultEnum;
import com.treehole.framework.domain.member.result.ResultUtil;
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
    public Result findAllPoint()  {

        List<Points> points = pointService.findAllPoints();
        if(points != null){

            return ResultUtil.success(points);

        }else{
            return ResultUtil.error(ResultEnum.DATA_IS_NULL.getCode(),ResultEnum.DATA_IS_NULL.getMsg());
        }
    }

    @Override
    @GetMapping("/find/id/{points_id}")
    public Result getPointById(@PathVariable("points_id") String points_id)  {
        //System.out.println("==========+++++++++11111   "+id);
        List<Points> res = pointService.getPointById(points_id);
        //System.out.println("==========+++++++++      "+res);
        if(!res.isEmpty()){
            return ResultUtil.success(res);
        }else {
            return ResultUtil.error(ResultEnum.POINT_NOT_EXIST.getCode(),ResultEnum.POINT_NOT_EXIST.getMsg());
        }
    }


    @Override
    @PostMapping("/insert")
    public Result insertPoint(@RequestBody @Valid Points points)  {
        int res=pointService.insertPoint(points);
        if(res==1){
            return ResultUtil.success(res);
        }else {
            return ResultUtil.error(ResultEnum.INSERT_FAIL.getCode(),ResultEnum.INSERT_FAIL.getMsg());
        }
    }


    /*@Override
    @PostMapping("/update")
    public Result update( @RequestBody @Valid Points points) throws Exception {

        int res=pointService.updatePoint(points);
        if(res>0){
            return ResultUtil.success();
        }else {
            return ResultUtil.error(ResultEnum.UPDATE_FAIL.getCode(),ResultEnum.UPDATE_FAIL.getMsg());
        }
    }*/

    @Override
    @RequestMapping(value = "/delete/id/{points_id}")
    public Result deletePointById(@PathVariable("points_id") String points_id) {
        int del = pointService.deletePointById(points_id);
        //System.out.println("}}}}}}}}}}}}"+ del);
        List<Points> res=pointService.getPointById(points_id);
        if(res.isEmpty()){
            return ResultUtil.success(res);
        }else {
            return ResultUtil.error(ResultEnum.DELETE_FAIL.getCode(),ResultEnum.DELETE_FAIL.getMsg());
        }
    }


}
