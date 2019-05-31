package com.example.examinapp.activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.examinapp.R;
import com.example.examinapp.consts.ExamInApplication;
import com.example.examinapp.dataaccess.UnitOfWork;
import com.example.examinapp.dataaccess.dtos.question.QuestionRequest;
import com.example.examinapp.models.QuestionModel;

import java.util.Date;

public class QuestionActivity extends AppCompatActivity {

    private QuestionModel questionModel;

    private Button addQuestionButton;

    private TextInputEditText questionDescriptionTextInputEditText;
    private TextInputEditText questionMarksTextInputEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
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

        questionModel = new QuestionModel();

        // Get required data
        int examId = getIntent().getIntExtra(ExamInApplication.EXAM_ID, 0);
        questionModel.setExamId(examId);


        addQuestionButton = findViewById(R.id.addQuestionButton);
        questionDescriptionTextInputEditText = findViewById(R.id.questionDescriptionTextInputEditText);
        questionMarksTextInputEditText = findViewById(R.id.questionMarksTextInputEditText);

        addQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionModel.setDescription(questionDescriptionTextInputEditText.getText().toString());
                questionModel.setMarks(Float.parseFloat(questionMarksTextInputEditText.getText().toString()));

                final QuestionRequest questionRequest = new QuestionRequest(new Date(), ExamInApplication.getLoggedInUserModel().getUsername(), ExamInApplication.getLoggedInUserModel().getSessionId(), questionModel);

                try {

                    Thread thread = new Thread(new Runnable() {

                        @Override
                        public void run() {
                            try {
                                ExamInApplication.UNIT_OF_WORK.getQuestionRepository().addQuestionAsync(questionRequest);

                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    public void run() {
                                        Toast.makeText(QuestionActivity.this, "Question added", Toast.LENGTH_LONG).show();
                                        finish();
                                    }
                                });
                            }
                            catch (Exception ex) {
                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    public void run() {
                                        Toast.makeText(QuestionActivity.this, "Question adding failed", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }
                    });

                    thread.start();
                }
                catch (Exception ex) {
                }

            }
        });
    }

}
