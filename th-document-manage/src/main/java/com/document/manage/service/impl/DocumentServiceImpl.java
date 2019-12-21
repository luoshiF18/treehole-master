package com.document.manage.service.impl;

import com.document.manage.mapper.DocumentMapper;
import com.document.manage.pojo.Document;
import com.document.manage.pojo.Type;
import com.document.manage.service.*;
import com.document.manage.utils.FtpUtil;
import com.document.manage.utils.IDUtils;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Auther: lee
 * @Date: 2019/10/26
 * @Description: com.lee.springboot.service.impl
 * @version: 1.0
 */
@Service
public class DocumentServiceImpl implements DocumentService {

    @Value("${vsftpd.host}")
    private String host;
    @Value("${vsftpd.port}")
    private int port;
    @Value("${vsftpd.username}")
    private String username;
    @Value("${vsftpd.password}")
    private String password;
    @Value("${vsftpd.basePath}")
    private String basePath;
    @Value("${vsftpd.filepath}")
    private String filepath;

    @Autowired
    private DocumentMapper documentMapper;
    @Autowired
    private FolderService folderServiceImpl;
    @Autowired
    private TypeService typeServiceImpl;
    @Autowired
    private LogsService logsServiceImpl;
    @Autowired
    private SolrService solrServiceImpl;

    //根据文件夹id返回文档
    @Override
    public List<Document> showAllDocByFolder(String folderId, Integer status) {
        return documentMapper.findByStatusAndFolderId(status,folderId);
    }

