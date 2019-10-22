package com.treehole.member.api;

import com.treehole.framework.domain.member.result.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author shanhuijie
 * @Description:
 * @Date 2019.10.22 13:11
 */
public interface UserVoApi {

    @GetMapping("/uservo/find/userId/{user_id}")
    public Result getUserVoByUserId(@PathVariable String user_id);
}
