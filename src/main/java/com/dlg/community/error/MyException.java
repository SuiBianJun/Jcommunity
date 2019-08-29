package com.dlg.community.error;

import com.dlg.community.enums.ErrorCode;

public class MyException extends RuntimeException {

    private String message;
    private Integer errorCode;

    public MyException(String message){
        this.message = message;
    }

    public MyException(ErrorCode errorCode){
        this.message = errorCode.getMessage();
        this.errorCode = errorCode.getErrorCode();
    }

    // 传递错误提示信息
    @Override
    public String getMessage() {
        return message;
    }

    public Integer getErrorCode(){return errorCode;}
}
