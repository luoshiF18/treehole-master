package com.treehole.framework.domain.train;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@ToString
@Entity
@NoArgsConstructor
@Table(name="document")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class Document {
    private static final long serialVersionUID = -916357110051689587L;
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    private String id;
    private String documentId;
    private String documentName;
    private Date uploadTime;
    private String classCourseId;
    private String studentId;

}
