package com.treehole.member.controller;

import com.treehole.api.member.CardsControllerApi;
import com.treehole.framework.domain.member.Cards;
import com.treehole.framework.domain.member.User;
import com.treehole.framework.domain.member.resquest.CardListRequest;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.member.service.CardsService;
import com.treehole.member.service.CardsVoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Shan HuiJie
 * @Description:
 * @Date 2019.10.25 18:18
 */
@RestController
@RequestMapping("member/card")
public class CardsController implements CardsControllerApi {
    @Autowired
    private CardsService cardsService;
    @Autowired
    private CardsVoService cardsVoService;
    @Override
    @GetMapping("/getAllCards/{page}/{size}")
        public QueryResponseResult findAllCards(@PathVariable("page") Integer page,
                                                @PathVariable("size") Integer size,
                                                CardListRequest cardListRequest) {
        return cardsVoService.findAllCardVos(page,size,cardListRequest);
    }

    @Override
    @PostMapping("/insert/id/{id}")
    public ResponseResult insertCard(@PathVariable("id") String id) {
        cardsService.insertCard(id);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    @Override
    @DeleteMapping(value ="/delete/userId/{user_id}")
    public ResponseResult deleteCardById(@PathVariable("user_id") String user_id) {
        cardsService.deleteCard(user_id);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    @Override
    @PutMapping("/update")
    public ResponseResult update(@RequestBody @Valid Cards cards) {
        cardsService.updateCard(cards);
        return new ResponseResult(CommonCode.SUCCESS);
    }

}
