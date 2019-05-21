package com.example.examinapp.consts;

import android.app.Application;
import android.content.Context;

import com.example.examinapp.activities.MainActivity;
import com.example.examinapp.dataaccess.UnitOfWork;
import com.example.examinapp.models.UserModel;

import okhttp3.MediaType;


public class ExamInApplication extends Application {

    private static ExamInApplication _examInApplication;

    public static final String EXAM_ID = "ExamInApp_EXAM_ID";

    public static final String BASE_URL = "http://10.0.2.2:5000/api/";
    public static final MediaType JSON_HEADER = MediaType.parse("application/json; charset=utf-8");

    public static final UnitOfWork UNIT_OF_WORK = UnitOfWork.getInstance();

    private static MainActivity _mainActivity;
    private static UserModel _loggedInUserModel;

    @Override
    public void onCreate() {
        super.onCreate();

        _examInApplication = this;
    }

    public static ExamInApplication getCreatedInstance() {
        return _examInApplication;
    }

    public static MainActivity getMainActivity() {
        return _mainActivity;
    }

    public static void setMainActivity(MainActivity mainActivity) {
        _mainActivity = mainActivity;
    }

    public static UserModel getLoggedInUserModel() {
        return _loggedInUserModel;
    }

    public static void setLoggedInUserModel(UserModel loggedInUserModel) {
        _loggedInUserModel = loggedInUserModel;
    }
}
