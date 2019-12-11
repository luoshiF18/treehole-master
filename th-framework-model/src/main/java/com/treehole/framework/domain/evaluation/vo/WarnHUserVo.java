package com.treehole.framework.domain.evaluation.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author: Qbl
 * Created by 20:24 on 2019/12/10.
 * Version: 1.0
 */
@Data
@NoArgsConstructor
@ToString
public class WarnHUserVo extends WarningVo {
    private String reason;
    private String context;
    private String way;
}
