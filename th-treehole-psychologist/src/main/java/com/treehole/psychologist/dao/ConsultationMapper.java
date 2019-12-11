package com.treehole.psychologist.dao;

import com.treehole.framework.domain.psychologist.Consultation;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author Helay
 * @date 2019/11/19 10:48
 */
public interface ConsultationMapper extends Mapper<Consultation> {

    //根据用户id查询用户咨询记录列表
    @Select("select * from consultation where user_id = #{user_id}")
    List<Consultation> getConsultationsByUser(String user_id);

    //查询所有用户咨询记录
    @Select("select * from consultation")
    List<Consultation> getAllConsultations();

    //根据咨询师id查询用户咨询记录列表
    @Select("select * from consultation where psychologist_id = #{psychologist_id}")
    List<Consultation> getConsultationsByPsy(String psychologist_id);

}
