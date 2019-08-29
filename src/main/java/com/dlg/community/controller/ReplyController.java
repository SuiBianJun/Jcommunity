package com.dlg.community.controller;

import com.dlg.community.dto.ReplyVO;
import com.dlg.community.service.ReplyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    public String reply(@RequestBody ReplyVO replyVO){

        logger.info("replyVO: " + replyVO);

        replyService.addReply(replyVO);

        return "{msg: 'ok'}";// @ResposeBody 返回值必须符合json格式（或因为前端指定了返回值类型）

    }

}
