package com.dlg.community.response;

import com.dlg.community.pojo.Reply;
import com.dlg.community.pojo.UserLoginStatus;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Data
public class QuestionReplyVO {

    private Integer id;

    private Integer question_id;

    private String content;

    private Integer user_id;

    private Integer type;

    private Date gmt_create;

    private Date gmt_update;

    private UserLoginStatus user;

    public QuestionReplyVO(Reply r){
        BeanUtils.copyProperties(r, this);
    }

}
