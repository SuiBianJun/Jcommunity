package com.dlg.community.service;

import com.dlg.community.dao.CommentDao;
import com.dlg.community.dao.QuestionDao;
import com.dlg.community.dao.ReplyDao;
import com.dlg.community.dao.UserLoginStatusDao;
import com.dlg.community.dto.ReplyVO;
import com.dlg.community.enums.ErrorCode;
import com.dlg.community.error.MyException;
import com.dlg.community.pojo.Comment;
import com.dlg.community.pojo.Reply;
import com.dlg.community.request.CommentQueryVO;
import com.dlg.community.response.QuestionCommentVO;
import com.dlg.community.response.QuestionReplyVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ReplyService {

    @Autowired
    ReplyDao replyDao;

    @Autowired
    CommentDao commentDao;

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

    public List<QuestionCommentVO> getComment(CommentQueryVO commentQueryVO) {
        /*if(!commentDao.exists(commentQueryVO.getQuestion_id())){
            throw new MyException(ErrorCode.QUESTION_NOT_FOUND);
        }*/

        List<QuestionCommentVO> commentList = commentDao.getComment(commentQueryVO.getQuestion_id(), commentQueryVO.getUser_id(), commentQueryVO.getType(),commentQueryVO.getReply_id()).stream().map(QuestionCommentVO::new).collect(Collectors.toList());;

        List<QuestionCommentVO> list = commentList.stream().map(questionCommentVO -> {

            questionCommentVO.setUser(userLoginStatusDao.selUserById(questionCommentVO.getUser_id()));

            return questionCommentVO;

        }).collect(Collectors.toList());
        //list.sort(Comparator.comparing(QuestionCommentVO::getGmt_create));

        return list;
    }

    public void addComment(CommentQueryVO commentQueryVO) {

        if(!questionDao.exists(commentQueryVO.getQuestion_id())){
            throw new MyException(ErrorCode.QUESTION_NOT_FOUND);
        }

        if(commentQueryVO.getUser_id() == null || !(userLoginStatusDao.existByUserId(commentQueryVO.getUser_id()) > 1)){
            throw new MyException(ErrorCode.USER_NOT_LOGIN);
        }

        Comment comment = new Comment();
        BeanUtils.copyProperties(commentQueryVO, comment);

        comment.setGmt_create(new Date());
        comment.setGmt_update(new Date());

        commentDao.save(comment);

    }
}
