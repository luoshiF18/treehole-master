package com.treehole.auth.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;

/**
 * @Author: Qbl
 * Created by 19:30 on 2019/11/12.
 * Version: 1.0
 */
@FeignClient(value = "th-treehole-member-auth")
@Repository
public interface UserClient {


}
