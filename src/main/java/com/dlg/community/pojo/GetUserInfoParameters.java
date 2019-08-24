package com.dlg.community.pojo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GetUserInfoParameters {

    @Value("${oauth.userInfoRequestUrl}")
    private String url;

    private String access_token;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    @Override
    public String toString() {
        return "GetUserInfoParameters{" +
                "url='" + url + '\'' +
                ", access_token='" + access_token + '\'' +
                '}';
    }
}
