package com.example.examinapp.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.Handler;
import android.os.Looper;

import com.example.examinapp.consts.ExamInApplication;
import com.example.examinapp.dataaccess.ExamInAppDBHandler;
import com.example.examinapp.dataaccess.dtos.exam.ExamResponse;
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
    private MutableLiveData<LoadingInformationModel> _examsLoadingInformationModelData = new MutableLiveData<>();
    private MutableLiveData<List<ExamModel>> _examList = new MutableLiveData<>();

    private void makeExamListAdaper() {
        final List<ExamModel> exams = new ArrayList<>();

        if (_exams != null) {

            String searchText = _searchText;

            if (searchText == null) {
                searchText = "";
            }
            else {
                searchText = searchText.trim().toLowerCase();
            }

            for(int i = 0, size = _exams.size(); i < size; i++) {
                ExamModel exam = _exams.get(i);

                if (exam == null) {
                    continue;
                }

                if (searchText.equals("") ||
                        exam.getName().toLowerCase().contains(searchText) ||
                        exam.getDescription().toLowerCase().contains(searchText)) {
                    exams.add(exam);
                }
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

    public LiveData<LoadingInformationModel> getExamsLoadingInformationModelData() {
        return _examsLoadingInformationModelData;
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

        _examsLoadingInformationModel = new LoadingInformationModel(false, false,true, null);
        _examsLoadingInformationModelData.setValue(_examsLoadingInformationModel);
    }

    public void navigateToAddNewExam() {
        _nextScreenEnum.setValue(NextScreenEnum.AddNewExam);
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

    public synchronized boolean setMainActivityViewEnumAndRequestData(final MainActivityViewEnum mainActivityViewEnum) {

        if (_examsLoadingInformationModel.getIsPending()) {
            return false;
        }

        _examsLoadingInformationModel.setIsPending(true);
        _examsLoadingInformationModel.setIsError(false);
        _examsLoadingInformationModel.setIsSucess(false);
        _examsLoadingInformationModel.setMessage("");

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                _examsLoadingInformationModelData.setValue(_examsLoadingInformationModel);
                _mainActivityViewEnum.setValue(mainActivityViewEnum);
            }
        });

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    ExamResponse examResponse = null;

                    if (mainActivityViewEnum == MainActivityViewEnum.LecturerAllExams) {
                        examResponse = ExamInApplication.UNIT_OF_WORK.getExamRepository().getAllExamsAsync();
                    }
                    else if (mainActivityViewEnum == MainActivityViewEnum.LecturerMyExams) {
                        examResponse = ExamInApplication.UNIT_OF_WORK.getExamRepository().getExamsByLectureIdAsync(getLoggedInUserModel().getId());
                    }
                    else if (mainActivityViewEnum == MainActivityViewEnum.StudentAllExams) {
                        examResponse = ExamInApplication.UNIT_OF_WORK.getExamRepository().getAllPublishedExamsAsync();
                    }
                    else if (mainActivityViewEnum == MainActivityViewEnum.StudentEntrolledExams) {
//                        examResponse = ExamInApplication.UNIT_OF_WORK.getExamRepository().getAllExamsAsync();
                    }
                    else {
                        throw new Exception("Invalid view switch");
                    }

                    _examsLoadingInformationModel.setIsSucess(examResponse.getIsSuccess());
                    _examsLoadingInformationModel.setIsError(!examResponse.getIsSuccess());

                    if (!examResponse.getIsSuccess()) {
                        _examsLoadingInformationModel.setMessage(examResponse.getMessage());
                    }
                    else {
                        _exams = examResponse.getExams();
                        makeExamListAdaper();
                    }
                }
                catch (Exception ex) {
                    _examsLoadingInformationModel.setMessage("Something went wrong");
                    _examsLoadingInformationModel.setIsSucess(false);
                    _examsLoadingInformationModel.setIsError(true);
                }

                _examsLoadingInformationModel.setIsPending(false);

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    public void run() {
                        _examsLoadingInformationModelData.setValue(_examsLoadingInformationModel);
                    }
                });
            }
        });

        thread.start();

        return true;
    }
}
