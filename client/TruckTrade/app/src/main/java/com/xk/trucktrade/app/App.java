package com.xk.trucktrade.app;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;

import com.baidu.mapapi.SDKInitializer;
import com.xk.trucktrade.im.service.ImService;
import com.xk.trucktrade.utils.SharedPreferencesUtil;
import com.yolanda.nohttp.Logger;
import com.yolanda.nohttp.NoHttp;


public class App extends Application {
    private Thread mUiThread = Thread.currentThread();
    private Handler handler = new Handler();

    private static App mApp;

    public static App getInstance() {
        return mApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(getApplicationContext());


        mApp = this;



        NoHttp.initialize(this);
        Logger.setTag("NoHttp");
        Logger.setDebug(true);// 开始NoHttp的调试模式, 这样就能看到请求过程和日志

        startService(new Intent(this, ImService.class));
    }


    public void mRunOnUiThread(Runnable runnable) {
        if (Thread.currentThread() != mUiThread) {
            handler.post(runnable);
        } else {
            runnable.run();
        }
    }
}
