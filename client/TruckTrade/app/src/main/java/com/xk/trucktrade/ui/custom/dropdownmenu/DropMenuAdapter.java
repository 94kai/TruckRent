package com.xk.trucktrade.ui.custom.dropdownmenu;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import com.baiiu.filter.adapter.MenuAdapter;
import com.baiiu.filter.adapter.SimpleTextAdapter;
import com.baiiu.filter.interfaces.OnFilterDoneListener;
import com.baiiu.filter.interfaces.OnFilterItemClickListener;
import com.baiiu.filter.typeview.DoubleListView;
import com.baiiu.filter.typeview.SingleGridView;
import com.baiiu.filter.typeview.SingleListView;
import com.baiiu.filter.util.CommonUtil;
import com.baiiu.filter.util.UIUtil;
import com.baiiu.filter.view.FilterCheckedTextView;
import com.xk.trucktrade.R;
import com.xk.trucktrade.ui.custom.dropdownmenu.entity.FilterType;
import com.xk.trucktrade.ui.custom.dropdownmenu.entity.FilterUrl;
import com.xk.trucktrade.ui.custom.dropdownmenu.view.betterDoubleGrid.BetterDoubleGridView;
import com.xk.trucktrade.ui.custom.dropdownmenu.view.doubleGrid.DoubleGridView;
import com.xk.trucktrade.utils.XmlUtils;

import java.util.ArrayList;
import java.util.List;

import cn.qqtheme.framework.picker.AddressPicker;

/**
 * author: baiiu
 * date: on 16/1/17 21:14
 * description:
 */
public class DropMenuAdapter implements MenuAdapter {
    private final Context mContext;
    private OnFilterDoneListener onFilterDoneListener;
    private String[] titles;
    private int index;//第几个选项卡 xk
    private String content;//选中的内容


    public DropMenuAdapter(Context context, String[] titles, OnFilterDoneListener onFilterDoneListener) {
        this.mContext = context;
        this.titles = titles;
        this.onFilterDoneListener = onFilterDoneListener;
    }

    @Override
    public int getMenuCount() {
        return titles.length;
    }

    @Override
    public String getMenuTitle(int position) {
        Log.e("DropMenuAdapter","getMenuTitle"+position);
        return titles[position];
    }


    @Override
    public int getBottomMargin(int position) {
        if (position == 3) {
            return 0;
        }

        return UIUtil.dp(mContext, 140);
    }

    @Override
    public View getView(int position, FrameLayout parentContainer) {
        View view = parentContainer.getChildAt(position);
        List<FilterType> cityList = parperCityList();
        List<FilterType> timeList = parperTimeList();
        switch (position) {
            case 0:
                view = createDoubleListView(cityList,0);
                break;
            case 1:
                view = createDoubleListView(cityList,1);
                break;
            case 2:
                view = createDoubleListView(timeList,2);
                break;
        }

        return view;
    }

    private List<FilterType> parperCityList() {
        ArrayList<AddressPicker.Province> provinces = XmlUtils.parseArea(mContext, "china_area.xml");
        List<FilterType> list = new ArrayList<>();

        for (AddressPicker.Province province : provinces) {
            for (AddressPicker.City city : province.getCities()) {
                FilterType filterType = new FilterType();
                filterType.desc = city.getAreaName();
                ArrayList<String> child = new ArrayList<>();
                for (AddressPicker.County county : city.getCounties()) {
                    child.add(county.getAreaName());
                }
                filterType.child = child;
                list.add(filterType);
            }
        }
        return list;
    }


    private List<String> get31List() {
        ArrayList<String> strings = new ArrayList<>();
        strings.addAll(get30List());
        strings.add("三十一号");
        return strings;
    }

    private List<String> get30List() {
        ArrayList<String> strings = new ArrayList<>();
        strings.addAll(get29List());
        strings.add("三十号");
        return strings;
    }

    private List<String> get29List() {
        ArrayList<String> strings = new ArrayList<>();
        strings.addAll(get28List());
        strings.add("二十九号");
        return strings;
    }

