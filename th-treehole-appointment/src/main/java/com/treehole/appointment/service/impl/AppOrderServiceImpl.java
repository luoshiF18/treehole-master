package com.treehole.appointment.service.impl;

import com.treehole.appointment.MyUtils.MyOrderCodeUtils;
import com.treehole.appointment.client.PsychologistClient;
import com.treehole.appointment.dao.AppOrderRepository;
import com.treehole.appointment.service.AppOrderService;
import com.treehole.framework.domain.appointment.AppOrder;
import com.treehole.framework.domain.appointment.ext.AppOrderExt;
import com.treehole.framework.domain.appointment.request.QueryAppOrderExtRequest;
import com.treehole.framework.domain.appointment.request.QueryAppOrderRequest;
import com.treehole.framework.domain.appointment.response.AppOrderResult;
import com.treehole.framework.domain.psychologist.ext.DetailExt;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.framework.model.response.ResponseResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    @Autowired
    private PsychologistClient psychologistClient;

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
        if (StringUtils.isNoneEmpty(queryAppOrderRequest.getCltId())){
            appOrder.setCltId(queryAppOrderRequest.getCltId());
        }
        if (StringUtils.isNoneEmpty(queryAppOrderRequest.getUserId())){
            appOrder.setUserId(queryAppOrderRequest.getUserId());
        }

        // 分页参数
        if (page <= 0){
            page = 1;
        }
        page = page - 1;

        if (size <= 0){
            size = 10;
        }
        // 设置查询条件
        Example<AppOrder> example = Example.of(appOrder);
        // 设置分页参数
        Pageable pageable = PageRequest.of(page,size, Sort.by("createTime"));
        // 查询
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
        appOrder.setCreateTime(new Date());
        appOrder.setUpdateTime(new Date());
        appOrder.setId(MyOrderCodeUtils.getOrderCode());
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

            //更新预约日期
            byId.setAppDate(appOrder.getAppDate());
            //更新预约咨询方式
            byId.setAppMode(appOrder.getAppMode());
            //更新预约备注
            byId.setAppNote(appOrder.getAppNote());
            //更新预约订单状态
            byId.setAppStatus(appOrder.getAppStatus());
            //更新订单修改时间
            byId.setUpdateTime(new Date());

            //提交修改
            AppOrder save = appOrderRepository.save(byId);
            return new AppOrderResult(CommonCode.SUCCESS,save);

        }
        return new AppOrderResult(CommonCode.FAIL,null);
    }

    //查询预约订单扩展类
    @Override
    public QueryResponseResult<AppOrderExt> findListByUserId(int page, int size, QueryAppOrderExtRequest queryAppOrderExtRequest) {
        //判断传入的数据是否为空
        if (queryAppOrderExtRequest == null){
            queryAppOrderExtRequest = new QueryAppOrderExtRequest();
        }
        AppOrder appOrder = new AppOrder();
        // 自定义条件查询
        //设置条件值...
        //订单id
        if (StringUtils.isNoneEmpty(queryAppOrderExtRequest.getId())){
            appOrder.setId(queryAppOrderExtRequest.getId());
        }
        //咨询师id
        if (StringUtils.isNoneEmpty(queryAppOrderExtRequest.getCltId())){
            appOrder.setCltId(queryAppOrderExtRequest.getCltId());
        }
        //用户id
        if (StringUtils.isNoneEmpty(queryAppOrderExtRequest.getUserId())){
            appOrder.setUserId(queryAppOrderExtRequest.getUserId());
        }
        //订单状态
        if (queryAppOrderExtRequest.getAppStatus() == null){
            appOrder.setAppStatus(queryAppOrderExtRequest.getAppStatus());
        }

        // 分页参数
        if (page <= 0){
            page = 1;
        }
        page = page - 1;

        if (size <= 0){
            size = 10;
        }
        // 设置查询条件
        Example<AppOrder> example = Example.of(appOrder);
        // 设置分页参数
        Pageable pageable = PageRequest.of(page,size);
        // 查询
        Page<AppOrder> all = appOrderRepository.findAll(example, pageable);
        List<AppOrder> appOrders = all.getContent();
        //判断集合是否为空
        if (CollectionUtils.isEmpty(appOrders)) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        //遍历consultations，将consultations集合转换为consultationExts集合
        List<AppOrderExt> appOrderExts = appOrders.stream().map(appOrder1 -> {
            //创建Consultation扩展类实例对象
            AppOrderExt appOrderExt = new AppOrderExt();
            BeanUtils.copyProperties(appOrder1, appOrderExt);  //copy共同属性的值到新的对象
            //设置扩展字段数据
            DetailExt detailById = psychologistClient.getPsychologistDetail(appOrderExt.getCltId());
            if (detailById == null) {
                ExceptionCast.cast(CommonCode.FAIL);
            }
            appOrderExt.setCltName(detailById.getName());//咨询师姓名
            appOrderExt.setImage(detailById.getImage());//咨询师头像
            return appOrderExt;
        }).collect(Collectors.toList());
        QueryResult queryResult = new QueryResult();
        queryResult.setList(appOrderExts);
        queryResult.setTotal(all.getTotalElements());
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
    }

    //取消预约单
    @Override
    @Transactional
    public AppOrderResult cancel(String id) {
        //根据id从数据库查询预约订单信息
        AppOrder byId = this.findById(id);
        byId.setAppStatus(3);
        //提交修改
        AppOrder save = appOrderRepository.save(byId);
        return new AppOrderResult(CommonCode.SUCCESS,save);
    }
}
