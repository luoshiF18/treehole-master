package com.document.manage.controller;

import com.document.manage.service.SolrService;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

/**
 * @Auther: lee
 * @Date: 2019/11/12
 * @Description: com.lee.springboot.controller
 * @version: 1.0
 */
@Controller
public class SolrController {
    @Autowired
    private SolrService solrServiceImpl;

    /**
     * solr初始化
     * @return
     */
    @RequestMapping("init")
    public String init(){
        try {
            solrServiceImpl.init();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
        return "index";
    }

}
