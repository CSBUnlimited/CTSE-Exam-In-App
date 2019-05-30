package com.example.examinapp.dataaccess.dtos.question;

import com.example.examinapp.dataaccess.dtos.base.BaseResponse;
import com.example.examinapp.models.QuestionModel;

import java.util.Date;
import java.util.List;

public class QuestionResponse extends BaseResponse {

    private List<QuestionModel> questions;

    public QuestionResponse(boolean isSuccess, int status, String message, Date respondDateTime, List<QuestionModel> questions) {
        super(isSuccess, status, message, respondDateTime);
        this.questions = questions;
    }

    public List<QuestionModel> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionModel> questions) {
        this.questions = questions;
    }
}
