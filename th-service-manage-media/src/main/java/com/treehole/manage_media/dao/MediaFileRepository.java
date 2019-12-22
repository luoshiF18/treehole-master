package com.treehole.manage_media.dao;

import com.treehole.framework.domain.onlineCourse.MediaFile;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MediaFileRepository extends JpaRepository<MediaFile,String> {
}
