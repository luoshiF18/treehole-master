package com.treehole.framework.domain.train.ext;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@ToString
@NoArgsConstructor
public class StudentCourseParams {

    private String studentId;
    private String courseId;
    private String courseName;
    private String courseType;
    private String courseTeacher;

}
