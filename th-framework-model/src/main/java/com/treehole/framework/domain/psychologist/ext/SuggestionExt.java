package com.treehole.framework.domain.psychologist.ext;

import com.treehole.framework.domain.psychologist.Suggestion;
import lombok.Data;

/**
 * 建议信息扩展类
 *
 * @author Helay
 * @date 2019/11/19 10:25
 */
@Data
public class SuggestionExt extends Suggestion {

    //用户昵称
    private String user_nickname;

    //用户姓名
    private String user_name;

    //用户联系方式
    private String user_phone;

    //咨询师姓名
    private String psychologist_name;

    //心理咨询师联系方式
    private String psychologist_phone;

}
