package com.example.term_project;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AchievementActivity extends AppCompatActivity {

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
                Toast.makeText(getApplicationContext(),
                        myAdapter.getItem(position).getTitle(),
                        Toast.LENGTH_LONG).show();
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