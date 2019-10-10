package com.dlg.community.response;

import lombok.Data;

@Data
public class ResponseVO {

    int code;
    String msg;
    Object data;

    public ResponseVO(){}

    public ResponseVO(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
