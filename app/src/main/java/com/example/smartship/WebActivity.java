package com.example.smartship;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.LocationClient;
import com.example.smartship.until.net.http.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class WebActivity extends BaseActivity {



    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        getLocation();


    }







    //入口是getLocation

    /**
     * 定位：权限判断
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void getLocation() {
        //检查定位权限
        ArrayList<String> permissions = new ArrayList<>();
        if (ActivityCompat.checkSelfPermission(WebActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ActivityCompat.checkSelfPermission(WebActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }

        //判断
        if (permissions.size() == 0) {//有权限，直接获取定位
            getLocationLL();
        } else {//没有权限，获取定位权限
            requestPermissions(permissions.toArray(new String[permissions.size()]), 2);


        }
    }

    /**
     * 定位：获取经纬度
     */
    private void getLocationLL() {

        Location location = getLastKnownLocation();
        if (location != null) {
            //传递经纬度给网页
            String result = "{code: '0',type:'2',data: {longitude: '" + location.getLongitude() + "',latitude: '" + location.getLatitude() + "'}}";
//            tex.loadUrl("javascript:callback(" + result + ");");

            //日志
            String locationStr = "维度：" + location.getLatitude() + "\n"
                    + "经度：" + location.getLongitude();
//            tv.setText(  "经纬度：\n" + locationStr);
            getGdMapUri();
        } else {
            Toast.makeText(this, "位置信息获取失败", Toast.LENGTH_SHORT).show();
//            tv.setText(  "获取定位权限7 - " + "位置获取失败");
        }
    }

    /**
     * 定位：得到位置对象
     * @return
     */
    private Location getLastKnownLocation() {
        //获取地理位置管理器
        LocationManager mLocationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            Location l = mLocationManager.getLastKnownLocation(provider);//此处是提示，不是报错
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }

    /**
     * 定位：权限监听
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 2://定位
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    tv.setText(   "同意定位权限");
                    getLocationLL();
                } else {
                    Toast.makeText(this, "未同意获取定位权限", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    /**
     * 获取打开高德地图应用uri
     * style
     *导航方式(0 速度快; 1 费用少; 2 路程短; 3 不走高速；4 躲避拥堵；5
     *不走高速且避免收费；6 不走高速且躲避拥堵；
     *7 躲避收费和拥堵；8 不走高速躲避收费和拥堵)
     */
    public  void getGdMapUri(){
        Uri uri=Uri.parse("https://www.aotu.fun/qiq");
        Intent intent= new Intent(Intent.ACTION_VIEW, uri);
        intent.setData(uri);
        startActivity(intent);
    }
}