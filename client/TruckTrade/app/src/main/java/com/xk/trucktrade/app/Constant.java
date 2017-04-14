package com.xk.trucktrade.app;

import android.content.IntentFilter;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;


/**
 * 各种常量
 */
public class Constant {
    public static final String ACTION_STARTSOCKET_CLIENT = "com.xk.ACTION_STARTSOCKET_CLIENT";
    public static final String ACTION_SEND_MSG = "com.xk.ACTION_SEND_MSG";
    public static double latitude = 0;
    public static double lontitude = 0;


    public static final TimeZone timeZone = TimeZone.getTimeZone("GMT+8");
    public static final SimpleDateFormat shortDayFormat = new SimpleDateFormat("MM-dd");
    public static final SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat secondFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");


    public static final String APP_ID = "pBHqU5JhLJ4Bu2vYnOhB06P7-gzGzoHsz";
    public static final String APP_KEY = "kiKyJ3BUDry5hdlBs31HqLbl";


    public static final String IS_SIGN_UP = "is_sign_up";


    public static String[] tab_names = {"找车", "找货", "会话列表", "我的"};

    //以下是SharedPreferences的key
    public static final String SPKEY_CURRENTUSERPHONENUMBER = "SPKEY_CURRENTUSERPHONENUMBER";
    public static final String SPKEY_IMEI = "SPKEY_IMEI";
    public static final String SPKEY_LEANCLOUND_ID = "SPKEY_LEANCLOUND_ID";

    //以下是请求URL
//    public static final String baseurl_virtual_machine = "http://192.168.56.2:8080/truck/servlet/";//模拟器
//    public static final String baseurl_real_machine = "http://192.168.23.1:8080/truck/servlet/";//真机
//    IPv4 地址 . . . . . . . . . . . . : 192.168.131.2
//    子网掩码  . . . . . . . . . . . . : 255.255.255.0
//    IPv4 地址 . . . . . . . . . . . . : 192.168.10.46
//    子网掩码  . . . . . . . . . . . . : 255.255.255.0
//    默认网关. . . . . . . . . . . . . : 192.168.10.1
//    IPv4 地址 . . . . . . . . . . . . : 192.168.191.1
//    子网掩码  . . . . . . . . . . . . : 255.255.255.0
    public static final String baseurl_address = "http://10.0.2.2:8080/truck/";//模拟器
    public static String baseurl = baseurl_address + "/servlet/";//测试阶段 baseuri在app里获取 因为考虑到真机和模拟器的不同
    //以下部分都需要在app中再次赋值
    public static String url_login = baseurl + "LoginServlet";
    public static String url_autologin = baseurl + "AutoLoginServlet";
    public static String url_register = baseurl + "RigisterServlet";
    public static String url_logout = baseurl + "LogoutServlet";
    public static String url_truck = baseurl + "TruckServlet";
    public static String url_trucksource = baseurl + "TruckSourceServlet";
    public static String url_upload_head = baseurl + "UploadHeadServlet";
    public static String url_update_userinfo = baseurl + "UpdateUserinfoServlet";
    public static String url_my_friend = baseurl + "FriendServlet";
    public static String url_base_headimg = baseurl_address + "imghead/";

    public static String IM_IP="192.168.239.2";

    //车的类型
    public static final int Variety_1 = 1;
    public static final int Variety_2 = 2;
    public static final int Variety_3 = 3;
    public static final int Variety_4 = 4;
    public static final int Variety_5 = 5;
    public static final int Variety_6 = 6;
    public static final int Variety_7 = 7;
    public static final int Variety_8 = 8;

    //请求的what
    public static final int request_what_login = 0;
    public static final int reuqest_what_logout = 1;
    public static final int reuqest_what_autologin = 2;
    public static final int reuqest_what_register = 3;
    public static final int reuqest_what_addtruck = 4;
    public static final int reuqest_what_queryalltruck = 5;
    public static final int reuqest_what_addtrucksource = 6;
    public static final int reuqest_what_queryalltrucksource = 7;


}
