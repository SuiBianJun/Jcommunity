package com.dlg.community.request;

import lombok.Data;

@Data
public class CommentQueryVO {

    private Integer question_id;
    private Integer user_id;
    private Integer type = 2;
    private String content;
    private Integer reply_id;
}
