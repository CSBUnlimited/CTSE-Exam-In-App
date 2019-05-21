//package com.example.examinapp.adapters;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//
//import com.example.examinapp.R;
//import com.example.examinapp.models.ExamModel;
//
//import java.util.List;
//
//public class ExamListAdapter extends BaseAdapter {
//
//    private Context _activityContext;
//    private LayoutInflater _inflate;
//
//    private List<ExamModel> _examList;
//
//    public ExamListAdapter(Context activityContext, List<ExamModel> examList) {
//        _activityContext = activityContext;
//        _inflate = LayoutInflater.from(_activityContext);
//        _examList = examList;
//    }
//
//    @Override
//    public int getCount() {
//        return _examList.size();
//    }
//
//    @Override
//    public Alarm getItem(int i) {
//        return _examList.get(i);
//    }
//
//    @Override
//    public long getItemId(int i) {
//        return getItem(i).getId();
//    }
//
//    @Override
//    public View getView(int i, View view, ViewGroup viewGroup) {
//        view = _inflate.inflate(R.layout.content_one_alarm, null);
//
//        GridLayout alarmLayout = view.findViewById(R.id.oneAlarmLayout);
//        TextView nameTextView = view.findViewById(R.id.oneAlarmNameTextView);
//        TextView timeTextView = view.findViewById(R.id.oneAlarmTimeTextView);
//        Switch onOffSwith = view.findViewById(R.id.oneAlarmOnOffSwitch);
//
//        final Alarm alarm = getItem(i);
//
//        nameTextView.setText(alarm.getName());
//        timeTextView.setText(((alarm.getDate().getHours() < 10) ? "0" : "") + alarm.getDate().getHours()
//                + " : " + ((alarm.getDate().getMinutes() < 10) ? "0" : "") + alarm.getDate().getMinutes());
//        onOffSwith.setChecked(alarm.getIsActive());
//
//        alarmLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View clickedView) {
////                Toast.makeText(clickedView.getContext(), "test " + alarm.getId(), Toast.LENGTH_LONG).show();
//
//                Intent intent = new Intent(_activityContext, NewAlarmActivity.class);
//                intent.putExtra(AlarmApplication.ALARM_ID, alarm.getId());
//                _activityContext.startActivity(intent);
//            }
//        });
//
//        return view;
//    }
//}
