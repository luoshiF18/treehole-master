package com.document.manage.service;

import com.document.manage.pojo.EasyUIResult;
import org.apache.solr.client.solrj.SolrServerException;

import java.io.IOException;

/**
 * @Auther: lee
 * @Date: 2019/11/12
 * @Description: com.lee.springboot.service
 * @version: 1.0
 */
public interface SolrService {
    /**
     * 初始化solr索引
     * @throws IOException
     * @throws SolrServerException
     */
    void init() throws IOException, SolrServerException;

    /**
     * 查询
     * @param searchType
     * @param searchDate
     * @param message
     * @return
     * @throws IOException
     * @throws SolrServerException
     */
    EasyUIResult query(String searchType, String searchDate, String message, String describe, int page, int rows) throws IOException, SolrServerException;

    /**
     * 添加或修改
     * @param id
     * @param name
     * @param type
     * @param date
     * @throws IOException
     * @throws SolrServerException
     */
    void add_OR_update(String id, String name, String type, String describe, long date) throws IOException, SolrServerException;

    /**
     * 删除
     * @param id
     * @throws IOException
     * @throws SolrServerException
     */
    void delete(String id) throws IOException, SolrServerException;

}
