package com.example.myapplication2;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class MyReportAdapter extends BaseAdapter {

    private Context context;
    private List<ReportData> arrayList;
    private TextView UserID, reportSubject, Description;
    public MyReportAdapter(Context context, List<ReportData> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }
    @Override
    public int getCount() {
        return arrayList.size();
    }
    @Override
    public Object getItem(int position) {
        return position;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.activity_report_view, parent, false);
        // serialNum = convertView.findViewById(R.id.serailNumber);
        UserID = convertView.findViewById(R.id.UserID);
        reportSubject = convertView.findViewById(R.id.reportSubject);
        Description = convertView.findViewById(R.id.Description);
        //serialNum.setText(" " + arrayList.get(position).getNum());
        UserID.setText(arrayList.get(position).getUserID());
        reportSubject.setText(arrayList.get(position).getReportSubject());
        Description.setText(arrayList.get(position).getDescription());

        return convertView;
    }
}
