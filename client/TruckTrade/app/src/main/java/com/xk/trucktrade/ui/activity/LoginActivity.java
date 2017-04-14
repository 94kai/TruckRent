package com.xk.trucktrade.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.xk.trucktrade.R;
import com.xk.trucktrade.ui.adapter.LoginPagerAdapter;
import com.xk.trucktrade.ui.base.BaseActivity;
import com.xk.trucktrade.ui.fragment.SplashFragment;
import com.xk.trucktrade.utils.ViewUtils;
import com.xk.trucktrade.utils.map.MyLocationListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LoginActivity extends BaseActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private LoginPagerAdapter adapter;

    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void findViews() {
        tabLayout = ViewUtils.findViewById(this, R.id.tl_login_tab_layout);
        viewPager = ViewUtils.findViewById(this, R.id.vp_login_pager);
    }

    @Override
    protected void setupViews(Bundle bundle) {

        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("Splash")
                .add(R.id.root, new SplashFragment())
                .commit();

        tabLayout.addTab(tabLayout.newTab().setText("登录"));
        tabLayout.addTab(tabLayout.newTab().setText("注册"));

        adapter = new LoginPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
    }

    @Override
    protected void setListener() {
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    @Override
    protected void fetchData() {


    }

}
