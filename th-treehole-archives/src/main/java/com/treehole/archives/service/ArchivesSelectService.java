package com.treehole.archives.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.treehole.archives.client.ScaleSelectClient;
import com.treehole.archives.client.UserVoClient;
import com.treehole.archives.dao.ArchivesResultMapper;
import com.treehole.framework.domain.archives.ext.ResuAnswer;
import com.treehole.framework.domain.archives.ext.ResultBase;
import com.treehole.framework.domain.archives.ext.ResultExt;
import com.treehole.framework.domain.archives.ext.ResultList;
import com.treehole.framework.domain.archives.resquest.ResultListRequest;
import com.treehole.framework.domain.evaluation.Result;
import com.treehole.framework.domain.evaluation.response.DetailResult;
import com.treehole.framework.domain.evaluation.response.UserOptionResult;
import com.treehole.framework.domain.evaluation.vo.ScaleDetailVO2;
import com.treehole.framework.domain.evaluation.vo.UserOptionVO;
import com.treehole.framework.domain.member.Vo.UserVo;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author 任志强
 * @version 1.0
 * @date 2019/10/22 9:01
 * @description: 个体报告业务处理层
 */
@Service
public class ArchivesSelectService {

    @Autowired
    private ScaleSelectClient scaleSelectClient;

    @Autowired
    private UserVoClient userVoClient;

    @Autowired
    private ArchivesResultMapper archivesResultMapper;

    /**
     * 查询出个体报告的列表(需要,昵称,姓名,量表名称,提交时间)
     * @param page
     * @param size
     * @return
     */
    public QueryResponseResult findArchivesList(
            int page,
            int size,
            ResultListRequest resultListRequest
    ) {
        //判断请求条件的合法性
        if (resultListRequest == null){
            resultListRequest = new ResultListRequest();
        }
        //判断页码是否合法
        if (page < 0){
            page = 0;
        }
        //判断size是否合法,默认值为8页
        if (size <= 0){
            size = 8;
        }
        //根据用户昵称进行搜索用户信息
        if (StringUtils.isNotEmpty(resultListRequest.getUser_nickname())){
            //如果用户昵称不为空时搜索出用户信息,将用户id添加至查询对象中
            UserVo userVoByNickname = userVoClient.getUserVoByNickname(resultListRequest.getUser_nickname());
            if (userVoByNickname == null){
                ExceptionCast.cast(CommonCode.INVALID_PARAM);  //查询不到用户数据
            }
            resultListRequest.setUserId(userVoByNickname.getUser_id());
        }
        //设置分页
        PageHelper.startPage(page,size);
        //进行查询
        Page<Result> allResult = archivesResultMapper.findAll(resultListRequest);
        if (allResult == null){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        List<Result> results = allResult.getResult();
        //将allResult转成ArchivesList
        List<ResultList> resultLists = new ArrayList<>();
        for (Result result : results){
            ResultList resultList = new ResultList();
            //设置result_id
            resultList.setId(result.getId());
            //设置用户id
            resultList.setUserId(result.getUserId());
            //根据用户id查询用户信息
            UserVo userVo = findUserById(result.getUserId());
            //设置用户名
            resultList.setUserName(userVo.getUser_name());
            //设置用户昵称
            resultList.setUser_nickname(userVo.getUser_nickname());
            //设置用户角色
            resultList.setRole_name(userVo.getRole_name());
            //设置量表名称
            resultList.setScaleName(result.getScaleName());
            //设置创建时间
            resultList.setCreate_date(result.getCreateTime());
            //将archivesList对象添加到集合中
            resultLists.add(resultList);
        }
        QueryResult queryResult = new QueryResult();
        queryResult.setList(resultLists);
        queryResult.setTotal(allResult.getTotal());
        return new QueryResponseResult(CommonCode.SUCCESS,queryResult);
    }

    /**
     * 根据用户id查询用户信息
     * @param userId
     * @return
     */
    private UserVo findUserById(String userId){
        UserVo userVoByUserId = userVoClient.getUserVoByUserId(userId);
        return userVoByUserId;  //上层接口已做了空值判定
    }

    /**
     * 根据个体报告id查询个体报告的详细内容
     * @param resultId 个体报告id
     * @return
     */
    public ResultExt findResultExtReport(String resultId) {
        //根据个体报告id查询出结果信息
        ResultBase result = archivesResultMapper.findResultById(resultId);
        if (result == null){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        //创建一个完整的个体报告结果
        ResultExt resultExt = new ResultExt();
        //将resultbase拷贝到resultExt中
        BeanUtils.copyProperties(result,resultExt);
        //获取到用户id
        String userId = result.getUserId();
        //根据用户id查询用户数据
        UserVo userVo = findUserById(userId);
        if (userVo == null){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);  //此处应该是找不到用户异常,然而没有定义
        }
        //将用户中的数据拷贝到resultExt中
        BeanUtils.copyProperties(userVo,resultExt);

        //构建量表的基本信息
        //根据量表id(名称)查询量表的基本信息
        DetailResult scaleDetailRes = scaleSelectClient.findScaleDetail(null, result.getScaleName());
        ScaleDetailVO2 scaleDetail = scaleDetailRes.getScaleDetail();
        //设置量表的描述信息
        resultExt.setTopicBackground(scaleDetail.getTopicBackground());
        //设置主题问题建议
        resultExt.setTopicSuggest(scaleDetail.getTopicSuggest());
        //构建选项列表
        UserOptionResult userOption = scaleSelectClient.findUserOption(result.getScaleName(), result.getUserId());
        //设置选项题
        UserOptionVO userOptionVO = userOption.getUserOptionVO();
        Map<String,List<String>> userOptionVOResult = userOptionVO.getResult();

        Set<Map.Entry<String, List<String>>> entrySet = userOptionVOResult.entrySet();
        List<ResuAnswer> resuAnswers = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry: entrySet){
            ResuAnswer resuAnswer = new ResuAnswer();
            String key = entry.getKey();
            List<String> value = entry.getValue();
            String[] split = key.split("：");
            String resuValue = value.toString();
            resuAnswer.setSort(split[0]);       //设置题号
            resuAnswer.setQuestion(split[1]);  //设置问题
            resuAnswer.setAnswer(resuValue);  //设置回答
            resuAnswers.add(resuAnswer);
        }

        //设置选项答案
        resultExt.setQuestionAndOption(resuAnswers);

        //这里先将健康等级设置为1级
        resultExt.setHeal_level("一级");
        //将个体报告进行返回
        return resultExt;
    }
}
