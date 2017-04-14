package com.xk.trucktrade.ui.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xk.trucktrade.R;
import com.xk.trucktrade.ui.activity.PublishSourceActivity;
import com.xk.trucktrade.utils.ViewUtils;


/**
 * Created by xk on 2016/6/6 10:11.
 */
public class OptionItemView extends RelativeLayout {
    private TextView tv_left, tv_right;
    private ImageView iv_img, iv_jiantou;
    private View divider_top, divider_bottom;
    private View content;
    private Drawable left_img;
    private String rightText;
    private String leftText;
    private boolean showBottom;
    private boolean showTop;
    private boolean showJiantou;
    private int color;

    public OptionItemView(Context context) {
        super(context);
    }

    public OptionItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OptionItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View.inflate(context, R.layout.layout_optionitem, this);
        findView();

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.OptionItemView, defStyleAttr, 0);

        showTop = a.getBoolean(R.styleable.OptionItemView_show_top_division, false);
        showBottom = a.getBoolean(R.styleable.OptionItemView_show_bottom_division, false);
        leftText = a.getString(R.styleable.OptionItemView_left_text);
        rightText = a.getString(R.styleable.OptionItemView_right_text);
        left_img = a.getDrawable(R.styleable.OptionItemView_left_img);
        color = a.getColor(R.styleable.OptionItemView_mbackground, 0xffffffff);
        showJiantou = a.getBoolean(R.styleable.OptionItemView_show_jiantou, false);

        initView();
    }

    private void initView() {
        divider_top.setVisibility(showTop ? VISIBLE : INVISIBLE);
        divider_bottom.setVisibility(showBottom ? VISIBLE : INVISIBLE);
        tv_left.setText(leftText);
        tv_right.setText(rightText);
        iv_img.setImageDrawable(left_img);
        setBackgroundColor(color);
        iv_jiantou.setVisibility(showJiantou ? VISIBLE : INVISIBLE);
    }

    public void setRightText(String text) {
        tv_right.setText(text);

    }

    public void setLeftText(String text) {
        tv_left.setText(text);

    }

    private void findView() {
        divider_top = ViewUtils.findViewById(this, R.id.divider_top);
        tv_left = ViewUtils.findViewById(this, R.id.tv_left);
        tv_right = ViewUtils.findViewById(this, R.id.tv_right);
        iv_img = ViewUtils.findViewById(this, R.id.iv_img);
        divider_bottom = ViewUtils.findViewById(this, R.id.divider_bottom);
        iv_jiantou = ViewUtils.findViewById(this, R.id.right_jiantou);
    }

    public String getLeftText() {
        return tv_left.getText().toString();
    }
}
