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
import com.example.examinapp.dataaccess.dtos.base.BaseRequest;
import com.example.examinapp.dataaccess.dtos.base.BaseResponse;
import com.example.examinapp.dataaccess.dtos.exam.ExamRequest;
import com.example.examinapp.dataaccess.dtos.exam.ExamResponse;
import com.example.examinapp.models.ExamModel;
import com.example.examinapp.models.LoadingInformationModel;
import com.example.examinapp.models.UserModel;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class LecturerManageExamViewModel extends ViewModel {

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

    private MutableLiveData<String> _toastMessage = new MutableLiveData<>();

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

    public LiveData<String> getToastMessage() {
        return _toastMessage;
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
    }

    public void setExamModelDescription(String description) {
        _exam.setDescription(description);
    }

    public void setExamModelGivenTimeMinutes(int givenMinutes) {
        _exam.setGivenTimeSeconds(givenMinutes * 60);
    }

    public void setExamModelEffectiveDateTime(Date effectiveDateTime) {
        _exam.setEffectiveDateTime(effectiveDateTime);
    }

    public void setExamModelExpireDateTime(Date expireDateTime) {
        _exam.setExpireDateTime(expireDateTime);
    }

    public void setExamModelIsPublish(boolean isPublish) {
        _exam.setIsPublish(isPublish);
    }

    public void setExamToExamModelData() {
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

    public void saveUpdateExam() {

        if (_exam.getName().trim().equals("")) {
            _toastMessage.setValue("Exam name is required");
            return;
        }

        if (_exam.getDescription().trim().equals("")) {
            _toastMessage.setValue("Exam description is required");
            return;
        }

        if (_exam.getEffectiveDateTime().getTime() >= _exam.getExpireDateTime().getTime()) {
            _toastMessage.setValue("Expire date time should larger than effective date time");
            return;
        }

        if (_exam.getGivenTimeSeconds() <= 0) {
            _toastMessage.setValue("Exam given time should be larger than zero");
            return;
        }

        _savingExamInfo.setIsPending(true);
        _savingExamInfo.setIsSucess(false);
        _savingExamInfo.setIsError(false);
        _savingExamInfo.setMessage("");

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                _savingExamInfoData.setValue(_savingExamInfo);
            }
        });

        _exam.setLecturerUserId(_loggedInUser.getId());

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {

                    ExamRequest examRequest = new ExamRequest(new Date(), _loggedInUser.getUsername(), _loggedInUser.getSessionId(), _exam);
                    ExamResponse examResponse;

                    if (_exam.getId() > 0) {
                        examResponse = ExamInApplication.UNIT_OF_WORK.getExamRepository().updateExamAsync(examRequest);
                    }
                    else {
                        examResponse = ExamInApplication.UNIT_OF_WORK.getExamRepository().addExamAsync(examRequest);
                    }

                    _savingExamInfo.setIsSucess(examResponse.getIsSuccess());
                    _savingExamInfo.setIsError(!examResponse.getIsSuccess());

                    if (!examResponse.getIsSuccess()) {
                        _savingExamInfo.setMessage(examResponse.getMessage());
                    } else {
                        List<ExamModel> exams = examResponse.getExams();

                        if (exams == null || exams.get(0) == null) {
                            _savingExamInfo.setIsSucess(false);
                            _savingExamInfo.setIsError(true);
                            _savingExamInfo.setMessage("Something went wrong");
                        } else {

                            _savingExamInfo.setMessage(_exam.getId() == 0 ? "Exam added successfully" : "Exam updated successfully");

                            _exam = exams.get(0);

                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                public void run() {
                                    _examModelData.setValue(_exam);
                                }
                            });

                        }
                    }
                } catch (Exception ex) {
                    _savingExamInfo.setIsSucess(false);
                    _savingExamInfo.setIsError(true);
                    _savingExamInfo.setMessage("Something went wrong");
                }

                _savingExamInfo.setIsPending(false);

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    public void run() {
                        _savingExamInfoData.setValue(_savingExamInfo);
                    }
                });
            }
        });

        thread.start();
    }

    public void deleteExam() {

        _savingExamInfo.setIsPending(true);
        _savingExamInfo.setIsSucess(false);
        _savingExamInfo.setIsError(false);
        _savingExamInfo.setMessage("");

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                _savingExamInfoData.setValue(_savingExamInfo);
            }
        });

        _exam.setLecturerUserId(_loggedInUser.getId());

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {

                    BaseRequest baseRequest = new BaseRequest(new Date(), _loggedInUser.getUsername(), _loggedInUser.getSessionId());
                    BaseResponse baseResponse = ExamInApplication.UNIT_OF_WORK.getExamRepository().deleteExamByIdAsync(_exam.getId(), baseRequest);;

                    _savingExamInfo.setIsSucess(baseResponse.getIsSuccess());
                    _savingExamInfo.setIsError(!baseResponse.getIsSuccess());

                    if (!baseResponse.getIsSuccess()) {
                        _savingExamInfo.setMessage(baseResponse.getMessage());
                    } else {
                        _exam = null;
                        _savingExamInfo.setMessage("Exam deleted");
                    }
                } catch (Exception ex) {
                    _savingExamInfo.setIsSucess(false);
                    _savingExamInfo.setIsError(true);
                    _savingExamInfo.setMessage("Something went wrong");
                }

                _savingExamInfo.setIsPending(false);

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    public void run() {
                        _examModelData.setValue(_exam);
                        _savingExamInfoData.setValue(_savingExamInfo);
                    }
                });
            }
        });

        thread.start();
    }
}
