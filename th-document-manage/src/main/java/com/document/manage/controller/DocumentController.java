package com.document.manage.controller;

import com.document.manage.pojo.Document;
import com.document.manage.pojo.EasyUIResult;
import com.document.manage.pojo.Folder;
import com.document.manage.service.DocumentService;
import com.document.manage.service.FolderService;
import com.document.manage.service.SolrService;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @Auther: lee
 * @Date: 2019/10/26
 * @Description: com.lee.springboot.controller
 * @version: 1.0
 */

@Controller
@RequestMapping("document")
public class DocumentController {
    @Autowired
    private DocumentService documentServiceImpl;
    @Autowired
    private FolderService folderServiceImpl;
    @Autowired
    private SolrService solrServiceImpl;

    //进入回收站.test
    @RequestMapping("recycled")
    @ResponseBody
    public EasyUIResult recycledJson(){
        EasyUIResult result = new EasyUIResult();
        List list = new ArrayList();
        List<Folder> folderList = folderServiceImpl.showRecycledFolder();
        Document doc = new Document();
        for (Folder folder:folderList){
            doc.setId(folder.getId());
            doc.setName(folder.getName());
            doc.setTypeId("文件夹");
            list.add(doc);
        }
        List<Document> docList = documentServiceImpl.showRecycledDocumentJson();
        for (Document document:docList){
            list.add(document);
        }
        result.setRows(list);
        result.setTotal(folderList.size()+docList.size());
        return result;
    }

    @RequestMapping("recycled.html")
    public String recycled(){
        return "recycled";
    }

    //上传单个或多个文件
    @RequestMapping("upload")
    @ResponseBody
    public String uploadDoc(MultipartFile[] file, String describe, String folderId, String userId) {
        if (file.length==0){
            return "上传文档失败！";
        }
        List list = documentServiceImpl.upload(file,folderId,describe.equals("")?null:describe,userId);
        if (list.size()==file.length){
            return "成功上传"+list.size()+"个文档！";
        }else {
            return "上传文档失败！";
        }
    }

    //上传头像
    @RequestMapping("uploadImg")
    @ResponseBody
    public String uploadImg(MultipartFile[] file, String describe, String userId){
        System.out.println(file.length);
        System.out.println(userId);
        List<Document> list = documentServiceImpl.upload(file, "1",describe, userId);
        return list.get(0).getUrl();
    }

    //逻辑删除
    @RequestMapping("delete")
    @ResponseBody
    public String deleteDoc(String docId,String userId) {
        int index = documentServiceImpl.delete(docId,userId);
        if(index==1){
            return "删除成功";
        }
        return "删除失败";
    }
    //物理删除
    @RequestMapping("destroy")
    @ResponseBody
    public String destroyDoc(String docId,String userId){
        int index = documentServiceImpl.destroy(docId,userId);
        if(index==1){
            return "彻底删除成功";
        }
        return "彻底删除失败";
    }

    //下载文档
    @RequestMapping("download")
    public void downloadDoc(String docId,String userId, HttpServletResponse response){
        documentServiceImpl.download(docId,userId,response);
    }

    @RequestMapping("update")
    @ResponseBody
    public String updateName(String id,String name,String userId){
        int index = documentServiceImpl.updateName(id, name,userId);
        if(index==1){
            return "重命名成功";
        }
        return "重命名失败";
    }

    //对逻辑删除的文档进行还原
    @RequestMapping("recover")
    @ResponseBody
    public String recoverDoc(String docId,String userId){
        int index = documentServiceImpl.recoverDoc(docId,userId);
        if(index==1){
            return "还原成功";
        }
        return "还原失败";
    }

    @RequestMapping("search")
    @ResponseBody
    public EasyUIResult search(@RequestParam(defaultValue="0") String type, @RequestParam(defaultValue="0") String date, String message, String describe, @RequestParam(defaultValue="1") int page, @RequestParam(defaultValue="10") int rows, Model model){
        if((message==null||message.equals(""))&&(describe==null||describe.equals(""))){
            return null;
        }
        switch (type){
            case "0":
                type = null;
                break;
            case "1":
                System.out.println("音频");
                type = "doc_type:.wav or doc_type:.aif or doc_type:.au  or doc_type:.mp3  or doc_type:.ram";
                break;
            case "2":
                System.out.println("视频");
                type = "doc_type:.avi or doc_type:.mkv or doc_type:.flv  or doc_type:.rmvb  or doc_type:.rm or doc_type:.wmv or doc_type:.mov or doc_type:.asx or doc_type:.mp4";
                break;
            case "3":
                System.out.println("文档");
                type = "doc_type:.docx or doc_type:.doc or doc_type:.txt or doc_type:.html or doc_type:.pdf or doc_type:.ppt or doc_type:.pptx or doc_type:.xls or doc_type:.xlsx";
                break;
            case "4":
                System.out.println("图片");
                type = "doc_type:.bmp or doc_type:.gif or doc_type:.jpg  or doc_type:.pic  or doc_type:.png  or doc_type:.tif";
                break;
        }

        //当前毫秒数
        long now = System.currentTimeMillis();

        long nowEnd;

        Calendar cal = Calendar.getInstance();

        switch (date){
            case "2":
                cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
                cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                break;
            case "3":
                cal.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
                break;
            case "4":
                cal.set(Calendar.MONTH, 0);
                cal.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
                break;
        }
        if(date.equals("0")){
            date = null;
        }else {
            //将小时至0
            cal.set(Calendar.HOUR_OF_DAY, 0);
            //将分钟至0
            cal.set(Calendar.MINUTE, 0);
            //将秒至0
            cal.set(Calendar.SECOND,0);
            //将毫秒至0
            cal.set(Calendar.MILLISECOND, 0);
            nowEnd = cal.getTimeInMillis();
            date ="["+nowEnd+" TO "+now+"]";
        }
        try {
            return solrServiceImpl.query(type,date,message,describe,page,rows);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("search.html")
    public String searchHtml(){
        return "search";
    }

}
