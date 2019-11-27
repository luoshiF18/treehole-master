package com.treehole.train.dao;

import com.treehole.framework.domain.train.Cost;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import java.util.List;

@Mapper
@Component
public interface CostMapper {

  //查询cost按时间降序
    List<Cost> findCostByStudentId(String studentId);

  //查询交费信息
  Cost findInfoByStudentId(String studentId);


}
