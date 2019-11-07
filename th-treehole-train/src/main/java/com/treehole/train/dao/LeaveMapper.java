package com.treehole.train.dao;
import com.treehole.framework.domain.train.Leave;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.List;

@Mapper
@Component
public interface LeaveMapper {

   List<Leave> findByLeavePeopleIdAndLeavePeopleType(HashMap<String,String> hashMap);


}
