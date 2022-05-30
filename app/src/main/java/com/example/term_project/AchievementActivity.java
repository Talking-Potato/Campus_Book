package com.example.term_project;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AchievementActivity extends AppCompatActivity {
    Integer gainExp = 0;
    ArrayList<Achievement> achievList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement);

        this.IntializeData();


        ListView listView = (ListView)findViewById(R.id.achivList);
        final AchievementAdapter myAdapter = new AchievementAdapter(this,achievList);

        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id){
                Achievement item = myAdapter.getItem(position);
                Toast.makeText(getApplicationContext(),
                        item.getTitle(),
                        Toast.LENGTH_LONG).show();
                LinearLayout bg = (LinearLayout) v.findViewById(R.id.achivBox);
                //set clicked item background to white.
                item.setCanObtainExp(false);
                if(!item.getCanObtainExp()) {
                        bg.setBackgroundColor(Color.WHITE);
                }
                gainExp += item.getExp();
                Toast.makeText(getApplicationContext(), gainExp.toString(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void IntializeData()
    {
        achievList = new ArrayList<Achievement>();

        achievList.add(new Achievement("매장/건물 최초 방문 (일 최대 각 1회 / gps 거리 10m이내 20분 이상)", "22.05.26"));
        achievList.add(new Achievement("매장/건물 10회 방문+20", "22.05.27"));
    }
}