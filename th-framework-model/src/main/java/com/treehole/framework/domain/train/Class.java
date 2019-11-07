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
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class Class implements Serializable {
    private static final long serialVersionUID = -916357110051689587L;
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(name="class_id")
    private String classId;
    @Column(name="class_name")
    private  String className;
    @Column(name="class_plan")
    private  String classPlan;
    @Column(name="class_number")
    private  Integer classNumber;
    @Column(name= "class_creat_time")
    private Date classCreatTime;
    @Column(name="class_teacher_id")
    private String classTeacherId;
    @Column(name="class_other")
    private  String classOther;

}
