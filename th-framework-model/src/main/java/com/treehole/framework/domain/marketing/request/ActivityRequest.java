package com.treehole.framework.domain.marketing.request;

import com.treehole.framework.domain.marketing.Activity;
import com.treehole.framework.domain.marketing.ActivityGoods;
import com.treehole.framework.domain.marketing.ActivityRule;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author wanglu
 */
@Data
public class ActivityRequest implements Serializable {
    private Activity activity;
    private ActivityRule activityRule;
    private ActivityGoods activityGoods;
    private List<ActivityGoods> activityGoodsList;

}
