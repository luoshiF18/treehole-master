package com.document.manage.service.impl;

import com.document.manage.mapper.DocumentMapper;
import com.document.manage.mapper.FolderMapper;
import com.document.manage.mapper.TypeMapper;
import com.document.manage.pojo.Document;
import com.document.manage.pojo.EasyUIResult;
import com.document.manage.pojo.Folder;
import com.document.manage.pojo.Type;
import com.document.manage.service.SolrService;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: lee
 * @Date: 2019/11/12
 * @Description: com.lee.springboot.service.impl
 * @version: 1.0
 */
@Service
public class SolrServiceImpl implements SolrService {
    @Autowired
    private DocumentMapper documentMapper;
    @Autowired
    private FolderMapper folderMapper;
    @Autowired
    private TypeMapper typeMapper;
    @Autowired
    private SolrClient solrClient;

    @Override
    public void init() throws IOException, SolrServerException {
        List<Document> documentList = documentMapper.findDocumentByStatus(1);
        List<Folder> folderList = folderMapper.findFolderByStatus(1);
        for (Document doc:documentList){
            SolrInputDocument document = new SolrInputDocument();
            Type type = typeMapper.findTypeById(doc.getTypeId());
            document.addField("id",doc.getId());
            document.addField("doc_name",doc.getName());
            document.addField("doc_type",type.getType());
            document.addField("doc_describe",doc.getDocDescribe());
            document.addField("doc_updated",doc.getUpdateTime().getTime());
            solrClient.add(document);
            solrClient.commit();
        }
        for (Folder folder:folderList){
            SolrInputDocument document = new SolrInputDocument();
            document.addField("id",folder.getId());
            document.addField("doc_name",folder.getName());
            document.addField("doc_updated",folder.getUpdateTime().getTime());
            solrClient.add(document);
            solrClient.commit();
        }
    }

    @Override
    public EasyUIResult query(String searchType, String searchDate, String message, String describe, int page, int rows) throws IOException, SolrServerException {
        EasyUIResult easyUIResult = new EasyUIResult();
        SolrQuery query = new SolrQuery();

        //根据名称查询的关键字
        if((message!=null&&!message.equals(""))&&(describe==null||describe.equals(""))){
            query.setQuery("doc_name:"+message);
        }else if((message==null||message.equals(""))&&(describe!=null&&!describe.equals(""))){
            query.setQuery("doc_describe:"+describe);
        }else{
            query.setQuery("doc_name:"+message);
            //根据备注信息查询的关键字
            query.addFilterQuery("doc_describe:"+describe);
        }


        //是否有类型限制
        if(searchType!=null){
            query.addFilterQuery(searchType);
        }

        //是否有时间限制
        if(searchDate!=null){
            query.addFilterQuery("doc_updated:"+searchDate);
        }

        //设置分页条件
        query.setStart(rows*(page-1));
        query.setRows(rows);

        QueryResponse response = solrClient.query(query);
        SolrDocumentList list = response.getResults();
        List queryList = new ArrayList();
        for (SolrDocument document:list){
            Document doc = new Document();
            doc.setId(document.get("id").toString());
            doc.setName(document.get("doc_name").toString());
            doc.setTypeId(document.get("doc_type")==null?"文件夹":document.get("doc_type").toString());
            doc.setDocDescribe(document.get("doc_describe")==null?null:document.get("doc_describe").toString());
            queryList.add(doc);
        }
        easyUIResult.setRows(queryList);
        easyUIResult.setTotal((int) list.getNumFound());
        return easyUIResult;
    }

    @Override
    public void add_OR_update(String id,String name,String type,String describe,long date) throws IOException, SolrServerException {
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id",id);
        document.addField("doc_name",name);
        if(type!=null){
            document.addField("doc_type",type);
        }
        if(describe!=null){
            document.addField("doc_describe",describe);
        }
        document.addField("doc_updated",date);
        solrClient.add(document);
        solrClient.commit();
    }

    @Override
    public void delete(String id) throws IOException, SolrServerException {
        solrClient.deleteById(id);
        solrClient.commit();
    }

}
