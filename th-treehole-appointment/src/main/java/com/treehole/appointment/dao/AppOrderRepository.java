package com.treehole.appointment.dao;

/**
 * @ClassName AppOrderRepository
 * @Description: TODO
 * @Author XDD
 * @Date 2019/10/25
 * @Version V1.0
 **/

import com.treehole.framework.domain.appointment.AppOrder;
import org.springframework.data.jpa.repository.JpaRepository;

//继承JpaRepository来完成对数据库的操作
public interface AppOrderRepository extends JpaRepository<AppOrder,String> {
}
