package com.example.examinapp.models;

import java.util.List;

public class AnswerModel {
    private int id;
    private String description;
    private boolean isValid;

    private int questionId;
    private QuestionModel question;

    private List<StudentQuestionAnswerModel> studentQuestionAnswers;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public QuestionModel getQuestion() {
        return question;
    }

    public void setQuestion(QuestionModel question) {
        this.question = question;
    }

    public List<StudentQuestionAnswerModel> getStudentQuestionAnswers() {
        return studentQuestionAnswers;
    }

    public void setStudentQuestionAnswers(List<StudentQuestionAnswerModel> studentQuestionAnswers) {
        this.studentQuestionAnswers = studentQuestionAnswers;
    }
}
