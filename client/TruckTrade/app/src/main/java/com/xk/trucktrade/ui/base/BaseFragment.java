package com.xk.trucktrade.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 万物起源
 */
public abstract class BaseFragment extends Fragment {

    protected BaseActivity mActivity;

    @LayoutRes
    protected int layoutRes;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mActivity = (BaseActivity) getActivity();

        setLayoutRes();

        View v = LayoutInflater.from(getContext()).inflate(layoutRes, container, false);

        findViews(v);
        setupViews(v);
        setListener(v);
        fetchData(v);

        return v;
    }

    /**
     * 依据条件, 初始化数据成员 layoutRes
     */
    protected abstract void setLayoutRes();

    protected abstract void findViews(View v);

    protected abstract void setupViews(View v);

    protected abstract void setListener(View v);

    protected abstract void fetchData(View v);

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
        Intent intent = new Intent(getContext(), targetActivity);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 跳转到另一个 Activity 请求结果
     * <p/>
     * 注意: 要保证 Fragment 能调用 onActivityResult,
     * 该 Fragment 所处的 Activity
     * 的 onActivityResult 中必须调用 super.onActivityResult.
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
        Intent intent = new Intent(getContext(), targetActivity);
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
        Intent intent = new Intent(getContext(), targetActivity);
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
     * 显示一个 Toast, 不用于 Debug,
     * 基于 BaseActivity 中的 toast
     *
     * @param text Toast 的内容
     */
    public void toast(@NonNull CharSequence text) {
        if (mActivity != null)
            mActivity.toast(text);
    }

}
