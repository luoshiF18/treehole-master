package com.treehole.framework.domain.onlineCourse.ext;

import com.treehole.framework.domain.onlineCourse.Teachplan;
import lombok.Data;
import lombok.ToString;

/**
 * Created by admin on 2018/2/7.
 */
@Data
@ToString
public class TeachplanExt extends Teachplan {

    //媒资文件id
    private String mediaId;

    //媒资文件原始名称
    private String mediaFileOriginalName;

    //媒资文件访问地址
    private String mediaUrl;
}
