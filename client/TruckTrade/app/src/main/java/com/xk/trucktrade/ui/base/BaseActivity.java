package com.xk.trucktrade.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.xk.trucktrade.app.App;



public abstract class BaseActivity extends AppCompatActivity {

    protected App mApp;
    protected BaseActivity mContext;
    protected Intent mIntent;
    protected Bundle mBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Init data-members
        mApp = App.getInstance();
        mContext = this;
        mIntent = getIntent();
        mBundle = mIntent.getExtras();

        setLayout();
        findViews();
        setupViews(mBundle);
        setListener();
        fetchData();
    }

    protected abstract void setLayout();

    protected abstract void findViews();

    protected abstract void setupViews(Bundle bundle);

    protected abstract void setListener();

    protected abstract void fetchData();

    /**
     * 跳转到另一个 Activity
     *
     * @param targetActivity 要跳转到的 Activity
     */
    public void toActivity(@NonNull Class<?> targetActivity) {
        toActivity(targetActivity, null);
    }

    /**
     * 跳转到另一个 Activity, 并附带 Bundle
     *
     * @param targetActivity 要跳转到的 Activity
     * @param bundle         要附带的 Bundle
     */
    public void toActivity(@NonNull Class<?> targetActivity, Bundle bundle) {
        Intent intent = new Intent(this, targetActivity);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 跳转到另一个 Activity 请求结果
     *
     * @param targetActivity 要跳转到的 Activity
     * @param requestCode    此次请求的标记
     */
    public void toActivityForResult(@NonNull Class<?> targetActivity, int requestCode) {
        toActivityForResult(targetActivity, requestCode, null);
    }

    /**
     * 跳转到另一个 Activity 请求结果, 并附带 Bundle
     *
     * @param targetActivity 要跳转到的 Activity
     * @param requestCode    此次请求的标记
     * @param bundle         要附带的 Bundle
     */
    public void toActivityForResult(@NonNull Class<?> targetActivity, int requestCode, Bundle bundle) {
        Intent intent = new Intent(this, targetActivity);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 跳转到另一个 Activity，该 Activity 会是以新生状态出现
     *
     * @param targetActivity 要跳转到的 Activity
     */
    public void toActivityWithClearTop(@NonNull Class<?> targetActivity) {
        Intent intent = new Intent(this, targetActivity);
        // 清除在目标之上的 Activity, 且清除已存在的目标 Activity
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    /**
     * 显示一个 Toast, 不用于 Debug
     *
     * @param resId Toast 的内容的 id
     */
    public void toast(@StringRes int resId) {
        toast(getString(resId));
    }

    /**
     * 显示一个 Toast, 不用于 Debug
     *
     * @param text Toast 的内容
     */
    public void toast(@NonNull final CharSequence text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
