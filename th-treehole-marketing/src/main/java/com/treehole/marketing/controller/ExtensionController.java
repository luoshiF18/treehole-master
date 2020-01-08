package com.treehole.marketing.controller;

import com.treehole.api.marketing.ExtensionControllerApi;
import com.treehole.framework.domain.marketing.Extension;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.marketing.service.ExtensionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author wanglu
 */
@RestController
@RequestMapping("/marketing/extension")
public class ExtensionController implements ExtensionControllerApi {

    @Autowired
    private ExtensionService extensionService;



    @PostMapping
    @Override
    public ResponseResult addExtension(@RequestBody Extension extension) {
        this.extensionService.addExtensionInfo(extension);
        return new ResponseResult(CommonCode.SUCCESS);
    }



    @Override
    @GetMapping("/all")
    public QueryResponseResult queryExtension(@RequestParam(value = "key", required = false)String key,
                                              @RequestParam(value = "page", defaultValue = "1")Integer page,
                                              @RequestParam(value = "size", defaultValue = "5")Integer size,
                                              @RequestParam(value = "sortBy", required = false)String sortBy,
                                              @RequestParam(value = "desc", defaultValue = "true")Boolean desc,
                                              @RequestParam(value = "mode", required = false)Integer mode,
                                              @RequestParam(value = "usedFor", required = false)Boolean usedFor) {
        QueryResult<Extension> queryResult = this.extensionService.queryExtension(key, page, size, sortBy, desc, mode, usedFor);
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseResult deleteExtension(@PathVariable String id) {
        this.extensionService.deleteExtension(id);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    @PostMapping("/push_to_client")
    public ResponseResult addPushToClient(@RequestBody Extension extension){
        this.extensionService.addPushToClient(extension);
        return new ResponseResult(CommonCode.SUCCESS);
    }
}
