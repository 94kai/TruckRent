package com.xk.trucktrade.ui.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xk.trucktrade.R;
import com.xk.trucktrade.utils.AvatarProduceUtil;
import com.xk.trucktrade.utils.ViewUtils;


/**
 * Created by xk on 2016/6/6 10:11.
 */
public class OptionItem2View extends RelativeLayout {
    private AvatarProduceUtil avUtil;
    private TextView tv_left, tv_right,tv_xing;
    private CircleImageView iv_right;
    private View divider_top, divider_bottom;
    private View content;
    private Drawable right_img;
    private String rightText;
    private String leftText;
    private boolean showBottom,showImg;
    private boolean showTop,showXing;
    private int color;
    private Context context;

    public OptionItem2View(Context context) {
        super(context);
        this.context=context;
    }

    public OptionItem2View(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OptionItem2View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View.inflate(context, R.layout.layout_optionitem2, this);
        findView();

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.OptionItemView, defStyleAttr, 0);

        showTop = a.getBoolean(R.styleable.OptionItemView_show_top_division, false);
        showBottom = a.getBoolean(R.styleable.OptionItemView_show_bottom_division, false);
        showXing = a.getBoolean(R.styleable.OptionItemView_show_xing, false);
        leftText = a.getString(R.styleable.OptionItemView_left_text);
        showImg = a.getBoolean(R.styleable.OptionItemView_show_img,false);
        rightText = a.getString(R.styleable.OptionItemView_right_text);
        right_img = a.getDrawable(R.styleable.OptionItemView_right_img);
        color = a.getColor(R.styleable.OptionItemView_mbackground, 0xffffffff);
        avUtil=new AvatarProduceUtil (context);
        initView();
    }

    private void initView() {
        divider_top.setVisibility(showTop ? VISIBLE : INVISIBLE);
        divider_bottom.setVisibility(showBottom ? VISIBLE : INVISIBLE);
        tv_xing.setVisibility(showXing?VISIBLE:GONE);
        tv_left.setText(leftText);
        tv_right.setText(rightText);
        if (showImg) {
            iv_right.setVisibility(VISIBLE);
            setRightImg(avUtil.getBitmapByText("无名"));
            if (right_img != null) {
                iv_right.setImageDrawable(right_img);
            }
        }else{
            iv_right.setVisibility(GONE);
        }
        setBackgroundColor(color);
    }

    public void setRightText(String text) {
        tv_right.setText(text);

    }

    public void setLeftText(String text) {
        tv_left.setText(text);

    }

    public void setRightImg(Bitmap bitmap){
        iv_right.setVisibility(VISIBLE);
        iv_right.setImageBitmap(bitmap);

    }
    private void findView() {
        divider_top = ViewUtils.findViewById(this, R.id.divider_top);
        tv_left = ViewUtils.findViewById(this, R.id.tv_left);
        tv_right = ViewUtils.findViewById(this, R.id.tv_right);
        iv_right = ViewUtils.findViewById(this, R.id.iv_right);
        divider_bottom = ViewUtils.findViewById(this, R.id.divider_bottom);
        tv_xing = ViewUtils.findViewById(this, R.id.tv_xing);
    }

    public String getLeftText() {
        return tv_left.getText().toString();
    }
}
