package com.document.manage.service.impl;

import com.document.manage.mapper.FolderMapper;
import com.document.manage.pojo.Document;
import com.document.manage.pojo.Folder;
import com.document.manage.service.*;
import com.document.manage.utils.IDUtils;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Auther: lee
 * @Date: 2019/10/28
 * @Description: com.lee.springboot.service.impl
 * @version: 1.0
 */
@Service
public class FolderServiceImpl implements FolderService {
    @Autowired
    private FolderMapper folderMapper;
    @Autowired
    private DocumentService documentServiceImpl;
    @Autowired
    private TypeService typeServiceImpl;
    @Autowired
    private LogsService logsServiceImpl;
    @Autowired
    private SolrService solrServiceImpl;

    //新建文件夹
    @Override
    public int addFolder(String name,String parentId,String userId) {
        Folder folderByParentIdAndNameAndStatus = folderMapper.findFolderByParentIdAndNameAndStatus(parentId, name, 1);
        if(folderByParentIdAndNameAndStatus!=null){
            return 0;
        }
        Date date = new Date();
        Folder folder = new Folder();
        folder.setId(IDUtils.genDocumentId());
        folder.setName(name);
        folder.setParentId(parentId);
        folder.setStatus(1);
        folder.setCreateTime(date);
        folder.setUpdateTime(date);
        folder.setIsParent(0);
        folder.setCreateId(userId);
        Folder save = folderMapper.save(folder);
        if(save==null){
            return 0;
        }
        //父文件夹是根目录，就不用修改父文件夹的isParent状态
        if(!parentId.equals("0")){
            Folder parentFolder = folderMapper.findFolderByIdAndStatus(parentId,1);
            if(parentFolder.getIsParent()!=1){
                parentFolder.setIsParent(1);
                folderMapper.save(parentFolder);
            }
        }
        try {
            solrServiceImpl.add_OR_update(save.getId(),name,null,null,date.getTime());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
        return 1;
    }

    //根据父id查询所有文件夹
    @Override
    public List<Folder> findAllFolder(String parentId) {
        return folderMapper.findAllByParentIdAndStatus(parentId,1);
    }

    //根据id和状态查询此文件夹
    @Override
    public Folder findFolderById(String id,Integer status) {
        return folderMapper.findFolderByIdAndStatus(id,status);
    }

    //重命名文件夹
    @Override
    public int updateName(String id, String name,String userId) {
        //根据准备修改名字的文件夹id查到文件夹对象
        Folder folder = folderMapper.findFolderByIdAndStatus(id, 1);
        if(folder!=null){
            //根据文件夹的隶属文件夹以及新名称查找是否有重合的
            Folder folder1 = folderMapper.findFolderByParentIdAndNameAndStatus(folder.getParentId(), name, 1);
            if(folder1==null){
                Date date = new Date();
                folder.setName(name);
                folder.setUpdateTime(date);
                Folder save = folderMapper.save(folder);
                if(save==null){
                    return 0;
                }
                logsServiceImpl.logs(id,3,0,userId);
                try {
                    solrServiceImpl.add_OR_update(id,name,null,null,date.getTime());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SolrServerException e) {
                    e.printStackTrace();
                }
            }
        }
        return 1;
    }

    //物理删除回收站中的文件夹，以及文件夹下的文件夹和文档
    @Override
    public int destroyFolder(String id,String userId) {
        callback(id,userId);
        return 1;
    }

    //逻辑删除文件夹
    @Override
    public int deleteFolder(String id,String userId) {
        callback(id,0,userId);
        return 1;
    }

    //还原回收站中的文件夹
    @Override
    public int recoverFolder(String id,String userId){
        callback(id,1,userId);
        return 1;
    }

    //物理删除的回调方法
    private void callback(String id,String userId){       //物理删除文件夹以及文件夹下的文档的调用
        Folder folderByIdAndStatus = folderMapper.findFolderByIdAndStatus(id, 0);
        folderMapper.delete(folderByIdAndStatus);
        logsServiceImpl.logs(id,0,0,userId);
        List<Document> documentList = documentServiceImpl.showAllDocByFolder(id, 0);
        for (Document doc:documentList) {
            documentServiceImpl.destroy(doc.getId(),userId);
        }
        if(folderByIdAndStatus.getIsParent()==1){
            List<Folder> list = folderMapper.findAllByParentIdAndStatus(folderByIdAndStatus.getId(), 0);
            for(Folder folder:list){
                callback(folder.getId(),userId);
            }
        }
    }

    //逻辑删除或还原的回调方法
    private void callback(String id,Integer status,String userId){    //逻辑（删除或还原）文件夹以及文件夹下的文档的调用
        Folder callFolder = folderMapper.findFolderByIdAndStatus(id, status==1?0:1);
        Date date = new Date();
        callFolder.setStatus(status);
        callFolder.setUpdateTime(date);
        folderMapper.save(callFolder);
        if(status==0){//删除
            try {
                logsServiceImpl.logs(id,1,0,userId);
                solrServiceImpl.delete(id);//删除文件夹
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SolrServerException e) {
                e.printStackTrace();
            }
            List<Document> documentList = documentServiceImpl.showAllDocByFolder(id,status==1?0:1);
            for (Document document:documentList){
                documentServiceImpl.delete(document.getId(),userId);
                try {
                    solrServiceImpl.delete(document.getId());//删除每个文档
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SolrServerException e) {
                    e.printStackTrace();
                }
            }
        }else if(status==1){//还原
            try {
                logsServiceImpl.logs(id,2,0,userId);
                solrServiceImpl.add_OR_update(id,callFolder.getName(),null,null,date.getTime());//还原文件夹
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SolrServerException e) {
                e.printStackTrace();
            }
            List<Document> documentList = documentServiceImpl.showAllDocByFolder(id,status==1?0:1);
            for (Document document:documentList){
                documentServiceImpl.recoverDoc(document.getId(),userId);
                try {
                    solrServiceImpl.add_OR_update(document.getId(),document.getName(),typeServiceImpl.findType(document.getTypeId()).getType(),document.getDocDescribe(),document.getUpdateTime().getTime());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SolrServerException e) {
                    e.printStackTrace();
                }
            }
        }
        if(callFolder.getIsParent()==1){
            List<Folder> list = folderMapper.findAllByParentIdAndStatus(callFolder.getId(), status==1?0:1);
            for(Folder folder:list){
                callback(folder.getId(),status,userId);
            }
        }
    }

    //查询回收站中的文件夹
    @Override
    public List<Folder> showRecycledFolder() {
        List<Folder> folderByStatus = folderMapper.findFolderByStatus(0);
        List<Folder> list = new ArrayList<>();
        for (Folder folder:folderByStatus){
            if(folder.getParentId().equals("0")){
                list.add(folder);
            }
            if(folderMapper.findFolderByIdAndStatus(folder.getParentId(),1)!=null){
                list.add(folder);
            }
        }
        return list;
    }
}
