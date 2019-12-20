package com.treehole.api.marketing;

import com.treehole.framework.domain.marketing.ActivityGoods;
import com.treehole.framework.domain.marketing.ActivityRule;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author wanglu
 */
@Api(value = "活动规则和商品管理", description = "对活动信息进行管理")
public interface ActivityInfoControllerApi {

    @ApiOperation("根据活动id查询活动规则")
    public ActivityRule queryActivityRuleById(@PathVariable("id") String id);

    @ApiOperation("根据活动id查询活动商品")
    public List<ActivityGoods> queryActivityGoodsById(@PathVariable("id") String id);




}
