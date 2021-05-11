package com.example.smartship;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class BluetoothFragment extends Fragment {


    private View root;
    private View button;
    private View btn1;
    private Object view;
    private View textview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (root == null) {
            root = inflater.inflate(R.layout.fragment_bluetooth, container, false);
        }

        //添加震动监听器
        btn1 = root.findViewById(R.id.start);
        btn1.setOnClickListener(new ViewClickVibrate() {
            public void onClick(View v) {
                super.onClick(v);
                // TODO
                //添加intent
                Intent intent = new Intent();
                intent.setClass(getActivity(), MapActivity.class);
                startActivity(intent);

            }
        });
        return root;

    }
}