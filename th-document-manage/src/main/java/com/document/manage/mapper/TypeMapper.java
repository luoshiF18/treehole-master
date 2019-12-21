package com.document.manage.mapper;

import com.document.manage.pojo.Type;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Auther: lee
 * @Date: 2019/10/28
 * @Description: com.lee.springboot.mapper
 * @version: 1.0
 */
public interface TypeMapper extends JpaRepository<Type,String> {
    Type findTypeByType(String type);
    Type findTypeById(String id);
}
