package com.xk.trucktrade.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xk.trucktrade.R;
import com.xk.trucktrade.app.Constant;
import com.xk.trucktrade.nohttp.CallServer;
import com.xk.trucktrade.nohttp.HttpListener;
import com.xk.trucktrade.ui.base.BaseActivity;
import com.xk.trucktrade.ui.custom.MToolbar;
import com.xk.trucktrade.utils.SharedPreferencesUtil;
import com.xk.trucktrade.utils.ViewUtils;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.OptionPicker;

/**
 * Created by xk on 2016/6/26 16:12.
 */
public class AddTruckActivity extends BaseActivity implements View.OnClickListener {
    private MToolbar toolbar;
    private LinearLayout ll_select_truck_type, ll_factory_date;
    private TextView tv_factory_date,tv_truck_type;
    private Button btn_add_truck;
    private EditText et_truck_card_number,et_weight,et_height,et_length,et_width;
    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_addtruck);
    }

    @Override
    protected void findViews() {
        toolbar = ViewUtils.findViewById(this, R.id.toolbar);
        ll_select_truck_type = ViewUtils.findViewById(this, R.id.ll_select_truck_type);
        ll_factory_date = ViewUtils.findViewById(this, R.id.ll_factory_date);
        tv_factory_date = ViewUtils.findViewById(this, R.id.tv_factory_date);
        tv_truck_type = ViewUtils.findViewById(this, R.id.tv_truck_type);
        btn_add_truck = ViewUtils.findViewById(this, R.id.btn_add_truck);
        et_truck_card_number = ViewUtils.findViewById(this, R.id.et_truck_card_number);
        et_weight = ViewUtils.findViewById(this, R.id.et_weight);
        et_height = ViewUtils.findViewById(this, R.id.et_height);
        et_length = ViewUtils.findViewById(this, R.id.et_length);
        et_width = ViewUtils.findViewById(this, R.id.et_width);
    }

    @Override
    protected void setupViews(Bundle bundle) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        toolbar.setLeftImageButton(R.mipmap.ic_arrow_back);
        toolbar.setTitle("添加车辆");
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
        ll_select_truck_type.setOnClickListener(this);
        ll_factory_date.setOnClickListener(this);
        btn_add_truck.setOnClickListener(this);
    }

    @Override
    protected void fetchData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_select_truck_type:
                OptionPicker optionPicker = new OptionPicker(this, new String[]{
                        "平板", "高栏", "厢式", "保温", "冷藏", "集装箱","面包车", "危险品"
                });
                optionPicker.setOffset(2);
                optionPicker.setSelectedIndex(0);
                optionPicker.setTextSize(11);
                optionPicker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                    @Override
                    public void onOptionPicked(String option) {
                        tv_truck_type.setText(option);
                    }
                });
                optionPicker.show();
                break;
            case R.id.ll_factory_date:
                DatePicker picker = new DatePicker(this, DatePicker.YEAR_MONTH);
                picker.setRange(2000, 2020);//年份范围
                picker.setOnDatePickListener(new DatePicker.OnYearMonthPickListener() {
                    @Override
                    public void onDatePicked(String year, String month) {
                        tv_factory_date.setText(year+"年"+month+"月");
                    }
                });
                Calendar calendar = Calendar.getInstance();
                picker.setSelectedItem(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH) + 1);
                picker.show();
                break;
            case R.id.btn_add_truck:
                addTruck();
                break;
        }
    }

    private void addTruck() {
        Log.e("AddTruckActivity","addTruck");
        if (TextUtils.isEmpty(et_truck_card_number.getText().toString())) {
            toast("请输入车牌号码");
            return;
        }
        if (tv_truck_type.getText().toString().contains("请")) {
            toast("请选择车型");
            return;
        }
        if (TextUtils.isEmpty(et_weight.getText().toString())) {
            toast("请输入载重");
            return;
        }
        if (TextUtils.isEmpty(et_length.getText().toString())) {
            toast("请输入车长");
            return;
        }if (TextUtils.isEmpty(et_width.getText().toString())) {
            toast("请输入车宽");
            return;
        }if (TextUtils.isEmpty(et_height.getText().toString())) {
            toast("请输入车高");
            return;
        }
        if (tv_factory_date.getText().toString().contains("请")) {
            toast("请选择出厂日期");
            return;
        }
        //进行网络请求
        requestAddTruck();
    }

    /**
     * 进行网络请求
     * @author xk
     * @time 2016/7/20 16:23
     */
    private void requestAddTruck() {
        Request<JSONObject> registerRequest = NoHttp.createJsonObjectRequest(Constant.url_truck, RequestMethod.POST);
        registerRequest.add("action", "add");
        registerRequest.add("width",et_width.getText().toString() );
        registerRequest.add("height",et_height.getText().toString() );
        registerRequest.add("weight", et_weight.getText().toString());
        registerRequest.add("truckbirth",tv_factory_date.getText().toString() );
        registerRequest.add("variety", tv_truck_type.getText().toString());
        registerRequest.add("length", et_length.getText().toString());
        registerRequest.add("truckcardnumber", et_truck_card_number.getText().toString());
        registerRequest.add("userId",SharedPreferencesUtil.getString(this,Constant.SPKEY_CURRENTUSERPHONENUMBER));

        CallServer.getRequestInstance().add(this, Constant.reuqest_what_addtruck, registerRequest, new HttpListener<JSONObject>() {
            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                JSONObject jsonObject = response.get();
                try {
                    Boolean  addTruckState= jsonObject.getBoolean("addtruckstate");
                    if (addTruckState) {
                            toast("添加成功");
                            AddTruckActivity.this.finish();

                    } else {
                        toast("添加失败");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                toast("添加失败");
            }
        }, true, true, "正在添加中。。。");

    }
}
