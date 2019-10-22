package com.treehole.member.service;

import com.treehole.framework.domain.member.Points;
import com.treehole.framework.domain.member.User;
import com.treehole.member.mapper.PointsMapper;
import com.treehole.member.mapper.UserMapper;
import com.treehole.member.myUtil.MyNumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * @author shanhuijie
 * @Description: 积分信息服务
 * @Date
 */
@Service
public class PointService {
    @Autowired
    private PointsMapper pointsMapper;


    /**
     * 查询所有积分信息
     *
     * @param
     * @return List<Points>
     */
    public List<Points> findAllPoints() throws Exception{

        List<Points> points = pointsMapper.selectAll();
        return points;
    }

    /**
     * 根据id查询积分信息
     *
     * @param points_id
     * @return List<Points>
     */

    public List<Points> getPointById(String points_id) throws Exception{
        Points point = new Points();
        point.setPoints_id(points_id);
        List<Points> points = pointsMapper.select(point);
        return points;
    }
    /**
     * 插入一条积分信息
     * 会员注册后立即调用
     *
     * @param
     * @return int
     */

    public int insertPoint(Points points) throws Exception {
        points.setPoints_id(MyNumberUtils.getUUID());
        points.setPoints_time(new Date());
        points.setPoints_num(0);
        points.setPoints_later(0);
        points.setPoints_cumulate(0);
        points.setPoints_clear(0);
        //System.out.println("+++++++++"+ user.getUniq_id());
        int ins = pointsMapper.insert(points);

        return ins;
    }

    /**
     * 根据id删除积分信息
     * 暂时不用
     *
     * @param points_id
     * @return int
     */
    public int deletePointById(String points_id) throws Exception{
        //List<Points> points = this.getPointById(points_id);
        Points point = new Points();
        point.setPoints_id(points_id);
        int del = pointsMapper.delete(point);
        return del;
    }

    /**
     * 更新积分信息
     *
     *
     * @param points
     * @return int
     */
    public int updatePoint(Points points) throws Exception{
        Example example = new Example(Points.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("points_id",points.getPoints_id());
        int upd = pointsMapper.updateByExampleSelective(points, example);
        return upd;
    }
}
