package com.example.term_project;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import tyrantgit.explosionfield.ExplosionField;

public class MyLevelActivity extends AppCompatActivity {

    ProgressBar expBar;
    Button addBtn, lowerBtn;
    ImageView avatar;
    TextView myLevelText, myLevelDescriptionText;
    TextView level1TV, level2TV, level3TV, level4TV, level5TV;

    //폭발 효과 object
    ExplosionField explosionField;
    Handler mHandler = new Handler();
    //나의 현재 레벨
    private int myLevel = 1;
    //나의 현재 경험치
    private int myExp = 0;
    //경험치바의 현재 경험치량을 나타낼 값
    private int expVal = 0;
    //미리 설정된 레벨에 따른 경험치 값들
    private int level1Exp = 250;
    private int level2Exp = 350;
    private int level3Exp = 450;
    private int level4Exp = 550;
    private int level5Exp = 650;

//    private int level1Exp = 250;
//    private int level2Exp = 750;
//    private int level3Exp = 1500;
//    private int level4Exp = 2500;
//    private int level5Exp = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_level);
        expBar = (ProgressBar) findViewById(R.id.expBar);
        addBtn = (Button) findViewById(R.id.addExp);
        lowerBtn = (Button) findViewById(R.id.lowerExp);
        avatar = (ImageView) findViewById(R.id.levelAvatar);
        myLevelText = (TextView) findViewById(R.id.myLevel) ;
        myLevelDescriptionText = (TextView) findViewById(R.id.myLevel_description) ;

        level1TV = (TextView) findViewById(R.id.level1TV) ;
        level2TV = (TextView) findViewById(R.id.level2TV) ;
        level3TV = (TextView) findViewById(R.id.level3TV) ;
        level4TV = (TextView) findViewById(R.id.level4TV) ;
        level5TV = (TextView) findViewById(R.id.level5TV) ;

        explosionField = ExplosionField.attach2Window(this);

        //나의 현재 총 경험치 불러오기
        myExp = 0;
        expBar.setProgress(myExp);

        // 내 레벨에 맞는 경험치 최대치 설정
        setLevel(myLevel);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expVal = expBar.getProgress();
//                Log.d("EXP","EXP progresas " + expVal);
                int earnExp = 20; // 버튼 클릭당 증가하는 경험치
                if(expVal < expBar.getMax()){
                    for(int i = 0; i < earnExp; i++) {
                        Handler mHandler = new Handler();
                        mHandler.postDelayed(new Runnable() {
                            public void run() {
                                expVal = expVal + 1;
                                expBar.setProgress(expVal);
                                if (expVal >= expBar.getMax()) { // 만약 경펌치를 다 채우면
                                    expVal = 0; //현 경험치 = 0 초기화
                                    myLevel += 1; //레벨업 한칸
                                    setLevel(myLevel);//레벨업 처리
                                    //Toast로 알려주기
                                    String levelupText = "축하합니다! 당신은 level" + myLevel + "입니다";
                                    Toast.makeText(getApplicationContext(), levelupText, Toast.LENGTH_SHORT).show();
                                }}
                        }, i * 5);
                    }}
            }
        });

        lowerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expVal = expBar.getProgress();
//                Log.d("EXP","EXP progresas " + expVal);
                if(expVal > 0){
                    expVal = expVal - 10;
                }
                expBar.setProgress(expVal);
            }
        });
    }
    private void setLevel(int level){
        //현재 레벨 상태에 따라 최대 exp 세팅
        switch (level) {
            case 1 :
                expBar.setMax(level1Exp);
                expBar.setProgress(myExp);
                avatar.setImageResource(R.drawable.level1);
                myLevelText.setText(R.string.level1Title);
                myLevelDescriptionText.setText(R.string.level1Description);
                setLevelColor(level);

                break;
            case 2 :
                explosionField.explode(avatar);
                expBar.setMax(level2Exp);
                expBar.setProgress(myExp);
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
                expBar.setProgress(myExp);
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
                expBar.setProgress(myExp);
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
                expBar.setProgress(myExp);
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
                expBar.setProgress(myExp);
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