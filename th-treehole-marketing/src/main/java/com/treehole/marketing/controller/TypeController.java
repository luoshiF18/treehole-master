package com.treehole.marketing.controller;

import com.treehole.api.marketing.TypeControllerApi;
import com.treehole.framework.domain.marketing.Type;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.marketing.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author wanglu
 */
@RestController
@RequestMapping("type")
public class TypeController implements TypeControllerApi {

    @Autowired
    private TypeService typeService;
    @GetMapping
    public QueryResponseResult queryTypes(@RequestParam(defaultValue = "false") Boolean usedFor) {
        QueryResult queryResult = this.typeService.queryTypes(usedFor);
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
    }

    @PostMapping
    public ResponseResult saveType(Type type) {
        this.typeService.saveType(type);
        return new ResponseResult(CommonCode.SUCCESS);
    }
}
