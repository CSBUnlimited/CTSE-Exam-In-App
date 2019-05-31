package com.example.examinapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.examinapp.R;
import com.example.examinapp.activities.LecturerExamActivity;
import com.example.examinapp.consts.ExamInApplication;
import com.example.examinapp.enums.UserTypeEnum;
import com.example.examinapp.models.ExamModel;
import com.example.examinapp.models.QuestionModel;
import com.example.examinapp.models.UserModel;

import java.util.List;

public class QuestionListAdapter extends BaseAdapter {

    private Context _activityContext;
    private LayoutInflater _inflate;

    private List<QuestionModel> _questionList;
    private int _examOwnerId;

    public QuestionListAdapter(Context activityContext, int examOwnerId, List<QuestionModel> questionList) {
        _activityContext = activityContext;
        _inflate = LayoutInflater.from(_activityContext);
        _questionList = questionList;
        _examOwnerId = examOwnerId;
    }

    @Override
    public int getCount() {
        return _questionList.size();
    }

    @Override
    public QuestionModel getItem(int i) {
        return _questionList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return getItem(i).getId();
    }

    @Override
    public View getView(int i, View oneExamView, ViewGroup viewGroup) {
        oneExamView = _inflate.inflate(R.layout.content_one_picture_and_description, null);

        ConstraintLayout onePictureAndDescriptionConstraintLayout = oneExamView.findViewById(R.id.onePictureAndDescriptionConstraintLayout);
        ImageView onePictureAndDescriptionMainImageView = oneExamView.findViewById(R.id.onePictureAndDescriptionMainImageView);
        TextView onePictureAndDescriptionDescriptionTextView = oneExamView.findViewById(R.id.onePictureAndDescriptionDescriptionTextView);
        TextView onePictureAndDescriptionMarksTextView = oneExamView.findViewById(R.id.onePictureAndDescriptionMarksTextView);

        final QuestionModel questionModel = getItem(i);

        onePictureAndDescriptionDescriptionTextView.setText(questionModel.getDescription());
        onePictureAndDescriptionMarksTextView.setText("Marks: " + questionModel.getMarks());

        onePictureAndDescriptionConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View clickedView) {

                String test;

                UserModel loggedInUserModel = ExamInApplication.getLoggedInUserModel();

//                if (questionModel.getLecturerUserId() == loggedInUserModel.getId()) {
//                    Intent intent = new Intent(_activityContext, LecturerExamActivity.class);
//                    intent.putExtra(ExamInApplication.EXAM_ID, questionModel.getId());
//                    _activityContext.startActivity(intent);
//                }
//                else {
//
//
//                }

                if (loggedInUserModel.getUserTypeEnum() == UserTypeEnum.Lecturer) {

                    if (_examOwnerId == loggedInUserModel.getId()) {
                        test = "Owner";
                    }
                    else {
                        test = "Lecturer";
                    }
                }
                else {
                    test = "Student";
                }

                Toast.makeText(clickedView.getContext(), test + questionModel.getId(), Toast.LENGTH_LONG).show();


            }
        });

        return oneExamView;
    }
}
