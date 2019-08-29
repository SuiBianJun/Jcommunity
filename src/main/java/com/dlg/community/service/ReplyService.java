package com.dlg.community.service;

import com.dlg.community.dao.QuestionDao;
import com.dlg.community.dao.ReplyDao;
import com.dlg.community.dao.UserLoginStatusDao;
import com.dlg.community.dto.ReplyVO;
import com.dlg.community.enums.ErrorCode;
import com.dlg.community.error.MyException;
import com.dlg.community.pojo.Reply;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
@Slf4j
public class ReplyService {

    @Autowired
    ReplyDao replyDao;

    @Autowired
    QuestionDao questionDao;

    @Autowired
    UserLoginStatusDao userLoginStatusDao;

    @Transactional(rollbackOn = Exception.class)
    public void addReply(ReplyVO replyVO){

        // 检查replyVO

        if(!questionDao.exists(replyVO.getQuestion_id())){
            throw new MyException(ErrorCode.QUESTION_NOT_FOUND);
        }

        if(replyVO.getUser_id() == null || !(userLoginStatusDao.existByUserId(replyVO.getUser_id()) > 1)){
            throw new MyException(ErrorCode.USER_NOT_LOGIN);
        }

        Reply reply = new Reply();
        BeanUtils.copyProperties(replyVO, reply);

        reply.setGmt_create(new Date());
        reply.setGmt_update(new Date());

        replyDao.save(reply);

    }

}
