package com.dlg.community.entity;

import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Data
@RedisHash("tag")
public class QuestionTag {

    @Id
    String id;
    List<Tag> tagList;

    public QuestionTag(){
        this.tagList = new ArrayList<>();
    }
}

