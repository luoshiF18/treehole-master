package com.treehole.train.dao;

import com.treehole.framework.domain.train.Cost;
import com.treehole.framework.domain.train.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CostRepository extends JpaRepository<Cost,String> {

    //根据学生Id查询交费用记录
    public List<Cost> findByCostStudentId(String studentId);

}
