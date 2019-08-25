package com.dlg.community.pojo;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Date;

@Data
@ToString
@Accessors(chain = true)
@Entity
@Table(name = "t_question_info")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String question_title;
    private String question_detail;
    private String question_tags;
    private Date gmt_create;
    private Date gmt_update;
    private Integer creator_id;
    private Integer view_count;
}
