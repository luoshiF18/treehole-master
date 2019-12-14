package com.treehole.framework.domain.train.ext;

import com.treehole.framework.domain.train.Class;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;

@Data
@ToString
@NoArgsConstructor
public class ClassHeadmaster extends Class {


    private String teacherName;
    private String phaseName;
    //老师查询所教班级 老师的Id
    private String teacherId;


}
