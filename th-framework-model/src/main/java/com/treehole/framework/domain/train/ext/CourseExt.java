package com.treehole.framework.domain.train.ext;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@NoArgsConstructor
public class CourseExt implements Serializable {

    private String classId;
    private String courseId;
    private String courseName;
    private String courseType;
    private String courseTeacher;

}