    //文档上传
    @Override
    public List<Document> upload(MultipartFile[] file, String folderId, String describe, String userId) {
        List<Document> list = new ArrayList();
        if(folderId.equals("1")){
            for (int i = 0; i < file.length; i++) {
                String id = IDUtils.genDocumentId();
                String name = file[i].getOriginalFilename().substring(0, file[i].getOriginalFilename().lastIndexOf("."));
                Type type = typeServiceImpl.newType(file[i].getOriginalFilename());
                String genImageName = id+file[i].getOriginalFilename().substring(file[i].getOriginalFilename().lastIndexOf("."));
                boolean result = false;
                try {
                    result = FtpUtil.uploadFile(host, port, username, password, basePath, filepath, genImageName, file[i].getInputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(result){
                    Date date = new Date();
                    Document doc = new Document();
                    doc.setId(id);
                    doc.setName(name);
                    doc.setTypeId(type.getId());
                    doc.setUrl("http://"+host+"/"+genImageName);
                    doc.setFolderId(folderId);
                    doc.setStatus(1);
                    doc.setCreateTime(date);
                    doc.setUpdateTime(date);
                    doc.setUploadId(userId);
                    doc.setDocDescribe(describe);
                    try {
                        list.add(documentMapper.save(doc));
                        solrServiceImpl.add_OR_update(id,id,type.getType(),describe,date.getTime());
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (SolrServerException e) {
                        e.printStackTrace();
                    }
                }else {
                    try {
                        FtpUtil.deleteFile(host,port,username,password,basePath,id);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return list;
        }else {
            for (int i = 0; i < file.length; i++) {
                String name = file[i].getOriginalFilename().substring(0, file[i].getOriginalFilename().lastIndexOf("."));
                Type type = typeServiceImpl.newType(file[i].getOriginalFilename());
                Document document = documentMapper.findDocumentByNameAndStatusAndFolderIdAndTypeId(name, 1, folderId, type.getId());
                if (document != null && !document.equals("")) {
                    return list;
                }
            }
            for (int i = 0; i < file.length; i++) {
                String id = IDUtils.genDocumentId();
                String name = file[i].getOriginalFilename().substring(0, file[i].getOriginalFilename().lastIndexOf("."));
                Type type = typeServiceImpl.newType(file[i].getOriginalFilename());
                String genImageName = id+file[i].getOriginalFilename().substring(file[i].getOriginalFilename().lastIndexOf("."));
                boolean result = false;
                try {
                    result = FtpUtil.uploadFile(host, port, username, password, basePath, filepath, genImageName, file[i].getInputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(result){
                    Date date = new Date();
                    Document doc = new Document();
                    doc.setId(id);
                    doc.setName(name);
                    doc.setTypeId(type.getId());
                    doc.setUrl("http://"+host+"/"+genImageName);
                    doc.setFolderId(folderId);
                    doc.setStatus(1);
                    doc.setCreateTime(date);
                    doc.setUpdateTime(date);
                    doc.setUploadId(userId);
                    doc.setDocDescribe(describe);
                    try {
                        list.add(documentMapper.save(doc));
                        solrServiceImpl.add_OR_update(id,name,type.getType(),describe,date.getTime());
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (SolrServerException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return list;
    }

    //从回收站中还原文档
    @Override
    public int recoverDoc(String documentId,String userId) {
        Document recycledDoc = documentMapper.findDocumentById(documentId);
        Document document = documentMapper.findDocumentByNameAndStatusAndFolderIdAndTypeId(recycledDoc.getName(), 1, recycledDoc.getFolderId(), recycledDoc.getTypeId());
        if(document!=null){
            return 0;
        }
        recycledDoc.setStatus(1);
        recycledDoc.setUpdateTime(new Date());
        Document save = documentMapper.save(recycledDoc);
        if(save==null){
            return 0;
        }
        logsServiceImpl.logs(documentId,2,1,userId);
        try {
            solrServiceImpl.add_OR_update(save.getId(),save.getName(),typeServiceImpl.findType(save.getTypeId()).getType(),save.getDocDescribe(),save.getUpdateTime().getTime());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
        return 1;
    }

    //接口下载
    @Override
    public OutputStream downloadPort(String docId, String userId, HttpServletResponse response) {
        Document document = documentMapper.findDocumentById(docId);
        Type type = typeServiceImpl.findType(document.getTypeId());
        logsServiceImpl.logs(docId,4,1,userId);
        return FtpUtil.downloadPort(host,port,username,password,basePath,document.getId()+type.getType());
    }


    //逻辑删除文档
    @Override
    public int delete(String documentId,String userId) {
        Document document = documentMapper.findDocumentById(documentId);
        if (document != null && !document.equals("")) {
            document.setStatus(0);
            documentMapper.save(document);
            logsServiceImpl.logs(documentId,1,1,userId);
            try {
                solrServiceImpl.delete(documentId);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SolrServerException e) {
                e.printStackTrace();
            }
        }
        return 1;
    }

    //物理删除服务器文档
    @Override
    public int destroy(String documentId,String userId) {
        Document document = documentMapper.findDocumentById(documentId);
        Type type = typeServiceImpl.findType(document.getTypeId());
        try {
            boolean b = FtpUtil.deleteFile(host, port, username, password, basePath, document.getId() + type.getType());
            if(b){
                documentMapper.delete(document);
                logsServiceImpl.logs(documentId,0,1,userId);
                return 1;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    //下载文档
    @Override
    public void download(String documentId,String userId, HttpServletResponse response) {
        Document document = documentMapper.findDocumentById(documentId);
        Type type = typeServiceImpl.findType(document.getTypeId());
        logsServiceImpl.logs(documentId,4,1,userId);
        FtpUtil.downloadFile(host, port, username, password, basePath, document.getId()+type.getType(), document.getName()+type.getType(), response);
    }

    //重命名文档
    @Override
    public int updateName(String documentId, String name,String userId) {
        //根据id查找到要修改的文档对象
        Document documentById = documentMapper.findDocumentById(documentId);
        //根据文档的隶属的文件夹的id,以及新名字以及类型id，查询是否新名字和老名字冲突
        Document document = documentMapper.findDocumentByNameAndStatusAndFolderIdAndTypeId(name,1,documentById.getFolderId(),documentById.getTypeId());
        if(document!=null){
            return 0;
        }
        documentById.setName(name);
        documentById.setUpdateTime(new Date());
        Document newDocument = documentMapper.save(documentById);
        if(newDocument==null){
            return 0;
        }
        logsServiceImpl.logs(documentId,3,1,userId);
        try {
            solrServiceImpl.add_OR_update(newDocument.getId(),newDocument.getName(),typeServiceImpl.findType(newDocument.getTypeId()).getType(),newDocument.getDocDescribe(),newDocument.getUpdateTime().getTime());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
        return 1;
    }

    //查询回收站中的文档
    @Override
    public List<Document> showRecycledDocument() {
        List<Document> documentByStatus = documentMapper.findDocumentByStatus(0);
        List<Document> list = new ArrayList<>();
        for (Document doc:documentByStatus){
            doc.setTypeId(typeServiceImpl.findType(doc.getTypeId()).getType());
            if(doc.getFolderId().equals("0")){
                list.add(doc);
            }else if(folderServiceImpl.findFolderById(doc.getFolderId(), 1)!=null){
                list.add(doc);
            }
        }
        return list;
    }

}
