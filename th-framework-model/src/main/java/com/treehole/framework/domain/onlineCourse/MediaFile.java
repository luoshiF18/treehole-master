package com.treehole.framework.domain.onlineCourse;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@ToString
@Entity
@Table(name = "media")
@GenericGenerator(name = "jpa-assigned", strategy = "assigned")
public class MediaFile implements Serializable{
    /*
    文件id、名称、大小、文件类型、文件状态（未上传、上传完成、上传失败）、上传时间、视频处理方式、视频处理状态、hls_m3u8,hls_ts_list、课程视频信息（课程id、章节id）
     */
    //文件id
    @Id
    @GeneratedValue(generator = "jpa-assigned")
    private String fileId;
    //文件名称
    @Column(name="file_name")
    private String fileName;
    //文件原始名称
    @Column(name="file_original_name")
    private String fileOriginalName;
    //文件路径
    @Column(name="file_path")
    private String filePath;
    //文件url
    @Column(name="file_url")
    private String fileUrl;
    //文件类型
    @Column(name="file_type")
    private String fileType;
    //mimetype
    @Column(name="mime_type")
    private String mimeType;
    //文件大小
    @Column(name="file_size")
    private Long fileSize;
    //文件状态
    @Column(name="file_status")
    private String fileStatus;
    //上传时间
    @Column(name="upload_time")
    private Date uploadTime;
    //处理状态
    @Column(name="process_status")
    private String processStatus;
    //hls处理
    //private MediaFileProcess_m3u8 mediaFileProcess_m3u8;

    //tag标签用于查询
    @Column(name="tag")
    private String tag;


}
