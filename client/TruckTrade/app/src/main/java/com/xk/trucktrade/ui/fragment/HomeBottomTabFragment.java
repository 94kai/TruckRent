package com.xk.trucktrade.ui.fragment;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xk.trucktrade.R;
import com.xk.trucktrade.ui.activity.HomeActivity;
import com.xk.trucktrade.ui.activity.PublishSourceActivity;
import com.xk.trucktrade.ui.base.BaseFragment;
import com.xk.trucktrade.utils.ViewUtils;

/**
 * Created by xk on 2016/6/4 10:40.
 */
public class HomeBottomTabFragment extends BaseFragment implements View.OnClickListener {
    private boolean publishIsOpen = false;
    private int[] publishLocation;
    private int left;
    private int right;
    private ImageView iv_tab_icon1, iv_tab_icon2, iv_tab_icon3, iv_tab_icon4;
    private RelativeLayout rl_tab1, rl_tab2, rl_tab3, rl_tab4;
    private ImageView iv_tab_publish;
    private FrameLayout decorView, fl_tab_publish_top;
    private TextView tv_tab_name1, tv_tab_name2, tv_tab_name3, tv_tab_name4;

    private View shadowView;
    private RelativeLayout rl_publish_cargo, rl_publish_truck;


    @Override
    protected void setLayoutRes() {
        layoutRes = R.layout.layout_home_tab_bottom;
    }

    @Override
    protected void findViews(View v) {
        rl_tab1 = ViewUtils.findViewById(v,R.id.rl_tab1);
        rl_tab2 = ViewUtils.findViewById(v,R.id.rl_tab2);
        rl_tab3 = ViewUtils.findViewById(v,R.id.rl_tab3);
        rl_tab4 = ViewUtils.findViewById(v,R.id.rl_tab4);
        iv_tab_publish = ViewUtils.findViewById(v,R.id.iv_tab_publish);
        iv_tab_icon1 = ViewUtils.findViewById(v,R.id.iv_tab_icon1);
        iv_tab_icon2 = ViewUtils.findViewById(v,R.id.iv_tab_icon2);
        iv_tab_icon3 = ViewUtils.findViewById(v,R.id.iv_tab_icon3);
        iv_tab_icon4 = ViewUtils.findViewById(v,R.id.iv_tab_icon4);

        tv_tab_name1 = ViewUtils.findViewById(v,R.id.tv_tab_name1);
        tv_tab_name2 = ViewUtils.findViewById(v,R.id.tv_tab_name2);
        tv_tab_name3 = ViewUtils.findViewById(v,R.id.tv_tab_name3);
        tv_tab_name4 = ViewUtils.findViewById(v,R.id.tv_tab_name4);


    }

    @Override
    protected void setupViews(View v) {

    }

    @Override
    protected void setListener(View v) {
        rl_tab1.setOnClickListener(this);
        rl_tab2.setOnClickListener(this);
        rl_tab3.setOnClickListener(this);
        rl_tab4.setOnClickListener(this);
        iv_tab_publish.setOnClickListener(this);
    }

    @Override
    protected void fetchData(View v) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_tab1:
                selectBottomTab(1);
                break;
            case R.id.rl_tab2:
                selectBottomTab(2);

