package com.treehole.framework.domain.onlineCourse;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class MediaFileProcess_m3u8 extends MediaFileProcess {

    //ts列表
    private List<String> tslist;

}
