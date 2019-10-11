package com.treehole.framework.domain.cms.ext;

import com.treehole.framework.domain.cms.CmsPage;
import lombok.Data;
import lombok.ToString;

/**
 * @author 任志强
 * @version 1.0
 * @date 2019/10/11 11:10
 * @description: CmsPage实体类的扩展类
 */
@Data
@ToString
public class CmsPageExt extends CmsPage {
    private String htmlValue;

}
