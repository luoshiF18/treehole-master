package com.treehole.psychologist.controller;

import com.treehole.api.psychologist.DetailControllerApi;
import com.treehole.framework.domain.psychologist.Detail;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.psychologist.service.DetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author Helay
 * @date 2019/11/16 9:14
 */
@RestController
@RequestMapping("/psychologist/detail")
public class DetailController implements DetailControllerApi {

    @Autowired
    private DetailService detailService;

    /**
     * 根据咨询师id查询咨询师详情信息
     *
     * @param psychologist_id 咨询师id
     * @return
     */
    @Override
    @GetMapping("/get/{psychologist_id}")
    public Detail getDetailById(@PathVariable("psychologist_id") String psychologist_id) {
        return this.detailService.getDetailById(psychologist_id);
    }

    /**
     * 根据咨询师id更新其详情信息
     *
     * @param detail 详情信息
     * @return
     */
    @Override
    @PutMapping("/update")
    public ResponseResult updateDetail(@RequestBody Detail detail) {
        return this.detailService.updateDetail(detail);
    }

    /**
     * 根据咨询师姓名查询咨询师详情信息
     *
     * @param psychologist_name 咨询师姓名
     * @return
     */
    @Override
    @GetMapping("/get/all")
    public QueryResponseResult getDetailByName(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "5") Integer size,
            @RequestParam(value = "psychologist_name", required = false) String psychologist_name
    ) {
        return this.detailService.getDetailByName(page, size, psychologist_name);
    }

    /**
     * 查询所有咨询师的姓名
     *
     * @return
     */
    @Override
    @GetMapping("/get/names")
    public QueryResponseResult getPsychologistNames() {
        return this.detailService.getPsychologistNames();
    }

    /**
     * 查询所有咨询师的好评数
     *
     * @return
     */
    @Override
    @GetMapping("/get/praises")
    public QueryResponseResult getPraiseNumber() {
        return this.detailService.getPraiseNumber();
    }

    /**
     * 获取当前日期开始过去一年的日期
     *
     * @return
     */
    @Override
    @GetMapping("/get/month")
    public List<String> getMonthBeforeYear() {
        Calendar calendar = Calendar.getInstance();//获取日期实例，默认为当前日期，例如：2020-2-20
        calendar.add(Calendar.MONTH, -12);//获得当前日期之前第12个月的日期，例如：2019-2-20
        List<String> months = new ArrayList<>();//构建集合用来存放日期
        for (int i = 0; i < 12; i++) {
            //获得当前日期之后1个月的日期，第一次遍历得到2019-3-20
            calendar.add(Calendar.MONTH, 1);
            months.add(new SimpleDateFormat("yyyy.MM").format(calendar.getTime()));
        }
        return months;
    }

    /**
     * 根据月份查询当月咨询师总人数
     *
     * @return
     */
    @Override
    @GetMapping("/get/number")
    public List<Integer> findMemberCountByMonth() {
        List<String> months = this.getMonthBeforeYear();
        return this.detailService.findMemberCountByMonth(months);
    }
}
