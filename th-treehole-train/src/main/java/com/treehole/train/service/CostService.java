package com.treehole.train.service;
import com.treehole.framework.domain.train.Cost;
import com.treehole.framework.domain.train.Student;
import com.treehole.framework.domain.train.response.CostCode;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.train.dao.*;
import org.springframework.beans.BeanUtils;
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

    @Autowired
    PhaseRepository phaseRepository;

    @Autowired
    StudentService studentService;

    //交费
    @Transactional
    public ResponseResult pay(Cost cost) {
        //得到学生Id
        String studentId = cost.getCostStudentId();
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
        }

        if (cost1 != null) {
         //不是第一次交费的情况
             //如果欠费金额为0则，提示不应交学费
            if (cost1.getCostArrears() == 0 || cost1.getCostArrears() == 0.0 || cost1.getCostArrears() == 0.00) {
                ResponseResult responseResult = new ResponseResult(CostCode.NoPAYMENTISREQUIRED);
                return responseResult;
            } else {
                //欠费计算
                //欠费金额=应收金额-优惠金额-实收金额
                Double arrears = cost.getCostAmountPayable() - cost.getCostPreferentialAmount() - cost.getCostAmountReceived();
                cost.setCostArrears(arrears);

                //生成时间
                Date date = new Date();
                cost.setCostTime(date);
                if(arrears < 0){
                    ExceptionCast.cast(CostCode.MOREMONEY);
                }
                if(arrears == 0  || arrears == 0.0 || arrears == 0.00){
                    String costOther = cost.getCostOther();
                    if(costOther == null || costOther == "" ){
                        cost.setCostOther("学费已经交完");
                        this.updatestudentArrears(studentId);
                    }else {
                        cost.setCostOther(costOther+"（学费已经交完）");
                        this.updatestudentArrears(studentId);
                    }
                }
                Cost save = costRepository.save(cost);
                if (save != null) {
                    return new ResponseResult(CommonCode.SUCCESS);
                }
                return new ResponseResult(CommonCode.FAIL);
            }

        } else {
       //第一次交费的情况
            if(cost.getCostPreferentialAmount() > cost.getCostAmountPayable()){
                //优惠金额>学费的时候（这种情况极少出现,一般发生在新生录入信息自动交费）
                Date date = new Date();
                cost.setCostTime(date);
                cost.setCostArrears(0);
                cost.setCostOther("新生自动交费");
                this.updatestudentArrears(studentId);
                Cost save = costRepository.save(cost);
                if (save != null) {
                    return new ResponseResult(CommonCode.SUCCESS);
                }
            }else {
                //学费还没定的时候
                if(cost.getCostAmountPayable() == 0){
                    Date date = new Date();
                    cost.setCostTime(date);
                    cost.setCostArrears(cost.getCostAmountPayable());
                    this.updatestudentArrears(studentId);
                    Cost save = costRepository.save(cost);
                    if (save != null) {
                        return new ResponseResult(CommonCode.SUCCESS);
                    }
                }else {
                    //正常情况
                    //欠费计算
                    Double arrears = cost.getCostAmountPayable() - cost.getCostPreferentialAmount() - cost.getCostAmountReceived();
                    cost.setCostArrears(arrears);

                    //生成时间
                    Date date = new Date();
                    cost.setCostTime(date);
                    if(arrears < 0){
                        ExceptionCast.cast(CostCode.MOREMONEY);
                    }
                    if(arrears == 0  || arrears == 0.0 || arrears == 0.00){
                        String costOther = cost.getCostOther();
                        if(costOther == null || costOther == "" ){
                            cost.setCostOther("学费已经交完");
                            this.updatestudentArrears(studentId);
                        }else {
                            cost.setCostOther(costOther+"（学费已经交完）");
                            this.updatestudentArrears(studentId);
                        }
                    }
                    Cost save = costRepository.save(cost);
                    if (save != null) {
                        return new ResponseResult(CommonCode.SUCCESS);
                    }
                    return new ResponseResult(CommonCode.FAIL);
                }
            }
        }
        return new ResponseResult(CommonCode.SUCCESS);
    }

    //查询应收金额和优惠金额
    public Cost findTuition(String studentId){
        List<Cost> costList = costMapper.findCostByStudentId(studentId);

        if (costList.size() == 0){
          //从期数中拿
            Cost cost = costMapper.findInfoByStudentId(studentId);
            return cost;
        }else {
        //从记录中拿
            Cost cost = costList.get(0);
            if(cost.getCostAmountPayable() >=(cost.getCostPreferentialAmount()+cost.getCostAmountReceived())){
                //如果 应付金额 >=(优惠金额 + 实收金额)
                cost.setCostPreferentialAmount(0);
            }else if(cost.getCostAmountPayable()<cost.getCostPreferentialAmount()){
                cost.setCostPreferentialAmount(cost.getCostPreferentialAmount() - cost.getCostAmountPayable());
            }
            return cost;
        }

    }

    //修改学生欠费状态
    private void updatestudentArrears(String studentId){
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        Student student = null;
        if(optionalStudent.isPresent()){
            student = optionalStudent.get();
        }
        student.setStudentArrears(2);
        studentRepository.save(student);
    }
}
