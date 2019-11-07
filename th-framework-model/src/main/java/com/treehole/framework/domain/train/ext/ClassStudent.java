package com.treehole.framework.domain.train.ext;

import com.treehole.framework.domain.train.Student;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;
@Data
@ToString
@NoArgsConstructor
public class ClassStudent extends  Student{

    private String classId;
    private  String className;
    private  String classPlan;
    private  int classNumber;
    private Date classCreatTime;
    private int classTeacherId;


}
