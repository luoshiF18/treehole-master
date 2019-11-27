package com.treehole.framework.domain.psychologist.ext;

import com.treehole.framework.domain.psychologist.Comment;
import lombok.Data;

/**
 * @author Helay
 * @date 2019/11/23 9:19
 */
@Data
public class CommentExt extends Comment {

    //咨询师姓名
    private String psychologist_name;
}
