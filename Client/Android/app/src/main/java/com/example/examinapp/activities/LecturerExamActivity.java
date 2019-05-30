package com.example.examinapp.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.examinapp.R;
import com.example.examinapp.adapters.QuestionListAdapter;
import com.example.examinapp.consts.ExamInApplication;
import com.example.examinapp.enums.LectureExamTabViewEnum;
import com.example.examinapp.models.ExamModel;
import com.example.examinapp.models.LoadingInformationModel;
import com.example.examinapp.viewmodels.LecturerExamViewModel;

public class LecturerExamActivity extends AppCompatActivity {

    private ConstraintLayout loadingLecturerExamDataConstraintLayout;
    private ConstraintLayout lecturerExamDataConstraintLayout;

    private LectureExamTabViewEnum currentLectureExamTabViewEnum = LectureExamTabViewEnum.Questions;

    private TextView lecturerExamNameTextView;
    private TextView lecturerExamDescriptionTextView;
    private TextView lecturerExamLecturerNameTextView;
    private TextView noDataToShowExamLecturerTextView;

    private TabLayout examDetailsTabTabLayout;
    private LinearLayout loadingExamDetailsTabContentLinearLayout;
    private ListView examDetailsListView;

    private FloatingActionButton addQuestionFloatingActionButton;
    private Button updateExamLecturerButton;

