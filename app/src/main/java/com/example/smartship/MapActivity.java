package com.example.smartship;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class MapActivity extends AppCompatActivity implements RemoteView.onMoveClickListener {

    TextView locationInfo;
    LocationClient mLocationClient;
    private android.widget.Toast Toast;
    MapView mMapView;
    BaiduMap mBaiduMap =null;
    boolean isFirstLocate =true;
    private RemoteView controlDirection;
    public RemoteView orientation;
    private View btn4;
    private Object mqttManager;
    private String host = "tcp://a1qZl4JGSVn.iot-as-mqtt.cn-shanghai.aliyuncs.com:1883";
    private String userName = "my_app&a1qZl4JGSVn";
    private String passWord = "e4489347774951d00fb433d69aca1f72db2572fc";
    private String mqtt_id = "APP|securemode=3,signmethod=hmacsha1,timestamp=789|";
    private String mqtt_sub_topic = "/a1qZl4JGSVn/my_app/user/control";
    private String mqtt_pub_topic = "/a1qZl4JGSVn/my_app/user/control";
    private ScheduledExecutorService scheduler;
    private MqttClient client;
    private MqttConnectOptions options;
    private Handler handler;
    private View button_go;
    private View button_return;
    private View button_left;
    private View button_right;



    @SuppressLint("HandlerLeak")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SDKInitializer.initialize(getApplicationContext());


        setContentView(R.layout.activity_map);


        Mqtt_init();
        startReconnect();

        handler = new Handler() {
            @SuppressLint("SetTextI18n")
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 1: //????????????????????????
                        break;
                    case 2:  // ????????????

                        break;
                    case 3:  //MQTT ??????????????????   UTF8Buffer msg=new UTF8Buffer(object.toString());
                        Toast.makeText(MapActivity.this,msg.obj.toString() ,Toast.LENGTH_SHORT).show();
                        break;
                    case 30:  //????????????
                        Toast.makeText(MapActivity.this,"????????????" ,Toast.LENGTH_SHORT).show();
                        break;
                    case 31:   //????????????
                        Toast.makeText(MapActivity.this,"????????????" ,Toast.LENGTH_SHORT).show();
                        try {
                            client.subscribe(mqtt_sub_topic,1);
                        } catch (MqttException e) {
                            e.printStackTrace();
                        }
                        break;
                    default:
                        break;
                }
            }
        };



        locationInfo = findViewById(R.id.locationInfo);
        button_go =findViewById(R.id.button_go);
        button_return =findViewById(R.id.button_return);
        button_left =findViewById(R.id.button_left);
        button_right =findViewById(R.id.button_right);
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());


        mMapView = findViewById(R.id.bmapView);
        mBaiduMap =mMapView.getMap();

        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

        mBaiduMap.setMyLocationEnabled(true);

