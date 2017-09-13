package com.cn.gov.jms.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cn.gov.jms.model.Banners;
import com.facebook.drawee.view.SimpleDraweeView;
import com.cn.gov.jms.Config;
import com.cn.gov.jms.ui.DetailActivity;
import com.cn.gov.jms.ui.R;
import com.zanlabs.widget.infiniteviewpager.InfinitePagerAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/12/5.
 */

public class TopAdapter extends InfinitePagerAdapter
{
    private Context mContext;
    //    private List<Banners> mList;
    private List<String> banner_img;
    private List<Banners.ResultsBean> resultsBeanList;
    private List<String> banner_url;

    public TopAdapter(Context context, List<Banners.ResultsBean> resultsBeanList, List<String> banner_url)
    {
        mContext = context;
        this.resultsBeanList = resultsBeanList;
        this.banner_url = banner_url;
    }

    @Override
    public int getItemCount()
    {
//        return mList == null ? 0 : mList.size();
        return resultsBeanList == null ? 0 : resultsBeanList.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup container)
    {
        View view;
        ViewsHolder holder;
        if (convertView == null)
        {
            view = LayoutInflater.from(mContext).inflate(R.layout.banner_item, container, false);
            holder = new ViewsHolder(view);
            view.setTag(holder);
        } else
        {
            view = convertView;
            holder = (ViewsHolder) view.getTag();
        }
        holder.mDraweeView.setImageURI(Config.BANNER_BASE_URL+resultsBeanList.get(position).getUrl());
        holder.tv_title.setText(resultsBeanList.get(position).getTitle());
        view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra(Config.NEWS, banner_url.get(position));
                mContext.startActivity(intent);
            }
        });
        return view;
    }

    class ViewsHolder
    {
        @BindView(R.id.top_image)
        SimpleDraweeView mDraweeView;
        @BindView(R.id.tv_title)
        TextView tv_title;

        public ViewsHolder(View view)
        {
            ButterKnife.bind(this, view);
        }
    }
}
