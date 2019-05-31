package com.example.examinapp.models;

import java.util.Calendar;
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

    private List<QuestionModel> questions;
    private List<StudentExamModel> studentExams;

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

    public boolean getIsPublish() {
        return isPublish;
    }

    public void setIsPublish(boolean publish) {
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
        return questions;
    }

    public void setQuestions(List<QuestionModel> questions) {
        this.questions = questions;
    }

    public List<StudentExamModel> getStudentExams() {
        return studentExams;
    }

    public void setStudentExams(List<StudentExamModel> studentExams) {
        this.studentExams = studentExams;
    }

    public ExamModel() { }

    public ExamModel(int id, String name, String description, int givenTimeSeconds, boolean isPublish, int lecturerUserId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.givenTimeSeconds = givenTimeSeconds;
        this.isPublish = isPublish;
        this.lecturerUserId = lecturerUserId;
        this.effectiveDateTime = new Date();

        Calendar cal = Calendar.getInstance();
        cal.setTime(this.effectiveDateTime);
        cal.add(Calendar.DATE, 1);
        this.expireDateTime = cal.getTime();
    }
}
