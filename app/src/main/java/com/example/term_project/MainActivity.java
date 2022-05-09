package com.example.term_project;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    MyLevelFragment mylevelFrag;
    AchievementFragment achievementFrag;
//    Fragment3 fragment3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mylevelFrag = new MyLevelFragment();
        achievementFrag = new AchievementFragment();
//        fragment3 = new Fragment3();

        getSupportFragmentManager().beginTransaction().replace(R.id.bottom_container, mylevelFrag).commit();

        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

//                    case R.id.tab1: // 지도 선택시 지도 fragment로 넘어감
//                        Toast.makeText(getApplicationContext(), "첫번째 탭", Toast.LENGTH_SHORT).show();
//                        getSupportFragmentManager().beginTransaction().replace(R.id.bottom_container, mylevelFrag).commit();
//                        return true;

//                    case R.id.tab2: // QR 선택했을때 QR fragemnt로 넘어감
//                        Toast.makeText(getApplicationContext(), "두번째 탭", Toast.LENGTH_SHORT).show();
//                        getSupportFragmentManager().beginTransaction().replace(R.id.bottom_container, achievementFrag).commit();
//                        return true;

                    case R.id.tab3: // 업적 선택시 업적 fragment로 넘어감
                        Toast.makeText(getApplicationContext(), "두번째 탭", Toast.LENGTH_SHORT).show();
                        getSupportFragmentManager().beginTransaction().replace(R.id.bottom_container, achievementFrag).commit();
                        return true;

                    case R.id.tab4: // 마이페이지 선택시 마이페이지 fragment로 넘어감
                        Toast.makeText(getApplicationContext(), "세번째 탭", Toast.LENGTH_SHORT).show();
                        getSupportFragmentManager().beginTransaction().replace(R.id.bottom_container, mylevelFrag).commit();
                        return true;
                }
                return false;
            }
        });
    }
}