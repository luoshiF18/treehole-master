package com.treehole.evaluation.service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.treehole.evaluation.MyUtils.MyNumberUtils;
import com.treehole.evaluation.client.UserClient;
import com.treehole.evaluation.dao.ScaleMapper;
import com.treehole.evaluation.dao.WarningInterveneMapper;
import com.treehole.evaluation.dao.WarningMapper;
import com.treehole.framework.domain.evaluation.Scale;
import com.treehole.framework.domain.evaluation.Warning;
import com.treehole.framework.domain.evaluation.request.PieData;
import com.treehole.framework.domain.evaluation.request.WarnRequest;
import com.treehole.framework.domain.evaluation.response.EvaluationCode;
import com.treehole.framework.domain.evaluation.vo.WarnHUserVo;
import com.treehole.framework.domain.evaluation.vo.WarnReportVo;
import com.treehole.framework.domain.evaluation.vo.WarningVo;
import com.treehole.framework.domain.member.Vo.UserVo;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.framework.model.response.ResponseResult;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: Qbl
 * Created by 9:30 on 2019/10/17.
 * Version: 1.0
 */
@Service
@AllArgsConstructor
public class WarningService {


    private final WarningMapper warningMapper;
    private final ScaleMapper scaleMapper;
    private final UserClient userClient;
    private final WarningInterveneMapper warningInterveneMapper;



