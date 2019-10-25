package com.treehole.member.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.treehole.framework.domain.member.Cards;
import com.treehole.framework.domain.member.FreeGrade;
import com.treehole.framework.domain.member.PayGrade;
import com.treehole.framework.domain.member.User;
import com.treehole.framework.domain.member.result.MemberCode;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.member.mapper.CardsMapper;
import com.treehole.member.mapper.FreegradeMapper;
import com.treehole.member.mapper.PaygradeMapper;
import com.treehole.member.myUtil.MyNumberUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.util.List;

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

    public QueryResult findAllCards(Integer page, Integer size) {
        //        分页
        PageHelper.startPage(page, size);
        //查询
        List<Cards> cards = cardsMapper.selectAll();
        if (CollectionUtils.isEmpty(cards)) {
            ExceptionCast.cast(MemberCode.DATA_IS_NULL);
        }
        //        解析分页结果
        PageInfo<Cards> pageInfo = new PageInfo<>(cards);

        return new QueryResult(cards, pageInfo.getTotal());
    }

    /*根据user_id查询Cards*/
    public Cards findCardsByUserId(String user_id) {
        if(StringUtils.isBlank(user_id)){
            ExceptionCast.cast(MemberCode.SELECT_NULL);
        }
        Cards card = new Cards();
        card.setUser_id(user_id);
        Cards cards = cardsMapper.selectOne(card);
        if (cards == null){
            ExceptionCast.cast(MemberCode.CARD_NOT_EXIST);
        }
        return cards;
    }

    public void updateCard(Cards cards) {

        Example example =new Example(Cards.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("card_id",cards.getCard_id());
        //昵称
        /*String nickname = user.getUser_nickname();
        if(this.findUserByNickname(nickname) != null){
            ExceptionCast.cast(MemberCode.NICKNAME_EXIST);
        }*/
        int upd= cardsMapper.updateByExampleSelective(cards,example);
        if(upd != 1){
            ExceptionCast.cast(MemberCode.UPDATE_FAIL);
        }
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
