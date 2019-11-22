package com.treehole.psychologist.dao;

import com.treehole.framework.domain.psychologist.Consultation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Helay
 * @date 2019/11/19 10:48
 */
@Mapper
public interface ConsultationMapper {

    //根据用户id查询用户咨询记录列表
    List<Consultation> getConsultationListById(String user_id);
}
