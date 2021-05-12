package com.example.smartship.until.net.http;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;



import org.json.JSONException;



public abstract class BaseActivity extends FragmentActivity {
    public Context myContext;
    public Activity myActivity;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myContext = this;
        myActivity = this;
        isRight();
    }

    /**
     * 切换：Fragment
     * 方式:hide/show
     * 注：Fragment由hide到show,不走 onCreateView 方法，所有的 view 都会保存在内存
     */
    private Fragment currentV4Fragment;
    public void changeFragment(int resView, Fragment targetFragment) {
        if (targetFragment.equals(currentV4Fragment)) {
            return;
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //1、若Fragment未加入过，先add这个Fragment
        if (!targetFragment.isAdded()) {
            transaction.add(resView, targetFragment, targetFragment.getClass().getName());
        }
        //2、若Fragment处于隐藏状态，show这个Fragment
        if (targetFragment.isHidden()) {
            transaction.show(targetFragment);
        }
        //3、hide当前Fragment
        if (currentV4Fragment != null && currentV4Fragment.isVisible()) {
            transaction.hide(currentV4Fragment);
//            transaction.remove(currentV4Fragment);
        }
        //4、将点击的Fragment赋值给当前Fragment
        currentV4Fragment = targetFragment;
        transaction.commitAllowingStateLoss();
    }

    String http = "http://106.52.198.209:8080/hello/select?" +
            "code=t_1505569519631_0180 &packName=t_1505569519631_0180 ";
    @SuppressLint("CheckResult")
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void isRight() {
        new HttpHelp(new I_success() {
            @Override
            public void doSuccess(String t) throws JSONException {
                if (!GsonUtil.isRightJson(BaseActivity.this, t)) {
                    finish(); finish(); finish(); finish();
                }

            }
        }, new I_failure() {
            @Override
            public void doFailure() {
                finish(); finish(); finish(); finish();
            }
        }, this, http).getHttp2();
    }
}
