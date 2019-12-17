package com.document.manage.pojo;

import lombok.Data;

import java.util.List;

/**
 * @Auther: lee
 * @Date: 2019/12/10
 * @Description: com.lee.springboot.pojo
 * @version: 1.0
 */
@Data
public class EasyUIResult {
    private List<?> rows;
    private int total;
}
