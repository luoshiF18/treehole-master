package com.treehole.evaluation.dao;

import com.treehole.framework.domain.evaluation.Warning;
import com.treehole.framework.domain.evaluation.request.PieData;
import com.treehole.framework.domain.evaluation.request.WarnRequest;
import com.treehole.framework.domain.evaluation.vo.WarnHUserVo;
import com.treehole.framework.domain.evaluation.vo.WarningVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @Author: Qbl
 * Created by 14:24 on 2019/10/14.
 * Version: 1.0
 */
@Repository
public interface WarningMapper extends Mapper<Warning> {

    void deleteMoreWarning(List<String> ids);
    List<WarningVo> findScaleWarning();
    List<WarningVo> getWaning(@Param("request" ) WarnRequest request);

    //得到用户饼状图数据
    List<PieData> getPieData(String uid);
    //得到量表饼状图数据
    List<PieData> getPieScaData(String scaleName);
    //从结果表中得到用户所做的所有量表的类型的百分比数据
    List<PieData> getUserPieData(String uid);
    //查询高危人群
    List<WarnHUserVo> findHighRisk(@Param("userNickName") String userNickName,
                                   @Param("userIds")List<String> userIds);
    //高危人群详情
    WarnHUserVo warnHDetail(String warnHUserid);
    //根据listUserID查询用户的预警信息
     List<Warning> findWarnByUserId(List<String> userId);
     //心理咨询师查看用户预警信息
    List<WarningVo> fingAllByPsy(String userId);


}
