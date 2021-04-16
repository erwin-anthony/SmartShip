package com.example.smartship;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    Button btn1;
    private View btn2;
    private View btn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//添加左侧action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setLogo(R.drawable.ic_ship);//自己的图片
        actionBar.setDisplayUseLogoEnabled(true);



        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();//进行实例化
        inflater.inflate(R.menu.menu,menu);//解析菜单文件
        return super.onCreateOptionsMenu(menu);
    }




    public void startActivity2(View view) {
        //添加震动监听器
        btn1 =findViewById(R.id.start);
        btn1.setOnClickListener(new ViewClickVibrate() {
            public void onClick(View v) {
                super.onClick(v);
                // TODO
                //添加intent
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, MapActivity.class);
                startActivity(intent);

            }
        });



    }

    public void bluetoothActivity(MenuItem item) {
        //添加震动监听器
        btn2 =findViewById(R.id.bluetooth);
        btn2.setOnClickListener(new ViewClickVibrate() {
            public void onClick(View v) {
                super.onClick(v);
                //添加intent
                Intent intent =  new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
                startActivity(intent);
            }
        });

    }


    public void wifiActivity(MenuItem item) {
        //添加震动监听器
        btn3 =findViewById(R.id.WIFI);
        btn3.setOnClickListener(new ViewClickVibrate() {
            public void onClick(View v) {
                super.onClick(v);
                // TODO
                //添加intent
                Intent intent = new Intent();
                intent.setAction("android.net.wifi.PICK_WIFI_NETWORK");
                startActivity(intent);


            }
        });


    }






}


