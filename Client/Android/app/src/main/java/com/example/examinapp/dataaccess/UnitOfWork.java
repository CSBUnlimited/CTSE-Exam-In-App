package com.example.examinapp.dataaccess;

import com.example.examinapp.dataaccess.repositories.LoginRepository;

public class UnitOfWork {
    private static UnitOfWork _unitOfWork;

    private LoginRepository loginRepository;

    private UnitOfWork() {
        this.loginRepository = new LoginRepository();
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
}
