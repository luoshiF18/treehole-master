package com.treehole.onlineCourse;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication//(exclude = {DataSourceAutoConfiguration.class})
@EntityScan("com.treehole.framework.domain.onlineCourse")//扫描所有实体类
@ComponentScan(basePackages = {"com.treehole.api"}) //扫描所有接口
@ComponentScan(basePackages = {"com.treehole.onlineCourse"})//扫描本项目下所有类
@ComponentScan(basePackages = {"com.treehole.framework"})//扫描common下的类
@MapperScan(basePackages = {"com.treehole.onlineCourse"})
public class OnlineCourseApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineCourseApplication.class,args);
    }
}
