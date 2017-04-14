package com.xk.trucktrade.ui.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xk.trucktrade.R;
import com.xk.trucktrade.ui.base.BaseFragment;
import com.xk.trucktrade.ui.custom.DividerItemDecoration;
import com.xk.trucktrade.utils.ViewUtils;


/**
 * Created by xk on 2016/6/4 9:25.
 */
public class TabTopRefreshBaseFragment extends BaseFragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private TextView tv_tab_name1, tv_tab_name2, tv_tab_name3, tv_tab_name4;
    protected TextView tv_without_info;
    private View tab_underline1, tab_underline2, tab_underline3, tab_underline4;
    private ImageView iv_tab_triangle1, iv_tab_triangle2, iv_tab_triangle3, iv_tab_triangle4;
    protected SwipeRefreshLayout swipeRefreshLayout;
    protected RecyclerView rv_list;


    @Override
    protected void setLayoutRes() {
    }

    @Override
    protected void findViews(View v) {
        tv_tab_name1 = ViewUtils.findViewById(v, R.id.tv_tab_name1);
        tv_tab_name2 = ViewUtils.findViewById(v, R.id.tv_tab_name2);
        tv_tab_name3 = ViewUtils.findViewById(v, R.id.tv_tab_name3);
        tv_tab_name4 = ViewUtils.findViewById(v, R.id.tv_tab_name4);
        tab_underline1 = ViewUtils.findViewById(v, R.id.tab_underline1);
        tab_underline2 = ViewUtils.findViewById(v, R.id.tab_underline2);
        tab_underline3 = ViewUtils.findViewById(v, R.id.tab_underline3);
        tab_underline4 = ViewUtils.findViewById(v, R.id.tab_underline4);
        iv_tab_triangle1 = ViewUtils.findViewById(v, R.id.iv_tab_triangle1);
        iv_tab_triangle2 = ViewUtils.findViewById(v, R.id.iv_tab_triangle2);
        iv_tab_triangle3 = ViewUtils.findViewById(v, R.id.iv_tab_triangle3);
        iv_tab_triangle4 = ViewUtils.findViewById(v, R.id.iv_tab_triangle4);
        tv_without_info = ViewUtils.findViewById(v, R.id.tv_without_info);
        swipeRefreshLayout = (SwipeRefreshLayout) ViewUtils.findViewById(v, R.id.swipeRefreshLayout);
        rv_list = (RecyclerView) ViewUtils.findViewById(v, R.id.rv_list);
    }

    @Override
    protected void setupViews(View v) {
        swipeRefreshLayout.setColorSchemeColors(0xff3F51B5);
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(0xffffffff);
        rv_list.setLayoutManager(new LinearLayoutManager(getContext()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext());
        dividerItemDecoration.setColor(0xffB6B6B6);
        dividerItemDecoration.setSize(1);
        rv_list.addItemDecoration(dividerItemDecoration);
    }

    @Override
    protected void setListener(View v) {

        swipeRefreshLayout.setOnRefreshListener(this);

    }

    @Override
    protected void fetchData(View v) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_tab_top1:
                selectTabTop(1);
                break;
            case R.id.rl_tab_top2:
                selectTabTop(2);
                break;
            case R.id.rl_tab_top3:
                selectTabTop(3);
                break;
            case R.id.rl_tab_top4:
                selectTabTop(4);
                break;
        }
    }

    /**
     * 根据position来设置顶部tab的样式
     *
     * @author xk
     * @time 2016/6/3 21:47
     */
    private void selectTabTop(int position) {
        tv_tab_name1.setTextColor(position == 1 ? 0xFF3F51B5 : 0xFF212121);
        tv_tab_name2.setTextColor(position == 2 ? 0xFF3F51B5 : 0xFF212121);
        tv_tab_name3.setTextColor(position == 3 ? 0xFF3F51B5 : 0xFF212121);
        tv_tab_name4.setTextColor(position == 4 ? 0xFF3F51B5 : 0xFF212121);
        tab_underline1.setVisibility(position == 1 ? View.VISIBLE : View.INVISIBLE);
        tab_underline2.setVisibility(position == 2 ? View.VISIBLE : View.INVISIBLE);
        tab_underline3.setVisibility(position == 3 ? View.VISIBLE : View.INVISIBLE);
        tab_underline4.setVisibility(position == 4 ? View.VISIBLE : View.INVISIBLE);
        iv_tab_triangle1.setImageResource(position == 1 ? R.mipmap.retract : R.mipmap.expansion);
        iv_tab_triangle2.setImageResource(position == 2 ? R.mipmap.retract : R.mipmap.expansion);
        iv_tab_triangle3.setImageResource(position == 3 ? R.mipmap.retract : R.mipmap.expansion);
        iv_tab_triangle4.setImageResource(position == 4 ? R.mipmap.retract : R.mipmap.expansion);

    }

    @Override
    public void onRefresh() {

    }
}
