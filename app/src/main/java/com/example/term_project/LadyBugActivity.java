package com.example.term_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class LadyBugActivity extends AppCompatActivity {
    public String emailToken;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference("user");
    private int count;
    private String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ladybug);
        TextView counttext = (TextView)findViewById(R.id.count);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        emailToken=bundle.getString("token");

        databaseReference.child(emailToken).child("LadyBugCount").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                value = dataSnapshot.getValue(String.class);
                count = Integer.parseInt(value);
                counttext.setText(count + "");
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Button countbtn = (Button)findViewById(R.id.RideLadybug);

        countbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                counttext.setText(count + "");
                String value = Integer.toString(count);
                databaseReference.child(emailToken).child("LadyBugCount").setValue(value);
            }
        });
    }
}