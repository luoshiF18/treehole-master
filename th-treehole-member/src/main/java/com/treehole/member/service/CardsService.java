package com.treehole.member.service;

import com.treehole.framework.domain.member.Cards;
import com.treehole.framework.domain.member.FreeGrade;
import com.treehole.framework.domain.member.PayGrade;
import com.treehole.framework.domain.member.User;
import com.treehole.framework.domain.member.result.MemberCode;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.member.mapper.CardsMapper;
import com.treehole.member.mapper.FreegradeMapper;
import com.treehole.member.mapper.PaygradeMapper;
import com.treehole.member.myUtil.MyNumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author shanhuijie
 * @Description: 会员卡服务
 * @Date
 */
@Service
public class CardsService {
    @Autowired
    private CardsMapper cardsMapper;
    @Autowired
    private PaygradeMapper paygradeMapper;
    @Autowired
    private FreegradeMapper freegradeMapper;
    /*根据user_id查询Cards*/
    public Cards findCardsByUserId(String user_id) {
        Cards card = new Cards();
        card.setUser_id(user_id);
        Cards cards = cardsMapper.selectOne(card);
        if (cards == null){
            ExceptionCast.cast(MemberCode.CARD_NOT_EXIST);
        }
        return cards;


    }

    public void updateCard(Cards cards) {
    }



    public void insertCard(User user){
        Cards cards = new Cards();
        cards.setCard_id(MyNumberUtils.getUUID());
        cards.setUser_id(user.getUser_id());
        //int paygrade_id = CardsService.findGradeByRank(1);
        PayGrade payGrade = new PayGrade();
        payGrade.setRank(1);
        cards.setPaygrade_id(paygradeMapper.selectOne(payGrade).getPaygrade_id());
        FreeGrade freeGrade = new FreeGrade();
        freeGrade.setRank(1);
        cards.setFreegrade_id(freegradeMapper.selectOne(freeGrade).getFreegrade_id());
        cards.setConsum_all(BigDecimal.valueOf(0));

        cards.setPoints_now(0);
        cardsMapper.insert(cards);
    }
}
