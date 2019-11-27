package com.treehole.framework.domain.train.ext;

import com.treehole.framework.domain.train.Course;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@NoArgsConstructor
public class CourseList {

    private List<Course> courseList;
}
