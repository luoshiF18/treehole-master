package com.treehole.train.dao;

import com.treehole.framework.domain.train.Class;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassRepository extends JpaRepository<Class,String> {

}
