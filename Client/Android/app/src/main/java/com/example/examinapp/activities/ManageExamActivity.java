package com.example.examinapp.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.example.examinapp.R;
import com.example.examinapp.consts.ExamInApplication;
import com.example.examinapp.models.ExamModel;
import com.example.examinapp.models.UserModel;
import com.example.examinapp.viewmodels.ManageExamViewModel;

import java.util.Date;

public class ManageExamActivity extends AppCompatActivity {
    private int _examId;
    private ExamModel _exam;
    private UserModel _user;

    private ManageExamViewModel _manageExamViewModel;

    private TextInputEditText _manageExamNameTextInputEditText;
    private TextInputEditText _manageExamDescriptionTextInputEditText;
    private TextInputEditText _manageExamGivenTimeTextInputEditText;
    private TextInputEditText _manageExamEffectiveDateTextInputEditText;
    private Button _manageExamEffectiveDateSetButton;
    private TextInputEditText _manageExamExpireDateTextInputEditText;
    private Button _manageExamExpireDateSetButton;
    private Switch _manageExamPublishSwitch;

    private ConstraintLayout _manageExamAddNewConstraintLayout;
    private Button _manageExamSaveButton;

    private ConstraintLayout _manageExamUpdateConstraintLayout;
    private Button _manageExamUpdateButton;
    private Button _manageExamDeleteButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_exam);

        _manageExamNameTextInputEditText = findViewById(R.id.manageExamNameTextInputEditText);
        _manageExamDescriptionTextInputEditText = findViewById(R.id.manageExamDescriptionTextInputEditText);
        _manageExamGivenTimeTextInputEditText = findViewById(R.id.manageExamGivenTimeTextInputEditText);
        _manageExamEffectiveDateTextInputEditText = findViewById(R.id.manageExamEffectiveDateTextInputEditText);
        _manageExamEffectiveDateSetButton = findViewById(R.id.manageExamEffectiveDateSetButton);
        _manageExamExpireDateTextInputEditText = findViewById(R.id.manageExamExpireDateTextInputEditText);
        _manageExamExpireDateSetButton = findViewById(R.id.manageExamExpireDateSetButton);
        _manageExamPublishSwitch = findViewById(R.id.manageExamPublishSwitch);

        _manageExamAddNewConstraintLayout = findViewById(R.id.manageExamAddNewConstraintLayout);
        _manageExamSaveButton = findViewById(R.id.manageExamSaveButton);

        _manageExamUpdateConstraintLayout = findViewById(R.id.manageExamUpdateConstraintLayout);
        _manageExamUpdateButton = findViewById(R.id.manageExamUpdateButton);
        _manageExamDeleteButton = findViewById(R.id.manageExamDeleteButton);

        // View model controllers
        _manageExamViewModel = ViewModelProviders.of(this).get(ManageExamViewModel.class);
        _manageExamViewModel.init();

        // Get required data
        _user = ExamInApplication.getLoggedInUserModel();

        _examId = getIntent().getIntExtra(ExamInApplication.EXAM_ID, 0);

        // Set actions to view controllers
        _manageExamEffectiveDateSetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                _manageExamViewModel.pickDateTime(ManageExamActivity.this, new Date()).observe(ManageExamActivity.this, new Observer<Date>() {
                    @Override
                    public void onChanged(@Nullable Date date) {
                        Toast.makeText(ManageExamActivity.this, "new " + date.toString(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        _manageExamExpireDateSetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        _manageExamSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        _manageExamUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        _manageExamDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (_examId > 0) {

        }
        else {
            _exam = new ExamModel(0, "", "", 0, false, _user.getId());
        }
    }
}
