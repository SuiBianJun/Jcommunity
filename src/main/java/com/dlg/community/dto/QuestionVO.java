package com.dlg.community.dto;

import com.dlg.community.pojo.UserLoginStatus;
import lombok.Data;

import java.util.Date;

@Data
public class QuestionVO {

    private Integer id;
    private String question_title;
    private String question_detail;
    private String question_tags;
    private Date gmt_create;
    private Date gmt_update;
    private Integer creator_id;

    private UserLoginStatus creator;
}
