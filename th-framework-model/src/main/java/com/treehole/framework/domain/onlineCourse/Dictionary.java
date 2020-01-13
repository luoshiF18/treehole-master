package com.treehole.framework.domain.onlineCourse;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by admin on 2018/2/10.
 */
@Data
@ToString
@Entity
@Table(name="dictionary")
//@GenericGenerator(name = "jpa-assigned", strategy = "assigned")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class Dictionary implements Serializable {
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 32)
    private String id;
    @Column(name="d_name")
    private String dName;
    @Column(name="d_type")
    private String dType;
    @Column(name="sd_name")
    private String sdName;
    @Column(name="sd_id")
    private String sdId;
    @Column(name="sd_status")
    private String sdStatus;

}
