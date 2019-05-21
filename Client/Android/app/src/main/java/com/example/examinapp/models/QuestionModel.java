package com.example.examinapp.models;

import java.util.List;

public class QuestionModel {
    private int id;
    private String description;
    private float marks;

    private int examId;
    private ExamModel exam;

    private List<AnswerModel> answers;
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

    public float getMarks() {
        return marks;
    }

    public void setMarks(float marks) {
        this.marks = marks;
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

    public List<AnswerModel> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerModel> answers) {
        this.answers = answers;
    }

    public List<StudentQuestionAnswerModel> getStudentQuestionAnswers() {
        return studentQuestionAnswers;
    }

    public void setStudentQuestionAnswers(List<StudentQuestionAnswerModel> studentQuestionAnswers) {
        this.studentQuestionAnswers = studentQuestionAnswers;
    }
}