    private List<String> get28List() {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("一号");
        strings.add("二号");
        strings.add("三号");
        strings.add("四号");
        strings.add("五号");
        strings.add("六号");
        strings.add("七号");
        strings.add("八号");
        strings.add("九号");
        strings.add("十号");
        strings.add("十一号");
        strings.add("十二号");
        strings.add("十三号");
        strings.add("十四号");
        strings.add("十五号");
        strings.add("十六号");
        strings.add("十七号");
        strings.add("十八号");
        strings.add("十九号");
        strings.add("二十号");
        strings.add("二十一号");
        strings.add("二十二号");
        strings.add("二十三号");
        strings.add("二十四号");
        strings.add("二十五号");
        strings.add("二十六号");
        strings.add("二十七号");
        strings.add("二十八号");
        return strings;
    }

    private List<FilterType> parperTimeList() {
        List<FilterType> list = new ArrayList<>();
        //第一项
        FilterType filterType = new FilterType();
        filterType.desc = "一月";
        filterType.child = get31List();
        list.add(filterType);
        //第二项
        filterType = new FilterType();
        filterType.desc = "二月";
        filterType.child = get29List();
        list.add(filterType);
        //第一项
        filterType = new FilterType();
        filterType.desc = "三月";
        filterType.child = get31List();
        list.add(filterType);
        //第二项
        filterType = new FilterType();
        filterType.desc = "四月";
        filterType.child = get30List();
        list.add(filterType);
        //第一项
        filterType = new FilterType();
        filterType.desc = "五月";
        filterType.child = get31List();
        list.add(filterType);
        //第二项
        filterType = new FilterType();
        filterType.desc = "六月";
        filterType.child = get30List();
        list.add(filterType);
        //第一项
        filterType = new FilterType();
        filterType.desc = "七月";
        filterType.child = get31List();
        list.add(filterType);
        //第二项
        filterType = new FilterType();
        filterType.desc = "八月";
        filterType.child = get31List();
        list.add(filterType);
        //第一项
        filterType = new FilterType();
        filterType.desc = "九月";
        filterType.child = get30List();
        list.add(filterType);
        //第二项
        filterType = new FilterType();
        filterType.desc = "十月";
        filterType.child = get31List();
        list.add(filterType);
        //第一项
        filterType = new FilterType();
        filterType.desc = "十一月";
        filterType.child = get30List();
        list.add(filterType);
        //第二项
        filterType = new FilterType();
        filterType.desc = "十二月";
        filterType.child = get31List();
        list.add(filterType);
        return list;
    }

