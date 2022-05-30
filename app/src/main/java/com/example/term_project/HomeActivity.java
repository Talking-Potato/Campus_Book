package com.example.term_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    public String emailToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button button_map = findViewById(R.id.mapButton);
        Button button_qr = findViewById(R.id.qrButton);
        Button button_achiev = findViewById(R.id.achivButton);
        Button button_level = findViewById(R.id.levelButton);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        emailToken=bundle.getString("token");

        button_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MapActivity.class);
                startActivity(intent);
            }
        });

        button_qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ScanQRActivity.class);
                Bundle token = new Bundle();
                token.putString("token", emailToken);
                intent.putExtras(token);
                startActivity(intent);
            }
        });

        button_achiev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AchievementActivity.class);
                Bundle token = new Bundle();
                token.putString("token", emailToken);
                intent.putExtras(token);
                startActivity(intent);
            }
        });

        button_level.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MyLevelActivity.class);
                Bundle token = new Bundle();
                token.putString("token", emailToken);
                intent.putExtras(token);
                startActivity(intent);
            }
        });
    }
}