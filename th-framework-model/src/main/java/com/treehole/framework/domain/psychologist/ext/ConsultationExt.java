package com.treehole.framework.domain.psychologist.ext;

import com.treehole.framework.domain.psychologist.Consultation;
import lombok.Data;

/**
 * 咨询记录扩展类
 *
 * @author Helay
 * @date 2019/11/19 10:27
 */
@Data
public class ConsultationExt extends Consultation {

    //咨询师姓名
    private String psychologist_name;
}
