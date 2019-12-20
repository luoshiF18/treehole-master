package com.treehole.framework.domain.evaluation;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @Author: Qbl
 * Created by 19:30 on 2019/12/13.
 * Version: 1.0
 */
@Data
@ToString
public class WarnMsg {

    private List<String> userId;
    private String message;
}
