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
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.Toast;

import com.example.examinapp.R;
import com.example.examinapp.consts.ExamInApplication;
import com.example.examinapp.models.ExamModel;
import com.example.examinapp.models.LoadingInformationModel;
import com.example.examinapp.models.UserModel;
import com.example.examinapp.viewmodels.ManageExamViewModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ManageExamActivity extends AppCompatActivity {
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

    private ScrollView _manageExamScrollView;
    private LinearLayout _gettingExamDataProgressLinearLayout;

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

        _manageExamScrollView = findViewById(R.id.manageExamScrollView);
        _manageExamScrollView.setVisibility(View.GONE);
        _gettingExamDataProgressLinearLayout = findViewById(R.id.gettingExamDataProgressLinearLayout);
        _gettingExamDataProgressLinearLayout.setVisibility(View.GONE);

        // Get required data
        _user = ExamInApplication.getLoggedInUserModel();

        int examId = getIntent().getIntExtra(ExamInApplication.EXAM_ID, 0);

        // View model controllers
        _manageExamViewModel = ViewModelProviders.of(this).get(ManageExamViewModel.class);
        _manageExamViewModel.init(examId);

        // View model listeners
        _manageExamViewModel.getExamModelData().observe(this, new Observer<ExamModel>() {
            @Override
            public void onChanged(@Nullable ExamModel examModel) {
                _manageExamNameTextInputEditText.setText(examModel.getName());
                _manageExamDescriptionTextInputEditText.setText(examModel.getDescription());
                _manageExamGivenTimeTextInputEditText.setText(String.valueOf(examModel.getGivenTimeSeconds() / 60));

                DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");

                _manageExamEffectiveDateTextInputEditText.setText(dateFormat.format(examModel.getEffectiveDateTime()));
                _manageExamExpireDateTextInputEditText.setText(dateFormat.format(examModel.getExpireDateTime()));
                _manageExamPublishSwitch.setChecked(examModel.getIsPublish());
            }
        });

        _manageExamViewModel.getGettingExamInfoData().observe(this, new Observer<LoadingInformationModel>() {
            @Override
            public void onChanged(@Nullable LoadingInformationModel loadingInformationModel) {
                if (loadingInformationModel.getIsPending()) {
                    _manageExamScrollView.setVisibility(View.GONE);
                    _gettingExamDataProgressLinearLayout.setVisibility(View.VISIBLE);
                }
                else if (loadingInformationModel.getIsSucess()) {
                    _manageExamScrollView.setVisibility(View.VISIBLE);
                    _gettingExamDataProgressLinearLayout.setVisibility(View.GONE);
                }
                else {
                    Toast.makeText(ManageExamActivity.this, loadingInformationModel.getMessage(), Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });

        _manageExamViewModel.getSavingExamInfoData().observe(this, new Observer<LoadingInformationModel>() {
            @Override
            public void onChanged(@Nullable LoadingInformationModel loadingInformationModel) {

            }
        });

        // Set actions to view controllers
        _manageExamEffectiveDateSetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _manageExamViewModel.pickDateTime(ManageExamActivity.this, _manageExamViewModel.getExamModel().getEffectiveDateTime()).observe(ManageExamActivity.this, new Observer<Date>() {
                    @Override
                    public void onChanged(@Nullable Date date) {
//                        Toast.makeText(ManageExamActivity.this, "new " + date.toString(), Toast.LENGTH_LONG).show();
                        _manageExamViewModel.setExamModelEffectiveDateTime(date);
                    }
                });
            }
        });

        _manageExamExpireDateSetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _manageExamViewModel.pickDateTime(ManageExamActivity.this, _manageExamViewModel.getExamModel().getExpireDateTime()).observe(ManageExamActivity.this, new Observer<Date>() {
                    @Override
                    public void onChanged(@Nullable Date date) {
                        _manageExamViewModel.setExamModelExpireDateTime(date);
                    }
                });
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
    }
}
