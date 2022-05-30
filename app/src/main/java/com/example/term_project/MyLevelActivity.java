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
    Button addBtn, lowerBtn;
    ImageView avatar;

    TextView myLevelText, myLevelDescriptionText, usableExpText;

    TextView level1TV, level2TV, level3TV, level4TV, level5TV;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = database.getReference("user");

    //폭발 효과 object
    ExplosionField explosionField;
    Handler mHandler = new Handler();

    //더할 수 있는 경험치 양
    private int usableExp = 1000;

    //경험치바의 현재 경험치량을 나타낼 값
    private int expVal;
    //미리 설정된 레벨에 따른 경험치 값들
    private int level1Exp = 250;
    private int level2Exp = 350;
    private int level3Exp = 450;
    private int level4Exp = 550;
    private int level5Exp = 650;

    public String emailToken;
    private String exp_value;
    private int Allexp;
    private String exp_cur;
    private int currentExp;
    private int MyExp;
    private int myLevel;

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

        //나의 현재 총 경험치 불러오기
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        emailToken = bundle.getString("token");
        databaseReference.child(emailToken).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                exp_value = snapshot.child("Exp").getValue(String.class);
                Allexp = Integer.parseInt(exp_value);
                MyExp = Allexp;
                if(0 <= MyExp && MyExp <= 250){
                    myLevel = 1;
                    setLevel(myLevel);
                    expBar.setProgress(MyExp);
                }
                if(250 < MyExp && MyExp <= 350){
                    myLevel = 2;
                    setLevel(myLevel);
                    expBar.setProgress(MyExp);
                }
                if(350 < MyExp && MyExp <= 450){
                    myLevel = 3;
                    setLevel(myLevel);
                    expBar.setProgress(MyExp);
                }
                if(450 < MyExp && MyExp <= 550){
                    myLevel = 4;
                    setLevel(myLevel);
                    expBar.setProgress(MyExp);
                }
                if(550 < MyExp){
                    myLevel = 5;
                    setLevel(myLevel);
                    expBar.setProgress(MyExp);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        // 내가 추가할 수 있는 경험치양 불러오기
        databaseReference.child(emailToken).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                exp_cur = snapshot.child("CurrentExp").getValue(String.class);
                currentExp = Integer.parseInt(exp_cur);
                usableExp = currentExp;
                usableExpText.setText("사용 가능한 EXP : " + usableExp);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expVal = expBar.getProgress();
//                Log.d("EXP","EXP progresas " + expVal);

                int clickExp = 20; // 버튼 클릭당 증가하는 경험치
                if(usableExp > 0) {//사용가능한 EXP가 남아있다면
                        for(int i = 0; i < clickExp; i++) {
                            Handler mHandler = new Handler();
                            mHandler.postDelayed(new Runnable() {
                                public void run() {
                                    expVal = expVal + 1;
                                    expBar.setProgress(expVal);
                                    if (expVal >= expBar.getMax()) { // 만약 경험치를 다 채우면
                                        expVal = 0; //현 경험치 = 0 초기화
                                        myLevel += 1; //레벨업 한칸
                                        setLevel(myLevel);//레벨업 처리
                                        //Toast로 알려주기
                                        String levelupText = "축하합니다! 당신은 level" + myLevel + "입니다";
                                        Toast.makeText(getApplicationContext(), levelupText, Toast.LENGTH_SHORT).show();
                                    }}
                            }, i * 5);
                        }
                        usableExp = usableExp - clickExp;
                        MyExp = MyExp + clickExp;
                        if (usableExp < 0) usableExp = 0;
                    usableExpText.setText("사용 가능한 EXP : " + usableExp);
                    String value = Integer.toString(usableExp);
                    String Myvalue = Integer.toString(MyExp);
                    databaseReference.child(emailToken).child("CurrentExp").setValue(value);
                    databaseReference.child(emailToken).child("Exp").setValue(Myvalue);
                }
            }
        });
    }

    private void setLevel(int level){
        //현재 레벨 상태에 따라 최대 exp 세팅
        switch (level) {
            case 1 :
                expBar.setMax(level1Exp);
                expBar.setProgress(MyExp);
                avatar.setImageResource(R.drawable.level1);
                myLevelText.setText(R.string.level1Title);
                myLevelDescriptionText.setText(R.string.level1Description);
                setLevelColor(level);

                break;
            case 2 :
                explosionField.explode(avatar);
                expBar.setMax(level2Exp);
                expBar.setProgress(MyExp);
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
                expBar.setProgress(MyExp);
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
                expBar.setProgress(MyExp);
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
                expBar.setProgress(MyExp);
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
                expBar.setProgress(MyExp);
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