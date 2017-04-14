package com.xk.trucktrade.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xk.trucktrade.R;
import com.xk.trucktrade.app.Constant;
import com.xk.trucktrade.bean.UserBean;
import com.xk.trucktrade.nohttp.CallServer;
import com.xk.trucktrade.nohttp.HttpListener;
import com.xk.trucktrade.ui.adapter.FriendAdapter;
import com.xk.trucktrade.ui.base.BaseActivity;
import com.xk.trucktrade.ui.custom.MToolbar;
import com.xk.trucktrade.ui.custom.autoqueryedittext.SearchEditText;
import com.xk.trucktrade.utils.SharedPreferencesUtil;
import com.xk.trucktrade.utils.ViewUtils;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xk on 2016/6/10 21:13.
 */
public class FindFriendActivity extends BaseActivity {
    private Gson gson;
    private MToolbar toolbar;
    private RecyclerView rl_friend;
    private SearchEditText et_search;
    private TextView tv_my_cared;
    private List<String> myCaredFriendPhone = new ArrayList<String>();
    /**
     * 我关注的人 只有在第一次进入该activity的时候请求  请求成功之后 把他设置给adapter 更新ui
     *
     * @author xk
     * @time 2016/6/13 9:14
     */
    private List<UserBean> myCaredFriend = new ArrayList<UserBean>();
    /**
     * 搜索出来的friend都放在这里
     *
     * @author xk
     * @time 2016/6/13 10:20
     */
    private List<UserBean> searchFriend = new ArrayList<UserBean>();
    private FriendAdapter friendAdapter;

    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_find_friend);
    }

    @Override
    protected void findViews() {
        toolbar = ViewUtils.findViewById(this, R.id.toolbar);
        rl_friend = ViewUtils.findViewById(this, R.id.rl_friend);
        tv_my_cared = ViewUtils.findViewById(this, R.id.tv_my_cared);
        et_search = ViewUtils.findViewById(this, R.id.et_search);
    }

    @Override
    protected void setupViews(Bundle bundle) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        toolbar.setLeftImageButton(R.mipmap.ic_arrow_back);
        toolbar.setRightTextView(null);
        toolbar.setTitle("搜索好友");
        friendAdapter = new FriendAdapter(getApplicationContext());
        rl_friend.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rl_friend.setAdapter(friendAdapter);
    }

    @Override
    protected void setListener() {
        toolbar.setOnImageButtonClickListener(new MToolbar.OnImageButtonClickListener() {
            @Override
            public void onLeftClick() {
                onBackPressed();
            }

            @Override
            public void onRightClick() {

            }
        });

        et_search.setOnSearchListener(new SearchEditText.OnSearchListener() {


            @Override
            public void onSearchFinish(String result) {
                requestMyCaredFriend();

                if (result == null) {
                    requestMyCaredFriendObj();
//                    friendAdapter.setDataList(null);
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            friendAdapter.notifyDataSetChanged();
//                        }
//                    });
                    return;
                }
                ArrayList<UserBean> users = new ArrayList<>();
                try {
                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        UserBean userBean = new UserBean();
                        userBean.setUsername(jsonObject.has("username") ? jsonObject.getString("username") : "无名小卒");
                        userBean.setAvatarurl(jsonObject.has("avatarurl") ? jsonObject.getString("avatarurl") : "");
                        userBean.setPhoneNumber(jsonObject.getString("phonenumber"));
                        users.add(userBean);
                    }
                    ArrayList<UserBean> newUsers = users;
                    for (int i = 0; i < newUsers.size(); i++) {
                        if (newUsers.get(i).getPhoneNumber().equals(SharedPreferencesUtil.getString(getApplicationContext(), Constant.SPKEY_CURRENTUSERPHONENUMBER))) {
                            users.remove(i);
                        }
                    }

                    if (myCaredFriendPhone.size() > 0) {
                        for (UserBean user : users) {
                            if (myCaredFriendPhone.contains(user.getPhoneNumber())) {
                                user.setIsChecked(true);
                            } else {
                                user.setIsChecked(false);
                            }
                        }
                    }
                    friendAdapter.setDataList(users);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            friendAdapter.notifyDataSetChanged();
                        }
                    });


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    protected void fetchData() {
        gson = new Gson();
        //进入了activity查出我关心的所有的人
        requestMyCaredFriend();
        requestMyCaredFriendObj();
    }

    private void requestMyCaredFriend() {
        Request<JSONArray> request = NoHttp.createJsonArrayRequest(Constant.url_my_friend, RequestMethod.POST);
        request.add("action", "queryMyFriend");
        request.add("myphone", SharedPreferencesUtil.getString(getApplicationContext(), Constant.SPKEY_CURRENTUSERPHONENUMBER));
        CallServer.getRequestInstance().add(getApplicationContext(), 0, request, new HttpListener<JSONArray>() {
            @Override
            public void onSucceed(int what, Response<JSONArray> response) {
                myCaredFriendPhone.clear();
                JSONArray array = response.get();
                for (int i = 0; i < array.length(); i++) {
                    try {
                        myCaredFriendPhone.add((String) array.get(i));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

            }
        }, false, false, "");
    }

    private void requestMyCaredFriendObj() {
        Request<JSONArray> request = NoHttp.createJsonArrayRequest(Constant.url_my_friend, RequestMethod.POST);
        request.add("action", "querymyfriendobject");
        request.add("myphone", SharedPreferencesUtil.getString(getApplicationContext(), Constant.SPKEY_CURRENTUSERPHONENUMBER));
        CallServer.getRequestInstance().add(getApplicationContext(), 0, request, new HttpListener<JSONArray>() {
                    @Override
                    public void onSucceed(int what, Response<JSONArray> response) {
                        JSONArray array = response.get();
                        ArrayList<UserBean> users = new ArrayList<>();
                        for (int i = 0; i < array.length(); i++) {
                            try {
                                JSONObject jsonObject = null;
                                jsonObject = (JSONObject) array.get(i);

                                UserBean userBean = new UserBean();
                                userBean.setUsername(jsonObject.has("username") ? jsonObject.getString("username") : "无名小卒");
                                userBean.setAvatarurl(jsonObject.has("avatarurl") ? jsonObject.getString("avatarurl") : "");
                                userBean.setPhoneNumber(jsonObject.getString("phonenumber"));
                                userBean.setIsChecked(jsonObject.getBoolean("ischeck"));
                                users.add(userBean);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        friendAdapter.setDataList(users);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                friendAdapter.notifyDataSetChanged();
                            }
                        });

                    }

                    @Override
                    public void onFailed(int what, String url, Object tag, Exception exception,
                                         int responseCode, long networkMillis) {

                    }
                }

                , false, false, "");
    }
}