    private LecturerExamViewModel lecturerExamViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturer_exam);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loadingLecturerExamDataConstraintLayout = findViewById(R.id.loadingLecturerExamDataConstraintLayout);
        loadingLecturerExamDataConstraintLayout.setVisibility(View.GONE);
        lecturerExamDataConstraintLayout = findViewById(R.id.lecturerExamDataConstraintLayout);
        lecturerExamDataConstraintLayout.setVisibility(View.GONE);
        lecturerExamNameTextView = findViewById(R.id.lecturerExamNameTextView);
        lecturerExamDescriptionTextView = findViewById(R.id.lecturerExamDescriptionTextView);
        lecturerExamLecturerNameTextView = findViewById(R.id.lecturerExamLecturerNameTextView);

        examDetailsTabTabLayout = findViewById(R.id.examDetailsTabTabLayout);

        updateExamLecturerButton = findViewById(R.id.updateExamLecturerButton);

        addQuestionFloatingActionButton = findViewById(R.id.addQuestionFloatingActionButton);
        addQuestionFloatingActionButton.hide();
        loadingExamDetailsTabContentLinearLayout = findViewById(R.id.loadingExamDetailsTabContentLinearLayout);
        loadingExamDetailsTabContentLinearLayout.setVisibility(View.GONE);
        examDetailsListView = findViewById(R.id.examDetailsListView);
        noDataToShowExamLecturerTextView = findViewById(R.id.noDataToShowExamLecturerTextView);
        noDataToShowExamLecturerTextView.setVisibility(View.GONE);

        // Get required data
        int examId = getIntent().getIntExtra(ExamInApplication.EXAM_ID, 0);

        // View model controllers
        lecturerExamViewModel = ViewModelProviders.of(this).get(LecturerExamViewModel.class);
        lecturerExamViewModel.init(examId);

        // View model listeners
        lecturerExamViewModel.getExamData().observe(this, new Observer<ExamModel>() {
            @Override
            public void onChanged(@Nullable ExamModel examModel) {
                if (examModel == null) {
                    finish();
                    return;
                }

                lecturerExamNameTextView.setText(examModel.getName());
                lecturerExamDescriptionTextView.setText(examModel.getDescription());
                lecturerExamLecturerNameTextView.setText(examModel.getLecturerUser().getName());
            }
        });

        lecturerExamViewModel.getLoadingExamData().observe(this, new Observer<LoadingInformationModel>() {
            @Override
            public void onChanged(@Nullable LoadingInformationModel loadingInformationModel) {
                loadingLecturerExamDataConstraintLayout.setVisibility(View.GONE);
                lecturerExamDataConstraintLayout.setVisibility(View.GONE);

                if (loadingInformationModel.getIsPending()) {
                    loadingLecturerExamDataConstraintLayout.setVisibility(View.VISIBLE);
                }
                else if (loadingInformationModel.getIsError()) {
                    showErrorMessageDialog();
                }
                else if (loadingInformationModel.getIsSucess()) {
                    lecturerExamDataConstraintLayout.setVisibility(View.VISIBLE);
                    lecturerExamViewModel.getTabDataByTabEnum(currentLectureExamTabViewEnum);
                }
            }
        });

        lecturerExamViewModel.getLoadingTabData().observe(this, new Observer<LoadingInformationModel>() {
            @Override
            public void onChanged(@Nullable LoadingInformationModel loadingInformationModel) {
                addQuestionFloatingActionButton.hide();
                examDetailsListView.setVisibility(View.GONE);
                loadingExamDetailsTabContentLinearLayout.setVisibility(View.GONE);
                noDataToShowExamLecturerTextView.setVisibility(View.GONE);

                if (loadingInformationModel.getIsPending()) {
                    loadingLecturerExamDataConstraintLayout.setVisibility(View.VISIBLE);
                }
                else if (loadingInformationModel.getIsError()) {
                    showErrorMessageDialog();
                }
                else if (loadingInformationModel.getIsSucess()) {
                    lecturerExamDataConstraintLayout.setVisibility(View.VISIBLE);

                    if (currentLectureExamTabViewEnum == LectureExamTabViewEnum.Questions) {
                        addQuestionFloatingActionButton.show();

                        if (lecturerExamViewModel.getExam().getQuestions() != null) {

                            if (lecturerExamViewModel.getExam().getQuestions().size() == 0) {
                                noDataToShowExamLecturerTextView.setVisibility(View.VISIBLE);
                            }
                            else {
                                QuestionListAdapter questionListAdapter = new QuestionListAdapter(LecturerExamActivity.this, lecturerExamViewModel.getExam().getLecturerUserId(), lecturerExamViewModel.getExam().getQuestions());
                                examDetailsListView.setAdapter(questionListAdapter);
                                examDetailsListView.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                    else if (currentLectureExamTabViewEnum == LectureExamTabViewEnum.Students) {

                        if (lecturerExamViewModel.getExam().getStudentExams() != null) {

                            if (lecturerExamViewModel.getExam().getStudentExams().size() == 0) {
                                noDataToShowExamLecturerTextView.setVisibility(View.VISIBLE);
                            }
                            else {
//                                QuestionListAdapter questionListAdapter = new QuestionListAdapter(LecturerExamActivity.this, lecturerExamViewModel.getExam().getLecturerUserId(), lecturerExamViewModel.getExam().getQuestions());
//                                examDetailsListView.setAdapter(questionListAdapter);
                                examDetailsListView.setVisibility(View.VISIBLE);
                            }
                        }
                    }

                    examDetailsListView.setVisibility(View.VISIBLE);
                    loadingLecturerExamDataConstraintLayout.setVisibility(View.GONE);
                }
            }
        });

        // Activity listeners
        addQuestionFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        updateExamLecturerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LecturerExamActivity.this, LecturerManageExamActivity.class);
                intent.putExtra(ExamInApplication.EXAM_ID, lecturerExamViewModel.getExam().getId());
                LecturerExamActivity.this.startActivity(intent);
            }
        });

        examDetailsTabTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String tabName = tab.getText().toString().toLowerCase();

                if (tabName.equals(LectureExamTabViewEnum.Questions.toString().toLowerCase())) {
                    currentLectureExamTabViewEnum = LectureExamTabViewEnum.Questions;
                }
                else if (tabName.equals(LectureExamTabViewEnum.Students.toString().toLowerCase())) {
                    currentLectureExamTabViewEnum = LectureExamTabViewEnum.Students;
                }

                lecturerExamViewModel.getTabDataByTabEnum(currentLectureExamTabViewEnum);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        lecturerExamViewModel.getExamWithNoQuestionsData();
    }

    private void showErrorMessageDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Something went wrong");
        builder.setMessage("Something went wrong while getting data.\nPlease check your connection and try again!");

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });

        builder.setPositiveButton("Retry", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
                lecturerExamViewModel.getExamWithNoQuestionsData();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
