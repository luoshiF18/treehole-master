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
public class Student implements Serializable {
   @Id
   @Column(name = "student_id")
   private String studentId;
   @Column(name = "student_name")
   private String studentName;
   @Column(name = "student_phase")
   private String studentPhase; //属于第几期学生
   @Column(name = "student_telephone")
   private  Long  studentTelephone;
   @Column(name = "student_birth_date")
   private Date studentBirthDate;
   @Column(name = "student_gender")
   private Integer studentGender;//0:空 1:男 2:女
   @Column(name = "student_type")
   private  Integer studentType; //1:普通学生 2:组长 3:班长
   @Column(name = "student_class")
   private  String studentClass;
   @Column(name = "student_state")
   private  Integer  studentState;//1:正常 2:请假中
   @Column(name = "student_graduation")
   private  Integer  studentGraduation;//1:未毕业 2:已毕业
   @Column(name = "student_address")
   private  String studentAddress;
   @Column(name = "student_arrears")
   private  Integer studentArrears;//是否欠费 1 欠费  2 不欠费
   @Column(name = "student_enrollment_time")
   private  Date studentEnrollmentTime;//入学时间
   @Column(name = "student_graduation_time")
   private  Date studentGraduationTime;//毕业时间
   @Column(name = "student_other")
   private  String studentOther;

}
