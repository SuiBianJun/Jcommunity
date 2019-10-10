package com.dlg.community.response;

import com.dlg.community.entity.Tag;
import lombok.Data;

import java.util.List;

@Data
public class QuestionTagVO {

    String id;
    List<Tag> tagList;

    public QuestionTagVO(){}
}
