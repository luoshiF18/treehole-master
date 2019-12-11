package com.treehole.online.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.treehole.framework.domain.member.User;
import com.treehole.framework.domain.onlinetalk.Agent;
import com.treehole.framework.domain.member.result.MemberCode;
import com.treehole.framework.domain.onlinetalk.Vo.AgentVo;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.online.mapper.AgentMapper;
import com.treehole.online.myUtil.MyMd5Utils;
import com.treehole.online.myUtil.MyNumberUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author shanhuijie
 * @Description:
 * @Date
 */
@Service
public class AgentService {

    @Autowired
    private AgentMapper agentMapper;



    /**
     * 查询所有用户
     *
     * @param
     * @return List<User>
     */

    public QueryResult findAllAgent(int page, int size , String agent_name) {
        //        分页
        Page pag =PageHelper.startPage(page,size);
        List<Agent> agents = new ArrayList<>();
        if (!agent_name .equals("") ){
            Agent agent2 = new Agent();
            agent2.setAgent_name(agent_name);
            agents= agentMapper.select(agent2);
        }else {
            agents = agentMapper.selectAll();
        }

       //查询
        System.out.println(agents);
        List<AgentVo> agentVos = new ArrayList<>();
        for (Agent agent : agents){

            AgentVo agentVo = new AgentVo();
            agentVo.setAgent_id(agent.getAgent_id());
            agentVo.setAgent_from(agent.getAgent_from());
            agentVo.setAgent_name(agent.getAgent_name());
            agentVo.setAgent_no(agent.getAgent_no());
            agentVo.setAgent_phone(agent.getAgent_phone());
            if (agent.getAgent_sex().equals("1")){
                agentVo.setAgent_sex("男");
            }else{
                agentVo.setAgent_sex("女");
            }

            agentVo.setCreate_time(agent.getCreate_time());
            agentVo.setCreater(agent.getCreater());
            agentVos.add(agentVo);
        }
        /*if (CollectionUtils.isEmpty(agents)) {
            ExceptionCast.cast(MemberCode.DATA_IS_NULL);
        }*/
        //        解析分页结果
        PageInfo<AgentVo> pageInfo = new PageInfo<>(pag.getResult());

        return new QueryResult(agentVos, pageInfo.getTotal());

    }

    /**
     * 根据user对象查询所有user记录
     *
     * @param
     * @return List<UserVo>
     */
    public QueryResult findAgent(int page,int size,String name) {
        Agent agent2 = new Agent();
        agent2.setAgent_name(name);
        //        分页
        Page pag =PageHelper.startPage(page,size);
        //PageHelper.startPage(page, size);
        //查询
        List<Agent> agents = agentMapper.select(agent2);
        System.out.println(agents);
        List<AgentVo> agentVos = new ArrayList<>();
        for (Agent agent : agents){

            AgentVo agentVo = new AgentVo();
            agentVo.setAgent_id(agent.getAgent_id());
            agentVo.setAgent_from(agent.getAgent_from());
            agentVo.setAgent_name(agent.getAgent_name());
            agentVo.setAgent_no(agent.getAgent_no());
            agentVo.setAgent_phone(agent.getAgent_phone());
            if (agent.getAgent_sex().equals("1")){
                agentVo.setAgent_sex("男");
            }else{
                agentVo.setAgent_sex("女");
            }

            agentVo.setCreate_time(agent.getCreate_time());
            agentVo.setCreater(agent.getCreater());
            agentVos.add(agentVo);
        }
        if (CollectionUtils.isEmpty(agents)) {
            ExceptionCast.cast(MemberCode.DATA_IS_NULL);
        }
        //        解析分页结果
        PageInfo<AgentVo> pageInfo = new PageInfo<>(pag.getResult());

        return new QueryResult(agentVos, pageInfo.getTotal());



        /*if(agent1 == null){
            ExceptionCast.cast(MemberCode.DATA_IS_NULL);
        }
        return agent1;*/
    }

