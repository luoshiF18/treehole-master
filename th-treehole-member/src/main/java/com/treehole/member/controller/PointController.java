package com.treehole.member.controller;

import com.treehole.api.member.PointControllerApi;
import com.treehole.framework.domain.member.Points;
import com.treehole.framework.domain.member.User;
import com.treehole.framework.domain.member.resquest.PointListRequest;
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
@RequestMapping("member/point")
public class PointController implements PointControllerApi {

    @Autowired
    private PointService pointService;


    //http://localhost:40300/point/find/id/2?page=2
    @Override
    @GetMapping("/getAllPoints/{page}/{size}")
    public QueryResponseResult findAllPoint(@PathVariable("page") Integer page,
                                            @PathVariable("size") Integer size,
                                            PointListRequest pointListRequest)  {
         return pointService.findAllPoints(page,size,pointListRequest);

    }

    @Override
    @PostMapping("/insert")
    public ResponseResult insertPoint(@RequestBody @Valid Points points)  {
        pointService.insertPoint(points);
        return new ResponseResult(CommonCode.SUCCESS);
    }
    /*该删除方法保留，暂时不用！！*/
    //@Override
    //@DeleteMapping(value = "/delete/id/{points_id}")
    /*public ResponseResult deletePointById(@PathVariable("points_id") String points_id) {
        pointService.deletePointById(points_id);
        return new ResponseResult(CommonCode.SUCCESS);
    }*/
    @DeleteMapping("/delete/userid/{user_id}")
    public ResponseResult deletePointByUserId(@PathVariable("user_id") String user_id){
        pointService.deletePointByUserId(user_id);
        return  new ResponseResult(CommonCode.SUCCESS);
    }

    @DeleteMapping("/deleteByPointId/{point_id}")
    public ResponseResult deletePointByPointId(@PathVariable("point_id") String point_id){
        pointService.deletePointById(point_id);
        return   new ResponseResult(CommonCode.SUCCESS);
    }




}
