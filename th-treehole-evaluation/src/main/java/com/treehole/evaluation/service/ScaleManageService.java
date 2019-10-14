package com.treehole.evaluation.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.treehole.evaluation.MyUtils.MyChineseCharUtil;
import com.treehole.evaluation.MyUtils.MyNumberUtils;
import com.treehole.evaluation.dao.ScaleMapper;
import com.treehole.framework.domain.evaluation.Scale;
import com.treehole.framework.domain.evaluation.response.EvaluationCode;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.QueryResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * @auther: Yan Hao
 * @date: 2019/10/14
 */
@Service
public class ScaleManageService {

    @Autowired
    private ScaleMapper scaleMapper;

    /**
     * 添加新量表
     *
     * @return null
     */
    public void insertScale(Scale scale) {
//        如果数据为空报错
        if (scale == null) {
            ExceptionCast.cast(EvaluationCode.SCALE_DATA_NULL);
        }
        Scale scaleInsert = new Scale();
        scaleInsert.setId(MyNumberUtils.getUUID());
        scaleInsert.setScaleName(scale.getScaleName());
        scaleInsert.setShortName(scale.getShortName());
        String upperCase = MyChineseCharUtil.getUpperCase(scale.getScaleName(), false);
        scaleInsert.setLetter(upperCase);
        scaleInsert.setTopicDescription(scale.getTopicDescription());
        scaleInsert.setTopicSuggest(scale.getTopicSuggest());
        scaleInsert.setGuide(scale.getGuide());
        scaleInsert.setScaleFunction(scale.getScaleFunction());
        scaleInsert.setStatus(scale.getStatus());
        scaleInsert.setCreateTime(new Date());
        scaleInsert.setCreateUserId(scale.getCreateUserId());
        scaleInsert.setImages(scale.getImages());
        scaleInsert.setTypeId(scale.getTypeId());
        scaleInsert.setRemark(scale.getRemark());
        if (scaleMapper.insert(scaleInsert) != 1) {
            ExceptionCast.cast(EvaluationCode.SCALE_INSERT_FAIL);
        }
    }

    /**
     * 搜索量表
     *
     * @param page   页数
     * @param size   每页多少条
     * @param sortBy 排序方式
     * @param key    搜索条件
     * @return
     */
    public QueryResult findScale(Integer page, Integer size, String sortBy, Boolean desc, String key) {
//        分页
        PageHelper.startPage(page, size);
//        过滤
        /**
         * where 'scaleName' like "%x%" or letter=='x'
         * order by shortName desc
         */
//        把字节码传给example，就可以通过反射获取数据库信息
        Example example = new Example(Scale.class);
        if (StringUtils.isNotBlank(key)) {
            //过滤条件
            example.createCriteria().orLike("scaleName", "%" + key + "%")
                    .orEqualTo("shortName", key.toUpperCase()/*这里是把它变成大写，因为数据库里是大写*/);
        }
//         排序
    /*    if (StringUtils.isNotBlank(sortBy)) {
            if (sortBy.equals("DESC")) {
                example.setOrderByClause("type_id DESC");
            } else {
                example.setOrderByClause("type_id ASC");
            }
        }*/
        //排序
        if (StringUtils.isNotBlank(sortBy)) {
            String orderByClause = sortBy + " " + (desc ? "DESC" : "ASC");
            example.setOrderByClause(orderByClause);
        }
//        查询
        List<Scale> scales = scaleMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(scales)) {

        }
//        解析分页结果
        PageInfo<Scale> pageInfo = new PageInfo<>(scales);

        return new QueryResult(scales, pageInfo.getTotal());
    }
}
