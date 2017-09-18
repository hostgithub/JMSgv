package com.cn.gov.jms.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cn.gov.jms.base.BaseViewHolder;
import com.cn.gov.jms.model.Banners;
import com.cn.gov.jms.ui.R;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wangjiawei on 2017/9/15.
 * 市情概况
 */

public class SQGKAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private static final int TYPE_FOOTER = 0;
    private static final int ITEM_NOIMAGE = 1;
    private static final int ITEM_HASIMAGE = 2;

    private List<Banners.ResultsBean> datas;
    private Context mContext;
    private int viewFooter;
    private View footerView;
    private OnItemClickListener mOnItemClickListener;

    public SQGKAdapter(Context context, List<Banners.ResultsBean> datas) {
        this.datas = datas;
        this.mContext = context;
    }

//    public void replaceAll(List<DataInfo.Info> elements) {
//        if (datas.size() > 0) {
//            datas.clear();
//        }
//        datas.addAll(elements);
//        notifyDataSetChanged();
//    }
//
//    public void addAll(List<DataInfo.Info> elements) {
//        datas.addAll(elements);
//        notifyDataSetChanged();
//    }
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_NOIMAGE) {
            return new BaseViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_sqgk, parent, false));
        } else if (viewType == ITEM_HASIMAGE) {
            return new BaseViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_sqgk, parent, false));
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
            final Banners.ResultsBean item = datas.get(position);
//            if (type == ITEM_HASIMAGE) {
//                ImageLoaderUtils.loadingImg(mContext, (ImageView) holder.getView(R.id.image), item.url);
//            }
            //CircleImageView avatar = holder.getView(R.id.avatar);
            holder.setText(R.id.tv_title, datas.get(position).getTitle());
            String content=datas.get(position).getUrl();
            String strSub=content.substring(0,15);
            String str=strSub+"....查看详情";   //http://blog.csdn.net/xuwenneng/article/details/51027625?locationNum=10&fps=1  还没有成功

            SpannableString spannableString = new SpannableString(str);
            Pattern p = Pattern.compile("查看详情");

            Matcher m = p.matcher(spannableString);

            while (m.find()) {
                int start = m.start();
                int end = m.end();
                spannableString.setSpan(new ForegroundColorSpan(Color.RED), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            String s= String.valueOf(spannableString);
            holder.setText(R.id.tv_content,s);
            //holder.setText(R.id.tv_content, datas.get(position).getUrl());

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
        if (TextUtils.isEmpty(datas.get(position).get_id())) {
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
