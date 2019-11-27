package com.treehole.framework.domain.train;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@ToString
@Entity
@NoArgsConstructor
@Table(name="course_type")
public class CourseType {
    @Id
    @Column(name = "course_type_id")
    private String courseTypeId;
    @Column(name = "course_type_name")
    private String courseTypeName;
    @Column(name = "course_type_course_number")
    private Integer courseTypeCourseNumber;
    @Column(name = "course_type_describe")
    private String courseTypeDescribe;
    @Column(name = "course_type_adaptive_person")
    private String courseTypeAdaptivePerson;
}
