package com.treehole.evaluation.domath;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @auther: Yan Hao
 * @date: 2019/12/13
 */
@Data
@ConfigurationProperties(prefix = "ev")
public class ScoreProperties {
    private List<String> method;
}
