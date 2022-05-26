package com.example.term_project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyLevelFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyLevelFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ProgressBar expBar;
    Button addBtn, lowerBtn;
    private int expVal = 0;



    public MyLevelFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Mylevel.
     */
    // TODO: Rename and change types and number of parameters
    public static MyLevelFragment newInstance(String param1, String param2) {
        MyLevelFragment fragment = new MyLevelFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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

        expBar = (ProgressBar) rootView.findViewById(R.id.expBar);
        addBtn = (Button) rootView.findViewById(R.id.addExp);
        lowerBtn = (Button) rootView.findViewById(R.id.lowerExp);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expVal = expBar.getProgress();
//                Log.d("EXP","EXP progresas " + expVal);
                if(expVal < 120){
                    expVal = expVal + 10;
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