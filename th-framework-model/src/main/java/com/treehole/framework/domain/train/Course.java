package com.treehole.framework.domain.train;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
@Data
@ToString
@Entity
@NoArgsConstructor
@Table(name="course")
public class Course implements Serializable {
    @Id
    @Column(name = "course_id")
    private String courseId;
    @Column(name = "course_name")
    private String courseName;
    @Column(name = "course_describe")
    private String courseDescribe;
    @Column(name = "course_type")
    private String courseType;
    @Column(name = "course_begin_time")
    private Date courseBeginTime;
    @Column(name = "course_end_time")
    private Date courseEndTime;
    @Column(name = "course_time")
    private String courseTime;
    @Column(name = "course_teacher")
    private String courseTeacher;
    @Column(name = "course_other")
    private String courseOther;

}
