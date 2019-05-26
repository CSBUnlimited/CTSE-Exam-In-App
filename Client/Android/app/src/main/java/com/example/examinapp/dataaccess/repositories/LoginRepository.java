package com.example.examinapp.dataaccess.repositories;

import com.example.examinapp.consts.ExamInApplication;
import com.example.examinapp.dataaccess.dtos.base.BaseRequest;
import com.example.examinapp.dataaccess.dtos.login.LoginRequest;
import com.example.examinapp.dataaccess.dtos.login.LoginResponse;
import com.google.gson.Gson;

import java.util.Date;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginRepository {

    private static String LOGIN_CONTROLLER_NAME = "Login/";

    public LoginResponse loginAsync(String username, String password) throws Exception {

        Gson gson = new Gson();
        LoginRequest loginRequest = new LoginRequest(new Date(), username, null, password);
        String loginRequestString = gson.toJson(loginRequest);

        RequestBody requestBody = RequestBody.create(ExamInApplication.JSON_HEADER, loginRequestString);

        String url = ExamInApplication.BASE_URL + LOGIN_CONTROLLER_NAME + "LoginAsync";
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();

        return gson.fromJson(response.body().string(), LoginResponse.class);
    }

    public LoginResponse logoutAsync(String username, String sessionId) throws Exception {

        Gson gson = ExamInApplication.getGsonObject();
        BaseRequest baseRequest = new BaseRequest(new Date(), username, sessionId);
        String baseRequestString = gson.toJson(baseRequest);

        RequestBody requestBody = RequestBody.create(ExamInApplication.JSON_HEADER, baseRequestString);

        String url = ExamInApplication.BASE_URL + LOGIN_CONTROLLER_NAME + "LogoutAsync";
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();

        return gson.fromJson(response.body().string(), LoginResponse.class);
    }

    public LoginResponse getLoggedInUserByUsernameAndSessionIdAsync(String username, String sessionId) throws Exception {

        Gson gson = ExamInApplication.getGsonObject();

        String url = ExamInApplication.BASE_URL + LOGIN_CONTROLLER_NAME + "GetLoggedInUserByUsernameAndSessionIdAsync/" + username + "," + sessionId;
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        Response response = client.newCall(request).execute();

        return gson.fromJson(response.body().string(), LoginResponse.class);
    }
}
