package com.dlg.community.dto;

import com.dlg.community.enums.ErrorCode;
import com.dlg.community.error.MyException;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class ErrorVO implements Serializable {

    Integer errorCode;
    String errorMsg;

    Object data;

    public ErrorVO(Integer errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public static ErrorVO errorOf(ErrorCode errorCode){
        return new ErrorVO(errorCode.getErrorCode(), errorCode.getMessage());
    }

    public static ErrorVO errorOf(ErrorCode errorCode, Object data){
        ErrorVO errorVO = new ErrorVO(errorCode.getErrorCode(), errorCode.getMessage());
        errorVO.setData(data);
        return errorVO;
    }

    public static ErrorVO errorOf(MyException e){
        return new ErrorVO(e.getErrorCode(), e.getMessage());
    }

}
