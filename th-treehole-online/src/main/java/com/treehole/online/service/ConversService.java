package com.treehole.online.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.treehole.framework.domain.member.result.MemberCode;
import com.treehole.framework.domain.onlinetalk.Convers;
import com.treehole.framework.domain.onlinetalk.Vo.AgentVo;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.online.mapper.ConversMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author hewenze
 * @Description:
 * @Date
 */
@Service
@Cacheable(value = "ConversService")
public class ConversService {

    @Autowired
    private ConversMapper conversMapper;


    /**
     * 根据id查询会话
     * @param convers_id
     * @return
     */
    public Convers getConversById(String convers_id) {
        Convers convers = new Convers();
        convers.setConvers_id(convers_id);
        Convers convers1 = conversMapper.selectOne(convers);
        if ( convers1 == null){
            ExceptionCast.cast(MemberCode.DATA_IS_NULL);
        }
        return convers1;
    }

    /**
     * 新增会话
     * @param convers
     */
    @CacheEvict(value="ConversService",allEntries=true)
    public void insertConvers(Convers convers) {
        if (convers == null){
            ExceptionCast.cast(MemberCode.DATA_IS_NULL);
        }
        Convers convers1 = new Convers();
        convers1.setConvers_id(convers.getConvers_id());
        Convers one = conversMapper.selectOne(convers1);
        if (one != null){

        }else{
            convers.setBegin_time(new Date());
            int i = conversMapper.insert(convers);
            if (i<0 || i==0){
                ExceptionCast.cast(MemberCode.DATA_IS_NULL);
            }
        }



    }

    /**
     * 根据id删除会话
     * @param convers_id
     */
    @Transactional
    @CacheEvict(value="ConversService",allEntries=true)
    public void deleteConversById(String convers_id) {
        Convers convers = new Convers();
        convers.setConvers_id(convers_id);
        int i = conversMapper.delete(convers);
        if (i != 1){
            ExceptionCast.cast(MemberCode.DATA_IS_NULL);
        }
    }

    /**
     *查询所有会话
     * @param page
     * @param size
     * @param name
     * @return
     */
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


}
