package com.treehole.framework.domain.onlineCourse;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;


@Data
@ToString
public class CourseSearchParam implements Serializable {
    //关键字
    String keyword;
    //一级分类
    String mt;
    //二级分类
    String st;

    //难度等级
    String grade;

    //价格区间
    Float price_min;
    Float price_max;

    //排序字段
    String sort;
    //过虑字段
    String filter;

}
