package com.treehole.framework.domain.evaluation.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Qbl
 * Created by 8:51 on 2019/10/25.
 * Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WarningVo {
    private String scaleName;
    private String userName;
    private int warningLevel;
}
