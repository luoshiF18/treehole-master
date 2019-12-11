package com.treehole.member.controller;

import com.treehole.api.member.CardsControllerApi;
import com.treehole.framework.domain.member.Cards;
import com.treehole.framework.domain.member.resquest.CardListRequest;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.member.service.CardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


    @Override
    public QueryResponseResult findAllCards(Integer page, Integer size, CardListRequest cardListRequest) {
        return null;
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
