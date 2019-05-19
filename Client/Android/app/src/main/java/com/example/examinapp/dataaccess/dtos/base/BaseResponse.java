package com.example.examinapp.dataaccess.dtos.base;

import java.util.Date;

public class BaseResponse {

    private boolean isSuccess;
    private int status;
    private String message;
    private Date respondDateTime;

    public BaseResponse(boolean isSuccess, int status, String message, Date respondDateTime) {
        this.isSuccess = isSuccess;
        this.status = status;
        this.message = message;
        this.respondDateTime = respondDateTime;
    }

    public boolean getIsSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getRespondDateTime() {
        return respondDateTime;
    }

    public void setRespondDateTime(Date respondDateTime) {
        this.respondDateTime = respondDateTime;
    }
}

