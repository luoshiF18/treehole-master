package com.treehole.framework.domain.onlineCourse;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author cc
 * @date 2019/11/19 9:40
 */
@Data
@ToString
@Entity
@Table(name="teachplan_media_pub")
@GenericGenerator(name = "jpa‐assigned", strategy = "assigned")
public class TeachplanMediaPub implements Serializable {
    @Id
    @GeneratedValue(generator = "jpa‐assigned")
    @Column(name="teachplan_id")
    private String teachplanId;
    @Column(name="media_id")
    private String mediaId;
    @Column(name="media_fileoriginalname")
    private String mediaFileOriginalName;
    @Column(name="media_url")
    private String mediaUrl;
    @Column(name="courseid")
    private String courseId;
    @Column(name="timestamp")
    private Date timestamp;//时间戳
}
