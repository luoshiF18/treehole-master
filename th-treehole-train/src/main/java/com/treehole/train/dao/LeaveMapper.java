package com.treehole.train.dao;
import com.treehole.framework.domain.train.Leave;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.List;

@Mapper
@Component
public interface LeaveMapper {

   //请假信息统计(请假中的人员信息统计)
   List<Leave> findLeaveByFuzzyQuery(Leave leave);

   //请假信息统计(请假中的人员信息统计)
   List<Leave> findLeaveAll(Leave leave);

}
