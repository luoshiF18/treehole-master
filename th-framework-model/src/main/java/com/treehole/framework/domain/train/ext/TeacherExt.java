package com.treehole.framework.domain.train.ext;

import com.treehole.framework.domain.train.Teacher;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class TeacherExt extends Teacher {

    private String classId;
    private String studentId;

}
