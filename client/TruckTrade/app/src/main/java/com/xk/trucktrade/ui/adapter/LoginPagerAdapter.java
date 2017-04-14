package com.xk.trucktrade.ui.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;


import com.xk.trucktrade.app.Constant;
import com.xk.trucktrade.ui.fragment.LoginFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 登录页显示登录和注册用的 Adapter
 */
public class LoginPagerAdapter extends FragmentPagerAdapter {

    List<Fragment> fragments = new ArrayList<>(2);

    public LoginPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (fragments.size() > position) {
            return fragments.get(position);
        } else {
            LoginFragment fragment = new LoginFragment();
            if (position == 1) {
                Bundle arg = new Bundle();
                arg.putBoolean(Constant.IS_SIGN_UP, true);
                fragment.setArguments(arg);
            }
            fragments.add(fragment);
            return fragment;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

}
