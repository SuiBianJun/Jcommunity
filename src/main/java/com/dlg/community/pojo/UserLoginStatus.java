package com.dlg.community.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Accessors
@Table(name = "t_user_auto_login")
public class UserLoginStatus implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String user_token;
    private String user_name;

    private Date gmt_create;
    private Date gmt_modify;

    private int user_id;

    @Column(length = 200)
    private String user_avator_url;
}
