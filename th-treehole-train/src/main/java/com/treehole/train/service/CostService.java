package com.treehole.train.service;
import com.treehole.framework.domain.train.Cost;
import com.treehole.framework.domain.train.Student;
import com.treehole.framework.domain.train.response.CostCode;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.train.dao.CostMapper;
import com.treehole.train.dao.CostRepository;
import com.treehole.train.dao.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CostService {

    @Autowired
    CostRepository costRepository;

    @Autowired
    CostMapper costMapper;

    @Autowired
    StudentRepository studentRepository;
    //交费
    @Transactional
    public ResponseResult pay(String studentId,Cost cost) {

        if(cost.getCostStudentName() == null || cost.getCostStudentName().equals("")) {

            Optional<Student> optional = studentRepository.findById(studentId);
            if(optional.isPresent()){
                Student student = optional.get();
                String studentName = student.getStudentName();
                cost.setCostStudentName(studentName);
            }
        }

        //查找最近一次缴费
        List<Cost> list = costMapper.findCostByStudentId(studentId);
        Cost cost1 = null;
        if (list.size() != 0) {
            cost1 = list.get(0);
            System.out.println(cost1);
        }

        if (cost1 != null) {

            if (cost1.getCostArrears() == 0 || cost1.getCostArrears() == 0.0 || cost1.getCostArrears() == 0.00) {
                ResponseResult responseResult = new ResponseResult(CostCode.NoPAYMENTISREQUIRED);
                System.out.println(responseResult);
                return responseResult;
            } else {
                //欠费计算
                //应收金额
                cost.getCostAmountPayable();
                //优惠金额
                cost.getCostPreferentialAmount();
                //实收金额
                cost.getCostAmountReceived();
                //欠费金额
                Double arrears = cost.getCostAmountPayable() - cost.getCostPreferentialAmount() - cost.getCostAmountReceived();
                cost.setCostArrears(arrears);

                Date date = new Date();
                cost.setCostTime(date);
                cost.setCostStudentId(studentId);
                Cost save = costRepository.save(cost);
                if (save != null) {
                    return new ResponseResult(CommonCode.SUCCESS);
                }
                return new ResponseResult(CommonCode.FAIL);
            }
        } else {
            //欠费计算
            //应收金额
            cost.getCostAmountPayable();
            //优惠金额
            cost.getCostPreferentialAmount();
            //实收金额
            cost.getCostAmountReceived();
            //欠费金额
            Double arrears = cost.getCostAmountPayable() - cost.getCostPreferentialAmount() - cost.getCostAmountReceived();
            cost.setCostArrears(arrears);

            Date date = new Date();
            cost.setCostTime(date);
            cost.setCostStudentId(studentId);
            Cost save = costRepository.save(cost);
            if (save != null) {
                return new ResponseResult(CommonCode.SUCCESS);
            }
            return new ResponseResult(CommonCode.FAIL);

        }
    }
}
