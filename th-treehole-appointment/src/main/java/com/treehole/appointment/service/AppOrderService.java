package com.treehole.appointment.service;

import com.treehole.framework.domain.appointment.AppOrder;
import com.treehole.framework.domain.appointment.request.QueryAppOrderRequest;
import com.treehole.framework.domain.appointment.response.AppOrderResult;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.ResponseResult;
import org.springframework.stereotype.Service;

/**
 * @ClassName AppOrderService
 * @Description: TODO
 * @Author XDD
 * @Date 2019/10/25
 * @Version V1.0
 **/
@Service
public interface AppOrderService {
    //查询预约订单
    QueryResponseResult<AppOrder> findList(int page, int size, QueryAppOrderRequest queryAppOrderRequest);
    //新增预约订单
    AppOrderResult add(AppOrder appOrder);
    //删除预约订单
    ResponseResult delete(String id);
    //根据预约单id查询预约订单信息
    AppOrder findById(String id);
    //修改预约订单
    AppOrderResult update(String id, AppOrder appOrder);
}

