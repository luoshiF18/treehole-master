package com.treehole.member.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.treehole.framework.domain.evaluation.Scale;
import com.treehole.framework.domain.member.Cards;
import com.treehole.framework.domain.member.FreeGrade;
import com.treehole.framework.domain.member.PayGrade;
import com.treehole.framework.domain.member.User;
import com.treehole.framework.domain.member.Vo.UserVo;
import com.treehole.framework.domain.member.resquest.CardListRequest;
import com.treehole.framework.domain.member.result.MemberCode;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.member.mapper.CardsMapper;
import com.treehole.member.mapper.FreegradeMapper;
import com.treehole.member.mapper.PaygradeMapper;
import com.treehole.member.mapper.UserMapper;
import com.treehole.member.myUtil.MyNumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    @Autowired
    private CheckinService checkinService;
    @Autowired
    private  UserVoService userVoService;
    @Autowired
    private  PointService pointService;
    /*
    *查询
    * */
    public QueryResult findAllCards(Integer page,
                                    Integer size,
                                    CardListRequest cardListRequest) {
        //分页
        PageHelper.startPage(page, size);
        Example example = new Example(Cards.class);
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
            UserVo user = userVoService.getUserByUserPhone(cardListRequest.getUser_phone());
            String id1=user.getUser_id();
            String id2=cardListRequest.getUser_id();

            if(user.getUser_id().equals(cardListRequest.getUser_id()) ){
                cards.setUser_id(user.getUser_id());
            }else{
                ExceptionCast.cast(MemberCode.DATA_IS_NULL);
            }
        }
        //查询
        List<Cards> cardsList = cardsMapper.select(cards);
        if (CollectionUtils.isEmpty(cardsList)) {
            ExceptionCast.cast(MemberCode.DATA_IS_NULL);
        }
        //        解析分页结果
        PageInfo<Cards> pageInfo = new PageInfo<>(cardsList);
        return new QueryResult(cardsList, pageInfo.getTotal());
    }


    /*
    *更新
    * */
    @Transactional
    public void updateCard(Cards cards) {

        Example example =new Example(Cards.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("card_id",cards.getCard_id());

        int upd= cardsMapper.updateByExampleSelective(cards,example);
        if(upd != 1){
            ExceptionCast.cast(MemberCode.UPDATE_FAIL);
        }
    }


    /*新增
    * 根据user_id*/
    @Transactional
    public void insertCard(String id){
        if(org.apache.commons.lang3.StringUtils.isBlank(id)){
            ExceptionCast.cast(MemberCode.DATA_ERROR);
        }
        Cards cards = new Cards();
        cards.setCard_id(MyNumberUtils.getUUID());
        cards.setUser_id(id);
        //int paygrade_id = CardsService.findGradeByRank(1);
        PayGrade payGrade = new PayGrade();
        payGrade.setRank(0);
        cards.setPaygrade_id(paygradeMapper.selectOne(payGrade).getPaygrade_id());
        FreeGrade freeGrade = new FreeGrade();
        freeGrade.setRank(0);
        cards.setFreegrade_id(freegradeMapper.selectOne(freeGrade).getFreegrade_id());
        cards.setConsum_all(BigDecimal.valueOf(0));
        cards.setPoints_now(0);
        int ins= cardsMapper.insert(cards);
        if(ins != 1){
            ExceptionCast.cast(MemberCode.INSERT_FAIL);
        }
    }

    public Cards findCardsByUserId(String id){
       Cards cards = new Cards();
       cards.setUser_id(id);
       Cards cards1 = cardsMapper.selectOne(cards);
        if(cards1 == null){
            return null;
        }
        return cards1;
    }
    /*根据userid删除*/
    @Transactional
    public void deleteCard(String id) {
        //id不为空
        if(org.apache.commons.lang3.StringUtils.isBlank(id)){
            ExceptionCast.cast(MemberCode.DATA_ERROR);
        }
            //如果通过user-id查找到card对象
            Cards card = this.findCardsByUserId(id);
            if(card == null){
                ExceptionCast.cast(MemberCode.USER_NOT_EXIST);
            }
        //签到信息删除
        checkinService.deleteCheckinByUserId(id);
        //会员积分信息删除！！！
        pointService.deletePointByUserId(id);
            int del= cardsMapper.delete(card);
            if(del < 1){
                ExceptionCast.cast(MemberCode.DELETE_FAIL);
            }
    }
}
