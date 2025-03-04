package com.example.thy.dto.response;

import com.example.thy.enums.ErrorCodeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ErrorResponse {
    @Schema(
            description = "code of the error",
            name = "errorCode",
            type = "ErrorCodeEnum",
            example = "GENERAL_ERROR")
    private ErrorCodeEnum errorCode;
    @Schema(
            description = "Error message",
            name = "errorMessage",
            type = "String",
            example = "not found")
    private String errorMessage;

    public ErrorResponse(ErrorCodeEnum errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

}