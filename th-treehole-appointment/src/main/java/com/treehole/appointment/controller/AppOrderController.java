package com.treehole.appointment.controller;

import com.treehole.api.appointment.AppOrderControllerApi;
import com.treehole.appointment.service.AppOrderService;
import com.treehole.framework.domain.appointment.AppOrder;
import com.treehole.framework.domain.appointment.request.QueryAppOrderRequest;
import com.treehole.framework.domain.appointment.response.AppOrderResult;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName AppOrderController
 * @Description: TODO
 * @Author XDD
 * @Date 2019/10/26
 * @Version V1.0
 **/
@RestController
@RequestMapping("/appointment/order")
public class AppOrderController implements AppOrderControllerApi{

    @Autowired
    private AppOrderService appOrderService;

    //分页查询预约订单
    @Override
    @GetMapping("/list/{page}/{size}")
    public QueryResponseResult<AppOrder> findList(@PathVariable("page") int page, @PathVariable("size") int size,QueryAppOrderRequest queryAppOrderRequest) {
        return appOrderService.findList(page,size, queryAppOrderRequest);
    }

    //新增预约订单
    @Override
    @PostMapping("/add")
    public AppOrderResult add(@RequestBody AppOrder appOrder) {
        return  appOrderService.add(appOrder);
    }

    //删除预约订单
    @Override
    @DeleteMapping("/del/{id}")
    public ResponseResult delete(@PathVariable("id") String id) {
        return appOrderService.delete(id);
    }

    //根据预约单id查询预约订单信息
    @Override
    @GetMapping("/get/{id}")
    public AppOrder findById(@PathVariable("id") String id) {
        return appOrderService.findById(id);
    }

    //修改预约订单
    @Override
    @PutMapping("/edit/{id}")
    public AppOrderResult update(@PathVariable("id") String id, @RequestBody AppOrder appOrder) {
        return appOrderService.update(id,appOrder);
    }

}
