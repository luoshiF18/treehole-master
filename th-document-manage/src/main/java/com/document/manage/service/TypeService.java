package com.document.manage.service;

import com.document.manage.pojo.Type;

/**
 * @Auther: lee
 * @Date: 2019/10/28
 * @Description: com.lee.springboot.service
 * @version: 1.0
 */
public interface TypeService {
    Type newType(String docName);
    Type findType(String id);
}
