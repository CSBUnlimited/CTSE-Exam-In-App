package com.example.examinapp.dataaccess.dtos.exam;

import com.example.examinapp.dataaccess.dtos.base.BaseRequest;
import com.example.examinapp.models.ExamModel;

import java.util.Date;

public class ExamRequest extends BaseRequest {
    private ExamModel exam;

    public ExamRequest(Date requestedDateTime, String username, String sessionId, ExamModel exam) {
        super(requestedDateTime, username, sessionId);
        this.exam = exam;
    }

    public ExamModel getExam() {
        return exam;
    }

    public void setExam(ExamModel exam) {
        this.exam = exam;
    }
}
