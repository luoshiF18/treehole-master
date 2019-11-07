package com.treehole.framework.domain.train.ext;

import com.treehole.framework.domain.train.Course;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;

@Data
@ToString
@Entity
@NoArgsConstructor
public class StudentCourse extends Course {

    private String teacherName;



}
