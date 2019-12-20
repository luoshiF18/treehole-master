package com.treehole.framework.domain.train.ext;

import com.treehole.framework.domain.train.Course;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class CourseTeacher extends Course {

    private String teacherName;

    private String courseTypeName;
    private String classCourseId;


}
