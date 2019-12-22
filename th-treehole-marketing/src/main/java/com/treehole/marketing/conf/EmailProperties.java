package com.treehole.marketing.conf;

import lombok.Data;
<<<<<<< HEAD
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
=======
>>>>>>> master
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
