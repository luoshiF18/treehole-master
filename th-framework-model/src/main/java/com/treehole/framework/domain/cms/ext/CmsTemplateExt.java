package com.treehole.framework.domain.cms.ext;

import com.treehole.framework.domain.cms.CmsTemplate;
import lombok.Data;
import lombok.ToString;

/**
 * @author 任志强
 * @version 1.0
 * @date 2019/10/11 11:11
 * @description: CmsTemplate实体类的扩展类
 */
@Data
@ToString
public class CmsTemplateExt extends CmsTemplate {

    //模版内容
    private String templateValue;

}
