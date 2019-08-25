package com.dlg.community.enums;

public enum ErrorCode {

    PAGE_NOT_FOUND(1, "访问的页面不存在"),
    SERVER_ERROR(2, "服务器内部错误");

    Integer errorCode;
    String errorMsg;

    ErrorCode(Integer errorCode){
        this.errorCode = errorCode;
    }

    ErrorCode(Integer errorCode, String errorMsg){
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public String getMessage(){
        return this.errorMsg;
    }

}
