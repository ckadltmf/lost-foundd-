package com.example.myapplication2;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends BaseAdapter {
    private Context context;
    private List<objectData> arrayList;
    private TextView  Category, lostOrFound, Description;
    public MyAdapter(Context context, List<objectData> arrayList) {
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
        convertView = LayoutInflater.from(context).inflate(R.layout.activity_row_view, parent, false);
        Log.d(parent.toString(),"TAG");
       // serialNum = convertView.findViewById(R.id.serailNumber);
        Category = convertView.findViewById(R.id.reportSubject);
        lostOrFound = convertView.findViewById(R.id.UserID);
        Description = convertView.findViewById(R.id.Description);

        //serialNum.setText(" " + arrayList.get(position).getNum());
        Category.setText(arrayList.get(position).getCategory());
        lostOrFound.setText(arrayList.get(position).getLostOrFound());
        Description.setText(arrayList.get(position).getDescription());

        return convertView;
    }
}