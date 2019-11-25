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
 * @author shanhuijie
 * @Description:
 * @Date
 */
@RestController
@RequestMapping("reply")
public class ReplyController implements ReplyControllerApi {
    @Autowired
    private ReplyService replyService;





    @Override
    @GetMapping ("/getAllReply")
    public QueryResponseResult getAllReply(@RequestParam(value = "page", defaultValue = "1") int page,
                                           @RequestParam(value = "size", defaultValue = "5") int size,
                                           @RequestParam(value = "category", defaultValue = "")String category)  {
        System.out.println("conreoller1111111"+category);
        QueryResult queryResult = replyService.findAllReply(page, size,category);
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);

    }




    @Override
    @GetMapping("/find/id/{id}")
    public ReplyVo getReplyById(@PathVariable("id") String id) {
         return replyService.getReplyById(id);
    }
    @GetMapping("/find")
    public Reply getAgent(@RequestBody @Valid Reply reply){
        return  replyService.findReply(reply);

    }

    /*@GetMapping("/find/")
    public User getUser*/

    @Override
    @DeleteMapping(value ="/delete/id/{reply_id}")
    public ResponseResult deleteReplyById(@PathVariable("reply_id") String reply_id) {
        replyService.deleteReplyById(reply_id);
         //再判断用户是否存在
         /*if(this.getUserById(user_id) != null){
             ExceptionCast.cast(MemberCode.DELETE_USER_NOT_EXIST);
         }*/
        return new ResponseResult(CommonCode.SUCCESS);

    }



    @Override
    @PostMapping ("/insert")
    public ResponseResult insertReply(@RequestBody @Valid Reply reply) {


        replyService.insertReply(reply);
        return new ResponseResult(CommonCode.SUCCESS);


    }



    /*接收到的数据为前端update后的*/
    @Override
    @PutMapping("/update")
    public ResponseResult updateReply(@RequestBody @Valid ReplyVo replyVo){
        //System.out.println("前端传来的+++++++++++++"+user);
        replyService.updateReply(replyVo);

        return new ResponseResult(CommonCode.SUCCESS);
    }




}