    //心理咨询师添加咨询用户的预警信息
    @Transactional
    public ResponseResult addWarningByPsy(Warning warning) {
        //得到预警对象,判断对象里用户
        if (warning == null) {
            ExceptionCast.cast( CommonCode.FAIL );
        }
        try {
            warning.setId( MyNumberUtils.getUUID() );
            warning.setUserId( "null" );//从哪里得到用户id???
            warning.setCreateTime( new Date() );
            warning.setScaleId( "null" );//得到咨询师让用户做的那张量表id
            warning.setStatus( 1 );  //咨询师给出的预警信息状态码为1
            warningMapper.insert( warning );
            return ResponseResult.SUCCESS();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //根据用户id查询用户的预警信息，分页查询
    public QueryResponseResult selectWarningByUserId(String userId, int page, int size) {
        //TODO 只限后台管理查询 ，心理咨询师查看待写
        if (userId == null || StringUtils.isEmpty( userId )) {
            ExceptionCast.cast( EvaluationCode.DATA_ERROR );
        }
        Warning warning = new Warning();
        warning.setUserId(userId);
        //设置分页参数
        if (page <= 0) {
            page = 1;
        }
        page = page - 1;
        if (size <= 0) {
            size = 10;
        }
        try {
            PageHelper.startPage( page, size );
            List<Warning> warnings = warningMapper.select( warning );
            PageInfo<Warning> warningPageInfo = new PageInfo<Warning>( warnings );
            QueryResult queryResult = new QueryResult<>();
            queryResult.setList( warningPageInfo.getList() ); //数据列表
            queryResult.setTotal( warningPageInfo.getTotal() ); //总记录数
            QueryResponseResult queryResponseResult = new QueryResponseResult( CommonCode.SUCCESS, queryResult );
            return queryResponseResult;
        } catch (RuntimeException e) {
            ExceptionCast.cast( EvaluationCode.SELECT_NULL );
            return null;
        }
    }

    //分页查询数据库中所有预警信息
    public QueryResponseResult findAll(int page,int size) {
        //设置分页参数
        PageHelper.startPage(page,size);
        //得到量表名称和预警信息
        List<WarningVo> scaleWarning = warningMapper.findScaleWarning();
        //遍历得到userId
        List listUserId = new ArrayList();
        for (WarningVo warningVo : scaleWarning) {
            listUserId.add(warningVo.getUserId());
        }
        //远程调用得到用户信息
        List<UserVo> allUser = userClient.getAllUser( listUserId );
        if (allUser.get(0)!=null||scaleWarning.get(0)!=null) {
            for (UserVo userVo : allUser) {
                for (WarningVo warningVo : scaleWarning) {
                    if (userVo.getUser_id().equals(warningVo.getUserId())) {
                        if(userVo.getGender()==null||userVo.getUser_birth()==null){
                            warningVo.setSex(userVo.getGender());
                            warningVo.setUserBirth(null);
                        }

                        warningVo.setUserName( userVo.getUser_name() );
                        warningVo.setUserNickName( userVo.getUser_nickname() );
                    }
                }
            }
        }else {
            ExceptionCast.cast(EvaluationCode.DATA_ERROR);
            return new QueryResponseResult(CommonCode.FAIL,null);
        }
        PageInfo<WarningVo> warningVoPageInfo = new PageInfo<WarningVo>(scaleWarning);
        QueryResult<WarningVo> queryResult = new QueryResult<>();
        queryResult.setTotal(warningVoPageInfo.getTotal());
        queryResult.setList(warningVoPageInfo.getList());
        QueryResponseResult queryResponseResult = new QueryResponseResult( CommonCode.SUCCESS, queryResult );
        return queryResponseResult;
    }

    //后台管理条件查询，根据量表名称、用户名、预警等级综合分页查询预警信息
    public QueryResponseResult findWarningCondition(int page,int size,WarnRequest warnRequest) {
        if(warnRequest==null){
            return  this.findAll(page, size);
        }
        //根据传入的条件查询出用户的预警信息
        if(warnRequest.getUserNickName()!=null
           ||StringUtils.isNotEmpty(warnRequest.getUserNickName())){
            UserVo userVoByNickname = userClient.getUserVoByNickname( warnRequest.getUserNickName());
            warnRequest.setUserId(userVoByNickname.getUser_id());
        }
        PageHelper.startPage(page,size);
        System.out.println(warnRequest);
        List<WarningVo> warning = warningMapper.getWaning(warnRequest);
        if(warning.size()==0){
            return null;
        }
        List listUserId = new ArrayList();
        for (WarningVo vo : warning) {
            listUserId.add(vo.getUserId());
        }
        List<UserVo> allUser = userClient.getAllUser(listUserId);
        for (WarningVo warningVo : warning) {
            for (UserVo userVo : allUser){
                if(warningVo.getUserId().equals(userVo.getUser_id())){
                    warningVo.setUserName(userVo.getUser_name() );
                    warningVo.setUserNickName(userVo.getUser_nickname());
                    if(userVo.getGender()!=null){
                        warningVo.setSex(userVo.getGender());
                    }
                }
            }
        }
        PageInfo<WarningVo> warningVoPageInfo = new PageInfo<WarningVo>(warning);
        QueryResult<WarningVo> queryResult = new QueryResult<>();
        queryResult.setTotal(warningVoPageInfo.getTotal());
        queryResult.setList(warningVoPageInfo.getList());
        QueryResponseResult queryResponseResult = new QueryResponseResult( CommonCode.SUCCESS, queryResult );
        return queryResponseResult;
    }

    //查看预警详细信息
    public WarnReportVo lookWaring(String warningId) {
        //根据id查询出预警信息
        if (warningId == null || StringUtils.isEmpty( warningId )) {
            ExceptionCast.cast( EvaluationCode.DATA_ERROR );
        }
        try {
            Warning warning = warningMapper.selectByPrimaryKey( warningId );
            Scale scale = scaleMapper.selectByPrimaryKey( warning.getScaleId());
            UserVo userVo = userClient.getUserVoByUserId( warning.getUserId() );
            WarnReportVo warnReportVo = new WarnReportVo();
            //存入用户基本信息
            warnReportVo.setUserNickName(userVo.getUser_nickname() );
            warnReportVo.setUserName(userVo.getUser_name());
            warnReportVo.setScaleName(scale.getScaleName() );
            if(userVo.getUser_birth()!=null){
                warnReportVo.setUserBirth(userVo.getUser_birth() );
            }
            else {
                warnReportVo.setUserBirth(null);
            }
            if(userVo.getGender()!=null){
                warnReportVo.setSex(userVo.getGender());
            }
            warnReportVo.setPhone(userVo.getUser_phone());
            warnReportVo.setUserEmail( userVo.getUser_email() );
            warnReportVo.setUserRegion(userVo.getUser_region() );
            //存入量表信息
            warnReportVo.setScaleName(scale.getScaleName());
            warnReportVo.setTopicDescription( scale.getTopicDescription());
            warnReportVo.setScaleFunction(scale.getScaleFunction());
            //存入预警信息
            warnReportVo.setStatus(warning.getStatus());
            warnReportVo.setWarningLevel(warning.getWarningLevel() );
            warnReportVo.setWMessage(warning.getWMessage());
            warnReportVo.setCreateTime(warning.getCreateTime());
            return warnReportVo;
        }catch (Exception e)
        {
            e.printStackTrace();
            return  null;
        }
    }

    //根据用户id删除用户的预警记录
    public ResponseResult deleteWarning(String warningId) {
        if (warningId == null || StringUtils.isEmpty( warningId )) {
            ExceptionCast.cast( EvaluationCode.DATA_ERROR );
        }
        try {
            warningMapper.deleteByPrimaryKey( warningId );
            return ResponseResult.SUCCESS();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    //批量删除预警记录
    public ResponseResult deleteMoreWarning(List<String> ids) {
        if (ids == null) {
            ExceptionCast.cast( CommonCode.FAIL );
        }
        try {
            warningMapper.deleteMoreWarning(ids);
            return ResponseResult.SUCCESS();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    //根据量表名称得到量表id
    private String getScaleId(String scaleName) {
        if (scaleName==null||StringUtils.isEmpty(scaleName)){
            return null;
        }
        Scale scale = new Scale();
        scale.setScaleName(scaleName);
        Scale scaleOne = scaleMapper.selectOne( scale );
        return scaleOne.getId();
    }
    //根据用户名称得到用户信息
    private UserVo getUsersByName(String userNickNmae)
    {
        if(userNickNmae!=null&&StringUtils.isNotEmpty(userNickNmae )){
            UserVo userVoByNickname = userClient.getUserVoByNickname( userNickNmae );
            return userVoByNickname;
        }
        else {
            return  null;
        }
    }
    //根据用户id的list得到用户信息
    private List<UserVo> getUsersByUserId(List listUserId){
        if(listUserId.get( 0 )!=null){
            List<UserVo> allUser = userClient.getAllUser( listUserId );
            return allUser;
        }
        else {
            return null;}
    }
    //拼接饼状图数据返回给前台
    public String getPieData(String userNickName) {
        //根据用户名得到该用户id
        UserVo userVoByNickname = userClient.getUserVoByNickname( userNickName );
        if(userVoByNickname==null){
            //用户不存在，先返回Null
            return  null;
        }
        //得到用户id，去查询该用户在预警表产生的所有预警信息，关联查询量表得到量表信息
        String uid=userVoByNickname.getUser_id();
        List<PieData> pieData = warningMapper.getPieData(uid);
        //还可以拼接咨询师给他服务的次数，拿到咨询师服务的简要内容
        //将list<PieData> 转换为json格式 返给前端
        String data = JSON.toJSONString(pieData);
        System.out.println(data);
        return data;
    }

    //得到量表饼状图数据
    public String getPieScaData(String scaleName){
        //根据输出的量表名称得到数据
        List<PieData> pieScaData = warningMapper.getPieScaData( scaleName );
        if(pieScaData==null||pieScaData.size()<0){
            return null;
        }
        System.out.println(pieScaData);
        //遍历list，取出预警等级并且为其赋值,先硬编码
        for (PieData pieScaDatum : pieScaData) {
            switch (pieScaDatum.getName()){
                case "1":
                    pieScaDatum.setName("正常");
                    break;
                case "2":
                    pieScaDatum.setName("关注");
                    break;
                case "3":
                    pieScaDatum.setName("追踪");
                    break;
                case "4":
                    pieScaDatum.setName("严重");
                    break;
                case "5":
                    pieScaDatum.setName("警戒");
                    break;
                    default:break;
            }
        }
        System.out.println(pieScaData);
        //将集合转化为JSON格式 返回给前端
        String data=JSON.toJSONString(pieScaData);
        System.out.println(data);
        return  data;
    }
    //得到用户最常做量表类型的数据
    public String getUserPieData(String userNickName){
        //远程调用用户服务根据用户登录名得到用户id
        UserVo usersByName = this.getUsersByName( userNickName );
        String uid=usersByName.getUser_id();
        //从数据库得到数据
        List<PieData> userPieData = warningMapper.getUserPieData( uid );
        if(userPieData==null){
            return null;
        }
        //得到最多的类型
       /* Optional<PieData> userPie=userPieData.stream().filter( Objects::nonNull).max( Comparator.comparingInt(PieData ::getValue));
            PieData maxPie=userPie.get();
        System.out.println(maxPie);*/

        //转换为json格式便于前台接收
        String data=JSON.toJSONString(userPieData);


            return data;
    }

    //分页查询高危人群
    public QueryResponseResult findHighRisk(int page,int size,String userNickName){

        if(page==0){
            ExceptionCast.cast( EvaluationCode.SELECT_NULL);
        }
        //根据请求条件得到高危人群的预警信息和干预记录
        List<WarnHUserVo> highRisk = warningMapper.findHighRisk(userNickName);
        System.out.println("测试数据"+highRisk);
        //从结果中得到用户id，查询用户信息
        List<String> users = new ArrayList<>();
        for (WarnHUserVo user : highRisk) {
            users.add(user.getUserId());
        }
        //远程调用用户接口得到用户信息
        List<UserVo> allUser = userClient.getAllUser( users );
        //循环拼接用户信息
        for (WarnHUserVo user : highRisk) {
           for (UserVo userVo : allUser){
               if(user.getUserId().equals( userVo.getUser_id())){
                   user.setUserName(userVo.getUser_name() );
                   user.setUserNickName(userVo.getUser_nickname() );
                   user.setSex(userVo.getGender() );
                   user.setPhone( userVo.getUser_phone() );
               }
           }
        }
        //设置分页，展示数据
        PageHelper.startPage(page,size);
        PageInfo<WarnHUserVo> highRisks = new PageInfo<WarnHUserVo>(highRisk);
        QueryResult<WarnHUserVo> queryResult = new QueryResult<>();
        queryResult.setTotal(highRisks.getTotal());
        queryResult.setList(highRisks.getList());
        QueryResponseResult queryResponseResult = new QueryResponseResult( CommonCode.SUCCESS, queryResult );
        return queryResponseResult;
    }

    //查看预警信息详情
    public WarnHUserVo lookDetailHWarn(String warnHUserid){
        if(warnHUserid==null||StringUtils.isBlank(warnHUserid)){
            ExceptionCast.cast(CommonCode.FAIL);
        }
        try {
            return warningMapper.warnHDetail( warnHUserid );
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
