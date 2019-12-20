package com.treehole.train.dao;

import com.treehole.framework.domain.train.ext.DocumentExt;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface DocumentMapper {

    //老师查询学生的作业
    public List<DocumentExt> findStudentTask(DocumentExt documentExt);

    //学生查询老师的资料
    public List<DocumentExt> findTeacherData(DocumentExt documentExt);
}
