package com.example.examinapp.adapters;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.examinapp.R;
import com.example.examinapp.models.ExamModel;

import java.util.List;

public class ExamListAdapter extends BaseAdapter {

    private Context _activityContext;
    private LayoutInflater _inflate;

    private List<ExamModel> _examList;

    public ExamListAdapter(Context activityContext, List<ExamModel> examList) {
        _activityContext = activityContext;
        _inflate = LayoutInflater.from(_activityContext);
        _examList = examList;
    }

    @Override
    public int getCount() {
        return _examList.size();
    }

    @Override
    public ExamModel getItem(int i) {
        return _examList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return getItem(i).getId();
    }

    @Override
    public View getView(int i, View oneExamView, ViewGroup viewGroup) {
        oneExamView = _inflate.inflate(R.layout.content_one_exam, null);

        ConstraintLayout oneExamConstraintLayout = oneExamView.findViewById(R.id.oneExamConstraintLayout);
        ImageView oneExamMainImageView = oneExamView.findViewById(R.id.oneExamMainImageView);
        TextView oneExamNameTextView = oneExamView.findViewById(R.id.oneExamNameTextView);
        TextView oneExamDescriptionTextView = oneExamView.findViewById(R.id.oneExamDescriptionTextView);

        final ExamModel examModel = getItem(i);

        oneExamNameTextView.setText(examModel.getName());
        oneExamDescriptionTextView.setText(examModel.getDescription());

        oneExamConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View clickedView) {
                Toast.makeText(clickedView.getContext(), "test " + examModel.getId(), Toast.LENGTH_LONG).show();

//                Intent intent = new Intent(_activityContext, NewAlarmActivity.class);
//                intent.putExtra(AlarmApplication.ALARM_ID, examModel.getId());
//                _activityContext.startActivity(intent);
            }
        });

        return oneExamView;
    }
}
