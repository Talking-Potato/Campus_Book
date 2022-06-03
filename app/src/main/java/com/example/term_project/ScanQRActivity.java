package com.example.term_project;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ScanQRActivity extends AppCompatActivity {
    private IntentIntegrator qrScan;
    private String uri;
    private String visionUri = "https://bijeontaweo.hr22.repl.co";
    private String gachonUri = "https://gaceongwan.hr22.repl.co";
    private String aiUri = "https://AIgonghaggwan.hr22.repl.co";

    public String emailToken;
    private int vision_count;
    private int AI_count;
    private int Gachon_count;
    private String vision_value;
    private String AI_value;
    private String Gachon_value;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference("user");

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);

        qrScan = new IntentIntegrator(this);
        qrScan.setOrientationLocked(false);//default:세로모드인데 휴대폰 방향에 따라 가로, 세로로 자동 변경
        qrScan.initiateScan();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        emailToken=bundle.getString("token");

        databaseReference.child(emailToken).child("VisionTowerCount").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                vision_value = dataSnapshot.getValue(String.class);
                vision_count = Integer.parseInt(vision_value);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        databaseReference.child(emailToken).child("GachonGwanCount").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Gachon_value = dataSnapshot.getValue(String.class);
                Gachon_count = Integer.parseInt(Gachon_value);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        databaseReference.child(emailToken).child("AIGwanCount").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                AI_value = dataSnapshot.getValue(String.class);
                AI_count = Integer.parseInt(AI_value);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if(result!=null){
            if(result.getContents()==null){ //content 가 없을때
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
                finish();
            }
            else{   //content 가 있을때
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                uri = result.getContents();

                if(uri.equals(visionUri)){
                    vision_count++;
                    String visionvalue = Integer.toString(vision_count);
                    databaseReference.child(emailToken).child("VisionTowerCount").setValue(visionvalue);
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    startActivity(intent);
                }
                else if(uri.equals(gachonUri)){
                    Gachon_count++;
                    String gachonvalue = Integer.toString(Gachon_count);
                    databaseReference.child(emailToken).child("GachonGwanCount").setValue(gachonvalue);
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    startActivity(intent);
                }
                else if(uri.equals(aiUri)){
                    AI_count++;
                    String AIvalue = Integer.toString(AI_count);
                    databaseReference.child(emailToken).child("AIGwanCount").setValue(AIvalue);
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(getApplicationContext(), LadyBugActivity.class);
                    Bundle token = new Bundle();
                    token.putString("token", emailToken);
                    intent.putExtras(token);
                    startActivity(intent);
                }
            }
        }
        else{
            super.onActivityResult(requestCode,resultCode,data);
        }
    }
}