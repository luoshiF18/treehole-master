package com.treehole.evaluation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @Author: Qbl
 * Created by 10:22 on 2019/12/6.
 * Version: 1.0
 */
@Configuration
public class WebSocketAutoConfig {

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}
