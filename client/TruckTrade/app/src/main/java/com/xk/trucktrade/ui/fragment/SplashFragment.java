package com.xk.trucktrade.ui.fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.xk.trucktrade.R;
import com.xk.trucktrade.app.Constant;
import com.xk.trucktrade.nohttp.CallServer;
import com.xk.trucktrade.nohttp.HttpListener;
import com.xk.trucktrade.ui.activity.HomeActivity;
import com.xk.trucktrade.ui.base.BaseFragment;
import com.xk.trucktrade.utils.SharedPreferencesUtil;
import com.xk.trucktrade.utils.map.MyLocationListener;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import org.json.JSONException;
import org.json.JSONObject;

import static android.security.KeyStore.getApplicationContext;

/**
 * 是启动屏，用于判断是否已经登录
 * Created by LanKoXv on 2015/11/23.
 */
public class SplashFragment extends BaseFragment {
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener;
    private ImageButton toolbarLeft;

    @Override
    protected void setLayoutRes() {
        layoutRes = R.layout.fragment_splash;
    }

    @Override
    protected void findViews(View v) {

    }

    @Override
    protected void setupViews(View v) {

    }

    @Override
    protected void setListener(View v) {

    }

    @Override
    protected void fetchData(View v) {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }else{
            savePhoneIMEI();
            getLocate();
        }

        autoLogin();
    }

    private void savePhoneIMEI() {
        TelephonyManager telephonyMgr = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        SharedPreferencesUtil.saveString(getApplicationContext(), Constant.SPKEY_IMEI, telephonyMgr.getDeviceId());
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,  int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    savePhoneIMEI();
                } else {
                    getActivity().finish();
                }
                break;
            default:
        }
    }
    /**
     * 进入splashFragment就需要自动登陆
     *
     * @author xk
     * @time 2016/6/24 10:16
     */
    private void autoLogin() {
        Request<JSONObject> autoLoginRequest = NoHttp.createJsonObjectRequest(Constant.url_autologin, RequestMethod.POST);
        String currentUserPhonenumber = SharedPreferencesUtil.getString(getContext(), Constant.SPKEY_CURRENTUSERPHONENUMBER);
        if (currentUserPhonenumber.equals("")) {
            //自动登陆失败
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //退出当前fragment
                    getActivity().onBackPressed();
                }
            }, 800);
            return;
        }
        final long startAutoLoginDate = System.currentTimeMillis();

        autoLoginRequest.add("phoneNumber", currentUserPhonenumber);
        autoLoginRequest.add("IMEI", SharedPreferencesUtil.getString(getContext(), Constant.SPKEY_IMEI));
        CallServer.getRequestInstance().add(getContext(), Constant.reuqest_what_autologin, autoLoginRequest, new HttpListener<JSONObject>() {

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                long stopAutoLoginDate = System.currentTimeMillis();
                //判断是否成功  如果成功
                JSONObject jsonObject = response.get();
                Log.e("SplashFragment", "onSucceed" + response.get().toString());
                try {
                    if (jsonObject.getBoolean("isLogin")) {
                        if (isAdded()) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    toActivity(HomeActivity.class);
                                    getActivity().finish();
                                }
                            }, 800 - (stopAutoLoginDate - startAutoLoginDate) > 0 ? 800 - (stopAutoLoginDate - startAutoLoginDate) : 0);
                        }
                    } else {
                        new Handler().postDelayed(new Runnable() {
                                                      @Override
                                                      public void run() {
                                                          //退出当前fragment
                                                          getActivity().onBackPressed();
                                                      }
                                                  }, 800 - (stopAutoLoginDate - startAutoLoginDate) > 0 ? 800 - (stopAutoLoginDate - startAutoLoginDate) : 0
                        );
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                //由于网络原因 请求失败 认为登陆成功
                long stopAutoLoginDate = System.currentTimeMillis();
                if (SharedPreferencesUtil.getString(getContext(), Constant.SPKEY_CURRENTUSERPHONENUMBER).equals("-1")) {
                    new Handler().postDelayed(new Runnable() {
                                                  @Override
                                                  public void run() {
                                                      //退出当前fragment
                                                      getActivity().onBackPressed();
                                                  }
                                              }, 800 - (stopAutoLoginDate - startAutoLoginDate) > 0 ? 800 - (stopAutoLoginDate - startAutoLoginDate) : 0
                    );
                } else {
                    if (isAdded()) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                toActivity(HomeActivity.class);
                                getActivity().finish();
                            }
                        }, 800 - (stopAutoLoginDate - startAutoLoginDate) > 0 ? 800 - (stopAutoLoginDate - startAutoLoginDate) : 0);
                    }
                }
            }
        }, false, false, "");
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(false);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

    public void getLocate() {
        mLocationClient = new LocationClient(getContext().getApplicationContext());     //声明LocationClient类
        myListener = new MyLocationListener(mLocationClient);
        initLocation();
        mLocationClient.registerLocationListener(myListener);    //注册监听函数
        mLocationClient.start();
    }
}
