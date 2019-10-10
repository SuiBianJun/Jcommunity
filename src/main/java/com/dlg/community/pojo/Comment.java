package com.dlg.community.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Accessors(chain = true)
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer reply_id;// 评论的哪条回复

    private Integer question_id;

    private String content;

    private Integer user_id;

    private Integer type;

    private Date gmt_create;

    private Date gmt_update;

}
