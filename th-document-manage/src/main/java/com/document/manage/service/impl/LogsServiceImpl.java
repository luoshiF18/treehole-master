package com.document.manage.service.impl;

import com.document.manage.mapper.LogsMapper;
import com.document.manage.pojo.EasyUIResult;
import com.document.manage.pojo.Logs;
import com.document.manage.service.LogsService;
import com.document.manage.utils.IDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Auther: lee
 * @Date: 2019/11/16
 * @Description: com.lee.springboot.service.impl
 * @version: 1.0
 */
@Service
public class LogsServiceImpl implements LogsService {
    @Autowired
    private LogsMapper logsMapper;
    @Override
    public void logs(String docId,int action,int type,String userId) {
        Date date = new Date();
        Logs logs = new Logs();
        logs.setId(IDUtils.genImageName());
        logs.setAction(action);
        logs.setDocId(docId);
        logs.setType(type);
        logs.setUserId(userId);
        logs.setDate(date);
        logsMapper.save(logs);
    }

    @Override
    public EasyUIResult showLogs(int page, int rows) {
        EasyUIResult result = new EasyUIResult();
        result.setRows(logsMapper.findAllByPage(rows * (page - 1) == 0 ? 0 : rows * (page - 1) - 1, rows));
        result.setTotal(logsMapper.findAll().size());
        return result;
    }
}
