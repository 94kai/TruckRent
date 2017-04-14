package com.xk.trucktrade.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.xk.trucktrade.R;

/**
 * Created by xk on 2016/6/4 21:58.
 */
public class CargoInfoAdapter extends RecyclerView.Adapter<CargoInfoAdapter.MViewHolder> {

    private Context context;

    public CargoInfoAdapter(Context context) {
        this.context = context;
    }

    @Override
    public MViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = View.inflate(context, R.layout.item_cargo_info, null);
        return new MViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MViewHolder holder, final int position) {
    }


    @Override
    public int getItemCount() {
        return 30;
    }


    class MViewHolder extends RecyclerView.ViewHolder {
        public MViewHolder(View itemView) {
            super(itemView);
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
