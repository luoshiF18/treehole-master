package com.document.manage.service.impl;

import com.document.manage.mapper.TypeMapper;
import com.document.manage.pojo.Type;
import com.document.manage.service.TypeService;
import com.document.manage.utils.IDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: lee
 * @Date: 2019/10/28
 * @Description: com.lee.springboot.service.impl
 * @version: 1.0
 */
@Service
public class TypeServiceImpl implements TypeService {
    @Autowired
    private TypeMapper typeMapper;

    @Override
    public Type newType(String docName) {
        String typeName = docName.substring(docName.lastIndexOf("."));
        Type type = typeMapper.findTypeByType(typeName);
        if(type==null||type.equals("")){
            Type newType = new Type();
            newType.setId(IDUtils.genImageName());
            newType.setType(typeName);
            return typeMapper.save(newType);
        }
        return type;
    }
    @Override
    public Type findType(String id){
        return typeMapper.findTypeById(id);
    }
}
