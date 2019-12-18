package com.treehole.member.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.treehole.framework.domain.member.*;
import com.treehole.framework.domain.member.Vo.CardsVo;
import com.treehole.framework.domain.member.Vo.UserVo;
import com.treehole.framework.domain.member.resquest.CardListRequest;
import com.treehole.framework.domain.member.result.MemberCode;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.member.mapper.CardsMapper;
import com.treehole.member.mapper.CardsVoMapper;
import com.treehole.member.mapper.FreegradeMapper;
import com.treehole.member.mapper.PaygradeMapper;
import com.treehole.member.myUtil.MyNumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author shanhuijie
 * @Description: 会员卡服务
 * @Date
 */
@Service
@Cacheable(value="MemberCard")
public class CardsVoService {
    @Autowired
    private CardsMapper cardsMapper;
    @Autowired
    private PaygradeMapper paygradeMapper;
    @Autowired
    private FreegradeMapper freegradeMapper;
    @Autowired
    private  UserService userService;

    @Autowired
    private CardsService cardsService;
    /**
     * 查询所有CardsVo信息
     * 自定义条件查询 user_id/card_id/手机号码
     */
    public QueryResponseResult findAllCardVos(Integer page,
                                              Integer size,
                                              CardListRequest cardListRequest) {
        //分页
        Page pag =PageHelper.startPage(page,size);
        //
        if(cardListRequest == null){
            cardListRequest =new CardListRequest();
        }
        Cards cards = new Cards();
        if(StringUtils.isNotEmpty(cardListRequest.getCard_id())){
            cards.setCard_id(cardListRequest.getCard_id());
        }
        if(StringUtils.isNotEmpty(cardListRequest.getUser_id())){
            cards.setUser_id(cardListRequest.getUser_id());
        }
        if (StringUtils.isNotEmpty(cardListRequest.getUser_phone())) {
            User user = userService.findUserByPhone(cardListRequest.getUser_phone());
            cards.setUser_id(user.getUser_id());
        }
        if (StringUtils.isNotEmpty(cardListRequest.getUser_nickname())) {
            User user = userService.findUserByNickname(cardListRequest.getUser_nickname());
            cards.setUser_id(user.getUser_id());
        }
        //查询
        List<Cards> cardsList = cardsMapper.select(cards);
        if (CollectionUtils.isEmpty(cardsList)) {
            //ExceptionCast.cast(MemberCode.DATA_IS_NULL);
            ExceptionCast.cast(MemberCode.DATA_IS_NULL);
        }
        List<CardsVo> cardsVos = new ArrayList<CardsVo>();
        for(Cards cards1:cardsList){
            CardsVo cardsVo = new CardsVo();
            cardsVo.setCard_id(cards1.getCard_id());
            cardsVo.setUser_id(cards1.getUser_id());
            cardsVo.setUser_nickname((userService.getUserById(cards1.getUser_id()).getUser_nickname()));
            //cardsVo.setUser_nickname(userService.getUserById(cards1.getUser_id()).getUser_nickname());
            String freegradeId = cards1.getFreegrade_id();
            FreeGrade freeGrade = new FreeGrade();
            freeGrade.setFreegrade_id(freegradeId);
            cardsVo.setFreegrade(freegradeMapper.selectOne(freeGrade).getFreegrade_name());//数据库freeID对应的free等级记录为空的话，空指针异常
            cardsVo.setConsum_all(cards1.getConsum_all());
            cardsVo.setPoints_now(cards1.getPoints_now());
            cardsVo.setPoints_sum(cards1.getPoints_sum());
            //付费会员的等级变化在payGardeService中
            String paygradeId = cards1.getPaygrade_id();
            if(StringUtils.isEmpty(paygradeId) || (cards1.getPaygrade_id()).equals("")||paygradeId == null){
                cardsVo.setPaygrade("无");
                cardsVo.setPaygrade_start(null);
                cardsVo.setPaygrade_end(null);
            }else {
                PayGrade payGrade = new PayGrade();
                payGrade.setPaygrade_id(paygradeId);
                cardsVo.setPaygrade(paygradeMapper.selectOne(payGrade).getPaygrade_name());
                cardsVo.setPaygrade_start(cards1.getPaygrade_start());
                cardsVo.setPaygrade_end(cards1.getPaygrade_end());
        }
            cardsVos.add(cardsVo);
        }
        //解析分页结果
        PageInfo<CardsVo> pageInfo = new PageInfo<CardsVo>(pag.getResult());
        QueryResult queryResult = new QueryResult();
        queryResult.setList(cardsVos);
        queryResult.setTotal(pageInfo.getTotal());
        return new QueryResponseResult(CommonCode.SUCCESS,queryResult);
    }

