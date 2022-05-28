package com.example.term_project;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SignupActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    // 파이어베이스 데이터베이스 연동
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    //DatabaseReference 데이터베이스의 특정 위치로 연결하는 거라고 생각하면 된다.
    //현재 연결은 데이터베이스에만 딱 연결해놓고
    //키값(테이블 또는 속성)의 위치 까지는 들어가지는 않은 모습이다.
    private DatabaseReference databaseReference = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        firebaseAuth = FirebaseAuth.getInstance();

        Button backButton = findViewById(R.id.signBack);
        Button button = findViewById(R.id.complete);
        EditText editEmail = findViewById(R.id.editEmail);
        EditText editPW = findViewById(R.id.editPW);
        EditText editCheckPW = findViewById(R.id.editCheckPW);
        EditText editName = findViewById(R.id.editName);
        EditText editSchoolID = findViewById(R.id.editSchoolID);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editEmail.getText().toString().equals("") && !editPW.getText().toString().equals("")
                        && !editName.getText().toString().equals("") && !editSchoolID.getText().toString().equals("")) {
                    // 모든 editText 공백이 아닐때
                    if (editPW.getText().toString().equals(editCheckPW.getText().toString()))
                        // 패스워드 둘 다 알맞게 입력되었을 때
                        createUser(editEmail.getText().toString(), editPW.getText().toString(),
                                editName.getText().toString(), editSchoolID.getText().toString());
                    else {
                        // 패스워드 2개가 서로 일치하지 않을 때
                        Toast.makeText(SignupActivity.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SignupActivity.this, "모든 정보를 입력해주세요", Toast.LENGTH_LONG).show();

                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void createUser(String ID, String PW, String name, String schoolID) {
        firebaseAuth.createUserWithEmailAndPassword(ID, PW)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // 회원가입 성공시
                            SignUpData signupData = new SignUpData(name, schoolID);
                            String[] emailTokenList = ID.split("@");
                            String[] emailTokenList2 = emailTokenList[1].split("\\.");
                            String emailToken = emailTokenList[0].concat(emailTokenList2[0]);
                            databaseReference.child("user").child(emailToken).setValue(signupData);
                            Toast.makeText(SignupActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                        } else {
                            // 계정이 중복된 경우
                            Toast.makeText(SignupActivity.this, "이미 존재하는 계정입니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}