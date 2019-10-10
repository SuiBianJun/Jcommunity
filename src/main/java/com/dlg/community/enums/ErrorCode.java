package com.dlg.community.enums;

public enum ErrorCode {

    PAGE_NOT_FOUND(1, "访问的页面不存在"),
    SERVER_ERROR(2, "服务器内部错误"),
    QUESTION_NOT_FOUND(3, "回复的问题已经不存在"),
    USER_NOT_LOGIN(4, "请先登录"),

    REQUEST_OK(200, "操作成功"),
    ADD_COMMENT_OK(200, "评论成功");;

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

    public Integer getErrorCode(){return this.errorCode;}

}
