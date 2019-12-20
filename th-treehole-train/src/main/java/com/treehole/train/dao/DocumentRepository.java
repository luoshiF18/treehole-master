package com.treehole.train.dao;

import com.treehole.framework.domain.train.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document,String> {



}
