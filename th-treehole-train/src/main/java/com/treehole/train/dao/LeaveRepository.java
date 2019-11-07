package com.treehole.train.dao;

import com.treehole.framework.domain.train.Leave;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LeaveRepository extends JpaRepository<Leave,String> {
    //查询请假人员信息
    public List<Leave> findByLeavePeopleIdAndLeavePeopleType(String peopleId,String type);


    public Leave save(Leave leave);

}
