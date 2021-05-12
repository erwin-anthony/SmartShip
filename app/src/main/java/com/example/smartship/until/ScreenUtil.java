package com.example.smartship.until;


import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import java.lang.reflect.Field;


public class ScreenUtil {

    /**
     * @方法说明:获取DisplayMetrics对象
     * @方法名称:getDisPlayMetrics
     * @param context
     * @return
     * @返回值:DisplayMetrics
     */
    public static DisplayMetrics getDisPlayMetrics(Context context) {
        DisplayMetrics metric = new DisplayMetrics();
        if (null != context) {
            ((Activity) context).getWindowManager().getDefaultDisplay()
                    .getMetrics(metric);
        }
        return metric;
    }

    /**
     * @方法说明:获取屏幕的宽度（像素）
     * @方法名称:getScreenWidth
     * @param context
     * @return
     * @返回值:int
     */
    public static int getScreenWidth(Context context) {
        int width = getDisPlayMetrics(context).widthPixels;
        return width;
    }

    /**
     * @方法说明:获取屏幕的高（像素）
     * @方法名称:getScreenHeight
     * @param context
     * @return
     * @返回值:int
     */
    public static int getScreenHeight(Context context) {
        int height = getDisPlayMetrics(context).heightPixels;
        return height;
    }

    /**
     * @方法说明:屏幕密度(0.75 / 1.0 / 1.5)
     * @方法名称:getDensity
     * @param context
     * @return
     * @返回 float
     */
    public static float getDensity(Context context) {
        float density = getDisPlayMetrics(context).density;
        return density;
    }

    /**
     * @方法说明:屏幕密度DPI(120 / 160 / 240)
     * @方法名称:getDensityDpi
     * @param context
     * @return
     * @返回 int
     */
    public static int getDensityDpi(Context context) {
        int densityDpi = getDisPlayMetrics(context).densityDpi;
        return densityDpi;
    }

    //获取状态栏高度
    public static int getStatusBarHeight(Context context){
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
            Log.v("@@@@@@", "the status bar height is : " + statusBarHeight);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }

    //设置屏幕的背景透明度(整个布局)
    public static void setBackgroundAlpha(Activity activity, float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0f-1.0f
        activity.getWindow().setAttributes(lp);
    }
}

