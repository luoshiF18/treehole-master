package com.treehole.member.service;


import com.treehole.framework.domain.member.*;
import com.treehole.framework.domain.member.Vo.CardsVo;
import com.treehole.framework.domain.member.result.MemberCode;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.member.mapper.CardsMapper;
import com.treehole.member.mapper.FreegradeMapper;
import com.treehole.member.mapper.PaygradeMapper;
import com.treehole.member.myUtil.MyNumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    private  PointService pointService;

    /*
    *更新
    * */
    @Transactional
    @CacheEvict(value="MemberCard",allEntries=true)
    public void updateCardVo(CardsVo cardsVo) {

        Cards cards = new Cards();
        cards.setCard_id(cardsVo.getCard_id());
        cards.setUser_id(cardsVo.getUser_id());

        FreeGrade byName1 = freegradeService.getByName(cardsVo.getFreegrade());
        cards.setFreegrade_id(byName1.getFreegrade_id());
        PayGrade byName2 = paygradeService.getByName(cardsVo.getPaygrade());
        if(cardsVo.getPaygrade().equals("无")){
            cards.setPaygrade_id(byName2.getPaygrade_id());
            cards.setPaygrade_start(null);
            cards.setPaygrade_end(null);
        }else{
            cards.setPaygrade_id(byName2.getPaygrade_id());
            cards.setPaygrade_start(cardsVo.getPaygrade_start());
            cards.setPaygrade_end(cardsVo.getPaygrade_end());
        }
        cards.setConsum_all(cardsVo.getConsum_all());
        cards.setPoints_sum(cardsVo.getPoints_sum());
        cards.setPoints_now(cardsVo.getPoints_now());
        Example example =new Example(Cards.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("card_id",cards.getCard_id());
        int upd= cardsMapper.updateByExample(cards,example);
        if(upd != 1){
            ExceptionCast.cast(MemberCode.UPDATE_FAIL);
        }
    }

    @Transactional
    @CacheEvict(value="MemberCard",allEntries=true)
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
    @CacheEvict(value="MemberCard",allEntries=true)
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
    @CacheEvict(value="MemberCard",allEntries=true)
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
        //签到信息删除 1找2删
        QueryResult checkin = checkinService.getCheckinByUserId1(id, 1, 5);
        if(checkin.getTotal() != 0){
            checkinService.deleteCheckinByUserId(id);
        }
        //会员积分信息删除 1找2删
        QueryResult points = pointService.findAllPoints1(1, 5, id);
        if (points.getTotal() != 0) {
            pointService.deletePointByUserId(id);
        }
        int del= cardsMapper.delete(card);
        if(del < 1){
            ExceptionCast.cast(MemberCode.DELETE_FAIL);
        }
    }

    @Transactional
    //@CacheEvict(value="MemberCard",allEntries=true)
    public void updateByPayEndTime(){

        List<Cards> cardList = cardsMapper.selectAll();
        int pp =0;
        for(Cards card : cardList){
            pp++;
            System.out.println(pp);
            if(! card.getPaygrade_id().equals("p000")){
                Date date = new Date(); //现在的时间
                Date date1 = card.getPaygrade_end();  //结束时间
                //当前时间大于结束时间  过期 date > date1
                if(date.compareTo(date1) == 1){
                    Example example = new Example(Cards.class);
                    Example.Criteria criteria = example.createCriteria();
                    criteria.andEqualTo("card_id",card.getCard_id());
                    card.setPaygrade_id("p000");
                    card.setPaygrade_start(null);
                    card.setPaygrade_end(null);
                    int upd = cardsMapper.updateByExample(card,example);
                    //System.out.println("+++++++++++++++++"+ upd);
                    if(upd != 1){
                        ExceptionCast.cast(MemberCode.UPDATE_FAIL);
                    }
                }
            }

        }
        //判断当前日期是否大于vipend时间
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date date = new Date(); //现在的时间
//        Date date1 = cards1.getPaygrade_end();  //结束时间
        //当时时间小于结束时间或等于结束时间
        //if(date.compareTo(date1) == -1 ||date.compareTo(date1) == 0){
        //当前时间大于结束时间  过期 date > date1
        //根据end时间判断是否是vip
        /*List<Cards> selectAll = cardsMapper.selectAll();
        for(Cards cards2 : selectAll){
            Date date = new Date(); //现在的时间
            Date date1 = cards2.getPaygrade_end();  //结束时间
            if(date.compareTo(date1) == 1){
                cards.setPaygrade_id(byRank.getPaygrade_id());
                cards.setPaygrade_start(null);
                cards.setPaygrade_end(null);
            }
            Example example =new Example(Cards.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("card_id",card_id);
            int upd= cardsMapper.updateByExampleSelective(cards,example);
            if(upd != 1){
                ExceptionCast.cast(MemberCode.UPDATE_FAIL);
            }
        }*/

    }

}
