package com.example.examinapp.viewmodels;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.example.examinapp.consts.ExamInApplication;
import com.example.examinapp.dataaccess.dtos.exam.ExamResponse;
import com.example.examinapp.enums.MainActivityViewEnum;
import com.example.examinapp.models.ExamModel;
import com.example.examinapp.models.LoadingInformationModel;
import com.example.examinapp.models.UserModel;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ManageExamViewModel extends ViewModel {

    private int _examId;
    private ExamModel _exam;
    private UserModel _loggedInUser;

    private DatePickerDialog _datePickerDialog;
    private TimePickerDialog _timePickerDialog;
    private Calendar _selectedDateTimeCalendar;

    private LoadingInformationModel _gettingExamInfo;
    private LoadingInformationModel _savingExamInfo;

    private MutableLiveData<ExamModel> _examModelData = new MutableLiveData<>();

    private MutableLiveData<LoadingInformationModel> _gettingExamInfoData = new MutableLiveData<>();
    private MutableLiveData<LoadingInformationModel> _savingExamInfoData = new MutableLiveData<>();

    private DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            _selectedDateTimeCalendar = Calendar.getInstance();
            _selectedDateTimeCalendar.set(year, month, dayOfMonth);

            _timePickerDialog.show();
        }
    };

    private TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            Calendar calendar = _selectedDateTimeCalendar;
            _selectedDateTimeCalendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), hourOfDay, minute);
            _selectedDateTimeData.setValue(calendar.getTime());
        }
    };

    private MutableLiveData<Date> _selectedDateTimeData;

    public LiveData<Date> pickDateTime(Context activityContext, final Date initialDate) {
        _selectedDateTimeData = new MutableLiveData<>();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(initialDate);

        _datePickerDialog = new DatePickerDialog(activityContext, onDateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        _timePickerDialog = new TimePickerDialog(activityContext, onTimeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);

        DialogInterface.OnCancelListener onCancelListener = new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
                _selectedDateTimeData.setValue(initialDate);
            }
        };

        _datePickerDialog.setOnCancelListener(onCancelListener);
        _timePickerDialog.setOnCancelListener(onCancelListener);

        _datePickerDialog.show();

        return _selectedDateTimeData;
    }

    public void init(int examId) {
        _examId = examId;

        _loggedInUser = ExamInApplication.getLoggedInUserModel();
        _savingExamInfo = new LoadingInformationModel(false, false, false, "");
        _gettingExamInfo = new LoadingInformationModel(false, false, false, "");

        initializeExamData();
    }

    public LiveData<LoadingInformationModel> getGettingExamInfoData() {
        return _gettingExamInfoData;
    }

    public LiveData<LoadingInformationModel> getSavingExamInfoData() {
        return _savingExamInfoData;
    }

    public LiveData<ExamModel> getExamModelData() {
        return _examModelData;
    }

    public ExamModel getExamModel() {
        return _exam;
    }

    public void setExamModelName(String name) {
        _exam.setName(name);
        _examModelData.setValue(_exam);
    }

    public void setExamModelDescription(String description) {
        _exam.setDescription(description);
        _examModelData.setValue(_exam);
    }

    public void setExamModelGivenTimeMinutes(int givenMinutes) {
        _exam.setGivenTimeSeconds(givenMinutes * 60);
        _examModelData.setValue(_exam);
    }

    public void setExamModelEffectiveDateTime(Date effectiveDateTime) {
        _exam.setEffectiveDateTime(effectiveDateTime);
        _examModelData.setValue(_exam);
    }

    public void setExamModelExpireDateTime(Date expireDateTime) {
        _exam.setExpireDateTime(expireDateTime);
        _examModelData.setValue(_exam);
    }

    public void setExamModelIsPublish(boolean isPublish) {
        _exam.setIsPublish(isPublish);
        _examModelData.setValue(_exam);
    }

    public void initializeExamData() {

        _gettingExamInfo.setIsPending(true);
        _gettingExamInfo.setIsSucess(false);
        _gettingExamInfo.setIsError(false);
        _gettingExamInfo.setMessage("");

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                _gettingExamInfoData.setValue(_gettingExamInfo);
            }
        });

        if (_examId == 0) {
            _exam = new ExamModel(0, "", "", 0, false, _loggedInUser.getId());

            new Handler(Looper.getMainLooper()).post(new Runnable() {
                public void run() {
                    _examModelData.setValue(_exam);
                }
            });

            _gettingExamInfo.setIsPending(false);
            _gettingExamInfo.setIsSucess(true);
            _gettingExamInfo.setIsError(false);
            _gettingExamInfo.setMessage("");

            new Handler(Looper.getMainLooper()).post(new Runnable() {
                public void run() {
                    _gettingExamInfoData.setValue(_gettingExamInfo);
                }
            });
        }
        else {
            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        ExamResponse examResponse = ExamInApplication.UNIT_OF_WORK.getExamRepository().getExamNoQuestionsByIdAsync(_examId);

                        _gettingExamInfo.setIsSucess(examResponse.getIsSuccess());
                        _gettingExamInfo.setIsError(!examResponse.getIsSuccess());

                        if (!examResponse.getIsSuccess()) {
                            _gettingExamInfo.setMessage(examResponse.getMessage());
                        } else {
                            List<ExamModel> exams = examResponse.getExams();

                            if (exams == null || exams.get(0) == null) {
                                _gettingExamInfo.setIsSucess(false);
                                _gettingExamInfo.setIsError(true);
                                _gettingExamInfo.setMessage("Exam you searching is not found");
                            } else {
                                _exam = exams.get(0);

                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    public void run() {
                                        _examModelData.setValue(_exam);
                                    }
                                });
                            }
                        }
                    } catch (Exception ex) {
                        _gettingExamInfo.setIsSucess(false);
                        _gettingExamInfo.setIsError(true);
                        _gettingExamInfo.setMessage("Something went wrong");
                    }

                    _gettingExamInfo.setIsPending(false);

                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        public void run() {
                            _gettingExamInfoData.setValue(_gettingExamInfo);
                        }
                    });
                }
            });

            thread.start();
        }
    }
}
