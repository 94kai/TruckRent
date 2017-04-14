package com.xk.trucktrade.ui.fragment;

import android.util.Log;
import android.view.View;

import com.xk.trucktrade.R;
import com.xk.trucktrade.ui.adapter.CargoInfoAdapter;
import com.xk.trucktrade.ui.adapter.TruckInfoAdapter;

/**
 * Created by xk on 2016/6/2 20:05.
 */
public class FindCargoFragment extends TabTopRefreshBaseFragment {
    @Override
    protected void setLayoutRes() {
        layoutRes = R.layout.fragment_findcargo;
    }

    @Override
    protected void findViews(View v) {
        super.findViews(v);
    }

    @Override
    protected void setupViews(View v) {
        super.setupViews(v);     
        rv_list.setAdapter(new CargoInfoAdapter(getContext()));

    }

    @Override
    protected void setListener(View v) {
        super.setListener(v);

    }

    @Override
    protected void fetchData(View v) {
        super.fetchData(v);
    }



}
