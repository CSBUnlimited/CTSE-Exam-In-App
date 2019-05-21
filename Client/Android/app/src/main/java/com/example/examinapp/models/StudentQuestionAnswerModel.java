package com.example.examinapp.models;

public class StudentQuestionAnswerModel {
    public int id;

    public int studentUserId;
    public UserModel studentUser;

    public int questionId;
    public QuestionModel question;

    public int answerId;
    public AnswerModel answer;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudentUserId() {
        return studentUserId;
    }

    public void setStudentUserId(int studentUserId) {
        this.studentUserId = studentUserId;
    }

    public UserModel getStudentUser() {
        return studentUser;
    }

    public void setStudentUser(UserModel studentUser) {
        this.studentUser = studentUser;
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

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    public AnswerModel getAnswer() {
        return answer;
    }

    public void setAnswer(AnswerModel answer) {
        this.answer = answer;
    }
}
