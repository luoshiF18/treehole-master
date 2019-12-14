package com.treehole.framework.domain.psychologist.ext;

import com.treehole.framework.domain.psychologist.Detail;
import com.treehole.framework.domain.psychologist.Profile;
import com.treehole.framework.domain.psychologist.State;
import lombok.Data;

/**
 * @author Helay
 * @date 2019/12/4 10:47
 */
@Data
public class ProfileExt extends Profile {

    //心理咨询师状态信息
    private State state;

    //心理咨询师详情信息
    private Detail detail;
}
