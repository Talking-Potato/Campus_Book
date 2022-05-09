package com.example.term_project;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomActivity extends AppCompatActivity {

    Mylevel mylevelFrag;
    Achievement achievementFrag;
//    Fragment3 fragment3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_activity);

        mylevelFrag = new Mylevel();
        achievementFrag = new Achievement();
//        fragment3 = new Fragment3();

        getSupportFragmentManager().beginTransaction().replace(R.id.bottom_container, mylevelFrag).commit();

        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
//                    case R.id.tab1:
//                        Toast.makeText(getApplicationContext(), "첫번째 탭", Toast.LENGTH_SHORT).show();
//                        getSupportFragmentManager().beginTransaction().replace(R.id.bottom_container, mylevelFrag).commit();
//                        return true;
                    case R.id.tab2:
                        Toast.makeText(getApplicationContext(), "두번째 탭", Toast.LENGTH_SHORT).show();
                        getSupportFragmentManager().beginTransaction().replace(R.id.bottom_container, achievementFrag).commit();
                        return true;
                    case R.id.tab3:
                        Toast.makeText(getApplicationContext(), "세번째 탭", Toast.LENGTH_SHORT).show();
                        getSupportFragmentManager().beginTransaction().replace(R.id.bottom_container, mylevelFrag).commit();
                        return true;
                }
                return false;
            }
        });
    }
}