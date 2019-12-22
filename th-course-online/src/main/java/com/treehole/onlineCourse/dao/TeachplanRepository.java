package com.treehole.onlineCourse.dao;

import com.treehole.framework.domain.onlineCourse.Teachplan;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.print.DocFlavor;
import java.util.List;

/**
 * @author cc
 * @date 2019/10/24 15:18
 */
public interface TeachplanRepository extends JpaRepository<Teachplan,String> {
    //定义方法根据课程id和父节点id查询出节点列表，可以使用此方法实现查询根节点
    List<Teachplan> findByCourseidAndParentid(String courseid, String s);

    //定义方法，根据课程父节点id查询出所有子节点
    List<Teachplan> findByParentid(String parentid);
}