    /*根据id 获取cardsVo对象*/
    public CardsVo getCardByCardId(String id){
        Cards cards1 = cardsService.getCardById(id);
        CardsVo cardsVo = new CardsVo();

        cardsVo.setCard_id(cards1.getCard_id());
        cardsVo.setUser_id(cards1.getUser_id());
        //cardsVo.setUser_nickname(userService.getUserById(cards1.getUser_id()).getUser_nickname());
        String freegradeId = cards1.getFreegrade_id();
        FreeGrade freeGrade = new FreeGrade();
        freeGrade.setFreegrade_id(freegradeId);
        cardsVo.setFreegrade(freegradeMapper.selectOne(freeGrade).getFreegrade_name());//数据库freeID对应的free等级记录为空的话，空指针异常
        cardsVo.setConsum_all(cards1.getConsum_all());
        cardsVo.setPoints_now(cards1.getPoints_now());
        cardsVo.setPoints_sum(cards1.getPoints_sum());
        //付费会员的等级变化在payGardeService中
        String paygradeId = cards1.getPaygrade_id();
        if(StringUtils.isNotEmpty(paygradeId)){
            PayGrade payGrade = new PayGrade();
            payGrade.setPaygrade_id(paygradeId);
            cardsVo.setPaygrade(paygradeMapper.selectOne(payGrade).getPaygrade_name());
            cardsVo.setPaygrade_start(cards1.getPaygrade_start());
            cardsVo.setPaygrade_end(cards1.getPaygrade_end());
        }else {
            cardsVo.setPaygrade("无");
            cardsVo.setPaygrade_start(null);
            cardsVo.setPaygrade_end(null);
        }
        return cardsVo;
    }


    /*根据id 获取cardsVo对象*/
    public CardsVo getCardByUserId(String id){
        Cards cards1 = cardsService.findCardsByUserId(id);
        CardsVo cardsVo = new CardsVo();

        cardsVo.setCard_id(cards1.getCard_id());
        cardsVo.setUser_id(cards1.getUser_id());
        //cardsVo.setUser_nickname(userService.getUserById(cards1.getUser_id()).getUser_nickname());
        String freegradeId = cards1.getFreegrade_id();
        FreeGrade freeGrade = new FreeGrade();
        freeGrade.setFreegrade_id(freegradeId);
        cardsVo.setFreegrade(freegradeMapper.selectOne(freeGrade).getFreegrade_name());//数据库freeID对应的free等级记录为空的话，空指针异常
        cardsVo.setConsum_all(cards1.getConsum_all());
        cardsVo.setPoints_now(cards1.getPoints_now());
        cardsVo.setPoints_sum(cards1.getPoints_sum());
        //付费会员的等级变化在payGardeService中
        String paygradeId = cards1.getPaygrade_id();
        if(StringUtils.isNotEmpty(paygradeId)){
            PayGrade payGrade = new PayGrade();
            payGrade.setPaygrade_id(paygradeId);
            cardsVo.setPaygrade(paygradeMapper.selectOne(payGrade).getPaygrade_name());
            cardsVo.setPaygrade_start(cards1.getPaygrade_start());
            cardsVo.setPaygrade_end(cards1.getPaygrade_end());
        }else {
            cardsVo.setPaygrade("无");
            cardsVo.setPaygrade_start(null);
            cardsVo.setPaygrade_end(null);
        }
        return cardsVo;
    }
}
