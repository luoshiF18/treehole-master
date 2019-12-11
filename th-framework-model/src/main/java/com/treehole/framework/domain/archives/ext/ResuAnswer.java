package com.treehole.framework.domain.archives.ext;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 任志强
 * @version 1.0
 * @date 2019/10/26 15:21
 * @description: 用户原始答卷
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResuAnswer {

    //题号
    private String sort;

    //问题
    private String question;

    //答案
    private String answer;

}