    /**
     * 根据user_nickname查询所有user记录
     *
     * @param
     * @return User
     */
   /* public User findUserByNickname(String nickname){
        if(StringUtils.isBlank(nickname)){
            ExceptionCast.cast(MemberCode.DATA_ERROR);
        }
        User user = new User();
        user.setUser_nickname(nickname);
        User use = userMapper.selectOne(user);

        return use;
    }*/

/*public UserExt getUserExt(String userNickName){
        if (StringUtils.isBlank(userNickName)){
            ExceptionCast.cast( MemberCode.DATA_ERROR);
        }
     User user = new User();
     user.setUser_nickname(userNickName);
    User use = userMapper.selectOne(user);
    UserExt userExt = new UserExt();
    BeanUtils.copyProperties(use,userExt);
    return userExt;
}*/


   /* *//**
     * 根据user_iphone查询所有user记录
     *
     * @param phonenumber
     * @return User
     *//*
    public User findUserByPhone(String phonenumber)  {
        User user = new User();
        user.setUser_phone(phonenumber);
        return  userMapper.selectOne(user);
    }*/


    /**
     * 通过id查询用户
     * @return List<User>
     */
    public Agent getAgentById(String agent_id){
       Agent agent = new Agent();
       //
        agent.setAgent_id(agent_id);
        Agent agent1 = agentMapper.selectOne(agent);
        if(agent1 == null){
            ExceptionCast.cast(MemberCode.USER_NOT_EXIST);
        }
        return agent1;
    }

    /**
     * 通过id删除客服
     * @param agent_id
     * @return
     */
    public void deleteAgentById(String agent_id) {

        if(StringUtils.isBlank(agent_id)){
            ExceptionCast.cast(MemberCode.DATA_ERROR);
        }
        if(this.getAgentById(agent_id) == null){
            ExceptionCast.cast(MemberCode.DELETE_USER_NOT_EXIST);
        }
        Agent agent = new Agent();
        agent.setAgent_id(agent_id);
        int del = agentMapper.delete(agent);

        if( del != 1){
            ExceptionCast.cast(MemberCode.DELETE_FAIL);
        }

    }

    /**
     * 创建一条用户信息
     *
     * @param agent
     * @return int
     */
    public void insertAgent(Agent agent)  {
        agent.setAgent_id(MyNumberUtils.getUUID());
        //将密码MD5加密！！！！
        String pw=agent.getAgent_password();
        try {
            agent.setAgent_password(MyMd5Utils.getMd5(pw));
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*String nickname1 = user.getUser_nickname();*/
        /*if(this.findUserByNickname(nickname1) != null){
           // ExceptionCast.cast(MemberCode.NICKNAME_EXIST);
            Random random = new Random();
            String nickname2 = nickname1 + random.nextInt(1000);
        }
        if(this.findUserByNickname(nickname2) != null){
            ExceptionCast.cast(MemberCode.NICKNAME_EXIST);
        }*/
        /*if(this.findUserByNickname(nickname1) != null){
            ExceptionCast.cast(MemberCode.NICKNAME_EXIST);
        }*/

        agent.setCreate_time(new Date());
        //
        int ins = agentMapper.insert(agent);
        if( ins != 1){
            ExceptionCast.cast(MemberCode.INSERT_FAIL);
        }
        //往cards表中插入数据
       //cardsService.insertCard(user);
    }

    /**
     * 更新客服基本信息
     *
     *
     * @param agent
     * @return int
     */
    public void updateAgent(Agent agent){

        Example example =new Example(Agent.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("agent_id",agent.getAgent_id());
        //昵称
        /*String nickname = user.getUser_nickname();
        if(this.findUserByNickname(nickname) != null){
            ExceptionCast.cast(MemberCode.NICKNAME_EXIST);
        }*/
        int upd= agentMapper.updateByExampleSelective(agent,example);
        if(upd != 1){
            ExceptionCast.cast(MemberCode.UPDATE_FAIL);
        }
    }

    /*更改密码 未实现*/

    /*忘记密码 未实现*/
}
