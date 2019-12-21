package com.document.manage.mapper;

import com.document.manage.pojo.Logs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Auther: lee
 * @Date: 2019/11/16
 * @Description: com.lee.springboot.mapper
 * @version: 1.0
 */
public interface LogsMapper extends JpaRepository<Logs,String> {
    @Query(value = "select * from logs limit ?,? " , nativeQuery = true)
    List<Logs> findAllByPage(int pageIndex, int rows);
}
