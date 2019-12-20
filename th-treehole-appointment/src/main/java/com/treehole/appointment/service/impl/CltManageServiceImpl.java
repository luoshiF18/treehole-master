package com.treehole.appointment.service.impl;

import com.treehole.appointment.dao.CltManageRepository;
import com.treehole.appointment.service.CltManageService;
import com.treehole.framework.domain.appointment.CltManage;
import com.treehole.framework.domain.appointment.request.QueryCltManageRequest;
import com.treehole.framework.domain.appointment.response.CltManageResult;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.framework.model.response.ResponseResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @ClassName CltManageServiceImpl
 * @Description: TODO
 * @Author XDD
 * @Date 2019/11/27 19:41
 **/
@Service
public class CltManageServiceImpl implements CltManageService {

    @Autowired
    private CltManageRepository cltManageRepository;

    /**
    * @Description //新增咨询师预约管理
    * @Date 19:44
    * @Param [cltManage]
    * @return com.treehole.framework.domain.appointment.response.CltManageResult
    **/
    @Override
    @Transactional
    public CltManageResult add(CltManage cltManage) {
        if (cltManage == null){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        CltManage save = cltManageRepository.save(cltManage);
        return new CltManageResult(CommonCode.SUCCESS,save);
    }

    /**
    * @Description //分页查询咨询师预约管理单
    * @Param [page, size, queryCltManageRequest]
    * @return com.treehole.framework.model.response.QueryResponseResult<com.treehole.framework.domain.appointment.CltManage>
    **/
    @Override
    public QueryResponseResult<CltManage> findList(int page, int size, QueryCltManageRequest queryCltManageRequest) {
        if (queryCltManageRequest == null){
            queryCltManageRequest = new QueryCltManageRequest();
        }
        CltManage cltManage = new CltManage();
        // 自定义条件查询
        //设置咨询师ID查询
        if (StringUtils.isNoneEmpty(queryCltManageRequest.getCltId())){
            cltManage.setCltId(queryCltManageRequest.getCltId());
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
        Example<CltManage> example = Example.of(cltManage);
        // 设置分页参数
        Pageable pageable = PageRequest.of(page,size, Sort.by("cltDate"));
        //Sort sort = new Sort(Sort.Direction.ASC,"leftTime");
        // 查询
        Page<CltManage> cltManageList = cltManageRepository.findAll(example,pageable);

        // 查询结果
        QueryResult queryResult = new QueryResult();
        // 数据列表
        queryResult.setList(cltManageList.getContent());
        // 数据总记录数
        queryResult.setTotal(cltManageList.getTotalElements());
        //返回
        return new QueryResponseResult<>(CommonCode.SUCCESS,queryResult);
    }

    /**
    * @Description //删除咨询师预约管理单
    * @Param [id]
    * @return com.treehole.framework.model.response.ResponseResult
    **/
    @Override
    @Transactional
    public ResponseResult delete(String id) {
        //先查询咨询师预约管理单
        Optional<CltManage> optional = cltManageRepository.findById(id);
        if (optional.isPresent()){
            cltManageRepository.deleteById(id);
            return new ResponseResult(CommonCode.SUCCESS);
        }
        return new ResponseResult(CommonCode.FAIL);
    }

    /*
    * @Description //根据id查询咨询师预约管理单信息
    * @Param [id]
    * @return com.treehole.framework.domain.appointment.CltManage
    **/
    @Override
    public CltManage findById(String id) {
        Optional<CltManage> byId = cltManageRepository.findById(id);
        if (byId.isPresent()){
            CltManage cltManage = byId.get();
            return cltManage;
        }
        return null;
    }

    /**
    * @Description //修改咨询师预约管理单
    * @Param [id, cltManage]
    * @return com.treehole.framework.domain.appointment.response.CltManageResult
    **/
    @Override
    @Transactional
    public CltManageResult update(String id, CltManage cltManage) {
        //根据id从数据库查询咨询师预约管理单信息
        CltManage byId = this.findById(id);
        if (byId != null){
            //准备更新数据
            //设置要修改的数据

            //更新预约结束时间
            byId.setCltDate(cltManage.getCltDate());
            byId.setCltStartTime(cltManage.getCltStartTime());
            byId.setCltEndTime(cltManage.getCltEndTime());

            //提交修改
            CltManage save = cltManageRepository.save(byId);
            return new CltManageResult(CommonCode.SUCCESS,save);
        }
        return new CltManageResult(CommonCode.FAIL,null);
    }
}
