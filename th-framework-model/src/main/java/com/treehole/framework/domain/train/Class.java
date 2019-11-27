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
@Table(name="class")
public class Class implements Serializable {
    @Id
    @Column(name="class_id")
    private String classId;
    @Column(name="class_name")
    private  String className;
    @Column(name="class_plan")
    private  String classPlan;
    @Column(name="class_number")
    private  Integer classNumber;
    @Column(name="class_planned_number")
    private  Integer classPlannedNumber;//计划人数
    @Column(name= "class_creat_time")
    private Date classCreatTime;
    @Column(name="class_course_number")
    private Integer classCourseNumber;
    @Column(name="class_headmaster")
    private String classHeadmaster;
    @Column(name="class_other")
    private  String classOther;

}
