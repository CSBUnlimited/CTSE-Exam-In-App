package com.example.examinapp.viewmodels;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

public class ManageExamViewModel extends ViewModel {

    private DatePickerDialog _datePickerDialog;
    private TimePickerDialog _timePickerDialog;
    private Calendar _selectedDateTimeCalendar;

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

    public void init() {

    }

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
}
