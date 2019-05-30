package com.example.examinapp.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.Handler;
import android.os.Looper;

import com.example.examinapp.consts.ExamInApplication;
import com.example.examinapp.dataaccess.dtos.exam.ExamResponse;
import com.example.examinapp.dataaccess.dtos.question.QuestionResponse;
import com.example.examinapp.enums.LectureExamTabViewEnum;
import com.example.examinapp.enums.NextScreenEnum;
import com.example.examinapp.models.ExamModel;
import com.example.examinapp.models.LoadingInformationModel;
import com.example.examinapp.models.UserModel;

public class LecturerExamViewModel extends ViewModel {

    private int examId;
    private ExamModel exam;

    private LoadingInformationModel loadingExam;
    private LoadingInformationModel loadingTab;

    private MutableLiveData<NextScreenEnum> nextScreenEnumData = new MutableLiveData<>();

    private MutableLiveData<LoadingInformationModel> loadingExamData = new MutableLiveData<>();
    private MutableLiveData<LoadingInformationModel> loadingTabData = new MutableLiveData<>();

    private MutableLiveData<ExamModel> examData = new MutableLiveData<>();

    public UserModel getLoggedInUser() {
        return ExamInApplication.getLoggedInUserModel();
    }

    public ExamModel getExam() {
        return exam;
    }

    public LiveData<LoadingInformationModel> getLoadingExamData() {
        return loadingExamData;
    }

    public LiveData<LoadingInformationModel> getLoadingTabData() {
        return loadingTabData;
    }

    public LiveData<ExamModel> getExamData() {
        return examData;
    }

    public void init(int examId) {
        this.examId = examId;

        loadingExam = new LoadingInformationModel(false, false, false, "");
        loadingTab = new LoadingInformationModel(false, false, false, "");
    }

    public void getExamWithNoQuestionsData() {

        if (loadingExam.getIsPending()) {
            return;
        }

        loadingExam.setIsPending(true);
        loadingExam.setIsError(false);
        loadingExam.setIsSucess(false);
        loadingExam.setMessage("");

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                loadingExamData.setValue(loadingExam);
            }
        });

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    ExamResponse examResponse = ExamInApplication.UNIT_OF_WORK.getExamRepository().getExamNoQuestionsByIdAsync(examId);

                    if (!examResponse.getIsSuccess()) {
                        loadingExam.setIsSucess(false);
                        loadingExam.setIsError(true);
                        loadingExam.setMessage("Something went wrong!");
                    }
                    else {
                        loadingExam.setIsSucess(true);
                        loadingExam.setIsError(false);
                        loadingExam.setMessage("");

                        if (examResponse.getExams() == null || examResponse.getExams().get(0) == null) {
                            exam = null;
                        }
                        else {
                            exam = examResponse.getExams().get(0);
                        }
                    }
                }
                catch (Exception ex) {
                    loadingExam.setIsSucess(false);
                    loadingExam.setIsError(true);
                    loadingExam.setMessage("Something went wrong!");
                }

                loadingExam.setIsPending(false);

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    public void run() {
                        if (loadingExam.getIsSucess()) {
                            examData.setValue(exam);
                        }

                        loadingExamData.setValue(loadingExam);
                    }
                });
            }
        });

        thread.start();
    }

    public void getTabDataByTabEnum(final LectureExamTabViewEnum lectureExamTabViewEnum) {

        if (loadingTab.getIsPending()) {
            return;
        }

        loadingTab.setIsPending(true);
        loadingTab.setIsError(false);
        loadingTab.setIsSucess(false);
        loadingTab.setMessage("");

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                loadingTabData.setValue(loadingTab);
            }
        });

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {

                    if (lectureExamTabViewEnum == LectureExamTabViewEnum.Questions) {

                        QuestionResponse questionResponse = ExamInApplication.UNIT_OF_WORK.getQuestionRepository().getQuestionsByExamIdAsync(examId);

                        if (!questionResponse.getIsSuccess()) {
                            loadingTab.setIsSucess(false);
                            loadingTab.setIsError(true);
                            loadingTab.setMessage("Something went wrong!");
                        }
                        else {
                            loadingTab.setIsSucess(true);
                            loadingTab.setIsError(false);
                            loadingTab.setMessage("");

                            exam.setQuestions(questionResponse.getQuestions());
                        }
                    }

                }
                catch (Exception ex) {
                    loadingTab.setIsSucess(false);
                    loadingTab.setIsError(true);
                    loadingTab.setMessage("Something went wrong!");
                }

                loadingTab.setIsPending(false);

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    public void run() {
                        if (loadingTab.getIsSucess()) {
                            examData.setValue(exam);
                        }

                        loadingTabData.setValue(loadingTab);
                    }
                });
            }
        });

        thread.start();
    }
}
