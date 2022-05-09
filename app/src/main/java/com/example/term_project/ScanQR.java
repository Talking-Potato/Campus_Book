package com.example.term_project;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ScanQR extends AppCompatActivity{
    private IntentIntegrator qrScan;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);

        qrScan = new IntentIntegrator(this);
        qrScan.setOrientationLocked(false);//default:세로모드인데 휴대폰 방향에 따라 가로, 세로로 자동 변경
        qrScan.initiateScan();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result!=null){
            if(result.getContents()==null){ //content가 없을때
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            }
            else{   //content가 있을때
                Toast.makeText(this, "Scanned: " + result.getContents(),Toast.LENGTH_LONG).show();
                finish();
            }
        }
        else{
            super.onActivityResult(requestCode,resultCode,data);
        }
    }
}
