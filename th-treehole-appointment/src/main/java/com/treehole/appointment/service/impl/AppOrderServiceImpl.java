package com.treehole.appointment.service.impl;

import com.treehole.appointment.dao.AppOrderRepository;
import com.treehole.appointment.service.AppOrderService;
import com.treehole.framework.domain.appointment.AppOrder;
import com.treehole.framework.domain.appointment.request.QueryAppOrderRequest;
import com.treehole.framework.domain.appointment.response.AppOrderResult;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.framework.model.response.ResponseResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @ClassName AppOrderServiceImpl
 * @Description: TODO
 * @Author XDD
 * @Date 2019/10/25
 * @Version V1.0
 **/
@Service
public class AppOrderServiceImpl implements AppOrderService{

    @Autowired
    private AppOrderRepository appOrderRepository;

    /**
     *  页面查询方法
     * @param page 页码，从1开计数
     * @param size 每页记录数
     * @param queryAppOrderRequest 查询条件
     * @return 查询响应结果
     */
    @Override
    public QueryResponseResult<AppOrder> findList(int page, int size, QueryAppOrderRequest queryAppOrderRequest) {
        if (queryAppOrderRequest == null){
            queryAppOrderRequest = new QueryAppOrderRequest();
        }
        AppOrder appOrder = new AppOrder();
        // 自定义条件查询
        //设置条件值...
        if (StringUtils.isNoneEmpty(queryAppOrderRequest.getId())){
            appOrder.setId(queryAppOrderRequest.getId());
        }

        // 分页参数
        if (page <= 0){
            page = 1;
        }
        page = page - 1;

        if (size <= 0){
            page = 10;
        }
        Example<AppOrder> example = Example.of(appOrder);
        Pageable pageable = PageRequest.of(page,size);
        Page<AppOrder> all = appOrderRepository.findAll(example, pageable);

        QueryResult queryResult = new QueryResult();
        queryResult.setList(all.getContent()); // 数据列表
        queryResult.setTotal(all.getTotalElements()); // 数据总记录数

        QueryResponseResult queryResponseResult = new QueryResponseResult(CommonCode.SUCCESS,queryResult);
        return queryResponseResult;
    }

    //新增预约订单
    @Override
    @Transactional
    public AppOrderResult add(AppOrder appOrder) {
        if (appOrder == null){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        AppOrder save = appOrderRepository.save(appOrder);
        return new AppOrderResult(CommonCode.SUCCESS,save);
    }

    //删除预约订单
    @Override
    @Transactional
    public ResponseResult delete(String id) {
        //先查询预约订单
        Optional<AppOrder> optional = appOrderRepository.findById(id);
        if (optional.isPresent()){
            appOrderRepository.deleteById(id);
            return new ResponseResult(CommonCode.SUCCESS);
        }
        return new ResponseResult(CommonCode.FAIL);
    }

    //根据预约单id查询预约订单信息
    @Override
    public AppOrder findById(String id) {
        Optional<AppOrder> optional = appOrderRepository.findById(id);
        if (optional.isPresent()){
            AppOrder appOrder = optional.get();
            return appOrder;
        }
        return null;
    }

    //修改预约订单
    @Override
    @Transactional
    public AppOrderResult update(String id, AppOrder appOrder) {
        //根据id从数据库查询预约订单信息
        AppOrder byId = this.findById(id);
        if (byId != null){
            //准备更新数据
            //设置要修改的数据

            //更新预约订单状态
            byId.setAppStatus(appOrder.getAppStatus());
            //更新预约订单时间
            byId.setAppTime(appOrder.getAppTime());

            //提交修改
            AppOrder save = appOrderRepository.save(byId);
            return new AppOrderResult(CommonCode.SUCCESS,save);

        }
        return new AppOrderResult(CommonCode.FAIL,null);
    }
}
