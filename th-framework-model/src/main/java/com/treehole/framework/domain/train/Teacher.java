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
@Table(name="teacher")
public class Teacher implements Serializable {
    @Id
    @Column(name = "teacher_id")
    private String teacherId;
    @Column(name = "teacher_name")
    private String teacherName;
    @Column(name = "teacher_gender")
    private Integer teacherGender;//0:空 1:男 2:女
    @Column(name = "teacher_birth_bate")
    private Date teacherBirthDate;
    @Column(name = "teacher_telephone")
    private String teacherTelephone;
    @Column(name = "teacher_state")
    private Integer teacherState;//1:正常 2:请假中
    @Column(name = "teacher_address")
    private String teacherAddress;
    @Column(name = "teacher_type")
    private Integer teacherType;//1:讲师 2:班主任
    @Column(name = "teacher_other")
    private String teacherOther;

}
