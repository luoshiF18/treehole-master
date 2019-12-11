package com.treehole.framework.domain.train;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Data
@ToString
@Entity
@NoArgsConstructor
@Table(name="teacher_course")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class TeacherCourse {
    private static final long serialVersionUID = -916357110051689587L;
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(name = "teacher_course_id")
    private String  teacherCourseId;
    @Column(name = "class_course_id")
    private String  classCourseId;
    @Column(name = "teacher_id")
    private String  teacherId;
    @Column(name = "state")
    private Integer  state; // 1审核成功  2 审核失败 3 审核中
    @Column(name = "select_course_time")
    private Date selectCourseTime;//选课时间
    @Column(name = "teacher_course_checked")
    private Integer teacherCourseChecked;//判断老师选课时可不可以显示  1 可以显示  2 不可以显示

}
