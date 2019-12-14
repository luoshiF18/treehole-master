package com.treehole.appointment.dao;

import com.treehole.framework.domain.appointment.CltManage;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @ClassName CltManageRepository
 * @Description: dao
 * @Author XDD
 * @Date 2019/11/27 19:42
 **/

//继承JpaRepository来完成对数据库的操作
public interface CltManageRepository extends JpaRepository<CltManage,String>{
}
