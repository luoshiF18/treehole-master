package com.treehole.framework.domain.train.ext;
import com.treehole.framework.domain.train.TeacherCourse;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@ToString
@NoArgsConstructor
public class TeacherCourseShow extends TeacherCourse {
    private  String phaseId;
    private  String phaseName;
    private  String className;
    private  Integer classNumber;
    private Integer classCourseNumber;
    private String classHeadmaster;
    private String classHeadmasterName;
    private String courseTypeId;
    private String courseTypeName;
    private String courseName;
    private String courseDescribe;
    private String courseTime;
    private String teacherId;
    private String teacherName;

    private String classCourseId;
    private String classId;
    private String courseId;

}
