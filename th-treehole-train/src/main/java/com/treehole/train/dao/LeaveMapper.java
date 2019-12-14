package com.treehole.train.dao;
import com.treehole.framework.domain.train.Leave;
import com.treehole.framework.domain.train.ext.StudentLeaveExamine;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.List;

@Mapper
@Component
public interface LeaveMapper {

   //请假信息统计(请假中的人员信息统计)
   List<Leave> findLeaveByFuzzyQuery(Leave leave);

   //请假信息统计(所有人员信息统计)
   List<StudentLeaveExamine> findLeaveAll(StudentLeaveExamine StudentLeaveExamine);

   //学生审核 信息统计
   List<Leave> findLeaveStudentExamine(StudentLeaveExamine studentLeaveExamine);

   //老师审核 信息统计
   List<Leave> findLeaveTeacherExamine(Leave leave);

   //统计个人最后一次请假的记录
   List<Leave> findLastLeaveExamine(String id);
}
