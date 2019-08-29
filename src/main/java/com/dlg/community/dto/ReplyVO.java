package com.dlg.community.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ReplyVO {

    private Integer question_id;

    private String content;

    private Integer user_id;

    private Integer type;
}
