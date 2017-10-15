package com.cn.gov.jms.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cn.gov.jms.base.BaseViewHolder;
import com.cn.gov.jms.model.ApplyPublic;
import com.cn.gov.jms.ui.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by llf on 2017/4/19.
 * 发现的适配器，分为两种样式
 */

public class ApplyPublicAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private static final int TYPE_FOOTER = 0;
    private static final int ITEM_NOIMAGE = 1;
    private static final int ITEM_HASIMAGE = 2;

    private ArrayList<ApplyPublic.ResultsBean> datas;
    private Context mContext;
    private int viewFooter;
    private View footerView;
    private OnItemClickListener mOnItemClickListener;

    public ApplyPublicAdapter(Context context, ArrayList<ApplyPublic.ResultsBean> datas) {
        this.datas = datas;
        this.mContext = context;
    }

    public void replaceAll(List<ApplyPublic.ResultsBean> elements) {
        if (datas.size() > 0) {
            datas.clear();
        }
        datas.addAll(elements);
        notifyDataSetChanged();
    }

    public void addAll(List<ApplyPublic.ResultsBean> elements) {
        datas.addAll(elements);
        notifyDataSetChanged();
    }
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_NOIMAGE) {
            return new BaseViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_apply, parent, false));
        } else if (viewType == ITEM_HASIMAGE) {
            return new BaseViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_apply, parent, false));
        } else {
            footerView = LayoutInflater.from(mContext).inflate(viewFooter, parent, false);
            return new BaseViewHolder(footerView);
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, final int position) {
        if (!(viewFooter != 0 && position == getItemCount() - 1)) {
            if (mOnItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.onItemClick(position);
                    }
                });
            }

            int type = getItemViewType(position);
            final ApplyPublic.ResultsBean item = datas.get(position);

            //CircleImageView avatar = holder.getView(R.id.avatar);
            holder.setText(R.id.apply_name, item.getName());
            holder.setText(R.id.apply_title, item.getTitle());
            holder.setText(R.id.tv_danwei, item.getWork());
            holder.setText(R.id.tv_result, item.getState1str());
            holder.setText(R.id.tv_shenqing_time, item.getCreateTime());
            holder.setText(R.id.reply_time, item.getHandleTime());

//            holder.setText(R.id.author, item.getAuthor());
//            holder.setText(R.id.seeNum, item.getWatch());
//            holder.setText(R.id.commentNum, item.getComments());
//            holder.setText(R.id.loveNum, item.getLike());
//            ImageLoaderUtils.loadingImg(mContext, avatar, HOST + item.getAuthorImg());
//            avatar.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    WebViewActivity.lanuch(mContext, HOST + "/member/index.php?uid" + item.getAuthor());
//                }
//            });

        }
    }

    @Override
    public int getItemCount() {
        int count = (datas == null ? 0 : datas.size());
        if (viewFooter != 0) {
            count++;
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        int type = ITEM_HASIMAGE;
        if (viewFooter != 0 && position == getItemCount() - 1) {
            type = TYPE_FOOTER;
            return type;
        }
        if (TextUtils.isEmpty(datas.get(position).getId())) {
            type = ITEM_NOIMAGE;
        } else {
            type = ITEM_HASIMAGE;
        }
        return type;
    }

    public void addFooterView(int footerView) {
        this.viewFooter = footerView;
        notifyItemInserted(getItemCount() - 1);
    }

    public void setFooterVisible(int visible) {
        footerView.setVisibility(visible);
    }

    //设置点击事件
    public void setOnItemClickLitener(OnItemClickListener mLitener) {
        mOnItemClickListener = mLitener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
