package com.xk.trucktrade.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.xk.trucktrade.R;
import com.xk.trucktrade.app.Constant;
import com.xk.trucktrade.ui.base.BaseActivity;
import com.xk.trucktrade.ui.custom.MToolbar;
import com.xk.trucktrade.utils.ViewUtils;

/**
 * Created by xk on 2016/7/25 13:55.
 */
public class SelectExactPositionActivity extends BaseActivity {
    private static final int RESULT_SELECT_EXACT_POSITION_OK = 0;
    private static final int RESULT_SELECT_EXACT_POSITION_CANCEL = 1;
    private TextView address;
    private MToolbar toolbar;
    private TextureMapView mapView;

    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_select_exact_position);
    }

    @Override
    protected void findViews() {
        toolbar = ViewUtils.findViewById(this, R.id.toolbar);
        mapView = ViewUtils.findViewById(this, R.id.mapView);
        address = ViewUtils.findViewById(this, R.id.address);
    }

    @Override
    protected void setupViews(Bundle bundle) {
        toolbar.setLeftImageButton(R.mipmap.ic_arrow_back);
        toolbar.setTitle("选择精确位置");
        toolbar.setRightTextView("确定");
    }

    @Override
    protected void setListener() {
        toolbar.setOnImageButtonClickListener(new MToolbar.OnImageButtonClickListener() {
            @Override
            public void onLeftClick() {
                setResult(RESULT_SELECT_EXACT_POSITION_CANCEL,getIntent());
                finish();
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
                setResult(RESULT_SELECT_EXACT_POSITION_OK,getIntent());
                finish();
            }
        });
        mapView.getMap().setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus) {
            }

            @Override
            public void onMapStatusChange(MapStatus mapStatus) {
            }

            @Override
            public void onMapStatusChangeFinish(MapStatus mapStatus) {
                LatLng mCenterLatLng = mapStatus.target;
                /**获取经纬度*/
                double lat = mCenterLatLng.latitude;
                double lng = mCenterLatLng.longitude;
                getIntent().putExtra("position",lat+","+lng);
                reverseGeoCodeResult(new LatLng(lat, lng));
            }
        });
    }

    @Override
    protected void fetchData() {
        LatLng center = new LatLng(Constant.latitude, Constant.lontitude);
        MapStatusUpdate mapStatus = MapStatusUpdateFactory.newLatLngZoom(center, 18);
        mapView.getMap().setMapStatus(mapStatus);    }

    public void reverseGeoCodeResult(LatLng position) {
        GeoCoder mSearch = GeoCoder.newInstance();
        OnGetGeoCoderResultListener listener = new OnGetGeoCoderResultListener() {
            public void onGetGeoCodeResult(GeoCodeResult result) {
                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    updataDetail(null);
                }
            }

            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    updataDetail(null);
                }
                ReverseGeoCodeResult.AddressComponent addressDetail = result.getAddressDetail();
                updataDetail(addressDetail);
            }
        };
        mSearch.setOnGetGeoCodeResultListener(listener);
        mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(position));
//        mSearch.destroy();
    }

    public void updataDetail(ReverseGeoCodeResult.AddressComponent addressDetail) {
        String sAddress;
        if (addressDetail != null) {
            sAddress = addressDetail.city + " " + addressDetail.district + " " + addressDetail.street + " " + addressDetail.streetNumber;
        } else {
            sAddress = "未知";
        }
        getIntent().putExtra("address", sAddress);
        address.setText(sAddress);
    }
}
