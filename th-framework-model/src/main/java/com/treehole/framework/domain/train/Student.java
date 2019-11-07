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
@Table(name="student")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class Student implements Serializable {
   private static final long serialVersionUID = -916357110051689587L;
   @Id
   @GeneratedValue(generator = "jpa-uuid")
   @Column(name = "student_id")
   private String studentId;
   @Column(name = "student_name")
   private String studentName;
   @Column(name = "student_telephone")
   private  Long  studentTelephone;
   @Column(name = "student_birth_date")
   private Date studentBirthDate;
   @Column(name = "student_gender")
   private String studentGender;
   @Column(name = "student_type")
   private  String studentType;
   @Column(name = "student_class")
   private  String studentClass;
   @Column(name = "student_state")
   private  String  studentState;
   @Column(name = "student_address")
   private  String studentAddress;
   @Column(name = "student_enrollment_time")
   private  Date studentEnrollmentTime;//入学时间
   @Column(name = "student_other")
   private  String studentOther;
}
