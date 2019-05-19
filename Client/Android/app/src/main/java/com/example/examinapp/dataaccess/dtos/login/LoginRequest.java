package com.example.examinapp.dataaccess.dtos.login;

import com.example.examinapp.dataaccess.dtos.base.BaseRequest;

import java.util.Date;

public class LoginRequest extends BaseRequest {
    private String password;

    public LoginRequest(Date requestedDateTime, String username, String sessionId, String password) {
        super(requestedDateTime, username, sessionId);
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
