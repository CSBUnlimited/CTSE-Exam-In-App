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
import com.example.examinapp.enums.NextScreenEnum;
import com.example.examinapp.enums.UserTypeEnum;
import com.example.examinapp.models.UserModel;

public class SplashViewModel extends ViewModel {

    private ExamInAppDBHandler _examInAppDBHandle;

    private MutableLiveData<Boolean> _isShowLoading = new MutableLiveData<>();
    private MutableLiveData<NextScreenEnum> _nextScreenEnum = new MutableLiveData<>();

    public void init() {
        _isShowLoading.setValue(false);
        _nextScreenEnum.setValue(NextScreenEnum.None);
    }

    public LiveData<Boolean> getIsShowLoading() {
        return _isShowLoading;
    }

    public LiveData<NextScreenEnum> getNextScreenEnum() {
        return _nextScreenEnum;
    }

    public void getLoggedInUserData(Context applicationContext) {
        _examInAppDBHandle = new ExamInAppDBHandler(applicationContext);

        try {
            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    }
                    catch (Exception ex) {
                    }

                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        public void run() {
                            _isShowLoading.setValue(true);
                        }
                    });

                    UserModel userModel = _examInAppDBHandle.getLoggedInUserModel();
                    NextScreenEnum nextScreenEnum = NextScreenEnum.None;

                    try {
                        if (userModel != null) {
                            LoginResponse loginResponse = ExamInApplication.UNIT_OF_WORK.getLoginRepository().getLoggedInUserByUsernameAndSessionIdAsync(userModel.getUsername(), userModel.getSessionId());

                            if (!loginResponse.getIsSuccess()) {
                                nextScreenEnum = NextScreenEnum.Login;
                            }
                            else {
                                if (userModel.getUserTypeEnum() == UserTypeEnum.Lecturer) {
                                    nextScreenEnum = NextScreenEnum.MainLecturer;
                                }
                                else {
                                    nextScreenEnum = NextScreenEnum.MainStudent;
                                }
                            }
                        }
                        else {
                            nextScreenEnum = NextScreenEnum.Login;
                        }
                    }
                    catch (Exception ex) {
                        nextScreenEnum = NextScreenEnum.SomeThingWentWrong;
                    }

                    final NextScreenEnum nextScreenEnumFinal = nextScreenEnum;
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        public void run() {
                            _nextScreenEnum.setValue(nextScreenEnumFinal);
                            _isShowLoading.setValue(false);
                        }
                    });
                }
            });

            thread.start();
        }
        catch (Exception ex) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                public void run() {
                    _nextScreenEnum.setValue(NextScreenEnum.SomeThingWentWrong);
                    _isShowLoading.setValue(false);
                }
            });
        }
    }
}
