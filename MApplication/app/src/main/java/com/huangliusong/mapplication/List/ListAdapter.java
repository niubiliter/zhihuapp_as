package com.huangliusong.mapplication.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.huangliusong.mapplication.Data.DataBean;
import com.huangliusong.mapplication.Data.Data_ArrayList;
import com.huangliusong.mapplication.Main.Main2Activity;
import com.huangliusong.mapplication.R;

import java.util.List;


/**
 * Created by Administrator on 2016/2/4.
 */
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.StaggeredViewHolder> {
    private Context mContext;
    private List<DataBean> mDatas;


    public ListAdapter(Context context, List<DataBean> mDatas) {
        this.mContext = context;
        this.mDatas = mDatas;
    }

    public StaggeredViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.item_staggered, null);
        return new StaggeredViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StaggeredViewHolder holder, int position) {
        DataBean bean = mDatas.get(position);
        holder.setData(bean);
    }

    @Override
    public int getItemCount() {
        if (mDatas != null) {
            return mDatas.size();
        }
        return 0;
    }

    public class StaggeredViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {
        private ImageView ivIcon;
        private TextView tvName;
        private int mPosition;

        public StaggeredViewHolder(View itemView) {
            super(itemView);
            ivIcon = (ImageView) itemView.findViewById(R.id.item_list_iv_item);
            tvName = (TextView) itemView.findViewById(R.id.item_list_iv_name);
            itemView.setOnClickListener(this);
        }

        public void setData(DataBean bean) {
            //设置数据的方法
            ivIcon.setImageBitmap(bean.icon);
            tvName.setText(bean.name);
        }
        public void setPosition(int position) {
            mPosition = position;
        }

        @Override
        public void onClick(View v) {
            // TODO 自动生成的方法存根

            String str=Data_ArrayList.data_bean.get(getPosition()).name_title;
            String id=Data_ArrayList.data_bean.get(getPosition()).id;
            Log.e("111", str + id);
            Intent intent=new Intent();
            Bundle bundle=new Bundle();
            bundle.putString("title",str);
            bundle.putString("id",id);
            intent.setClass(mContext, Main2Activity.class);
            intent.putExtras(bundle);
            mContext.startActivity(intent);
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }

}
