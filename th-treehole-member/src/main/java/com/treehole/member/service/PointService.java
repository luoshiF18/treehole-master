package com.treehole.member.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.treehole.framework.domain.member.Points;
import com.treehole.framework.domain.member.User;
import com.treehole.framework.domain.member.result.MemberCode;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.member.mapper.PointsMapper;
import com.treehole.member.mapper.UserMapper;
import com.treehole.member.myUtil.MyNumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
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
    public QueryResult findAllPoints(Integer page, Integer size){
//        分页
        PageHelper.startPage(page, size);
        //查询
        List<Points> points = pointsMapper.selectAll();
        if (CollectionUtils.isEmpty(points)) {
            ExceptionCast.cast(MemberCode.DATA_IS_NULL);
        }
        //        解析分页结果
        PageInfo<Points> pageInfo = new PageInfo<>(points);

        return new QueryResult(points, pageInfo.getTotal());
    }

    /**
     * 根据id查询积分信息
     *
     * @param points_id
     * @return List<Points>
     */

    public List<Points> getPointById(String points_id){
        Points point = new Points();
        point.setPoints_id(points_id);
        List<Points> points = pointsMapper.select(point);
        return points;
    }
    /**
     * 插入一条积分信息
     * 会员注册后立即调用(会员)
     * 积分插入的步骤：
     *   先查询user表中的pointnow付给before，后修改
     *   记录表需要4个参数： 1.before ：从user表里通过userid获取
     *            2.num  ：从前端获取
     *            3.after    ：before+num
     *            4.userid   ：从前端获取
     *   最后:user表内的pointnow = after;   updateUserById() //
     *
     *
     *
     * @param
     * @return int
     */

    public int insertPoint(Points points)  {
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
    public int deletePointById(String points_id) {
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
    public int updatePoint(Points points) {
        Example example = new Example(Points.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("points_id",points.getPoints_id());
        int upd = pointsMapper.updateByExampleSelective(points, example);
        return upd;
    }
}
