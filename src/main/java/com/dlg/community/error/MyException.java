package com.dlg.community.error;

public class MyException extends RuntimeException {

    private String msg;

    public MyException(String msg){
        this.msg = msg;
    }



}
