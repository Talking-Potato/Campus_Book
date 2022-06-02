package com.example.term_project;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText edit_id;
    private EditText edit_pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        edit_id = findViewById(R.id.editLoginID);
        edit_pw = findViewById(R.id.editLoginPW);
        Button button_login = findViewById(R.id.loginButton);
        Button button_signup = findViewById(R.id.signUp);

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn(edit_id.getText().toString(), edit_pw.getText().toString());
            }
        });

        button_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
            }
        });
    }

    private void signIn(String email, String password) {
        //ID, PW 공란 검사
        if (!validateForm()) {
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) { //로그인 성공시
                            //MainActivity로 이동
                            String[] emailTokenList = email.split("@");
                            String[] emailTokenList2 = emailTokenList[1].split("\\.");
                            String emailToken = emailTokenList[0].concat(emailTokenList2[0]);
                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                            Bundle token = new Bundle();
                            token.putString("token", emailToken);
                            intent.putExtras(token);
                            startActivity(intent);
                        } else {
                            //현재 화면에 '로그인 실패' 토스트 문구 노출
                            Toast.makeText(LoginActivity.this, "로그인을 실패하였습니다. 다시 확인해주세요.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = edit_id.getText().toString();
        if (TextUtils.isEmpty(email)) { //아이디 editText가 공란이면
            edit_id.setError("아이디를 입력해주세요.");
            valid = false;
        } else {
            edit_id.setError(null);
        }

        String password = edit_pw.getText().toString();
        if (TextUtils.isEmpty(password)) { //비밀번호 editText가 공란이면
            edit_pw.setError("비밀번호를 입력해주세요.");
            valid = false;
        } else {
            edit_pw.setError(null);
        }
        return valid;
    }
}