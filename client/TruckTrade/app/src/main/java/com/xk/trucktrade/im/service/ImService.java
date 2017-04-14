package com.xk.trucktrade.im.service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.xk.trucktrade.app.Constant;
import com.xk.trucktrade.im.receive.MBroadCastReceive;
import com.xk.trucktrade.utils.SharedPreferencesUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * Created by xk on 2016/8/10 20:23.
 */
public class ImService extends Service {
    private boolean startRecive=true;
    private Socket socket;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        registerReceiver();
        return super.onStartCommand(intent, flags, startId);
    }

    private void registerReceiver() {
        MBroadCastReceive mBroadCastReceive = new MBroadCastReceive(this);
        IntentFilter intentFilter = new IntentFilter(Constant.ACTION_SEND_MSG);
        registerReceiver(mBroadCastReceive,intentFilter);
        startSocketClientThread();

    }
    private void unRegisterReceiver() {
    }
    public void startSocketClientThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new Socket(Constant.IM_IP, 2000);
                    bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    //首先要发送一条数据表示自己的id
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("fromid", SharedPreferencesUtil.getString(getApplicationContext(), Constant.SPKEY_CURRENTUSERPHONENUMBER));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    bufferedWriter.write(jsonObject.toString() + "\n");
                    bufferedWriter.flush();
                    startReciver();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void startReciver() {
        while (startRecive) {
            try {
                final String line = bufferedReader.readLine();
                // TODO: by xk 2016/8/10 20:30 解析 然后插入到数据库中
                //发送一个有新消息的notify  再发送一个广播
            } catch (IOException e) {
            }
        }
    }

    public void sendMsg(String content,String toid){
        try {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("fromid", SharedPreferencesUtil.getString(getApplicationContext(), Constant.SPKEY_CURRENTUSERPHONENUMBER));
                jsonObject.put("content",content);
                jsonObject.put("toid",toid);
                jsonObject.put("time",System.currentTimeMillis());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            bufferedWriter.write(jsonObject.toString() + "\n");
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unRegisterReceiver();

        startRecive=false;
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
