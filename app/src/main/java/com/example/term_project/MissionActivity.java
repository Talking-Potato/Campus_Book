package com.example.term_project;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MissionActivity extends AppCompatActivity {
    int value = 0;
    Button timer_start, timer_stop, back;
    TextView time;
    Boolean on = true;
    Thread thread;
    String record = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission);

        timer_start = (Button) findViewById(R.id.timer_start);
        timer_stop = (Button) findViewById(R.id.btnStop);
        time = (TextView) findViewById(R.id.textView);
        back = (Button) findViewById(R.id.Back);

        timer_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setVisibility(View.GONE);
                timer_stop.setVisibility(View.VISIBLE);

                // create time thread and start it
                thread = new Thread(new timeThread());
                thread.start();
            }
        });

        timer_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Make start button visible and set rest invisible
                v.setVisibility(View.GONE);

                timer_start.setVisibility(View.VISIBLE);

                //Interrupt the thread
                thread.interrupt();
                // initialize textView and record
                time.setText("00:00:00:00");
                record="";

            }
        });

        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MissionActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    @SuppressLint("HandlerLeak")
    // create Handler
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            // format time
            int mSec = msg.arg1 % 100;
            int sec = (msg.arg1 / 100) % 60;
            int min = (msg.arg1 /100) / 60;
            int hour = (msg.arg1 / 100) / 360;
            String result = String.format("%02d:%02d:%02d:%02d", hour, min, sec, mSec);
            //set the result to the textview
            time.setText(result);
        }
    };

    //thread implementing the method runnable
    public class timeThread implements Runnable{
        @Override
        public void run() {
            int i = 0;
            while(true){
                while(on){
                    //If pause button pressed, thread will stop
                    Message msg = new Message();
                    msg.arg1=i++;
                    // send message with handler
                    handler.sendMessage(msg);
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        // If it receive interrupt, return
                        return;
                    }
                }
            }
        }
    }
}