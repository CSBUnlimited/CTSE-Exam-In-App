package com.example.examinapp.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.example.examinapp.adapters.ExamListAdapter;
import com.example.examinapp.consts.ExamInApplication;
import com.example.examinapp.dataaccess.ExamInAppDBHandler;
import com.example.examinapp.enums.NextScreenEnum;
import com.example.examinapp.models.LoadingInformationModel;
import com.example.examinapp.models.UserModel;

public class MainActivityViewModel extends ViewModel {

    private MutableLiveData<NextScreenEnum> _nextScreenEnum = new MutableLiveData<>();

    private MutableLiveData<ExamListAdapter> _examListAdapter = new MutableLiveData<>();

    private LoadingInformationModel _allExamsLoadingInformationModel;
    private LoadingInformationModel _entrolledExamsLoadingInformationModel;
    private LoadingInformationModel _myAllExamsLoadingInformationModel;

    private MutableLiveData<LoadingInformationModel> _allExamsLoadingInformationModelData = new MutableLiveData<>();
    private MutableLiveData<LoadingInformationModel> _entrolledExamsLoadingInformationModelData = new MutableLiveData<>();
    private MutableLiveData<LoadingInformationModel> _myAllExamsLoadingInformationModelData = new MutableLiveData<>();

    public UserModel getLoggedInUserModel() {
        return ExamInApplication.getLoggedInUserModel();
    }

    public LiveData<NextScreenEnum> getNextScreenEnum() {
        return _nextScreenEnum;
    }

    public LiveData<LoadingInformationModel> getAllExamsLoadingInformationModelData() {
        return _allExamsLoadingInformationModelData;
    }

    public LiveData<LoadingInformationModel> getEntrolledExamsLoadingInformationModelData() {
        return _entrolledExamsLoadingInformationModelData;
    }

    public LiveData<LoadingInformationModel> getMyAllExamsLoadingInformationModelData() {
        return _myAllExamsLoadingInformationModelData;
    }

    public LiveData<ExamListAdapter> getExamListAdapter() {
        return _examListAdapter;
    }

    public void init() {
        _nextScreenEnum.setValue(NextScreenEnum.None);

        _allExamsLoadingInformationModel = new LoadingInformationModel(false, false,false, null);
        _entrolledExamsLoadingInformationModel = new LoadingInformationModel(false, false,false, null);
        _myAllExamsLoadingInformationModel = new LoadingInformationModel(false, false,false, null);

        _allExamsLoadingInformationModelData.setValue(_allExamsLoadingInformationModel);
        _entrolledExamsLoadingInformationModelData.setValue(_entrolledExamsLoadingInformationModel);
        _myAllExamsLoadingInformationModelData.setValue(_myAllExamsLoadingInformationModel);
    }

    public void logoutLoggedInUser() {
        final ExamInAppDBHandler examInAppDBHandle = new ExamInAppDBHandler(ExamInApplication.getCreatedInstance().getApplicationContext());
        final UserModel userModel = getLoggedInUserModel();

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    ExamInApplication.UNIT_OF_WORK.getLoginRepository().logoutAsync(userModel.getUsername(), userModel.getSessionId());
                }
                catch (Exception ex) { }
            }
        });

        thread.start();

        examInAppDBHandle.deleteLoggedInUserModels();
        ExamInApplication.setLoggedInUserModel(null);

        _nextScreenEnum.setValue(NextScreenEnum.Splash);
    }
}
