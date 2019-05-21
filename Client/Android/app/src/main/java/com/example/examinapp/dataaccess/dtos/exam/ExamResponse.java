package com.example.examinapp.dataaccess.dtos.exam;

import com.example.examinapp.dataaccess.dtos.base.BaseResponse;
import com.example.examinapp.models.ExamModel;

import java.util.Date;
import java.util.List;

public class ExamResponse extends BaseResponse {
    private List<ExamModel> exams;

    public ExamResponse(boolean isSuccess, int status, String message, Date respondDateTime, List<ExamModel> exams) {
        super(isSuccess, status, message, respondDateTime);
        this.exams = exams;
    }

    public List<ExamModel> getExams() {
        return exams;
    }

    public void setExams(List<ExamModel> exams) {
        this.exams = exams;
    }
}