                break;
            case R.id.rl_tab3:
                selectBottomTab(3);
                break;
            case R.id.rl_tab4:
                selectBottomTab(4);
                break;
            case R.id.iv_tab_publish:
                startPublish();
                break;

        }
    }

    /**
     * 显示我有车 我有货 并且展示动画
     *
     * @author xk
     * @time 2016/6/2 20:00
     */
    public void startPublish() {
        publishIsOpen = true;
        // 1.获取到该按钮的位置OK
        publishLocation = new int[2];
        iv_tab_publish.getLocationInWindow(publishLocation);

        // 2.在根布局放置一个半透明的黑块OK
        decorView = (FrameLayout) mActivity.getWindow().getDecorView();
        shadowView = new View(mActivity);
        shadowView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        shadowView.setBackgroundColor(0x88000000);
        shadowView.setVisibility(View.INVISIBLE);
        shadowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopPublish();
            }
        });
        decorView.addView(shadowView);


        // 3.显示“我有货” “我有车”的按钮
        rl_publish_cargo = (RelativeLayout) View.inflate(mActivity, R.layout.layout_publish_cargo, null);
        rl_publish_truck = (RelativeLayout) View.inflate(mActivity, R.layout.layout_publish_truck, null);
        rl_publish_cargo.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        rl_publish_truck.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        rl_publish_cargo.measure(0, 0);
        int measuredWidth = rl_publish_cargo.getMeasuredWidth();

        DisplayMetrics metric = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(metric);

        left = rl_publish_cargo.getMeasuredWidth() / 3 * 2;
        right = metric.widthPixels - left - measuredWidth;

        rl_publish_truck.setX(left);
        rl_publish_cargo.setX(right);


        rl_publish_cargo.setY(publishLocation[1] - 4 * iv_tab_publish.getHeight());
        rl_publish_truck.setY(publishLocation[1] - 4 * iv_tab_publish.getHeight());
        decorView.addView(rl_publish_truck);
        decorView.addView(rl_publish_cargo);
        // 4.再在黑块以上的该位置放置一个imageview OK
        fl_tab_publish_top = (FrameLayout) View.inflate(mActivity, R.layout.layout_publish, null);
        fl_tab_publish_top.setLayoutParams(new ViewGroup.LayoutParams(iv_tab_publish.getWidth(), iv_tab_publish.getHeight()));
        fl_tab_publish_top.setX(publishLocation[0]);
        fl_tab_publish_top.setY(publishLocation[1]);
        decorView.addView(fl_tab_publish_top);
        // 5.播放动画（按钮旋转） 发布车源货源按钮出来的动画 以及黑快渐入的效果
        startAnimationRotation(ViewUtils.findViewById(fl_tab_publish_top,R.id.iv_tab_publish_top), 0, 90);
        startAnimationAlpha(shadowView, 0, 1);
        startAnimationTranslateAndScale(rl_publish_truck,
                publishLocation[0] - iv_tab_publish.getWidth() / 2, left,
                publishLocation[1] - iv_tab_publish.getWidth() / 2, publishLocation[1] - 4 * iv_tab_publish.getHeight(),
                0.3f, 1,
                0.3f, 1, true);


        //6.设置监听器
        ViewUtils.findViewById(rl_publish_cargo,R.id.iv_publish_cargo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Bundle bundle = new Bundle();
                bundle.putCharSequence("title", "发布货源信息");
                toActivity(PublishSourceActivity.class, bundle);
                if (publishIsOpen) {
                    stopPublish();
                }
            }
        });
        ViewUtils.findViewById(rl_publish_truck,R.id.iv_publish_truck).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Bundle bundle = new Bundle();
                bundle.putCharSequence("title", "发布车源信息");
                toActivity(PublishSourceActivity.class, bundle);
                if (publishIsOpen) {
                    stopPublish();
                }
            }
        });

    }

    private void startAnimationAlpha(View view, int startAlpha, int endAlpha) {
        view.setVisibility(View.VISIBLE);
        AlphaAnimation aa = new AlphaAnimation(startAlpha, endAlpha);
        aa.setDuration(300);
        view.startAnimation(aa);
    }

    private void startAnimationTranslateAndScale(final View view,
                                                 final float startTranslationX, final float endTranslationX,
                                                 float startTranslationY, float endTranslationY,
                                                 final float startScaleX, final float endScaleX,
                                                 final float startScaleY, final float endScaleY,
                                                 final boolean isFirst) {

        ValueAnimator translationX = ObjectAnimator.ofFloat(view, "translationX", startTranslationX, endTranslationX);
        ValueAnimator translationY = ObjectAnimator.ofFloat(view, "translationY", startTranslationY, endTranslationY);
        AnimatorSet animatorSet = new AnimatorSet();

        if (startScaleY < endScaleY) {
            translationX.setInterpolator(new OvershootInterpolator());
            translationY.setInterpolator(new OvershootInterpolator());
            animatorSet.setDuration(150);//出去 所以是一办的时间

        } else {
            animatorSet.setDuration(300);//出去 所以是一办的时间

        }


        ValueAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", startScaleX, endScaleX);
        ValueAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", startScaleY, endScaleY);
        animatorSet.play(translationX).with(translationY).with(scaleX).with(scaleY);

        animatorSet.addListener(new Animator.AnimatorListener() {
                                    @Override
                                    public void onAnimationStart(Animator animation) {
                                        if (isFirst) {//每次动画开始的时候 都要隐藏文字
                                            ViewUtils.findViewById(rl_publish_truck,R.id.publish_text).setVisibility(View.INVISIBLE);
                                            ViewUtils.findViewById(rl_publish_truck,R.id.publish_text).setVisibility(View.INVISIBLE);
                                        }

                                        if (startScaleY < endScaleY) {
                                            //出来
                                            if (isFirst) {
                                                rl_publish_cargo.setVisibility(View.INVISIBLE);
                                            } else {
                                                rl_publish_cargo.setVisibility(View.VISIBLE);
                                            }
                                        }
                                    }

                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        if (isFirst) {
                                            if (endScaleY > startScaleY) {
                                                startAnimationTranslateAndScale(rl_publish_cargo,
                                                        publishLocation[0] - iv_tab_publish.getWidth() / 2, right,
                                                        publishLocation[1] - iv_tab_publish.getWidth() / 2, publishLocation[1] - 4 * iv_tab_publish.getHeight(),
                                                        0.3f, 1,
                                                        0.3f, 1, false);
                                            }
                                        } else {
                                            rl_publish_cargo.setVisibility(View.VISIBLE);
                                            rl_publish_truck.setVisibility(View.VISIBLE);

                                            ViewUtils.findViewById(rl_publish_truck,R.id.publish_text).setVisibility(View.VISIBLE);
                                            ViewUtils.findViewById(rl_publish_cargo,R.id.publish_text).setVisibility(View.VISIBLE);

                                        }

                                        if (startScaleY > endScaleY) {
                                            //2.imageview隐藏
                                            decorView.removeView(fl_tab_publish_top);
                                            //3.黑快隐藏
                                            decorView.removeView(shadowView);
                                            //4.发布车源 货源按钮消失
                                            decorView.removeView(rl_publish_truck);
                                            decorView.removeView(rl_publish_cargo);
                                        }
                                    }

                                    @Override
                                    public void onAnimationCancel(Animator animation) {
                                    }

                                    @Override
                                    public void onAnimationRepeat(Animator animation) {
                                    }
                                }

        );

        animatorSet.start();


    }

    private void startAnimationRotation(View view, float start, float end) {
        RotateAnimation ra = new RotateAnimation(start, end, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        ra.setDuration(400);
        view.startAnimation(ra);
    }


    /**
     * 退出我有车 我有货 点击pop以外的区域会触发
     *
     * @author xk
     * @time 2016/6/2 20:00
     */
    private void stopPublish() {
        publishIsOpen = false;
        startAnimationRotation(ViewUtils.findViewById(fl_tab_publish_top,R.id.iv_tab_publish_top), 0, -90);

        startAnimationTranslateAndScale(rl_publish_cargo,
                right, publishLocation[0] - iv_tab_publish.getWidth() / 2,
                publishLocation[1] - 4 * iv_tab_publish.getHeight(), publishLocation[1] - iv_tab_publish.getWidth() / 2,
                1, 0.3f,
                1, 0.3f, true);
        startAnimationTranslateAndScale(rl_publish_truck,
                left, publishLocation[0] - iv_tab_publish.getWidth() / 2,
                publishLocation[1] - 4 * iv_tab_publish.getHeight(), publishLocation[1] - iv_tab_publish.getWidth() / 2,
                1, 0.3f,
                1, 0.3f, true);

        startAnimationAlpha(shadowView, 1, 0);

    }

    /**
     * 根据参数 设置底部tab的图标
     *
     * @author xk
     * @time 2016/6/2 19:50
     */
    public void selectBottomTab(int tabPosition) {
        ((HomeActivity) mActivity).selectContentFragment(tabPosition);

        if (tabPosition == 1) {
            iv_tab_icon1.setImageResource(R.mipmap.tab_icon_truck_select);
            tv_tab_name1.setTextColor(getResources().getColor(R.color.colorPrimary));
        } else {
            iv_tab_icon1.setImageResource(R.mipmap.tab_icon_truck);
            tv_tab_name1.setTextColor(getResources().getColor(R.color.primary_text));
        }
        if (tabPosition == 2) {
            iv_tab_icon2.setImageResource(R.mipmap.tab_icon_cargo_select);
            tv_tab_name2.setTextColor(getResources().getColor(R.color.colorPrimary));

        } else {
            iv_tab_icon2.setImageResource(R.mipmap.tab_icon_cargo);
            tv_tab_name2.setTextColor(getResources().getColor(R.color.primary_text));

        }
        if (tabPosition == 3) {
            iv_tab_icon3.setImageResource(R.mipmap.tab_icon_message_select);
            tv_tab_name3.setTextColor(getResources().getColor(R.color.colorPrimary));
        } else {
            iv_tab_icon3.setImageResource(R.mipmap.tab_icon_message);
            tv_tab_name3.setTextColor(getResources().getColor(R.color.primary_text));

        }
        if (tabPosition == 4) {
            iv_tab_icon4.setImageResource(R.mipmap.tab_icon_me_select);
            tv_tab_name4.setTextColor(getResources().getColor(R.color.colorPrimary));
        } else {
            iv_tab_icon4.setImageResource(R.mipmap.tab_icon_me);
            tv_tab_name4.setTextColor(getResources().getColor(R.color.primary_text));
        }

        ((HomeActivity) mActivity).setToolbar(tabPosition);

    }

    @Override
    public void onResume() {
        super.onResume();

    }
}
