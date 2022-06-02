package com.example.term_project;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import tyrantgit.explosionfield.ExplosionField;

public class MyLevelActivity extends AppCompatActivity {

    ProgressBar expBar;
    Button addBtn;
    ImageView avatar;

    TextView myLevelText, myLevelDescriptionText, usableExpText;
    TextView level1TV, level2TV, level3TV, level4TV, level5TV;

    //폭발 효과 object
    ExplosionField explosionField;
    Handler mHandler = new Handler();
    //나의 현재 레벨
    private int myLevel;
    //나의 현재 경험치
    private int myExp;
    //더할 수 있는 경험치 양
    private int usableExp;

    //경험치바의 현재 경험치량을 나타낼 값
    private int expVal;
    //미리 설정된 레벨에 따른 경험치 값들
    private int level1Exp = 240;
    private int level2Exp = 360;
    private int level3Exp = 480;
    private int level4Exp = 600;
    private int level5Exp = 720;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference("user");

    public String emailToken;
    private String myExp_value;
    private String current_exp;
    private int currentExp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_level);
        expBar = (ProgressBar) findViewById(R.id.expBar);
        addBtn = (Button) findViewById(R.id.addExp);
        avatar = (ImageView) findViewById(R.id.levelAvatar);
        myLevelText = (TextView) findViewById(R.id.myLevel) ;
        myLevelDescriptionText = (TextView) findViewById(R.id.myLevel_description) ;
        usableExpText = (TextView) findViewById(R.id.usableExpText);

        level1TV = (TextView) findViewById(R.id.level1TV) ;
        level2TV = (TextView) findViewById(R.id.level2TV) ;
        level3TV = (TextView) findViewById(R.id.level3TV) ;
        level4TV = (TextView) findViewById(R.id.level4TV) ;
        level5TV = (TextView) findViewById(R.id.level5TV) ;

        explosionField = ExplosionField.attach2Window(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        emailToken = bundle.getString("token");

        //나의 현재 총 경험치 불러오기
        databaseReference.child(emailToken).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myExp_value = snapshot.child("Exp").getValue(String.class);
                myExp = Integer.parseInt(myExp_value);
                expBar.setProgress(myExp);
                expVal = myExp;

                if(0 <= myExp && myExp <= 240){
                    myLevel = 1;
                }
                else if(240 < myExp && myExp <= 360){
                    myLevel = 2;
                }
                else if(360 < myExp && myExp <= 480){
                    myLevel = 3;
                }
                else if(480 < myExp && myExp <= 600){
                    myLevel = 4;
                }
                else if(600 < myExp){
                    myLevel = 5;
                }

                // 내 레벨에 맞는 경험치 최대치 설정
                setLevel(myLevel);

                // 내가 추가할 수 있는 경험치양 불러오기
                databaseReference.child(emailToken).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        current_exp = snapshot.child("CurrentExp").getValue(String.class);
                        currentExp = Integer.parseInt(current_exp);
                        usableExp = currentExp;
                        usableExpText.setText("사용 가능한 EXP : " + usableExp);

                        addBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                expVal =  expBar.getProgress();

                                int clickExp = 20; // 버튼 클릭당 증가하는 경험치
                                if(usableExp > 0) { //사용가능한 EXP가 남아있다면
                                    for(int i = 0; i < clickExp; i++) {
                                        Handler mHandler = new Handler();
                                        mHandler.postDelayed(new Runnable() {
                                            public void run() {
                                                expVal = expVal + 1;
                                                expBar.setProgress(expVal);
                                                if (expVal >= expBar.getMax()) { //만약 경험치를 다 채우면
                                                    expVal = 0; //현 경험치 = 0 초기화
                                                    myLevel = myLevel + 1; //레벨업 한칸
                                                    setLevel(myLevel);//레벨업 처리
                                                    //Toast로 알려주기
                                                    String levelupText = "축하합니다! 당신은 level" + myLevel + "입니다";
                                                    Toast.makeText(getApplicationContext(), levelupText, Toast.LENGTH_SHORT).show();
                                                    expBar.setProgress(0);
                                                }}
                                        }, i * 5);
                                    }
                                    usableExp = usableExp - clickExp;
                                    myExp = myExp + clickExp;
                                    if (usableExp < 0) usableExp = 0;
                                    usableExpText.setText("사용 가능한 EXP : " + usableExp);

                                    String value = Integer.toString(usableExp);
                                    String exp_value = Integer.toString(myExp);
                                    databaseReference.child(emailToken).child("CurrentExp").setValue(value);
                                    databaseReference.child(emailToken).child("Exp").setValue(exp_value);
                                    expBar.setProgress(myExp);

                                }
                            }
                        });
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void setLevel(int level){
        //현재 레벨 상태에 따라 최대 exp 세팅
        switch (level) {
            case 1 :
                expBar.setMax(level1Exp);
                avatar.setImageResource(R.drawable.level1);
                myLevelText.setText(R.string.level1Title);
                myLevelDescriptionText.setText(R.string.level1Description);
                setLevelColor(level);
                break;
            case 2 :
                explosionField.explode(avatar);
                expBar.setMax(level2Exp);
                myLevelText.setText(R.string.level2Title);
                myLevelDescriptionText.setText(R.string.level2Description);
                setLevelColor(level);
                mHandler.postDelayed(new Runnable() {
                    public void run() {
                        avatar.setImageResource(R.drawable.level2);
                        reset(avatar);
                        explosionField.clear();
                    };
                }, 1000);
                break;
            case 3 :
                explosionField.explode(avatar);
                expBar.setMax(level3Exp);
                avatar.setImageResource(R.drawable.level3);
                myLevelText.setText(R.string.level3Title);
                myLevelDescriptionText.setText(R.string.level3Description);
                setLevelColor(level);
                mHandler.postDelayed(new Runnable() {
                    public void run() {
                        avatar.setImageResource(R.drawable.level3);
                        reset(avatar);
                        explosionField.clear();
                    };
                }, 1000);
                break;
            case 4 :
                explosionField.explode(avatar);
                expBar.setMax(level4Exp);
                avatar.setImageResource(R.drawable.level4);
                myLevelText.setText(R.string.level4Title);
                myLevelDescriptionText.setText(R.string.level4Description);
                setLevelColor(level);
                mHandler.postDelayed(new Runnable() {
                    public void run() {
                        avatar.setImageResource(R.drawable.level4);
                        reset(avatar);
                        explosionField.clear();
                    };
                }, 1000);
                break;
            case 5 :
                explosionField.explode(avatar);
                expBar.setMax(level5Exp);
                avatar.setImageResource(R.drawable.level5);
                myLevelText.setText(R.string.level5Title);
                myLevelDescriptionText.setText(R.string.level5Description);
                setLevelColor(level);
                mHandler.postDelayed(new Runnable() {
                    public void run() {
                        avatar.setImageResource(R.drawable.level5);
                        reset(avatar);
                        explosionField.clear();
                    };
                }, 1000);
                break;
            default:
                expBar.setMax(120);
                explosionField.explode(avatar);
                break;
        }
    }

    private void reset(View root) {
        if (root instanceof ViewGroup) {
            ViewGroup parent = (ViewGroup) root;
            for (int i = 0; i < parent.getChildCount(); i++) {
                reset(parent.getChildAt(i));
            }
        } else {
            root.setScaleX(1);
            root.setScaleY(1);
            root.setAlpha(1);
        }
    }

    private void setLevelColor(int level){
        switch (level) {
            case 1:
                level1TV.setVisibility(View.VISIBLE);
                level1TV.setBackgroundColor(getResources().getColor(R.color.level1Color));
                level2TV.setBackgroundColor(getResources().getColor(R.color.white));
                level3TV.setBackgroundColor(getResources().getColor(R.color.white));
                level4TV.setBackgroundColor(getResources().getColor(R.color.white));
                level5TV.setBackgroundColor(getResources().getColor(R.color.white));
                break;
            case 2:
                level2TV.setVisibility(View.VISIBLE);
                level1TV.setBackgroundColor(getResources().getColor(R.color.white));
                level2TV.setBackgroundColor(getResources().getColor(R.color.level2Color));
                level3TV.setBackgroundColor(getResources().getColor(R.color.white));
                level4TV.setBackgroundColor(getResources().getColor(R.color.white));
                level5TV.setBackgroundColor(getResources().getColor(R.color.white));
                break;
            case 3:
                level3TV.setVisibility(View.VISIBLE);
                level1TV.setBackgroundColor(getResources().getColor(R.color.white));
                level2TV.setBackgroundColor(getResources().getColor(R.color.white));
                level3TV.setBackgroundColor(getResources().getColor(R.color.level3Color));
                level4TV.setBackgroundColor(getResources().getColor(R.color.white));
                level5TV.setBackgroundColor(getResources().getColor(R.color.white));
                break;
            case 4:
                level4TV.setVisibility(View.VISIBLE);
                level1TV.setBackgroundColor(getResources().getColor(R.color.white));
                level2TV.setBackgroundColor(getResources().getColor(R.color.white));
                level3TV.setBackgroundColor(getResources().getColor(R.color.white));
                level4TV.setBackgroundColor(getResources().getColor(R.color.level4Color));
                level5TV.setBackgroundColor(getResources().getColor(R.color.white));
                break;
            case 5:
                level5TV.setVisibility(View.VISIBLE);
                level1TV.setBackgroundColor(getResources().getColor(R.color.white));
                level2TV.setBackgroundColor(getResources().getColor(R.color.white));
                level3TV.setBackgroundColor(getResources().getColor(R.color.white));
                level4TV.setBackgroundColor(getResources().getColor(R.color.white));
                level5TV.setBackgroundColor(getResources().getColor(R.color.level5Color));
                break;
        }
    }
}