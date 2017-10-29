package com.cn.gov.jms.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cn.gov.jms.base.BaseViewHolder;
import com.cn.gov.jms.model.Sqgk;
import com.cn.gov.jms.ui.R;

import java.util.List;

/**
 * Created by wangjiawei on 2017/9/15.
 * 市情概况
 */

public class SQGKAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private static final int TYPE_FOOTER = 0;
    private static final int ITEM_NOIMAGE = 1;
    private static final int ITEM_HASIMAGE = 2;

    private List<Sqgk.ResultsBean> datas;
    private Context mContext;
    private int viewFooter;
    private View footerView;
    private OnItemClickListener mOnItemClickListener;

    public SQGKAdapter(Context context, List<Sqgk.ResultsBean> datas) {
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
            final Sqgk.ResultsBean item = datas.get(position);
//            if (type == ITEM_HASIMAGE) {
//                ImageLoaderUtils.loadingImg(mContext, (ImageView) holder.getView(R.id.image), item.url);
//            }
            //CircleImageView avatar = holder.getView(R.id.avatar);
            //holder.setText(R.id.tv_title, datas.get(position).getTitle());
            holder.setText(R.id.tv_title, datas.get(position).title);
           // String content=datas.get(position).text;
            if(datas.get(position).text.startsWith("?")){
                String str=datas.get(position).text.replace('?', ' ');
                holder.setText(R.id.tv_content, str);
            }else{
                holder.setText(R.id.tv_content, datas.get(position).text);
            }


            //String strSub=content.substring(0,15);//截取字符串
            //String str=content.substring(0,30)+"....查看详情";   //http://blog.csdn.net/xuwenneng/article/details/51027625?locationNum=10&fps=1  还没有成功

            //setTVColor(str, '查', '情', Color.RED);
            //holder.setText(R.id.tv_content,setTVColor(str, '查', '情', Color.RED));
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
//        if (TextUtils.isEmpty(datas.get(position).getUsername())) {
//            type = ITEM_NOIMAGE;
//        } else {
//            type = ITEM_HASIMAGE;
//        }
        return type;
    }

    public void addFooterView(int footerView) {
        this.viewFooter = footerView;
        notifyItemInserted(getItemCount() - 1);
    }

    //是字符串中特定字符变成红色
    private SpannableStringBuilder setTVColor(String str , char ch1 , char ch2 , int color){
        int a = str.indexOf(ch1); //从字符ch1的下标开始
        int b = str.indexOf(ch2)+1; //到字符ch2的下标+1结束,因为SpannableStringBuilder的setSpan方法中区间为[ a,b )左闭右开
        SpannableStringBuilder builder = new SpannableStringBuilder(str);
        builder.setSpan(new ForegroundColorSpan(color), a, b, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        //tv.setText(builder);
        return builder;
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
