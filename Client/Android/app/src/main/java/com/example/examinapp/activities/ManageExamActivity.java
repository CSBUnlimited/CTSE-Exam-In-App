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
import com.example.examinapp.viewmodels.ManageExamViewModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ManageExamActivity extends AppCompatActivity {

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
    private LinearLayout _savingExamDataProgressLinearLayout;
    private LinearLayout _updatingExamDataProgressLinearLayout;

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
        _savingExamDataProgressLinearLayout = findViewById(R.id.savingExamDataProgressLinearLayout);
        _savingExamDataProgressLinearLayout.setVisibility(View.GONE);
        _updatingExamDataProgressLinearLayout = findViewById(R.id.updatingExamDataProgressLinearLayout);
        _updatingExamDataProgressLinearLayout.setVisibility(View.GONE);

        // Get required data

        int examId = getIntent().getIntExtra(ExamInApplication.EXAM_ID, 0);

        // View model controllers
        _manageExamViewModel = ViewModelProviders.of(this).get(ManageExamViewModel.class);
        _manageExamViewModel.init(examId);

        // View model listeners
        _manageExamViewModel.getExamModelData().observe(this, new Observer<ExamModel>() {
            @Override
            public void onChanged(@Nullable ExamModel examModel) {

                if (examModel == null) {
                    finish();
                    return;
                }

                _manageExamNameTextInputEditText.setText(examModel.getName());
                _manageExamDescriptionTextInputEditText.setText(examModel.getDescription());
                _manageExamGivenTimeTextInputEditText.setText(String.valueOf(examModel.getGivenTimeSeconds() / 60));

                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

                _manageExamEffectiveDateTextInputEditText.setText(dateFormat.format(examModel.getEffectiveDateTime()));
                _manageExamExpireDateTextInputEditText.setText(dateFormat.format(examModel.getExpireDateTime()));
                _manageExamPublishSwitch.setChecked(examModel.getIsPublish());

                if (examModel.getId() > 0) {
                    _manageExamAddNewConstraintLayout.setVisibility(View.GONE);
                    _manageExamUpdateConstraintLayout.setVisibility(View.VISIBLE);
                }
                else {
                    _manageExamAddNewConstraintLayout.setVisibility(View.VISIBLE);
                    _manageExamUpdateConstraintLayout.setVisibility(View.GONE);
                }
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
                if (loadingInformationModel.getIsPending()) {
                    _manageExamNameTextInputEditText.setEnabled(false);
                    _manageExamDescriptionTextInputEditText.setEnabled(false);
                    _manageExamGivenTimeTextInputEditText.setEnabled(false);
                    _manageExamEffectiveDateSetButton.setEnabled(false);
                    _manageExamExpireDateSetButton.setEnabled(false);
                    _manageExamPublishSwitch.setEnabled(false);

                    _manageExamSaveButton.setEnabled(false);
                    _manageExamUpdateButton.setEnabled(false);
                    _manageExamDeleteButton.setEnabled(false);

                    _savingExamDataProgressLinearLayout.setVisibility(View.VISIBLE);
                    _updatingExamDataProgressLinearLayout.setVisibility(View.VISIBLE);
                }
                else {
                    _manageExamNameTextInputEditText.setEnabled(true);
                    _manageExamDescriptionTextInputEditText.setEnabled(true);
                    _manageExamGivenTimeTextInputEditText.setEnabled(true);
                    _manageExamEffectiveDateSetButton.setEnabled(true);
                    _manageExamExpireDateSetButton.setEnabled(true);
                    _manageExamPublishSwitch.setEnabled(true);

                    _manageExamSaveButton.setEnabled(true);
                    _manageExamUpdateButton.setEnabled(true);
                    _manageExamDeleteButton.setEnabled(true);

                    _savingExamDataProgressLinearLayout.setVisibility(View.GONE);
                    _updatingExamDataProgressLinearLayout.setVisibility(View.GONE);

                    Toast.makeText(ManageExamActivity.this, loadingInformationModel.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        _manageExamViewModel.getToastMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String message) {
                Toast.makeText(ManageExamActivity.this, message, Toast.LENGTH_LONG).show();
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
                        _manageExamViewModel.setExamToExamModelData();
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
                        _manageExamViewModel.setExamToExamModelData();
                    }
                });
            }
        });

        _manageExamSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                syncExamViewModelWithViewData();
                _manageExamViewModel.saveUpdateExam();
            }
        });

        _manageExamUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                syncExamViewModelWithViewData();
                _manageExamViewModel.saveUpdateExam();
            }
        });

        _manageExamDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _manageExamViewModel.deleteExam();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void syncExamViewModelWithViewData() {
        _manageExamViewModel.setExamModelName(_manageExamNameTextInputEditText.getText().toString());
        _manageExamViewModel.setExamModelDescription(_manageExamDescriptionTextInputEditText.getText().toString());
        _manageExamViewModel.setExamModelGivenTimeMinutes(Integer.parseInt(_manageExamGivenTimeTextInputEditText.getText().toString()));
        _manageExamViewModel.setExamModelIsPublish(_manageExamPublishSwitch.isChecked());

        _manageExamViewModel.setExamToExamModelData();
    }
}
