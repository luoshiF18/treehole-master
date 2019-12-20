package com.treehole.marketing.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author wanglu
 */
@ConfigurationProperties(prefix = "treehole.email")
@Data
@Component
public class EmailProperties {
    String USER;
    String PASSWORD;
}
