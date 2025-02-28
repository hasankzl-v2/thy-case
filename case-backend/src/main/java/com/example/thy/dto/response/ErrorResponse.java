package com.example.thy.dto.response;

import com.example.thy.enums.ErrorCodeEnum;
import lombok.Data;

@Data
public class ErrorResponse {
    private ErrorCodeEnum errorCode;
    private String errorMessage;

    public ErrorResponse(ErrorCodeEnum errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

}