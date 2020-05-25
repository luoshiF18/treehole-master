package com.treehole.marketing.controller;

import com.treehole.api.marketing.UserExtensionControllerApi;
import com.treehole.framework.domain.marketing.UserExtension;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.marketing.service.UserExtensionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/marketing/extension/user")
public class UserExtensionController implements UserExtensionControllerApi {

    @Autowired
    private UserExtensionService userExtensionService;

    /**
     * 查询用户看到的站内信通知
     * @param userId
     * @return
     */
    @Override
    @GetMapping("/my_extension_list/{userId}")
    public QueryResponseResult queryUserExtensions(@PathVariable("userId") String userId) {
        QueryResult queryResult = this.userExtensionService.queryUserExtensions(userId);
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
    }

    /**
     * 查询用户新消息数目
     * @param userId
     * @return
     */
    @Override
    @GetMapping("/count/{userId}")
    public int queryUserExtensionCount(@PathVariable("userId")String userId) {
        return this.userExtensionService.queryUserExtensionCount(userId);
    }



    @Override
    @DeleteMapping("/{id}")
    public ResponseResult deleteExtensionById(@PathVariable("id") String id) {
        this.userExtensionService.deleteExtensionById(id);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    @Override
    @PutMapping("/resolve/{userId}")
    public ResponseResult updateResolve(@PathVariable("userId") String userId) {
        this.userExtensionService.updateResolve(userId);
        return new ResponseResult(CommonCode.SUCCESS);
    }
}
