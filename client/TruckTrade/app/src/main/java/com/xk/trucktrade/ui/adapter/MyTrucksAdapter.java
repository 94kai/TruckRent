package com.xk.trucktrade.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xk.trucktrade.R;
import com.xk.trucktrade.bean.TruckBean;
import com.xk.trucktrade.bean.UserBean;
import com.xk.trucktrade.ui.custom.CircleImageView;
import com.xk.trucktrade.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的车辆的列表 Adapter
 */
public class MyTrucksAdapter extends RecyclerView.Adapter<MyTrucksAdapter.MViewHolder> {
    protected List<TruckBean> dataList = new ArrayList<TruckBean>();
    private Context context;
    private RecyclerView recyclerView;
    public MyTrucksAdapter(Context context, RecyclerView recyclerView) {
        this.context = context;
        this.recyclerView = recyclerView;
    }

    /**
     * 获取该 Adapter 中存的数据
     *
     * @return
     */
    public List<TruckBean> getDataList() {
        return dataList;
    }

    /**
     * 设置数据，会清空以前数据
     *
     * @param datas
     */
    public void setDataList(List<TruckBean> datas) {
        dataList.clear();
        if (null != datas) {
            dataList.addAll(datas);
        }
        notifyDataSetChanged();

    }

    /**
     * 添加数据，默认在最后插入，以前数据保留
     *
     * @param datas
     */
    public void addDataList(List<TruckBean> datas) {
        dataList.addAll(datas);
    }

    @Override
    public MViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==0){
            View v = View.inflate(context, R.layout.item_my_truck, null);
            v.setTag(viewType);
            v.setLayoutParams(new LinearLayout.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
            return new MViewHolder(v);
        }else{
            View v = View.inflate(context, R.layout.item_add_truck, null);
            v.setTag(viewType);
            v.setLayoutParams(new LinearLayout.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
            return new MViewHolder(v);
        }

    }

    @Override
    public void onBindViewHolder(MViewHolder holder, final int position) {
        if (onItemClickListener != null) {
            holder.root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(position);
                }
            });
        }
        if (position == dataList.size()) {
            return;
        }
        if (onItemClickListener != null) {
            holder.root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(position);
                }
            });
        }
        TruckBean truckBean = dataList.get(position);

        holder.tv_weight.setText("载重" + truckBean.getWeight() + "吨");
        holder.tv_hight.setText("车长" + truckBean.getLength() + "米");
        holder.tv_width.setText("车宽" + truckBean.getWidth() + "米");
        holder.tv_length.setText("车高" + truckBean.getHight() + "米");
        holder.tv_variety.setText(truckBean.getVariety() + "");
        holder.tv_truckcardnumber.setText("" + truckBean.getTruckCardNumber());
//        holder.iv_select.settext

    }

    @Override
    public int getItemViewType(int position) {
        if (position == dataList.size()) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size() + 1;
    }


    class MViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_weight, tv_hight, tv_width, tv_length, tv_variety, tv_truckcardnumber;
        public ImageView iv_select;
        public View root;
        public MViewHolder(View itemView) {
            super(itemView);
            if(((int)itemView.getTag())==0){
                root = itemView;
                tv_weight = (TextView) root.findViewById(R.id.tv_weight);
                tv_hight = (TextView) root.findViewById(R.id.tv_hight);
                tv_width = (TextView) root.findViewById(R.id.tv_width);
                tv_length = (TextView) root.findViewById(R.id.tv_length);
                tv_variety = (TextView) root.findViewById(R.id.tv_variety);
                tv_truckcardnumber = (TextView) root.findViewById(R.id.tv_truckcardnumber);
                iv_select = (ImageView) root.findViewById(R.id.iv_select);
                root=root.findViewById(R.id.root);
            }else{
                root=itemView.findViewById(R.id.ll_add_truck);
            }
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
