package com.document.manage.controller;

import com.document.manage.pojo.Document;
import com.document.manage.pojo.EasyUIResult;
import com.document.manage.pojo.Folder;
import com.document.manage.pojo.Type;
import com.document.manage.service.DocumentService;
import com.document.manage.service.FolderService;
import com.document.manage.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: lee
 * @Date: 2019/10/28
 * @Description: com.lee.springboot.controller
 * @version: 1.0
 */
@Controller
@RequestMapping("folder")
public class FolderController {
    @Autowired
    private FolderService folderServiceImpl;
    @Autowired
    private DocumentService documentServiceImpl;
    @Autowired
    private TypeService typeServiceImpl;

    //打开文件夹
    @RequestMapping("open.html")
    public String openFolder(String id, Model model){
        Folder folder = new Folder();
        if(id==null){     //代表进入的是根目录
            folder.setId("0");
            folder.setName("根目录");
            model.addAttribute("folder",folder);
        }else{
            model.addAttribute("folder",folderServiceImpl.findFolderById(id,1));
        }
        return "document";
    }

    @RequestMapping("open")
    @ResponseBody
    public EasyUIResult openFolderJson(String id){
        EasyUIResult result = new EasyUIResult();
        int status = 1;
        List list = new ArrayList<>();
        List<Document> docList;
        List<Folder> folderList;
        if(id==null){     //代表进入的是根目录
            folderList = folderServiceImpl.findAllFolder("0");
            docList = documentServiceImpl.showAllDocByFolder("0",status);
        }else{
            folderList = folderServiceImpl.findAllFolder(id);
            docList = documentServiceImpl.showAllDocByFolder(id,status);
        }
        for (Folder folder:folderList){
            Document document = new Document();
            document.setId(folder.getId());
            document.setName(folder.getName());
            document.setTypeId("文件夹");
            list.add(document);
        }
        for (Document doc:docList){
            Type type = typeServiceImpl.findType(doc.getTypeId());
            doc.setTypeId(type.getType());
            list.add(doc);
        }
        result.setRows(list);
        result.setTotal(list.size());
        return result;
    }

    @RequestMapping("add")
    @ResponseBody
    public String addFolder(String name,String parentId,String userId){
        int index = folderServiceImpl.addFolder(name, parentId,userId);
        if(index==1){
            return "新建文件夹成功";
        }
        return "新建文件夹失败";
    }

    @RequestMapping("update")
    @ResponseBody
    public String updateFolderName(String id,String name,String userId){
        int index = folderServiceImpl.updateName(id, name,userId);
        if(index==1){
            return "重命名成功";
        }
        return "重命名失败";
    }

    @RequestMapping("delete")
    @ResponseBody
    public String deleteFolder(String id,String userId){
        int index = folderServiceImpl.deleteFolder(id,userId);
        if(index==1){
            return "删除成功";
        }
        return "删除失败";
    }

    @RequestMapping("destroy")
    @ResponseBody
    public String destroyFolder(String id,String userId){
        System.out.println(id);
        int index = folderServiceImpl.destroyFolder(id,userId);
        if(index==1){
            return "彻底删除成功";
        }
        return "彻底删除失败";
    }

    @RequestMapping("recover")
    @ResponseBody
    public String recoverFolder(String id,String userId){
        int index = folderServiceImpl.recoverFolder(id,userId);
        if(index==1){
            return "还原成功";
        }
        return "还原失败";
    }
}
