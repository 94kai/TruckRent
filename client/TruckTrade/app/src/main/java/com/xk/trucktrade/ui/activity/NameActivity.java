package com.xk.trucktrade.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.xk.trucktrade.R;
import com.xk.trucktrade.ui.base.BaseActivity;
import com.xk.trucktrade.ui.custom.MToolbar;
import com.xk.trucktrade.utils.ViewUtils;

/**
 * Created by xk on 2016/6/26 16:12.
 */
public class NameActivity extends BaseActivity implements View.OnClickListener {
    private static final int RESULT_NAME_SAVE = 2;
    private static final int RESULT_NAME_BACK = 3;
    private MToolbar toolbar;
    private EditText et_name;

    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_name);
    }

    @Override
    protected void findViews() {
        toolbar = ViewUtils.findViewById(this, R.id.toolbar);
        et_name = ViewUtils.findViewById(this, R.id.et_name);
    }

    @Override
    protected void setupViews(Bundle bundle) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        toolbar.setLeftImageButton(R.mipmap.ic_arrow_back);
        toolbar.setTitle("修改姓名");
        toolbar.setRightTextView("保存");
    }

    @Override
    protected void setListener() {
        toolbar.setOnImageButtonClickListener(new MToolbar.OnImageButtonClickListener() {
            @Override
            public void onLeftClick() {
                setResult(RESULT_NAME_BACK);
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
                String s = et_name.getText().toString();
                getIntent().putExtra("name",s);
                setResult(RESULT_NAME_SAVE,getIntent());
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


