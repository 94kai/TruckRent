package com.xk.trucktrade.ui.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xk.trucktrade.R;
import com.xk.trucktrade.app.Constant;
import com.xk.trucktrade.bean.UserBean;
import com.xk.trucktrade.nohttp.CallServer;
import com.xk.trucktrade.nohttp.HttpListener;
import com.xk.trucktrade.ui.custom.CircleImageView;
import com.xk.trucktrade.utils.AvatarProduceUtil;
import com.xk.trucktrade.utils.PersistenceUtil;
import com.xk.trucktrade.utils.SharedPreferencesUtil;
import com.xk.trucktrade.utils.ViewUtils;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的朋友的列表 Adapter
 */
public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.MViewHolder> {
    protected List<UserBean> dataList = new ArrayList<UserBean>();
    private Context context;
    private AvatarProduceUtil avatarProduceUtil;

    public FriendAdapter(Context context) {
        this.context = context;
        avatarProduceUtil = new AvatarProduceUtil(context);
    }

    /**
     * 获取该 Adapter 中存的数据
     *
     * @return
     */
    public List<UserBean> getDataList() {
        return dataList;
    }

    /**
     * 设置数据，会清空以前数据
     *
     * @param datas
     */
    public void setDataList(List<UserBean> datas) {
        dataList.clear();
        if (null != datas) {
            dataList.addAll(datas);
        }
    }

    /**
     * 添加数据，默认在最后插入，以前数据保留
     *
     * @param datas
     */
    public void addDataList(List<UserBean> datas) {
        dataList.addAll(datas);
    }

    @Override
    public MViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = View.inflate(context, R.layout.item_friend, null);
        v.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return new MViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MViewHolder holder, final int position) {
        if (onItemClickListener != null) {
            holder.root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(position);
                }
            });
        }
        final UserBean friendBean = dataList.get(position);
        holder.tv_nickname.setText(friendBean.getUsername());
        if (friendBean.getAvatarurl().equals("")) {
            holder.civ_avatar.setImageBitmap(avatarProduceUtil.getBitmapByText(friendBean.getUsername()));
        } else {
            holder.civ_avatar.setImageURI(Uri.parse(friendBean.getAvatarurl()));
        }
        holder.ll_cared.setVisibility(friendBean.getIsChecked() ? View.VISIBLE : View.GONE);
        holder.ll_cared_no.setVisibility(!friendBean.getIsChecked() ? View.VISIBLE : View.GONE);

        holder.ll_btn_care.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.ll_cared.getVisibility()== View.VISIBLE) {
                    //请求取消关注
                    requestCancelCaredFriend(holder,SharedPreferencesUtil.getString(context, Constant.SPKEY_CURRENTUSERPHONENUMBER),friendBean.getPhoneNumber());
                }else{
                    //请求关注
                     requestCaredFriend(holder,SharedPreferencesUtil.getString(context, Constant.SPKEY_CURRENTUSERPHONENUMBER),friendBean.getPhoneNumber());

                }
            }
        });
    }

    private void requestCaredFriend(final MViewHolder holder, String mPhone, String caredPhone){
        Request<JSONObject> caredFriend = NoHttp.createJsonObjectRequest(Constant.url_my_friend, RequestMethod.POST);
        caredFriend.add("action","caredSomeBody");
        caredFriend.add("myPhone",mPhone);
        caredFriend.add("otherPhone",caredPhone);
        CallServer.getRequestInstance().add(context, 0, caredFriend, new HttpListener<JSONObject>() {
            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                try {
                    boolean result = response.get().getBoolean("result");
                        holder.ll_cared.setVisibility(result ? View.VISIBLE : View.GONE);
                        holder.ll_cared_no.setVisibility(!result ? View.VISIBLE : View.GONE);
                    if (!result) {
                        Toast.makeText(context, "关注失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                Toast.makeText(context, "关注失败", Toast.LENGTH_SHORT).show();
            }
        },true,false,"");
    }

    private void requestCancelCaredFriend(final MViewHolder holder, String mPhone, String caredPhone){
        Request<JSONObject> caredFriend = NoHttp.createJsonObjectRequest(Constant.url_my_friend, RequestMethod.POST);
        caredFriend.add("action","cancelcaredSomeBody");
        caredFriend.add("myPhone",mPhone);
        caredFriend.add("otherPhone",caredPhone);
        CallServer.getRequestInstance().add(context, 0, caredFriend, new HttpListener<JSONObject>() {
            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                try {
                    boolean result = response.get().getBoolean("result");
                    holder.ll_cared_no.setVisibility(result ? View.VISIBLE : View.GONE);
                    holder.ll_cared.setVisibility(!result ? View.VISIBLE : View.GONE);
                    if (!result) {
                        Toast.makeText(context, "取消关注失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                Toast.makeText(context, "取消关注失败", Toast.LENGTH_SHORT).show();
            }
        },true,false,"");
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    class MViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_nickname;
        public CircleImageView civ_avatar;
        public LinearLayout ll_cared_no, ll_cared,ll_btn_care;
        public View root;


        public MViewHolder(View itemView) {
            super(itemView);
            root = itemView;
            tv_nickname = (TextView) root.findViewById(R.id.tv_nickname);
            civ_avatar = (CircleImageView) root.findViewById(R.id.civ_avatar);
            ll_cared_no = (LinearLayout) root.findViewById(R.id.ll_cared_no);
            ll_cared = (LinearLayout) root.findViewById(R.id.ll_cared);
            ll_btn_care = (LinearLayout) root.findViewById(R.id.ll_btn_care);
        }
    }

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
