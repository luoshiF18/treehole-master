package com.treehole.framework.domain.onlineCourse.ext;

import com.treehole.framework.domain.onlineCourse.Teachplan;
import lombok.Data;
import lombok.ToString;

import java.util.List;


@Data
@ToString
public class TeachplanNode extends Teachplan {

    List<TeachplanNode> children;

    //媒资信息
    private String mediaId;
    private String mediaFileOriginalName;
}
