package com.dlg.community.dto;

import com.dlg.community.pojo.UserLoginStatus;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Data
public class QuestionVO {

    private Integer id;
    private String question_title;
    private String question_detail;
    private String question_tags;
    private Date gmt_create;
    private Date gmt_update;
    private Integer creator_id;
    private Integer view_count;
    private UserLoginStatus creator;
    private List<String> tagList;

    public void setTagList(String[] tagList){
        if(tagList == null){
            this.tagList = new ArrayList<>();
        }else{
            this.tagList = new ArrayList<>(Arrays.asList(tagList));
        }

    }
}
