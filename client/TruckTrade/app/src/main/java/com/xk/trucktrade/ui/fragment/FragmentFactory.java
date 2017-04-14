package com.xk.trucktrade.ui.fragment;

import android.support.v4.app.Fragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xk on 2016/6/2 21:00.
 */
public class FragmentFactory {
    public FragmentFactory() {
        fragments = new HashMap<Integer, Fragment>();
    }

    private Map<Integer, Fragment> fragments;

    public Fragment getFragment(int position) {
        if (fragments.get(position) == null) {
            //new 并且添加到map中
            Fragment fragment;
            switch (position) {
                case 1:
                    fragment = new FindTruckFragment();
                    fragments.put(1, fragment);
                    break;
                case 2:
                    fragment = new FindCargoFragment();
                    fragments.put(2, fragment);
                    break;
                case 3:
                    fragment = new MessageFragment();
                    fragments.put(3, fragment);
                    break;
                case 4:
                    fragment = new MeFragment();
                    fragments.put(4, fragment);
                    break;
            }
        }
        return fragments.get(position);
    }
}
