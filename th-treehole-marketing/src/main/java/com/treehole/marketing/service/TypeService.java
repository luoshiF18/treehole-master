package com.treehole.marketing.service;

import com.github.pagehelper.PageInfo;
import com.treehole.framework.domain.marketing.Type;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.marketing.dao.TypeMapper;
import com.treehole.marketing.utils.MyNumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wanglu
 */
@Service
public class TypeService {

    @Autowired
    private TypeMapper typeMapper;

    public QueryResult queryTypes(Boolean usedFor) {
        Type type = new Type();
        type.setUsedFor(usedFor);
        List<Type> types = this.typeMapper.select(type);
        PageInfo<Type> pageInfo = new PageInfo<>(types);
        return new QueryResult(types, pageInfo.getTotal());
    }

    public void saveType(Type type) {
        type.setId(MyNumberUtils.getUUID());
        this.typeMapper.insert(type);
    }
}
