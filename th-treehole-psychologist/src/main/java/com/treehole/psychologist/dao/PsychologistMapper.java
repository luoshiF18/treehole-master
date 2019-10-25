package com.treehole.psychologist.dao;

import com.treehole.framework.domain.psychologist.Psychologist;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author Helay
 * @date 2019/10/25 9:42
 */
@Repository
public interface PsychologistMapper extends Mapper<Psychologist> {
}
