package com.example.examinapp.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.example.examinapp.consts.ExamInApplication;
import com.example.examinapp.dataaccess.ExamInAppDBHandler;
import com.example.examinapp.dataaccess.dtos.login.LoginResponse;
import com.example.examinapp.models.LoadingInformationModel;

public class LoginViewModel extends ViewModel {

    private LoadingInformationModel _loadingInformationModel;
    private MutableLiveData<LoadingInformationModel> _loadingInformationModelData = new MutableLiveData<>();

    public LiveData<LoadingInformationModel> getLoadingInformationModelData() {
        return _loadingInformationModelData;
    }

    public void init() {
        _loadingInformationModel = new LoadingInformationModel();
        _loadingInformationModelData.setValue(_loadingInformationModel);
    }

    public void login(Context applicationContext, final String username, final String password) {

        final ExamInAppDBHandler examInAppDBHandle = new ExamInAppDBHandler(applicationContext);

        _loadingInformationModel.setIsPending(true);
        _loadingInformationModel.setIsSucess(false);
        _loadingInformationModel.setIsError(false);

        _loadingInformationModelData.setValue(_loadingInformationModel);

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    LoginResponse loginResponse = ExamInApplication.UNIT_OF_WORK.getLoginRepository().loginAsync(username, password);
                    _loadingInformationModel.setIsSucess(loginResponse.getIsSuccess());
                    _loadingInformationModel.setIsError(!loginResponse.getIsSuccess());

                    if (!loginResponse.getIsSuccess()) {
                        _loadingInformationModel.setMessage(loginResponse.getMessage());
                    }
                    else {
                        examInAppDBHandle.newLoggedInUserModel(loginResponse.getUser());
                    }
                }
                catch (Exception ex) {
                    _loadingInformationModel.setMessage("Something went wrong");
                    _loadingInformationModel.setIsSucess(false);
                    _loadingInformationModel.setIsError(true);
                }

                _loadingInformationModel.setIsPending(false);

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    public void run() {
                        _loadingInformationModelData.setValue(_loadingInformationModel);
                    }
                });
            }
        });

        thread.start();
    }
}
