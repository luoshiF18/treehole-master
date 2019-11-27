package com.treehole.framework.domain.train.ext;

import com.treehole.framework.domain.train.Course;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;
@Data
@ToString
@NoArgsConstructor
public class SelectCourses implements Serializable {

    private List<String> courseList;
    private String courseName;
    private String courseType;
    private String courseTeacher;
    private String courseId;
}
