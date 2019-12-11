package com.treehole.framework.domain.train.ext;

import com.treehole.framework.domain.train.Leave;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class StudentLeaveExamine extends Leave {
    private String classId;
}
