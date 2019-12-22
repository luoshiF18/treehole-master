package com.treehole.online.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.treehole.framework.domain.member.result.MemberCode;
import com.treehole.framework.domain.onlinetalk.Serv;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.online.mapper.ServMapper;
import com.treehole.online.myUtil.MyNumberUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author hewenze
 * @Description:
 * @Date
 */
@Service
@Cacheable(value = "ServService")
public class ServService {

    @Autowired
    private ServMapper servMapper;


    /**
     * 查询所有客服服务
     * @param page
     * @param size
     * @param agent_name
     * @return
     */
    public QueryResult findAllServ(int page, int size , String agent_name) {

        Page pag =PageHelper.startPage(page,size);
        List<Serv> servs = new ArrayList<>();
        if (!agent_name.equals("")){
            Serv serv = new Serv();
            serv.setAgent_name(agent_name);
           servs= servMapper.select(serv);
        }else {
            servs = servMapper.selectAll();
        }
        //        解析分页结果
        PageInfo<Serv> pageInfo = new PageInfo<>(servs);

        return new QueryResult(servs, pageInfo.getTotal());

    }

    /**
     * 根据serv对象查询serv对象
     * @param serv
     * @return
     */
    public Serv findServ(Serv serv) {
        Serv serv1 = servMapper.selectOne(serv);
        if(serv1 == null){
            ExceptionCast.cast(MemberCode.DATA_IS_NULL);
        }
        return serv1;
    }


    /**
     * 根据客服姓名查询客服服务
     * @param agent_name
     * @return
     */
    public Serv findServByAgentName(String agent_name)  {
        Serv serv = new Serv();
        serv.setAgent_name(agent_name);
        Serv serv1 = servMapper.selectOne(serv);
        return  serv1;
    }



   /**
     * 通过id查询服务
     * @return List<User>
     */
    public Serv getServById(String serv_id){
        Serv serv = new Serv();
        serv.setServ_id(serv_id);
        Serv serv1 = servMapper.selectOne(serv);
        if(serv1 == null){
            ExceptionCast.cast(MemberCode.DATA_IS_NULL);
        }

        return serv1;
    }

    /**
     * 通过id删除服务
     * @param serv_id
     * @return
     */
    @CacheEvict(value="ServService",allEntries=true)
    public void deleteServById(String serv_id) {

        if(StringUtils.isBlank(serv_id)){
            ExceptionCast.cast(MemberCode.DATA_ERROR);
        }
        if(this.getServById(serv_id) == null){
            ExceptionCast.cast(MemberCode.DELETE_USER_NOT_EXIST);
        }
        Serv serv =  new Serv();
        serv.setServ_id(serv_id);
        int del = servMapper.delete(serv);

        if( del != 1){
            ExceptionCast.cast(MemberCode.DELETE_FAIL);
        }

    }

    /**
     * 创建一条服务信息
     * @param serv
     * @return
     */
    @CacheEvict(value="ServService",allEntries=true)
    public void insertServ(Serv serv)  {
        serv.setServ_id(MyNumberUtils.getUUID());
        serv.setServ_time(new Date());
        int ins = servMapper.insert(serv);
        if( ins != 1){
            ExceptionCast.cast(MemberCode.INSERT_FAIL);
        }

    }


}
