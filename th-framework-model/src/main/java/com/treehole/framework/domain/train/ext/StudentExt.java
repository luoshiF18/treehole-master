package com.treehole.framework.domain.train.ext;

import com.treehole.framework.domain.train.Student;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class StudentExt extends Student {
    private String className;
    private String phaseName;
    private String classId;
}
