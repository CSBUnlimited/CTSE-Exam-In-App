package com.example.examinapp.models;

import java.util.Date;
import java.util.List;

public class ExamModel {

    private int id;
    private String name;
    private String description;
    private int givenTimeSeconds;
    private boolean isPublish;
    private float marks;

    private Date effectiveDateTime;
    private Date expireDateTime;

    private int lecturerUserId;
    private UserModel lecturerUser;

    private List<QuestionModel> Questions;
    private List<StudentExamModel> StudentExams;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getGivenTimeSeconds() {
        return givenTimeSeconds;
    }

    public void setGivenTimeSeconds(int givenTimeSeconds) {
        this.givenTimeSeconds = givenTimeSeconds;
    }

    public boolean isPublish() {
        return isPublish;
    }

    public void setPublish(boolean publish) {
        isPublish = publish;
    }

    public float getMarks() {
        return marks;
    }

    public void setMarks(float marks) {
        this.marks = marks;
    }

    public Date getEffectiveDateTime() {
        return effectiveDateTime;
    }

    public void setEffectiveDateTime(Date effectiveDateTime) {
        this.effectiveDateTime = effectiveDateTime;
    }

    public Date getExpireDateTime() {
        return expireDateTime;
    }

    public void setExpireDateTime(Date expireDateTime) {
        this.expireDateTime = expireDateTime;
    }

    public int getLecturerUserId() {
        return lecturerUserId;
    }

    public void setLecturerUserId(int lecturerUserId) {
        this.lecturerUserId = lecturerUserId;
    }

    public UserModel getLecturerUser() {
        return lecturerUser;
    }

    public void setLecturerUser(UserModel lecturerUser) {
        this.lecturerUser = lecturerUser;
    }

    public List<QuestionModel> getQuestions() {
        return Questions;
    }

    public void setQuestions(List<QuestionModel> questions) {
        Questions = questions;
    }

    public List<StudentExamModel> getStudentExams() {
        return StudentExams;
    }

    public void setStudentExams(List<StudentExamModel> studentExams) {
        StudentExams = studentExams;
    }
}
