package com.treehole.online.controller;

import com.treehole.api.onlinetalk.ReplyControllerApi;
import com.treehole.framework.domain.onlinetalk.Reply;
import com.treehole.framework.domain.onlinetalk.Vo.ReplyVo;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.online.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author hewenze
 * @Description:
 * @Date
 */
@RestController
@RequestMapping("online/reply")
public class ReplyController implements ReplyControllerApi {
    @Autowired
    private ReplyService replyService;


    /**
     *查找所有快捷回复
     * @param page
     * @param size
     * @param category
     * @return
     */
    @Override
    @GetMapping ("/getAllReply")
    public QueryResponseResult getAllReply(@RequestParam(value = "page", defaultValue = "1") int page,
                                           @RequestParam(value = "size", defaultValue = "5") int size,
                                           @RequestParam(value = "category", defaultValue = "")String category)  {
        System.out.println("conreoller1111111"+category);
        QueryResult queryResult = replyService.findAllReply(page, size,category);
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);

    }


    /**
     * 根据id查找快捷回复
     * @param id
     * @return
     */
    @Override
    @GetMapping("/find/id/{id}")
    public ReplyVo getReplyById(@PathVariable("id") String id) {
         return replyService.getReplyById(id);
    }

    /**
     * 根据快捷回复对象查找快捷回复
     * @param reply
     * @return
     */
    @GetMapping("/find")
    public Reply getAgent(@RequestBody @Valid Reply reply){
        return  replyService.findReply(reply);

    }


    /**
     * 根据id删除快捷回复
     * @param reply_id
     * @return
     */
    @Override
    @DeleteMapping(value ="/delete/id/{reply_id}")
    public ResponseResult deleteReplyById(@PathVariable("reply_id") String reply_id) {
        replyService.deleteReplyById(reply_id);
         //再判断用户是否存在

        return new ResponseResult(CommonCode.SUCCESS);

    }


    /**
     * 新增快捷回复
     * @param reply
     * @return
     */
    @Override
    @PostMapping ("/insert")
    public ResponseResult insertReply(@RequestBody @Valid Reply reply) {


        replyService.insertReply(reply);
        return new ResponseResult(CommonCode.SUCCESS);


    }


    /**
     * 修改快捷回复
     * @param replyVo
     * @return
     */
    @Override
    @PutMapping("/update")
    public ResponseResult updateReply(@RequestBody @Valid ReplyVo replyVo){
        replyService.updateReply(replyVo);

        return new ResponseResult(CommonCode.SUCCESS);
    }




}
