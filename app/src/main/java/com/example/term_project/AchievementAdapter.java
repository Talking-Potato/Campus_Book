package com.example.term_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AchievementAdapter extends BaseAdapter {
    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<Achievement> list;

    public AchievementAdapter(Context context, ArrayList<Achievement> data) {
        mContext = context;
        list = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Achievement getItem(int position) {
        return list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.listview_achiev, null);

        TextView title = (TextView) view.findViewById(R.id.achivTitle);

        TextView date = (TextView) view.findViewById(R.id.achivDate);
        TextView exp = (TextView) view.findViewById(R.id.achivExp);


        title.setText(list.get(position).getTitle());
        date.setText(list.get(position).getDate());
        exp.setText("획득 EXP : " + list.get(position).getExp().toString());

        return view;
    }


}
