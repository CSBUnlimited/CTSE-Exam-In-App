package com.example.examinapp.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.Handler;
import android.os.Looper;

import com.example.examinapp.adapters.ExamListAdapter;
import com.example.examinapp.consts.ExamInApplication;
import com.example.examinapp.dataaccess.ExamInAppDBHandler;
import com.example.examinapp.enums.MainActivityViewEnum;
import com.example.examinapp.enums.NextScreenEnum;
import com.example.examinapp.models.ExamModel;
import com.example.examinapp.models.LoadingInformationModel;
import com.example.examinapp.models.UserModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivityViewModel extends ViewModel {

    private LoadingInformationModel _examsLoadingInformationModel;
    private List<ExamModel> _exams;
    private String _searchText;

    private MutableLiveData<NextScreenEnum> _nextScreenEnum = new MutableLiveData<>();
    private MutableLiveData<MainActivityViewEnum> _mainActivityViewEnum = new MutableLiveData<>();
    private MutableLiveData<String> _examListDescription = new MutableLiveData<>();
    private MutableLiveData<List<ExamModel>> _examList = new MutableLiveData<>();

    private void makeExamListAdaper() {
        final List<ExamModel> exams = new ArrayList<>();

        if (_exams != null) {
            for(int i = 0, size = _exams.size(); i < size; i++) {
                ExamModel exam = _exams.get(i);

                if (exam == null) {
                    continue;
                }

                exams.add(exam);
            }
        }

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                _examList.setValue(exams);
            }
        });
    }

    public void setSearchText(String searchText) {
        _searchText = searchText;

        makeExamListAdaper();
    }

    public LiveData<NextScreenEnum> getNextScreenEnum() {
        return _nextScreenEnum;
    }

    public LiveData<MainActivityViewEnum> getMainActivityViewEnum() {
        return _mainActivityViewEnum;
    }

    public LiveData<String> getExamListDescription() {
        return _examListDescription;
    }

    public LiveData<List<ExamModel>> getExamList() {
        return _examList;
    }

    public UserModel getLoggedInUserModel() {
        return ExamInApplication.getLoggedInUserModel();
    }

    public void init() {
        _nextScreenEnum.setValue(NextScreenEnum.None);

        _examsLoadingInformationModel = new LoadingInformationModel(false, false,false, null);
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

    public boolean setMainActivityViewEnumAndRequestData(final MainActivityViewEnum mainActivityViewEnum) {

        if (_examsLoadingInformationModel.getIsPending()) {
            return false;
        }

        _examsLoadingInformationModel.setIsPending(true);
        _examsLoadingInformationModel.setIsError(false);
        _examsLoadingInformationModel.setIsSucess(false);
        _examsLoadingInformationModel.setMessage("");

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                _mainActivityViewEnum.setValue(mainActivityViewEnum);
            }
        });

        return true;
    }
}
