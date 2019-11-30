package com.treehole.member.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.treehole.framework.domain.evaluation.Scale;
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
import java.util.ArrayList;
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
    private FreegradeService freegradeService;
    @Autowired
    private PaygradeService paygradeService;
    @Autowired
    private CheckinService checkinService;
    @Autowired
    private  UserVoService userVoService;
    @Autowired
    private  PointService pointService;


    /*
    *更新
    * */
    @Transactional
    public void updateCardVo(CardsVo cardsVo) {

        Cards cards = new Cards();
        cards.setCard_id(cardsVo.getCard_id());
        cards.setUser_id(cardsVo.getUser_id());

        FreeGrade byName1 = freegradeService.getByName(cardsVo.getFreegrade());
        cards.setFreegrade_id(byName1.getFreegrade_id());

        if(cardsVo.getPaygrade().equals("无")){
            cards.setPaygrade_id(null);
            cards.setPaygrade_start(null);
            cards.setPaygrade_end(null);
        }else{
            PayGrade byName2 = paygradeService.getByName(cardsVo.getPaygrade());
            cards.setPaygrade_id(byName2.getPaygrade_id());
            cards.setPaygrade_start(cardsVo.getPaygrade_start());
            cards.setPaygrade_end(cardsVo.getPaygrade_end());
            cards.setPaygrade_end(cardsVo.getPaygrade_end());
        }
        cards.setConsum_all(cardsVo.getConsum_all());
        cards.setPoints_sum(cardsVo.getPoints_sum());
        cards.setPoints_now(cardsVo.getPoints_now());
        Example example =new Example(Cards.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("card_id",cards.getCard_id());

        int upd= cardsMapper.updateByExampleSelective(cards,example);
        if(upd != 1){
            ExceptionCast.cast(MemberCode.UPDATE_FAIL);
        }
    }

    @Transactional
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
        /*PayGrade payGrade = new PayGrade();
        payGrade.setRank(0);
        cards.setPaygrade_id(paygradeMapper.selectOne(payGrade).getPaygrade_id());*/
        cards.setPaygrade_id(null);
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

    /*
    * 根据userID查找cards
    * */
    public Cards findCardsByUserId(String id){
       Cards cards = new Cards();
       cards.setUser_id(id);
       Cards cards1 = cardsMapper.selectOne(cards);
        if(cards1 == null){
            return null;
        }
        return cards1;
    }
    /**
     * 通过id查询用户
     * @return List<User>
     */
    public Cards getCardById(String card_id){
        Cards cards = new Cards();
        cards.setCard_id(card_id);
        Cards cards1 = cardsMapper.selectOne(cards);
        if(cards1 == null){
            ExceptionCast.cast(MemberCode.CARD_NOT_EXIST);
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
        QueryResult checkin = checkinService.getCheckinByUserId1(id, 1, 5);
        if(checkin.getTotal() != 0){
            checkinService.deleteCheckinByUserId(id);
        }
        //会员积分信息删除！！！
        QueryResult points = pointService.findAllPoints1(1, 5, id);
        if (points.getTotal() != 0) {
            pointService.deletePointByUserId(id);
        }
            int del= cardsMapper.delete(card);
            if(del < 1){
                ExceptionCast.cast(MemberCode.DELETE_FAIL);
            }
    }
}
