package com.example.examinapp.dataaccess;

import com.example.examinapp.dataaccess.repositories.ExamRepository;
import com.example.examinapp.dataaccess.repositories.LoginRepository;
import com.example.examinapp.dataaccess.repositories.QuestionRepository;

public class UnitOfWork {
    private static UnitOfWork _unitOfWork;

    private LoginRepository loginRepository;
    private ExamRepository examRepository;
    private QuestionRepository questionRepository;

    private UnitOfWork() {
        this.loginRepository = new LoginRepository();
        this.examRepository = new ExamRepository();
        this.questionRepository = new QuestionRepository();
    }

    public static UnitOfWork getInstance() {
        if (_unitOfWork == null) {
            _unitOfWork = new UnitOfWork();
        }

        return _unitOfWork;
    }

    public LoginRepository getLoginRepository() {
        return loginRepository;
    }

    public ExamRepository getExamRepository() {
        return examRepository;
    }

    public QuestionRepository getQuestionRepository() {
        return questionRepository;
    }
}
