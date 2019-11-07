package com.treehole.framework.domain.train;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Data
@ToString
@Entity
@NoArgsConstructor
@Table(name="teacher")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class Teacher implements Serializable {
    private static final long serialVersionUID = -916357110051689587L;
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(name = "teacher_id")
    private String teacherId;
    @Column(name = "teacher_name")
    private String teacherName;
    @Column(name = "teacher_gender")
    private String teacherGender;
    @Column(name = "teacher_state")
    private String teacherState;
    @Column(name = "teacher_address")
    private String teacherAddress;
    @Column(name = "teacher_type")
    private String teacherType;
    @Column(name = "teacher_other")
    private String teacherOther;

}
