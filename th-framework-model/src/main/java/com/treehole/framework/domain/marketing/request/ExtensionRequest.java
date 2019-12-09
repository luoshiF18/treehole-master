package com.treehole.framework.domain.marketing.request;

import lombok.Data;

import java.util.List;

/**
 * @author wanglu
 */
@Data
public class ExtensionRequest {
    private String title;
    private String content;
    private Integer type;
    private Boolean usedFor;
    private String resId;
    private List<String> to;
}
