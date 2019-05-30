package com.example.examinapp.dataaccess.dtos.question;

import com.example.examinapp.dataaccess.dtos.base.BaseRequest;
import com.example.examinapp.models.QuestionModel;

import java.util.Date;

public class QuestionRequest extends BaseRequest {
    public QuestionModel question;

    public QuestionRequest(Date requestedDateTime, String username, String sessionId, QuestionModel question) {
        super(requestedDateTime, username, sessionId);
        this.question = question;
    }

    public QuestionModel getQuestion() {
        return question;
    }

    public void setQuestion(QuestionModel question) {
        this.question = question;
    }
}
