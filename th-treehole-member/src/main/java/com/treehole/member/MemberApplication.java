package com.treehole.member;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author shanhuijie
 * @version 1.0
 * @date 2019/10/16 15:10
 * @description: 会员管理
 */
@EnableCaching
@EnableFeignClients //feignClient
@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("com.treehole.member.mapper")
@EntityScan("com.treehole.framework.domain.member")//扫描实体类
@ComponentScan(basePackages = {"com.treehole.api"})//扫描接口
@ComponentScan(basePackages = {"com.treehole.framework"})//扫描common
@ComponentScan(basePackages = {"com.treehole.member"})//扫描本项目下的所有类,也可以不写
@EnableSwagger2
public class MemberApplication  {
    public static void main(String[] args) {
        SpringApplication.run(MemberApplication.class,args);
    }
}
