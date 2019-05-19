package com.example.examinapp.models;

import com.example.examinapp.enums.UserTypeEnum;

public class UserModel {
    private int id;
    private String name;
    private String username;
    private String sessionId;
    private int userType;

    public UserModel() {

    }

    public UserModel(int id, String name, String username, String sessionId, int userType) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.sessionId = sessionId;
        this.userType = userType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public UserTypeEnum getUserTypeEnum() {
        return (userType == 2) ? UserTypeEnum.Lecturer : UserTypeEnum.Student;
    }
}


