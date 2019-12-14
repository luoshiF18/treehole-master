package com.treehole.framework.domain.train.ext;

import com.treehole.framework.domain.train.Course;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
@NoArgsConstructor
public class TeacherCourseOfTeach extends Course {

        private String classId;
        private String className;
        private String phaseName;
        private String courseTypeName;
        private String teacherId;


}
