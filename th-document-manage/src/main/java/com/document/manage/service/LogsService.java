package com.document.manage.service;

import com.document.manage.pojo.EasyUIResult;

/**
 * @Auther: lee
 * @Date: 2019/11/16
 * @Description: com.lee.springboot.service
 * @version: 1.0
 */
public interface LogsService {
    //操作记录
    void logs(String docId, int action, int type, String userId);
    //操作记录展示
    EasyUIResult showLogs(int page, int rows);
}
