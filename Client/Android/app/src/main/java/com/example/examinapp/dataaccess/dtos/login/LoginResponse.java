package com.example.examinapp.dataaccess.dtos.login;

import com.example.examinapp.dataaccess.dtos.base.BaseResponse;
import com.example.examinapp.models.UserModel;

import java.util.Date;

public class LoginResponse extends BaseResponse {
    public UserModel user;

    public LoginResponse(boolean isSuccess, int status, String message, Date respondDateTime, UserModel user) {
        super(isSuccess, status, message, respondDateTime);
        this.user = user;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }
}
