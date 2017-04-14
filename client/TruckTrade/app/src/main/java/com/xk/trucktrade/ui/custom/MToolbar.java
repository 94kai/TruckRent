package com.xk.trucktrade.ui.custom;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xk.trucktrade.R;
import com.xk.trucktrade.utils.ViewUtils;


/**
 * Created by xk on 2016/5/11 10:43.
 */
public class MToolbar extends Toolbar implements View.OnClickListener {
    private View contentView;
    private ImageButton ib_left, ib_right;
    private TextView tv_left, tv_right, tv_toolbar_title;

    public MToolbar(Context context) {
        this(context, null, 0);
    }

    public MToolbar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        contentView = View.inflate(context, R.layout.toolbar, null);

        initView();
        setListener();
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        contentView.setLayoutParams(params);
        setLeft(0);
        setTop(0);
        addView(contentView);
    }

    private void setListener() {
        ib_left.setOnClickListener(this);
        ib_right.setOnClickListener(this);
        tv_left.setOnClickListener(this);
        tv_right.setOnClickListener(this);

    }

    private void initView() {

        ib_left = ViewUtils.findViewById(contentView,R.id.ib_left);
        ib_right = ViewUtils.findViewById(contentView,R.id.ib_right);
        tv_left = ViewUtils.findViewById(contentView,R.id.tv_left);
        tv_right = ViewUtils.findViewById(contentView,R.id.tv_right);
        tv_toolbar_title = ViewUtils.findViewById(contentView,R.id.tv_toolbar_title);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_left:
                if (onImageButtonClickListener != null) {
                    onImageButtonClickListener.onLeftClick();
                }
                break;
            case R.id.ib_right:
                if (onImageButtonClickListener != null) {
                    onImageButtonClickListener.onRightClick();
                }
                break;
            case R.id.tv_left:
                if (onTextViewClickListener != null) {
                    onTextViewClickListener.onLeftClick();
                }
                break;
            case R.id.tv_right:
                if (onTextViewClickListener != null) {
                    onTextViewClickListener.onRightClick();
                }
                break;
        }
    }


    public void setLeftImageButton(int id) {
        ib_left.setVisibility(VISIBLE);
        tv_left.setVisibility(GONE);
        ib_left.setImageResource(id);
    }

    public void setRightImageButton(int id) {
        ib_right.setVisibility(VISIBLE);
        tv_right.setVisibility(GONE);
        ib_right.setImageResource(id);
    }

    public void setLeftTextView(String left) {
        if (left == null) {
            ib_left.setVisibility(GONE);
            tv_left.setVisibility(GONE);
        }
        ib_left.setVisibility(GONE);
        tv_left.setVisibility(VISIBLE);
        tv_left.setText(left);
    }

    public void setRightTextView(String right) {
        if (right == null) {
            ib_right.setVisibility(GONE);
            tv_right.setVisibility(GONE);
        }
        ib_right.setVisibility(GONE);
        tv_right.setVisibility(VISIBLE);
        tv_right.setText(right);
    }


    public interface OnImageButtonClickListener {
        void onLeftClick();

        void onRightClick();
    }

    public interface OnTextViewClickListener {
        void onLeftClick();

        void onRightClick();
    }

    private OnTextViewClickListener onTextViewClickListener;
    private OnImageButtonClickListener onImageButtonClickListener;

    public void setOnImageButtonClickListener(OnImageButtonClickListener onImageButtonClickListener) {
        this.onImageButtonClickListener = onImageButtonClickListener;
    }

    public void setOnTextViewClickListener(OnTextViewClickListener onTextViewClickListener) {
        this.onTextViewClickListener = onTextViewClickListener;
    }


    public void setTitle(String titleContent) {
        tv_toolbar_title.setVisibility(VISIBLE);
        tv_toolbar_title.setText(titleContent);
    }
}
