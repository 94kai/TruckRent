package com.xk.trucktrade.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.utils.DistanceUtil;
import com.xk.trucktrade.R;
import com.xk.trucktrade.app.Constant;
import com.xk.trucktrade.bean.TruckSourceBean;
import com.xk.trucktrade.ui.base.BaseActivity;
import com.xk.trucktrade.ui.custom.CircleImageView;
import com.xk.trucktrade.ui.custom.MToolbar;
import com.xk.trucktrade.utils.AvatarProduceUtil;
import com.xk.trucktrade.utils.RelativeDateFormat;
import com.xk.trucktrade.utils.ViewUtils;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by xk on 2016/8/4 10:52.
 */
public class TruckSourceSetailActivity extends BaseActivity {
    private TextureMapView mapView;
    private TextView tv_name, tv_introduce, tv_start_point, tv_stop_point, tv_car_number, tv_exact_position, tv_distance, tv_publish_time, tv_start_time, tv_truck_type;
    private MToolbar toolbar;
    private CircleImageView civ_headimg;
    private TruckSourceBean trucksource;
    private GeoCoder mSearch;

    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_trucksource_detail);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return true;
    }

    @Override
    public boolean dispatchGenericMotionEvent(MotionEvent ev) {
        return true;
    }

    @Override
    protected void findViews() {
        toolbar = ViewUtils.findViewById(this, R.id.toolbar);
        tv_name = ViewUtils.findViewById(this, R.id.tv_name);
        tv_introduce = ViewUtils.findViewById(this, R.id.tv_introduce);
        tv_start_point = ViewUtils.findViewById(this, R.id.tv_start_point);
        tv_stop_point = ViewUtils.findViewById(this, R.id.tv_stop_point);
        tv_car_number = ViewUtils.findViewById(this, R.id.tv_car_number);
        tv_exact_position = ViewUtils.findViewById(this, R.id.tv_exact_position);
        tv_distance = ViewUtils.findViewById(this, R.id.tv_distance);
        tv_publish_time = ViewUtils.findViewById(this, R.id.tv_publish_time);
        tv_start_time = ViewUtils.findViewById(this, R.id.tv_start_time);
        tv_truck_type = ViewUtils.findViewById(this, R.id.tv_truck_type);
        civ_headimg = ViewUtils.findViewById(this, R.id.civ_headimg);
        mapView = ViewUtils.findViewById(this, R.id.mapView);
    }


    @Override
    protected void setupViews(Bundle bundle) {
        toolbar.setLeftImageButton(R.mipmap.ic_arrow_back);
        toolbar.setTitle("车源详情");
        if (bundle != null) {
            trucksource = (TruckSourceBean) bundle.getSerializable("trucksource");
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
    }

    @Override
    protected void fetchData() {

        String[] split1 = trucksource.getStart_place_point().split(",");
        reverseGeoCodeResult(new LatLng(Double.parseDouble(split1[0]), Double.parseDouble(split1[1])));
        AvatarProduceUtil avatarProducUtil = new AvatarProduceUtil(getApplicationContext());

        String start_place_point = trucksource.getStart_place_point();
        String[] split = start_place_point.split(",");
        double distance = DistanceUtil.getDistance(new LatLng(Constant.latitude, Constant.lontitude), new LatLng(Double.parseDouble(split[0]), Double.parseDouble(split[1])));
        float fDistance = (float) (distance / 1000);
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String format = decimalFormat.format(fDistance);

        tv_name.setText(trucksource.getUserBean().getUsername().length() == 0 ? "无名小卒" : trucksource.getUserBean().getUsername());
        tv_car_number.setText(trucksource.getTruckBean().getTruckCardNumber());
        tv_stop_point.setText(trucksource.getStop_place());
        tv_start_point.setText(trucksource.getStart_place());
        tv_introduce.setText(trucksource.getIntrocd().length() == 0 ? "这个人很懒，没有添加任何介绍信息" : trucksource.getIntrocd());
        tv_publish_time.setText(RelativeDateFormat.format(new Date(Long.parseLong(trucksource.getPublish_date())), new SimpleDateFormat("yyyy/MM/dd")));
        tv_distance.setText(format + "千米");
        Log.e("ss", "fetchData" + format);
        if (trucksource.getUserBean().getAvatarurl().length() == 0) {
            Bitmap bitmap = avatarProducUtil.getBitmapByText(trucksource.getUserBean().getUsername().length() == 0 ? "无名小卒" : trucksource.getUserBean().getUsername());
            civ_headimg.setImageBitmap(bitmap);
        } else {
            civ_headimg.setImageURL(trucksource.getUserBean().getAvatarurl(), TruckSourceSetailActivity.this);
        }
        tv_start_time.setText(trucksource.getLoad_date());

        tv_truck_type.setText(trucksource.getTruckBean().getVariety() + " 长" + trucksource.getTruckBean().getLength() + "米 宽" + trucksource.getTruckBean().getWidth() + "米 高"
                + trucksource.getTruckBean().getHight() + "米 重" + trucksource.getTruckBean().getWeight() + "吨");


        createMaker(Double.parseDouble(split1[0]), Double.parseDouble(split1[1]), R.mipmap.person);//画出货源的位置
        createMaker(Constant.latitude, Constant.lontitude, R.mipmap.current_position);//画出我的位置
        moveToCurrentRoutePoint();
    }

    private void createMaker(double la, double lo, int res) {
        LatLng latLng = new LatLng(la, lo);
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(res);
        OverlayOptions option = new MarkerOptions()
                .position(latLng)
                .icon(bitmap);
        mapView.getMap().addOverlay(option);
    }

    public void moveToCurrentRoutePoint() {
        float zoom = mapView.getMap().getMapStatus().zoom;
        LatLng center = new LatLng(Constant.latitude, Constant.lontitude);
        MapStatusUpdate mapStatus = MapStatusUpdateFactory.newLatLngZoom(center, zoom);
        mapView.getMap().setMapStatus(mapStatus);
    }

    public void reverseGeoCodeResult(LatLng position) {
        mSearch = GeoCoder.newInstance();
        OnGetGeoCoderResultListener listener = new OnGetGeoCoderResultListener() {
            public void onGetGeoCodeResult(GeoCodeResult result) {
                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                }
            }

            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                }
                ReverseGeoCodeResult.AddressComponent addressDetail = result.getAddressDetail();
                String sAddress = "";
                if (addressDetail != null) {
                    sAddress = addressDetail.city + " " + addressDetail.district + " " + addressDetail.street + " " + addressDetail.streetNumber;
                } else {
                    sAddress = "未知";
                }
                tv_exact_position.setText(sAddress);
            }
        };
        mSearch.setOnGetGeoCodeResultListener(listener);
        mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(position));
//        mSearch.destroy();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSearch.destroy();
    }
}
