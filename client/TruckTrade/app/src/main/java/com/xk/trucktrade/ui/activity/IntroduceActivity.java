package com.xk.trucktrade.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xk.trucktrade.R;
import com.xk.trucktrade.ui.base.BaseActivity;
import com.xk.trucktrade.ui.custom.MToolbar;
import com.xk.trucktrade.utils.ViewUtils;

import java.util.Calendar;

import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.OptionPicker;

/**
 * Created by xk on 2016/6/26 16:12.
 */
public class IntroduceActivity extends BaseActivity implements View.OnClickListener {
    private static final int RESULT_INTRODUCE_SAVE = 0;
    private static final int RESULT_INTRODUCE_BACK = 1;
    private MToolbar toolbar;
    private EditText et_introduce;

    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_introduce);
    }

    @Override
    protected void findViews() {
        toolbar = ViewUtils.findViewById(this, R.id.toolbar);
        et_introduce = ViewUtils.findViewById(this, R.id.et_introduce);
    }

    @Override
    protected void setupViews(Bundle bundle) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        toolbar.setLeftImageButton(R.mipmap.ic_arrow_back);
        toolbar.setTitle("备注信息");
        toolbar.setRightTextView("保存");
    }

    @Override
    protected void setListener() {
        toolbar.setOnImageButtonClickListener(new MToolbar.OnImageButtonClickListener() {
            @Override
            public void onLeftClick() {
                setResult(RESULT_INTRODUCE_BACK);
                onBackPressed();
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
                String s = et_introduce.getText().toString();
                getIntent().putExtra("introduce",s);
                setResult(RESULT_INTRODUCE_SAVE,getIntent());
                onBackPressed();
            }
        });
    }

    @Override
    protected void fetchData() {

    }

    @Override
    public void onClick(View v) {
    }
}


