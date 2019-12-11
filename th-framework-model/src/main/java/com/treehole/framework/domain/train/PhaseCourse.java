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
@Table(name="phase_course")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class PhaseCourse implements Serializable {
    private static final long serialVersionUID = -916357110051689587L;
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(name = "id")
    private String  id;
    @Column(name = "phase_id")
    private String  phaseId;
    @Column(name = "course_id")
    private String  courseId;
}
