package com.treehole.online.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.treehole.framework.domain.member.User;
import com.treehole.framework.domain.member.Vo.UserVo;
import com.treehole.framework.domain.member.result.MemberCode;
import com.treehole.framework.domain.onlinetalk.Agent;
import com.treehole.framework.domain.onlinetalk.Vo.AgentVo;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.online.client.UserClient;
import com.treehole.online.mapper.AgentMapper;
import com.treehole.online.myUtil.MyNumberUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author hewenze
 * @Description:
 * @Date
 */
@Service
//使用缓存注解为此类中的所有方法声明可缓存
//@Cacheable(value = "AgentService")
public class AgentService {

    @Autowired
    private AgentMapper agentMapper;
    @Autowired
    private UserClient userClient;


    /**
     * 查询所有客服
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
        List<AgentVo> agentVos  = agentsToAgentsVo(agents);

        if (CollectionUtils.isEmpty(agents)) {
            ExceptionCast.cast(MemberCode.DATA_IS_NULL);
        }
        //        解析分页结果
        PageInfo<AgentVo> pageInfo = new PageInfo<>(pag.getResult());
        return new QueryResult(agentVos, pageInfo.getTotal());

    }

//封装方法
  private List<AgentVo> agentsToAgentsVo(List<Agent> agents){
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
      return agentVos;
  }

    /**
     * 根据agent对象查询所有agent记录
     *
     * @param
     * @return List<agentVo>
     */
    public QueryResult findAgent(int page,int size,String name) {
        Agent agent2 = new Agent();
        agent2.setAgent_name(name);
        //        分页
        Page pag =PageHelper.startPage(page,size);
        //查询
        List<Agent> agents = agentMapper.select(agent2);
        System.out.println(agents);
        List<AgentVo> agentVos = new ArrayList<>();
        agentVos = agentsToAgentsVo(agents);
        if (CollectionUtils.isEmpty(agents)) {
            ExceptionCast.cast(MemberCode.DATA_IS_NULL);
        }
        //        解析分页结果
        PageInfo<AgentVo> pageInfo = new PageInfo<>(pag.getResult());

        return new QueryResult(agentVos, pageInfo.getTotal());


    }




    /**
     * 通过id查询客服
     * @return List<User>
     */
    public Agent getAgentById(String agent_id){
       Agent agent = new Agent();
       //
        agent.setAgent_id(agent_id);
        Agent agent1 = agentMapper.selectOne(agent);
        if(agent1 == null){
            ExceptionCast.cast(MemberCode.DATA_IS_NULL);
        }
        return agent1;
    }
    /**
     * 通过id查询客服
     * @return List<User>
     */
    public Agent getAgentByName(String agent_name){
        Agent agent = new Agent();
        //
        agent.setAgent_name(agent_name);
        Agent agent1 = agentMapper.selectOne(agent);
        if(agent1 == null){
            ExceptionCast.cast(MemberCode.DATA_IS_NULL);
        }
        return agent1;
    }

    /**
     * 通过id删除客服
     * @param agent_id
     * @return
     */
    @Transactional
    //进行增删改操作时清空缓存
    //@CacheEvict(value="AgentService",allEntries=true)
    public void deleteAgentById(String agent_id) {

        if(StringUtils.isBlank(agent_id)){
            ExceptionCast.cast(MemberCode.DATA_ERROR);
        }
        if(this.getAgentById(agent_id) == null){
            ExceptionCast.cast(MemberCode.DELETE_USER_NOT_EXIST);
        }

        //根据agent_id查询member_user表里边的user_id
        Agent agent1 = getAgentById(agent_id);
        UserVo userVo = this.userClient.getUserVoByNickname(agent1.getAgent_name());
        System.out.println(userVo);
        String userid = userVo.getUser_id();
        //调用远程服务将user表中的记录也删除
        this.userClient.deleteUserById(userid);
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
    @Transactional
    //@CacheEvict(value="AgentService",allEntries=true)
    public void insertAgent(Agent agent)  {

        User user = new User();
        //角色id
        user.setRole_id("4");
        //性别
        if (agent.getAgent_sex().equals("1")){
            user.setGender(1);
        }else{
            user.setGender(0);
        }
        //电话
        user.setUser_phone(agent.getAgent_phone());
        //地址
        user.setUser_region(agent.getAgent_from());
        //密码
        user.setPassword(agent.getAgent_password());
        //名字
        user.setUser_nickname(agent.getAgent_name());
        //时间
        user.setUser_createtime(new Date());
        //用户类型
        user.setUser_type(0);
        agent.setAgent_id(MyNumberUtils.getUUID());

        userClient.insertUser(user);
        agent.setCreate_time(new Date());
        //
        int ins = agentMapper.insert(agent);
        if( ins != 1){
            ExceptionCast.cast(MemberCode.INSERT_FAIL);
        }

    }

    /**
     * 更新客服基本信息
     *
     *
     * @param agent
     * @return int
     */
    //@CacheEvict(value="AgentService",allEntries=true)
    public void updateAgent(Agent agent){
        Example example =new Example(Agent.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("agent_id",agent.getAgent_id());
        int upd= agentMapper.updateByExampleSelective(agent,example);
        if(upd != 1){
            ExceptionCast.cast(MemberCode.UPDATE_FAIL);
        }
    }


}
