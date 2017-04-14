package com.xk.trucktrade.ui.custom.autoqueryedittext;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by xk on 2016/8/8 2:31.
 */
public class SearchEditText extends EditText {

    private SearchUtil searchUtil;

    public SearchEditText(Context context) {
        super(context);
    }

    public SearchEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        searchUtil = new SearchUtil(this,context);
        searchUtil.setOnSearchListener(new SearchUtil.OnSearchListener() {
            @Override
            public void onSearchSuccess(String result) {
                if (onSearchListener != null) {
                    onSearchListener.onSearchFinish(result);
                }
            }
        });

        this.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (onSearchListener != null) {
                    onSearchListener.beforeTextChanged(s,start,count,after);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchUtil.search(s.toString());
                if (onSearchListener != null) {
                    onSearchListener.onTextChanged(s,start,before,count);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (onSearchListener != null) {
                    onSearchListener.afterTextChanged(s);
                }
            }
        });
    }

    public SearchEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//
    }




    private OnSearchListener onSearchListener;

    public void setOnSearchListener(OnSearchListener onSearchListener) {
        this.onSearchListener = onSearchListener;
    }

    public interface OnSearchListener {
        void onSearchFinish(String result);

        void beforeTextChanged(CharSequence s, int start, int count, int after);

        void onTextChanged(CharSequence s, int start, int before, int count);

        void afterTextChanged(Editable s);
    }
}