//????????????
        button_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"????????????" ,Toast.LENGTH_SHORT).show();

                publishmessageplus(mqtt_pub_topic,"{\"??????\":150}");
            }
        } );

        button_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(),"????????????" ,Toast.LENGTH_SHORT).show();
                publishmessageplus(mqtt_pub_topic,"{\"??????\":0}");
            }
        });

        button_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(),"????????????" ,Toast.LENGTH_SHORT).show();
                publishmessageplus(mqtt_pub_topic,"{\"??????\":1}");
            }
        });

        button_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(),"????????????" ,Toast.LENGTH_SHORT).show();
                publishmessageplus(mqtt_pub_topic,"{\"??????\":255}");
            }
        });




        List<String> permissionList = new ArrayList<String>();

        if(ContextCompat.checkSelfPermission(MapActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if(ContextCompat.checkSelfPermission(MapActivity.this, Manifest.permission.READ_PHONE_STATE)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if(ContextCompat.checkSelfPermission(MapActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if(!permissionList.isEmpty()){
            String [] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(MapActivity.this,permissions,1);
        }else{
            requestLocation();
        }
        //?????????????????????
        controlDirection=(RemoteView)findViewById(R.id.controlDirectionBnt);
        controlDirection.setOnClickListener(this);





    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length>0){
                    for (int result :grantResults){
                        if(result != PackageManager.PERMISSION_GRANTED){
                            Toast.makeText(this,"????????????????????????????????????????????????",Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    requestLocation();
                }else {
                    Toast.makeText(this,"??????????????????",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
        }
    }

    private void requestLocation(){
        initLocation();
        mLocationClient.start();
    }


    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //?????????????????????????????????????????????
        //LocationMode.Hight_Accuracy???????????????
        //LocationMode. Battery_Saving???????????????
        //LocationMode. Device_Sensors?????????????????????

        option.setCoorType("bd09ll");
        //???????????????????????????????????????????????????GCJ02
        //GCJ02?????????????????????
        //BD09ll???????????????????????????
        //BD09???????????????????????????
        //????????????????????????????????????????????????????????????WGS84????????????

        option.setScanSpan(1000);
        //?????????????????????????????????????????????int???????????????ms
        //???????????????0?????????????????????????????????????????????????????????0
        //???????????????0????????????1000ms???????????????

        option.setOpenGps(true);
        //???????????????????????????gps?????????false
        //???????????????????????????????????????????????????????????????????????????true

        option.setLocationNotify(true);
        //????????????????????????GPS???????????????1S/1???????????????GPS???????????????false

        option.setIgnoreKillProcess(false);
        //???????????????SDK???????????????service??????????????????????????????
        //???????????????stop???????????????????????????????????????????????????????????????setIgnoreKillProcess(true)

        option.SetIgnoreCacheException(false);
        //???????????????????????????Crash????????????????????????????????????false

        option.setWifiCacheTimeOut(5 * 60 * 1000);
        //?????????V7.2??????????????????
        //?????????????????????????????????????????????????????????????????????Wi-Fi???????????????????????????????????????????????????????????????Wi-Fi???????????????

        option.setEnableSimulateGps(false);
        //?????????????????????????????????GPS??????????????????????????????????????????false

        option.setIsNeedAddress(true);

        mLocationClient.setLocOption(option);

    }

    @Override
    public void click(String oriration) {
        switch (oriration){
            case "up":
                Toast.makeText(this, "go", Toast.LENGTH_SHORT).show();
                publishmessageplus(mqtt_pub_topic,"{\"??????\":150}");
                break;
            case "down":
                Toast.makeText(this, "return", Toast.LENGTH_SHORT).show();
                publishmessageplus(mqtt_pub_topic,"{\"??????\":0}");
                break;
            case "left":
                Toast.makeText(this, "left", Toast.LENGTH_SHORT).show();
                publishmessageplus(mqtt_pub_topic,"{\"??????\":1}");
                break;
            case "right":
                Toast.makeText(this, "right", Toast.LENGTH_SHORT).show();
                publishmessageplus(mqtt_pub_topic,"{\"??????\":255}");
                break;
        }
    }


    private class MyLocationListener extends BDAbstractLocationListener{

        @Override
        public void onReceiveLocation(BDLocation location) {
            navigateTo(location);

            StringBuilder currentPosition =new StringBuilder();
            currentPosition.append("??????:").append(location.getLatitude()).append("\n");
            currentPosition.append("??????:").append(location.getLongitude()).append("\n");
            currentPosition.append("??????:").append(location.getCountry()).append("\n");
            currentPosition.append("???:").append(location.getProvince()).append("\n");
            currentPosition.append("???:").append(location.getCity()).append("\n");
            currentPosition.append("???:").append(location.getDistrict()).append("\n");
            currentPosition.append("??????:").append(location.getTown()).append("\n");
            currentPosition.append("??????:").append(location.getStreet()).append("\n");
            currentPosition.append("??????:").append(location.getAddrStr()).append("\n");
            currentPosition.append("???????????????");
            if (location.getLocType() == BDLocation.TypeGpsLocation) {
                currentPosition.append("GPS");
            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
                currentPosition.append("??????");
            }
            locationInfo.setText(currentPosition);

        }
    }

    private void navigateTo(BDLocation location){
        if(isFirstLocate) {
            LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
            mBaiduMap.animateMapStatus(update);
            update = MapStatusUpdateFactory.zoomTo(20f);
            mBaiduMap.animateMapStatus(update);
            isFirstLocate = false;
        }

        MyLocationData.Builder locationBuilder = new MyLocationData.Builder();
        locationBuilder.longitude(location.getLongitude());
        locationBuilder.latitude(location.getLatitude());
        MyLocationData locationData = locationBuilder.build();
        mBaiduMap.setMyLocationData(locationData);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        mBaiduMap.setMyLocationEnabled(false);
        mLocationClient.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    public void startActivity3(View view) {
        //?????????????????????
        btn4 =findViewById(R.id.location);
        btn4.setOnClickListener(new ViewClickVibrate() {
            public void onClick(View v) {
                super.onClick(v);
                // TODO
                //??????intent
                Intent intent = new Intent();
                intent.setClass(MapActivity.this, WebActivity.class);
                startActivity(intent);

            }
        });



    }

    private void Mqtt_init()
    {
        try {
            //host???????????????test???clientid?????????MQTT????????????ID?????????????????????????????????????????????MemoryPersistence??????clientid??????????????????????????????????????????
            client = new MqttClient(host, mqtt_id,
                    new MemoryPersistence());
            //MQTT???????????????
            options = new MqttConnectOptions();
            //??????????????????session,?????????????????????false??????????????????????????????????????????????????????????????????true??????????????????????????????????????????????????????
            options.setCleanSession(false);
            //????????????????????????
            options.setUserName(userName);
            //?????????????????????
            options.setPassword(passWord.toCharArray());
            // ?????????????????? ????????????
            options.setConnectionTimeout(30);
            // ???????????????????????? ???????????? ??????????????????1.5*20????????????????????????????????????????????????????????????????????????????????????????????????????????????
            options.setKeepAliveInterval(60);
            //????????????
            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    //????????????????????????????????????????????????
                    System.out.println("connectionLost----------");
                    //startReconnect();
                }
                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    //publish?????????????????????
                    System.out.println("deliveryComplete---------"
                            + token.isComplete());
                }
                @Override
                public void messageArrived(String topicName, MqttMessage message)
                        throws Exception {
                    //subscribe???????????????????????????????????????
                    System.out.println("messageArrived----------");
                    Message msg = new Message();
                    msg.what = 3;   //?????????????????????
                    msg.obj = topicName + "---" + message.toString();
                    handler.sendMessage(msg);    // hander ??????
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void Mqtt_connect() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if(!(client.isConnected()) )  //??????????????????
                    {
                        client.connect(options);
                        Message msg = new Message();
                        msg.what = 31;
                        handler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Message msg = new Message();
                    msg.what = 30;
                    handler.sendMessage(msg);
                }
            }
        }).start();
    }
    private void startReconnect() {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (!client.isConnected()) {
                    Mqtt_connect();
                }
            }
        }, 0 * 1000, 10 * 1000, TimeUnit.MILLISECONDS);
    }
    private void publishmessageplus(String topic,String message2)
    {
        if (client == null || !client.isConnected()) {
            return;
        }
        MqttMessage message = new MqttMessage();
        message.setPayload(message2.getBytes());
        try {
            client.publish(topic,message);
        } catch (MqttException e) {

            e.printStackTrace();
        }
    }

}