    private View createSingleListView() {
        SingleListView<String> singleListView = new SingleListView<String>(mContext)
                .adapter(new SimpleTextAdapter<String>(null, mContext) {
                    @Override
                    public String provideText(String string) {
                        return string;
                    }

                    @Override
                    protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
                        int dp = UIUtil.dp(mContext, 15);
                        checkedTextView.setPadding(dp, dp, 0, dp);
                    }
                })
                .onItemClick(new OnFilterItemClickListener<String>() {
                    @Override
                    public void onItemClick(String item) {
                        FilterUrl.instance().singleListPosition = item;

                        FilterUrl.instance().position = 0;
                        FilterUrl.instance().positionTitle = item;

                        onFilterDone();
                    }
                });

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            list.add("" + i);
        }
        singleListView.setList(list, -1);

        return singleListView;
    }


    private View createDoubleListView(List<FilterType> filterTypes,  int pPosition) {
        DoubleListView<FilterType, String> comTypeDoubleListView = new DoubleListView<FilterType, String>(mContext)
                .leftAdapter(new SimpleTextAdapter<FilterType>(null, mContext) {
                    @Override
                    public String provideText(FilterType filterType) {
                        return filterType.desc;
                    }

                    @Override
                    protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
                        checkedTextView.setPadding(UIUtil.dp(mContext, 44), UIUtil.dp(mContext, 15), 0, UIUtil.dp(mContext, 15));
                    }
                })
                .rightAdapter(new SimpleTextAdapter<String>(null, mContext) {
                    @Override
                    public String provideText(String s) {
                        return s;
                    }

                    @Override
                    protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
                        checkedTextView.setPadding(UIUtil.dp(mContext, 30), UIUtil.dp(mContext, 15), 0, UIUtil.dp(mContext, 15));
                        checkedTextView.setBackgroundResource(android.R.color.white);
                    }
                })
                .onLeftItemClickListener(new DoubleListView.OnLeftItemClickListener<FilterType, String>() {
                    @Override
                    public List<String> provideRightList(FilterType item, int position) {
                        List<String> child = item.child;
                        if (CommonUtil.isEmpty(child)) {
                            FilterUrl.instance().doubleListLeft = item.desc;
                            FilterUrl.instance().doubleListRight = "";

                            FilterUrl.instance().position = position;
                            FilterUrl.instance().positionTitle = item.desc;
                            onFilterDone();
                        }

                        return child;
                    }
                })
                .onRightItemClickListener(new DoubleListView.OnRightItemClickListener<FilterType, String>() {
                    @Override
                    public void onRightItemClick(FilterType item, String string,int index) {
                        FilterUrl.instance().doubleListLeft = item.desc;
                        FilterUrl.instance().doubleListRight = string;
                        DropMenuAdapter.this.index=index;
                        FilterUrl.instance().position = index;
                        String title;
                        if (index==2) {
                             title=item.desc+string;
                        }else{
                            title=string;
                        }
                        title= title;
                        FilterUrl.instance().positionTitle =title ;
                        content=item.desc+string;
                        onFilterDone();
                    }
                });

        //初始化选中.
        comTypeDoubleListView.setIndex(pPosition);
        comTypeDoubleListView.setLeftList(filterTypes, 0);
        comTypeDoubleListView.setRightList(filterTypes.get(0).child, -1);
        comTypeDoubleListView.getLeftListView().setBackgroundColor(mContext.getResources().getColor(R.color.b_c_fafafa));

        return comTypeDoubleListView;
    }


    private View createSingleGridView() {
        SingleGridView<String> singleGridView = new SingleGridView<String>(mContext)
                .adapter(new SimpleTextAdapter<String>(null, mContext) {
                    @Override
                    public String provideText(String s) {
                        return s;
                    }

                    @Override
                    protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
                        checkedTextView.setPadding(0, UIUtil.dp(context, 3), 0, UIUtil.dp(context, 3));
                        checkedTextView.setGravity(Gravity.CENTER);
                        checkedTextView.setBackgroundResource(R.drawable.selector_filter_grid);
                    }
                })
                .onItemClick(new OnFilterItemClickListener<String>() {
                    @Override
                    public void onItemClick(String item) {
                        FilterUrl.instance().singleGridPosition = item;

                        FilterUrl.instance().position = 2;
                        FilterUrl.instance().positionTitle = item;

                        onFilterDone();

                    }
                });

        List<String> list = new ArrayList<>();
        for (int i = 20; i < 39; ++i) {
            list.add(String.valueOf(i));
        }
        singleGridView.setList(list, -1);


        return singleGridView;
    }


    private View createBetterDoubleGrid() {

        List<String> phases = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            phases.add("3top" + i);
        }
        List<String> areas = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            areas.add("3bottom" + i);
        }


        return new BetterDoubleGridView(mContext)
                .setmTopGridData(phases)
                .setmBottomGridList(areas)
                .setOnFilterDoneListener(onFilterDoneListener)
                .build();
    }


    private View createDoubleGrid() {
        DoubleGridView doubleGridView = new DoubleGridView(mContext);
        doubleGridView.setOnFilterDoneListener(onFilterDoneListener);


        List<String> phases = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            phases.add("3top" + i);
        }
        doubleGridView.setTopGridData(phases);

        List<String> areas = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            areas.add("3bottom" + i);
        }
        doubleGridView.setBottomGridList(areas);

        return doubleGridView;
    }


    private void onFilterDone() {
        if (onFilterDoneListener != null) {
            onFilterDoneListener.onFilterDone(index, content, "xk");
        }
    }

}
