package com.example.examinapp.dataaccess.dtos.base;

import java.util.Date;

public class BaseRequest {
    private Date requestedDateTime;
    private String username;
    private String sessionId;

    public BaseRequest(Date requestedDateTime, String username, String sessionId) {
        this.requestedDateTime = requestedDateTime;
        this.username = username;
        this.sessionId = sessionId;
    }

    public Date getRequestedDateTime() {
        return requestedDateTime;
    }

    public void setRequestedDateTime(Date requestedDateTime) {
        this.requestedDateTime = requestedDateTime;
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
}
