package com.treehole.online.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.treehole.framework.domain.member.result.MemberCode;
import com.treehole.framework.domain.onlinetalk.Agent;
import com.treehole.framework.domain.onlinetalk.Convers;
import com.treehole.framework.domain.onlinetalk.Message;
import com.treehole.framework.domain.onlinetalk.Vo.AgentVo;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.online.mapper.ConversMapper;
import com.treehole.online.mapper.MessageMapper;
import com.treehole.online.myUtil.MyNumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author shanhuijie
 * @Description:
 * @Date
 */
@Service
public class ConversService {

    @Autowired
    private ConversMapper conversMapper;



    public Convers getConversById(String convers_id) {
        Convers convers = new Convers();
        convers.setConvers_id(convers_id);
        Convers convers1 = conversMapper.selectOne(convers);
        if ( convers1 == null){
            ExceptionCast.cast(MemberCode.USER_NOT_EXIST);
        }
        return convers1;
    }



    public void insertConvers(Convers convers) {
        if (convers == null){
            ExceptionCast.cast(MemberCode.USER_NOT_EXIST);
        }
        convers.setConvers_id(MyNumberUtils.getUUID());
        convers.setBegin_time(new Date());
        int i = conversMapper.insert(convers);
        if (i<0 || i==0){
            ExceptionCast.cast(MemberCode.USER_NOT_EXIST);
        }

    }
    @Transactional
    public void deleteConversById(String convers_id) {
        Convers convers = new Convers();
        convers.setConvers_id(convers_id);
        int i = conversMapper.delete(convers);
        System.out.println(convers);
        System.out.println("1111111"+i);
        if (i != 1){
            ExceptionCast.cast(MemberCode.USER_NOT_EXIST);
        }
    }

    public QueryResult getAllConvers(int page, int size, String name) {

        Page pag = PageHelper.startPage(page,size);
        List<Convers> conversList = new ArrayList<>();
        if (!name .equals("") ){
            Convers convers = new Convers();
            convers.setConvers_agentname(name);
            conversList= conversMapper.select(convers);
        }else {
            conversList = conversMapper.selectAll();
        }

        PageInfo<AgentVo> pageInfo = new PageInfo<>(pag.getResult());

        return new QueryResult(conversList, pageInfo.getTotal());


    }

    /*更改密码 未实现*/

    /*忘记密码 未实现*/
}
