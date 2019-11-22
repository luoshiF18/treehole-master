package com.treehole.member.controller;

import com.treehole.api.member.CardsControllerApi;
import com.treehole.framework.domain.member.Cards;
import com.treehole.framework.domain.member.User;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.member.service.CardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Shan HuiJie
 * @Description:
 * @Date 2019.10.25 18:18
 */
@RestController
@RequestMapping("card")
public class CardsController implements CardsControllerApi {
    @Autowired
    private CardsService cardsService;
    @Override
    @GetMapping("/getAllCards")
    public QueryResponseResult findAllCards(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                            @RequestParam(value = "size", defaultValue = "5") Integer size,
                                            @RequestParam(value = "sortBy", required = false) String sortBy,//排序方式
                                            @RequestParam(value = "desc", defaultValue = "false") Boolean desc) {
        QueryResult queryResult = cardsService.findAllCards(page, size,sortBy,desc);
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
    }

    @Override
    @PostMapping("/insert")
    public ResponseResult insertCard(@RequestBody @Valid User user) {
        cardsService.insertCard(user);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    @Override
    @DeleteMapping(value ="/delete/id/{card_id}")
    public ResponseResult deleteCardById(@PathVariable("card_id") String card_id) {

        return null;
    }

    @Override
    @PostMapping("/update")
    public ResponseResult update(@RequestBody @Valid Cards cards) {
        cardsService.updateCard(cards);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    @Override
    @GetMapping("/find/userId/{user_id}")
    public Cards findCardsByUserId(@PathVariable("user_id") String user_id) {
        return cardsService.findCardsByUserId(user_id);

    }
}
