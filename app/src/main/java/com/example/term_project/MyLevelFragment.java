package com.example.term_project;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Button;
import android.widget.Toast;

public class MyLevelFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Context context;
    ProgressBar expBar;
    Button addBtn, lowerBtn;
    private int expVal = 0;



    public MyLevelFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup ) inflater.inflate(R.layout.fragment_mylevel, container, false);
        context = container.getContext();

        expBar = (ProgressBar) rootView.findViewById(R.id.expBar);
        addBtn = (Button) rootView.findViewById(R.id.addExp);
        lowerBtn = (Button) rootView.findViewById(R.id.lowerExp);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expVal = expBar.getProgress();
//                Log.d("EXP","EXP progresas " + expVal);
                int earnExp = 10;
                if(expVal < expBar.getMax()){
                    for(int i = 0; i < earnExp; i++) {
                        Handler mHandler = new Handler();
                        mHandler.postDelayed(new Runnable() {
                            public void run() {
                                expVal = expVal + 1;
                                expBar.setProgress(expVal);
                                if (expVal >= expBar.getMax()) {
                                    expVal = 0;
                                    Toast.makeText(context, "LEVELUP", Toast.LENGTH_SHORT).show();
                                    expBar.setProgress(expVal);
                                }
                            }
                        }, i * 10); //

                    }

                }
                expBar.setProgress(expVal);


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

        return rootView;
    }



}