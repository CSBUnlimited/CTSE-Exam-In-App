package com.example.examinapp.dataaccess.repositories;

import com.example.examinapp.consts.ExamInApplication;
import com.example.examinapp.dataaccess.dtos.base.BaseRequest;
import com.example.examinapp.dataaccess.dtos.question.QuestionRequest;
import com.example.examinapp.dataaccess.dtos.question.QuestionResponse;
import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class QuestionRepository {

    private static String QUESTION_CONTROLLER_NAME = "Question/";

    public QuestionResponse getQuestionsByExamIdAsync(int examId) throws Exception {

        Gson gson = ExamInApplication.getGsonObject();

        String url = ExamInApplication.BASE_URL + QUESTION_CONTROLLER_NAME + "GetQuestionsByExamIdAsync/" + examId;
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        Response response = client.newCall(request).execute();

        return gson.fromJson(response.body().string(), QuestionResponse.class);
    }

    public QuestionResponse getQuestionByIdAsync(int id) throws Exception {

        Gson gson = ExamInApplication.getGsonObject();

        String url = ExamInApplication.BASE_URL + QUESTION_CONTROLLER_NAME + "GetQuestionByIdAsync/" + id;
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        Response response = client.newCall(request).execute();

        return gson.fromJson(response.body().string(), QuestionResponse.class);
    }

    public QuestionResponse addQuestionAsync(QuestionRequest questionRequest) throws Exception {

        Gson gson = ExamInApplication.getGsonObject();
        String examRequestString = gson.toJson(questionRequest);

        RequestBody requestBody = RequestBody.create(ExamInApplication.JSON_HEADER, examRequestString);

        String url = ExamInApplication.BASE_URL + QUESTION_CONTROLLER_NAME + "AddQuestionAsync";
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();

        return gson.fromJson(response.body().string(), QuestionResponse.class);
    }

    public QuestionResponse updateQuestionAsync(QuestionRequest examRequest) throws Exception {

        Gson gson = ExamInApplication.getGsonObject();
        String examRequestString = gson.toJson(examRequest);

        RequestBody requestBody = RequestBody.create(ExamInApplication.JSON_HEADER, examRequestString);

        String url = ExamInApplication.BASE_URL + QUESTION_CONTROLLER_NAME + "UpdateQuestionAsync";
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .put(requestBody)
                .build();

        Response response = client.newCall(request).execute();

        return gson.fromJson(response.body().string(), QuestionResponse.class);
    }

    public QuestionResponse deleteQuestionByIdAsync(int examId, BaseRequest baseRequest) throws Exception {

        Gson gson = ExamInApplication.getGsonObject();
        String baseRequestString = gson.toJson(baseRequest);

        RequestBody requestBody = RequestBody.create(ExamInApplication.JSON_HEADER, baseRequestString);

        String url = ExamInApplication.BASE_URL + QUESTION_CONTROLLER_NAME + "DeleteQuestionByIdAsync/" + examId;
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .put(requestBody)
                .build();

        Response response = client.newCall(request).execute();

        return gson.fromJson(response.body().string(), QuestionResponse.class);
    }
}
