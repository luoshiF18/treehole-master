package com.treehole.framework.domain.train.ext;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@NoArgsConstructor
public class TeacherSelectCourseList {

    private List<TeacherCourseShow> teacherSelectCourseList;

}
