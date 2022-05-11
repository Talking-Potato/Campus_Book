package com.example.term_project;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.Toast;

public class ScanQRFragment extends Fragment {
    private Context context;
    Button scanQRBtn;

    public ScanQRFragment() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static ScanQRFragment newInstance(String param1, String param2) {
        ScanQRFragment fragment = new ScanQRFragment();
        Bundle args = new Bundle();
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
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_scanqr ,container, false);
        // Inflate the layout for this fragment
        scanQRBtn = (Button)rootView.findViewById(R.id.scanQR);
        scanQRBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ScanQRActivity.class);
                startActivityForResult(intent, 101);
            }
        });
        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //getActivity();
        if(requestCode == 101 & resultCode ==Activity.RESULT_OK){
            Toast.makeText(context,"QR", Toast.LENGTH_LONG).show();
        }
    }
}