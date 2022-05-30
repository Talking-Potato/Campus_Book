package com.example.term_project;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import android.content.Intent;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AchievementActivity extends AppCompatActivity {

    Integer gainExp = 0;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = database.getReference("user");
    private ArrayList<Achievement> achievList;
    private AchievementAdapter myAdapter;

    public String emailToken;

    private int vision_value;
    private int gachon_value;
    private int ai_value;
    private int ladybug_value;
    private int current_exp = 0;
    private String vision;
    private String gachon;
    private String ai;
    private String ladybug;


    private final String[] vision_achieve = {"비전 타워 최초 방문", "비전 타워 3회 방문", "비전타워 5회 방문"};
    private final String[] gachon_achieve = {"가천관 최초 방문", "가천관 3회 방문", "가천관 5회 방문"};
    private final String[] ai_achieve = {"AI관 최초 방문", "AI관 3회 방문", "AI관 5회 방문"};
    private final String[] ladybug_achieve = {"무당이 최초 탑승", "무당이 3회 탑승", "무당이 5회 탑승"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement);

        ListView listView = (ListView) findViewById(R.id.achivList);

        achievList = new ArrayList<Achievement>();


        /* 토큰 */
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        emailToken = bundle.getString("token");

        databaseReference.child(emailToken).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                achievList.clear();

                vision = dataSnapshot.child("VisionTowerCount").getValue(String.class);
                gachon = dataSnapshot.child("GachonGwanCount").getValue(String.class);
                ai = dataSnapshot.child("AIGwanCount").getValue(String.class);
                ladybug = dataSnapshot.child("LadyBugCount").getValue(String.class);

                vision_value = Integer.parseInt(vision);
                gachon_value = Integer.parseInt(gachon);
                ai_value = Integer.parseInt(ai);
                ladybug_value = Integer.parseInt(ladybug);

                if (vision_value > 0) {
                    achievList.add(new Achievement(vision_achieve[0]));
                } if (vision_value >= 3) {
                    achievList.add(new Achievement(vision_achieve[1]));
                } if (vision_value >= 5) {
                    achievList.add((new Achievement(vision_achieve[2])));
                }

                if (gachon_value > 0) {
                    achievList.add(new Achievement(gachon_achieve[0]));
                } if (gachon_value >= 3) {
                    achievList.add(new Achievement(gachon_achieve[1]));
                } if (gachon_value >= 5) {
                    achievList.add((new Achievement(gachon_achieve[2])));
                }

                if (ai_value > 0) {
                    achievList.add(new Achievement(ai_achieve[0]));
                } if (ai_value >= 3) {
                    achievList.add(new Achievement(ai_achieve[1]));
                } if (ai_value >= 5) {
                    achievList.add((new Achievement(ai_achieve[2])));
                }

                if (ladybug_value > 0) {
                    achievList.add(new Achievement(ladybug_achieve[0]));
                } if (ladybug_value >= 3) {
                    achievList.add(new Achievement(ladybug_achieve[1]));
                } if (ladybug_value >= 5) {
                    achievList.add((new Achievement(ladybug_achieve[2])));
                }

                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        // 아이템 클릭하면 지정된 EXP만큼 gainExp에 저장됨
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick (AdapterView parent, View v,int position, long id){
                Achievement item = myAdapter.getItem(position);
                Toast.makeText(getApplicationContext(),
                        item.getTitle(),
                        Toast.LENGTH_LONG).show();
                LinearLayout bg = (LinearLayout) v.findViewById(R.id.achivBox);

                databaseReference.child(emailToken).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        vision = dataSnapshot.child("VisionTowerCount").getValue(String.class);
                        gachon = dataSnapshot.child("GachonGwanCount").getValue(String.class);
                        ai = dataSnapshot.child("AIGwanCount").getValue(String.class);
                        ladybug = dataSnapshot.child("LadyBugCount").getValue(String.class);

                        vision_value = Integer.parseInt(vision);
                        gachon_value = Integer.parseInt(gachon);
                        ai_value = Integer.parseInt(ai);
                        ladybug_value = Integer.parseInt(ladybug);

                        if (vision_value==1 || vision_value==3 || vision_value==5) {
                            if (item.getCanObtainExp()) {
                                bg.setBackgroundColor(Color.WHITE);
                                current_exp = current_exp + 60;
                                item.setCanObtainExp(false);
                            }
                        }
                        else if (gachon_value==1 || gachon_value==3 || gachon_value==5) {
                            if (item.getCanObtainExp()) {
                                bg.setBackgroundColor(Color.WHITE);
                                current_exp = current_exp + 60;
                                item.setCanObtainExp(false);
                            }
                        }
                        else if (ai_value==1 || ai_value==3 || ai_value==5) {
                            if (item.getCanObtainExp()) {
                                bg.setBackgroundColor(Color.WHITE);
                                current_exp = current_exp + 60;
                                item.setCanObtainExp(false);
                            }
                        }
                        else if (ladybug_value==1 || ladybug_value==3 || ladybug_value==5) {
                            if (item.getCanObtainExp()) {
                                bg.setBackgroundColor(Color.WHITE);
                                current_exp = current_exp + 60;
                                item.setCanObtainExp(false);
                            }
                        }

                        String current_value = Integer.toString(current_exp);
                        databaseReference.child(emailToken).child("CurrentExp").setValue(current_value);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });

                gainExp += item.getExp();
                Toast.makeText(getApplicationContext(), gainExp.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        myAdapter = new AchievementAdapter(this, achievList);
        listView.setAdapter(myAdapter);
    }
}