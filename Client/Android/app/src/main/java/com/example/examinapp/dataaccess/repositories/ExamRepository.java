package com.example.examinapp.dataaccess.repositories;

import com.example.examinapp.consts.ExamInApplication;
import com.example.examinapp.dataaccess.dtos.base.BaseRequest;
import com.example.examinapp.dataaccess.dtos.exam.ExamRequest;
import com.example.examinapp.dataaccess.dtos.exam.ExamResponse;
import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ExamRepository {
    private static String EXAM_CONTROLLER_NAME = "Exam/";

    public ExamResponse getAllExamsAsync() throws Exception {

        Gson gson = new Gson();

        String url = ExamInApplication.BASE_URL + EXAM_CONTROLLER_NAME + "GetAllExamsAsync";
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        Response response = client.newCall(request).execute();

        return gson.fromJson(response.body().string(), ExamResponse.class);
    }

    public ExamResponse getAllPublishedExamsAsync() throws Exception {

        Gson gson = new Gson();

        String url = ExamInApplication.BASE_URL + EXAM_CONTROLLER_NAME + "GetAllPublishedExamsAsync";
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        Response response = client.newCall(request).execute();

        return gson.fromJson(response.body().string(), ExamResponse.class);
    }

    public ExamResponse getExamsByLectureIdAsync(int lecturerId) throws Exception {

        Gson gson = new Gson();

        String url = ExamInApplication.BASE_URL + EXAM_CONTROLLER_NAME + "GetAllPublishedExamsAsync/" + lecturerId;
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        Response response = client.newCall(request).execute();

        return gson.fromJson(response.body().string(), ExamResponse.class);
    }

    public ExamResponse getExamByIdAsync(int id) throws Exception {

        Gson gson = new Gson();

        String url = ExamInApplication.BASE_URL + EXAM_CONTROLLER_NAME + "GetExamByIdAsync/" + id;
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        Response response = client.newCall(request).execute();

        return gson.fromJson(response.body().string(), ExamResponse.class);
    }

    public ExamResponse addExamAsync(ExamRequest examRequest) throws Exception {

        Gson gson = new Gson();
        String examRequestString = gson.toJson(examRequest);

        RequestBody requestBody = RequestBody.create(ExamInApplication.JSON_HEADER, examRequestString);

        String url = ExamInApplication.BASE_URL + EXAM_CONTROLLER_NAME + "AddExamAsync";
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();

        return gson.fromJson(response.body().string(), ExamResponse.class);
    }

    public ExamResponse updateExamAsync(ExamRequest examRequest) throws Exception {

        Gson gson = new Gson();
        String examRequestString = gson.toJson(examRequest);

        RequestBody requestBody = RequestBody.create(ExamInApplication.JSON_HEADER, examRequestString);

        String url = ExamInApplication.BASE_URL + EXAM_CONTROLLER_NAME + "UpdateExamAsync";
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .put(requestBody)
                .build();

        Response response = client.newCall(request).execute();

        return gson.fromJson(response.body().string(), ExamResponse.class);
    }

    public ExamResponse deleteExamByIdAsync(int examId, BaseRequest baseRequest) throws Exception {

        Gson gson = new Gson();
        String baseRequestString = gson.toJson(baseRequest);

        RequestBody requestBody = RequestBody.create(ExamInApplication.JSON_HEADER, baseRequestString);

        String url = ExamInApplication.BASE_URL + EXAM_CONTROLLER_NAME + "DeleteExamByIdAsync/" + examId;
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .put(requestBody)
                .build();

        Response response = client.newCall(request).execute();

        return gson.fromJson(response.body().string(), ExamResponse.class);
    }
}
