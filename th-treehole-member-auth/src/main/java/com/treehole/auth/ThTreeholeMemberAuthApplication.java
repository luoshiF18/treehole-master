package com.treehole.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;


@EnableDiscoveryClient
@EnableFeignClients
@EntityScan("com.treehole.framework.domain.member")//扫描实体类
@ComponentScan(basePackages = {"com.treehole.api"})//扫描接口
@ComponentScan(basePackages = {"com.treehole.framework"})//扫描common
@SpringBootApplication
public class ThTreeholeMemberAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(ThTreeholeMemberAuthApplication.class, args);
	}
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate(new OkHttp3ClientHttpRequestFactory());
	}
}
