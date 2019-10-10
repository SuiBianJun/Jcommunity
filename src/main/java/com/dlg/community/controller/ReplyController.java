package com.dlg.community.controller;

import com.dlg.community.dto.ErrorVO;
import com.dlg.community.dto.ReplyVO;
import com.dlg.community.enums.ErrorCode;
import com.dlg.community.request.CommentQueryVO;
import com.dlg.community.service.ReplyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ReplyController {

    Logger logger = LoggerFactory.getLogger(ReplyController.class);

    @Autowired
    ReplyService replyService;

    @ResponseBody
    @PostMapping("/reply")
    public Object reply(@RequestBody ReplyVO replyVO){

        logger.info("replyVO: " + replyVO);

        replyService.addReply(replyVO);

        return ErrorVO.errorOf(ErrorCode.REQUEST_OK);// @ResposeBody 返回值必须符合json格式（或因为前端指定了返回值类型）

    }

    @ResponseBody
    @PostMapping("/comment")
    public Object comment(@RequestBody CommentQueryVO commentQueryVO){

        logger.info("CommentVO: " + commentQueryVO);

        replyService.getComment(commentQueryVO);

        return ErrorVO.errorOf(ErrorCode.REQUEST_OK, replyService.getComment(commentQueryVO));// @ResposeBody 返回值必须符合json格式（或因为前端指定了返回值类型）

    }

    @ResponseBody
    @PostMapping("/comment/add")
    public Object addComment(@RequestBody CommentQueryVO commentQueryVO){

        logger.info("CommentVO: " + commentQueryVO);

        replyService.addComment(commentQueryVO);

        return ErrorVO.errorOf(ErrorCode.ADD_COMMENT_OK);// @ResposeBody 返回值必须符合json格式（或因为前端指定了返回值类型）

    }

}
