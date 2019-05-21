package com.example.examinapp.models;

import java.util.Date;

public class StudentExamModel {
    public int id;

    public int examId;
    public ExamModel exam;

    public int studentUserId;
    public UserModel studentUser;

    public Date startedDateTime;
    public Date endedDateTime;
    public float marks;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    public ExamModel getExam() {
        return exam;
    }

    public void setExam(ExamModel exam) {
        this.exam = exam;
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

    public Date getStartedDateTime() {
        return startedDateTime;
    }

    public void setStartedDateTime(Date startedDateTime) {
        this.startedDateTime = startedDateTime;
    }

    public Date getEndedDateTime() {
        return endedDateTime;
    }

    public void setEndedDateTime(Date endedDateTime) {
        this.endedDateTime = endedDateTime;
    }

    public float getMarks() {
        return marks;
    }

    public void setMarks(float marks) {
        this.marks = marks;
    }
}
