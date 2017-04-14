package com.xk.trucktrade.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.xk.trucktrade.R;
import com.xk.trucktrade.ui.custom.MaterialProgressDrawable;


public class ViewUtils {

    public static <T extends View> T findViewById(@NonNull View v, @IdRes int id) {
        return (T) v.findViewById(id);
    }

    public static <T extends View> T findViewById(@NonNull Activity activity, @IdRes int id) {
        return (T) activity.findViewById(id);
    }

    public static <T extends View> T findViewById(@NonNull Dialog dialog, @IdRes int id) {
        return (T) dialog.findViewById(id);
    }

    public static int dip2px(@NonNull Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 用于显示一个表示正在加载的对话框,
     * 其中有加载条和提示文本
     *
     * @param context        创建 Dialog 需要的上下文
     * @param message        对话框中的提示文本, 可以为空
     * @param cancelable     是够允许点击返回来退出此对话框
     * @param cancelListener 被取消时触发的 Listener, 可以为空
     * @return 一个已经定义完全的 AlertDialog 对象
     */
    public static AlertDialog makeLoadingDialog(@NonNull Context context, CharSequence message, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AppTheme_AlertDialog);

        View v = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null);

        // Message which show in this dialog.
        TextView mMessage = ViewUtils.findViewById(v, R.id.tv_message);
        mMessage.setText(message);

        // Container of material progress bar.
        ImageView container = ViewUtils.findViewById(v, R.id.iv_progress_bar_container);

        // Setup material progress bar.
        MaterialProgressDrawable mProgress;
        mProgress = new MaterialProgressDrawable(context, container);
        mProgress.setBackgroundColor(Color.WHITE);
        mProgress.setColorSchemeColors(v.getResources().getColor(R.color.colorPrimary));
        mProgress.updateSizes(MaterialProgressDrawable.LARGE);
        mProgress.showArrow(false);
        mProgress.setAlpha(255);
        mProgress.start();

        container.setImageDrawable(mProgress);

        builder.setView(v);

        builder.setCancelable(cancelable);
        builder.setOnCancelListener(cancelListener);

        return builder.create();
    }

    /**
     * 用于显示一个用于操作确认的对话框,
     * 其中有标题和提示文本
     *
     * @param context          创建 Dialog 需要的上下文
     * @param title            对话框中的标题, 可以为空
     * @param message          对话框中的提示文本, 可以为空
     * @param positiveListener 点击确定时的事件，可以为空
     * @param negativeListener 点击取消时的事件，可以为空
     * @return 一个已经定义完全的 AlertDialog 对象
     */
    public static AlertDialog makeConfirmAlertDialog(
            @NonNull Context context,
            CharSequence title,
            CharSequence message,
            DialogInterface.OnClickListener positiveListener,
            DialogInterface.OnClickListener negativeListener) {

        return makeConfirmAlertDialog(context, title, message, positiveListener, negativeListener, null);
    }

    /**
     * 用于显示一个用于操作确认的对话框,
     * 其中有标题和提示文本
     *
     * @param context          创建 Dialog 需要的上下文
     * @param title            对话框中的标题, 可以为空
     * @param message          对话框中的提示文本, 可以为空
     * @param positiveListener 点击确定时的事件，可以为空
     * @param negativeListener 点击取消时的事件，可以为空
     * @param cancelListener   窗口被取消的事件，可以为空
     * @return 一个已经定义完全的 AlertDialog 对象
     */
    public static AlertDialog makeConfirmAlertDialog(
            @NonNull Context context,
            CharSequence title,
            CharSequence message,
            DialogInterface.OnClickListener positiveListener,
            DialogInterface.OnClickListener negativeListener,
            DialogInterface.OnCancelListener cancelListener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setPositiveButton("确认", positiveListener);
        builder.setNegativeButton("取消", negativeListener);

        if (null != title)
            builder.setTitle(title);

        if (null != message) {
            builder.setMessage(message);
        }

        if (null != cancelListener) {
            builder.setOnCancelListener(cancelListener);
        }

        return builder.create();
    }

    /**
     * TODO: 会跳到开头
     * Sets ListView height dynamically based on the height of the items.
     *
     * @param listView to be resized
     * @return true if the listView is successfully resized, false otherwise
     */
    public static boolean setListViewHeightBasedOnItems(@NonNull ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();

        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                item.measure(0, 0);
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() *
                    (numberOfItems - 1);

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight;
            listView.setLayoutParams(params);

            return true;
        } else {
            return false;
        }
    }

    /**
     * 让文字有下划线
     *
     * @param text 要下划线的文字
     * @return 拥有下划线的 SpannableString
     */
    public static SpannableString setUnderLine(CharSequence text) {

        SpannableString spannableString = new SpannableString(text);

        spannableString.setSpan(new UnderlineSpan(), 0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }

    /**
     * 将 TextView 里的文字设置下划线
     *
     * @param textView 要设置的 TextView
     */
    public static void setUnderLine(TextView textView) {
        textView.setText(setUnderLine(textView.getText()));
    }
}
