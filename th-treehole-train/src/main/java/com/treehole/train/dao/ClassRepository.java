package com.treehole.train.dao;

import com.treehole.framework.domain.train.Class;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClassRepository extends JpaRepository<Class,String> {

    //得到这一期的班级
    List<Class> findByClassPhase(String phaseId);

}
