package com.dlg.community.dto;

import com.alibaba.fastjson.JSON;
import com.dlg.community.pojo.GetTokenParameters;
import com.dlg.community.pojo.GetUserInfoParameters;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GetTokenDto {

    public String getToken(GetTokenParameters getTokenParameters){
        MediaType JSON = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, com.alibaba.fastjson.JSON.toJSONString(getTokenParameters));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getUserInfo(GetUserInfoParameters getUserInfoParameters){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(getUserInfoParameters.getUrl() + "?" + "access_token=" + getUserInfoParameters.getAccess_token())
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
