package com.xk.trucktrade.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.xk.trucktrade.R;
import com.xk.trucktrade.app.Constant;
import com.xk.trucktrade.bean.TruckBean;
import com.xk.trucktrade.nohttp.CallServer;
import com.xk.trucktrade.nohttp.HttpListener;
import com.xk.trucktrade.ui.adapter.MyTrucksAdapter;
import com.xk.trucktrade.ui.base.BaseActivity;
import com.xk.trucktrade.ui.custom.DividerItemDecoration;
import com.xk.trucktrade.ui.custom.MToolbar;
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

/**
 * Created by xk on 2016/6/23 9:45.
 */
public class MyTrucksActivity extends BaseActivity implements View.OnClickListener {
    private static final int RESULT_SELECT_TRUCK_BACK = 0;
    private static final int RESULT_SELECT_TRUCK_SAVE = 1;
    private MToolbar toolbar;
    private RecyclerView recyclerView;
    private MyTrucksAdapter myTrucksAdapter;
    private boolean isSelectTruck;

    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_my_trucks);
    }

    @Override
    protected void findViews() {
        toolbar = ViewUtils.findViewById(this, R.id.toolbar);
        recyclerView = ViewUtils.findViewById(this, R.id.rl_mytrucks);
    }

    @Override
    protected void setupViews(Bundle bundle) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        toolbar.setLeftImageButton(R.mipmap.ic_arrow_back);
        toolbar.setRightTextView("编辑");
        toolbar.setTitle("我的车辆");
        myTrucksAdapter = new MyTrucksAdapter(mContext, recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(DividerItemDecoration.VERTICAL, true, mContext));
        recyclerView.setAdapter(myTrucksAdapter);
        if (mBundle != null) {
            isSelectTruck = mBundle.getBoolean("isSelectTruck", false);
        }


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
        toolbar.setOnTextViewClickListener(new MToolbar.OnTextViewClickListener() {
            @Override
            public void onLeftClick() {

            }

            @Override
            public void onRightClick() {
// TODO: by xk 2016/6/25 15:25 右侧编辑按钮被点击之后
            }
        });

        myTrucksAdapter.setOnItemClickListener(new MyTrucksAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (myTrucksAdapter.getItemCount() == position + 1) {
                    toActivity(AddTruckActivity.class);
                } else {
                    if (isSelectTruck) {
                        //finish 并且把车辆的信息传递出去（这个是从选择车辆那里调过来的）
                        String id = myTrucksAdapter.getDataList().get(position ).get_id();
                        String truckCardNumber = myTrucksAdapter.getDataList().get(position ).getTruckCardNumber();
                        getIntent().putExtra("truckid",id);
                        getIntent().putExtra("truckname",truckCardNumber);
                        setResult(RESULT_SELECT_TRUCK_SAVE,getIntent());
                        finish();
                    } else {
                        //进入addtruck页面  但是信息要自动显示  表示为编辑页面

                    }
                }
            }
        });
    }

    @Override
    protected void fetchData() {
    }

    /**
     * 请求我的车辆的信息
     */
    private void requestMyTrucksInfo() {
        Request<JSONArray> registerRequest = NoHttp.createJsonArrayRequest(Constant.url_truck, RequestMethod.POST);

        registerRequest.add("action", "queryAllTrucks");
        registerRequest.add("userid", SharedPreferencesUtil.getString(getApplicationContext(), Constant.SPKEY_CURRENTUSERPHONENUMBER));
        CallServer.getRequestInstance().add(this, Constant.reuqest_what_queryalltruck, registerRequest, new HttpListener<JSONArray>() {
            @Override
            public void onSucceed(int what, Response<JSONArray> response) {
                ArrayList<TruckBean> truckBeens = new ArrayList<>();
                try {
                    JSONArray jsonArray = response.get();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject json = (JSONObject) jsonArray.get(i);
                        TruckBean truckBean = new TruckBean();
                        truckBean.setHight(json.getString("height"));
                        truckBean.setLength(json.getString("length"));
                        truckBean.setTruckBrith(json.getString("truckbirth"));
                        truckBean.setTruckCardNumber(json.getString("truckcardnumber"));
                        truckBean.setVariety(json.getString("variety"));
                        truckBean.setWeight(json.getString("weight"));
                        truckBean.setWidth(json.getString("width"));
                        truckBean.set_id(json.getString("id"));
                        truckBeens.add(truckBean);
                    }
                    myTrucksAdapter.setDataList(truckBeens);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
            }
        }, true, true, "获取我的车辆");

    }

    @Override
    protected void onResume() {
        super.onResume();
        requestMyTrucksInfo();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_add_truck:
                //跳转到添加车辆的activity

                break;
        }
    }
}
