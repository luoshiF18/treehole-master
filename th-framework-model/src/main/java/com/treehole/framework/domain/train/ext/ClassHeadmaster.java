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

}
