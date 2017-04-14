package com.xk.trucktrade.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.xk.trucktrade.R;
import com.xk.trucktrade.app.Constant;
import com.xk.trucktrade.nohttp.CallServer;
import com.xk.trucktrade.nohttp.HttpListener;
import com.xk.trucktrade.ui.activity.HomeActivity;
import com.xk.trucktrade.ui.base.BaseFragment;
import com.xk.trucktrade.utils.SharedPreferencesUtil;
import com.xk.trucktrade.utils.ViewUtils;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 登录和注册共用的 Fragment
 */
public class LoginFragment extends BaseFragment {
    boolean isSignUp = false;
    String phoneNumber;
    String password;
    String password1;

    EditText passwordEditor1;
    EditText phoneEditor;
    EditText passwordEditor;
    TextView tv_password_label1;
    Button loginButton;
    private AlertDialog tipsDialog;
    private TelephonyManager telephonyMgr;


    @Override
    protected void setLayoutRes() {
        layoutRes = R.layout.fragment_login;

        Bundle arg = getArguments();
        if (arg != null) {
            isSignUp = arg.getBoolean(Constant.IS_SIGN_UP);
        }
    }

    @Override
    protected void findViews(View v) {
        phoneEditor = ViewUtils.findViewById(v, R.id.et_phonenumber);
        passwordEditor = ViewUtils.findViewById(v, R.id.et_password);
        loginButton = ViewUtils.findViewById(v, R.id.btn_login);
        tv_password_label1 = ViewUtils.findViewById(v, R.id.tv_password_label1);
        passwordEditor1 = ViewUtils.findViewById(v, R.id.et_password1);
    }

    @Override
    protected void setupViews(View v) {
        if (!isSignUp) {
            ((Button) ViewUtils.findViewById(v, R.id.btn_login)).setText("登录");
            TextView tv_action_forget_password = ViewUtils.findViewById(v, R.id.tv_action_forget_password);
            tv_action_forget_password.setVisibility(View.VISIBLE);
            tv_action_forget_password.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toast("跳转到找回密码的页面");
                }
            });
            ((EditText) ViewUtils.findViewById(v, R.id.et_password)).setHint("您的密码");
            ((EditText) ViewUtils.findViewById(v, R.id.et_phonenumber)).setHint("您的手机号码");
            tv_password_label1.setVisibility(View.GONE);
            passwordEditor1.setVisibility(View.GONE);
        }
    }

    @Override
    protected void setListener(View v) {
        if (isSignUp)
            setListenerForSignUp();
        else
            setListenerForLogin();
    }

    @Override
    protected void fetchData(View v) {

    }

    private void setListenerForSignUp() {


        // 注册
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                phoneNumber = phoneEditor.getText().toString();
                password = passwordEditor.getText().toString();
                password1 = passwordEditor1.getText().toString();

                if (TextUtils.isEmpty(phoneNumber)) {
                    toast("请输入手机号码");
                    return;
                }
                if (phoneNumber.length() != 11) {
                    toast("手机号码有误");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    toast("请输入密码");
                    return;
                }
                if (TextUtils.isEmpty(password1)) {
                    toast("请输入确认密码");
                    return;
                }
                if (!password.equals(password1)) {
                    toast("两次输入的密码不一致");
                    return;
                }
                signUp();
            }
        });
    }

    private void setListenerForLogin() {

        // 登录
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                phoneNumber = phoneEditor.getText().toString();
                password = passwordEditor.getText().toString();

                if (TextUtils.isEmpty(phoneNumber)) {
                    toast("请输入手机号码");
                    return;
                }
                if (phoneNumber.length() != 11) {
                    toast("手机号码有误");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    toast("请输入密码");
                    return;
                }


                login();
            }
        });
    }


    private void signUp() {
        tipsDialog = ViewUtils.makeLoadingDialog(getContext(), "正在注册", true, null);
        tipsDialog.show();
        Request<JSONObject> registerRequest = NoHttp.createJsonObjectRequest(Constant.url_register, RequestMethod.POST);
        registerRequest.add("phoneNumber", phoneNumber);
        registerRequest.add("password", password);
        telephonyMgr = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        registerRequest.add("IMEI", SharedPreferencesUtil.getString(getContext(), Constant.SPKEY_IMEI));
        CallServer.getRequestInstance().add(getContext(), Constant.reuqest_what_register, registerRequest, new HttpListener<JSONObject>() {
            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                JSONObject jsonObject = response.get();
                Log.e("LoginFragment", "onSucceed" + jsonObject.toString() + "-------");
                try {
                    String registerStateCode = jsonObject.getString("registerStateCode");
                    if (registerStateCode.equals("1")) {
                        String loginStateCode = jsonObject.getString("loginStateCode");
                        if (loginStateCode.equals("1")) {
                            String leanCloudId = jsonObject.getString("leanCloudId");
                            SharedPreferencesUtil.saveString(getContext(), Constant.SPKEY_LEANCLOUND_ID, leanCloudId);
                            SharedPreferencesUtil.saveString(getContext(), Constant.SPKEY_CURRENTUSERPHONENUMBER, phoneNumber);
                            toast("登陆成功");
                            toActivityWithClearTop(HomeActivity.class);
                            getActivity().finish();
                        } else {
                            toast("登陆失败");
                        }
                    } else {
                        toast("该手机号已被注册");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                tipsDialog.dismiss();
                //从response中取出json 然后判断是否成功
//                if(ischenggong){
//                    tipsDialog.dismiss();
//                    login();
//                }else{
//                   tipsDialog.dismiss();
//                   toast("注册失败");
//                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                tipsDialog.dismiss();
                toast("注册失败");
            }
        }, true, false, "");

    }


    private void login() {
        tipsDialog = ViewUtils.makeLoadingDialog(getContext(), "正在登录", true, null);
        tipsDialog.show();
        Request<JSONObject> loginRequest = NoHttp.createJsonObjectRequest(Constant.url_login, RequestMethod.POST);
        loginRequest.add("phoneNumber", phoneNumber);
        loginRequest.add("password", password);
        loginRequest.add("IMEI", SharedPreferencesUtil.getString(getContext(), Constant.SPKEY_IMEI));
        CallServer.getRequestInstance().add(getContext(), Constant.request_what_login, loginRequest, new HttpListener<JSONObject>() {
            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                JSONObject jsonObject = response.get();
                try {
                    String loginStateCode = jsonObject.getString("loginStateCode");
                    if (loginStateCode.equals("0")) {
                        toast("登陆失败");
                    } else {
                        toast("登陆成功");
                        String leanCloudId = jsonObject.getString("leanCloudId");
                        SharedPreferencesUtil.saveString(getContext(), Constant.SPKEY_CURRENTUSERPHONENUMBER, phoneNumber);
                        SharedPreferencesUtil.saveString(getContext(), Constant.SPKEY_LEANCLOUND_ID, leanCloudId);
                        toActivityWithClearTop(HomeActivity.class);
                        getActivity().finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                tipsDialog.dismiss();
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                tipsDialog.dismiss();
                toast("登陆失败");
            }
        }, true, false, "");

    }
}
