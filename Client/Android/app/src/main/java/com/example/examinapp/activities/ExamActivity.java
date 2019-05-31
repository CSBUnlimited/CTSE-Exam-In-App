package com.example.examinapp.activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.examinapp.R;
import com.example.examinapp.adapters.QuestionListAdapter;
import com.example.examinapp.consts.ExamInApplication;
import com.example.examinapp.dataaccess.dtos.exam.ExamResponse;
import com.example.examinapp.enums.LectureExamTabViewEnum;
import com.example.examinapp.enums.MainActivityViewEnum;
import com.example.examinapp.models.ExamModel;

public class ExamActivity extends AppCompatActivity {


    private ConstraintLayout nonLecturerExamDataConstraintLayout;
    private TextView nonLecturerExamNameTextView;
    private TextView nonLecturerExamDescriptionTextView;
    private TextView nonLecturerExamLecturerNameTextView;
    private ListView examDetailsListView;
    private TextView noDataToShowExamLecturerTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nonLecturerExamDataConstraintLayout = findViewById(R.id.nonLecturerExamDataConstraintLayout);
        nonLecturerExamNameTextView = findViewById(R.id.nonLecturerExamNameTextView);
        nonLecturerExamDescriptionTextView = findViewById(R.id.nonLecturerExamDescriptionTextView);
        nonLecturerExamLecturerNameTextView = findViewById(R.id.nonLecturerExamLecturerNameTextView);
        examDetailsListView = findViewById(R.id.examDetailsListView);
        examDetailsListView.setVisibility(View.GONE);
        noDataToShowExamLecturerTextView = findViewById(R.id.noDataToShowExamLecturerTextView);
        noDataToShowExamLecturerTextView.setVisibility(View.GONE);

        final int examId = getIntent().getIntExtra(ExamInApplication.EXAM_ID, 0);

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    final ExamResponse examResponse = ExamInApplication.UNIT_OF_WORK.getExamRepository().getExamByIdAsync(examId);

                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        public void run() {
                            ExamModel exam = examResponse.getExams().get(0);

                            nonLecturerExamNameTextView.setText(exam.getName());
                            nonLecturerExamDescriptionTextView.setText(exam.getDescription());
                            nonLecturerExamLecturerNameTextView.setText(exam.getLecturerUser().getName());

                            if (exam.getQuestions() == null || exam.getQuestions().size() == 0) {
                                noDataToShowExamLecturerTextView.setVisibility(View.VISIBLE);
                            }
                            else {
                                QuestionListAdapter questionListAdapter = new QuestionListAdapter(ExamActivity.this, exam.getLecturerUserId(), exam.getQuestions());
                                examDetailsListView.setAdapter(questionListAdapter);
                                examDetailsListView.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                }
                catch (Exception ex) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        public void run() {
                            Toast.makeText(ExamActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

        thread.start();
    }

}
