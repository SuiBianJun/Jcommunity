package com.dlg.community.pojo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class GithubUserInfo {

    private String login;
    private int id;
    private String avatar_url;

}
