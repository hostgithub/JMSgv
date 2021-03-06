package com.cn.gov.jms.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cn.gov.jms.Config;
import com.cn.gov.jms.model.Banners;
import com.cn.gov.jms.model.Detail;
import com.cn.gov.jms.services.Api;
import com.cn.gov.jms.ui.DetailActivity;
import com.cn.gov.jms.ui.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zanlabs.widget.infiniteviewpager.InfinitePagerAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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

        if(resultsBeanList.get(position).getUrl().isEmpty()){
            holder.mDraweeView.setImageResource(R.drawable.ic_empty_picture);
        }else{
            holder.mDraweeView.setImageURI(Config.BANNER_BASE_URL+resultsBeanList.get(position).getUrl());
        }
        if(resultsBeanList.get(position).getTitle().isEmpty()){
            holder.tv_title.setText("");
        }else{
            holder.tv_title.setText(resultsBeanList.get(position).getTitle());
        }

        view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getData(Integer.parseInt(resultsBeanList.get(position).get_id()));
//                Intent intent = new Intent(mContext, DetailActivity.class);
//                intent.putExtra(Config.NEWS, banner_url.get(position));
//                mContext.startActivity(intent);
            }
        });
        return view;
    }

    private void getData(int id){
        //使用retrofit配置api
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Config.BANNER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api =retrofit.create(Api.class);
        Call<Detail> call=api.getDetailData(id);
        call.enqueue(new Callback<Detail>() {
            @Override
            public void onResponse(Call<Detail> call, Response<Detail> response) {
                if(response!=null){
                    Detail detail=response.body();
                    Detail.ResultsBean resultsBean=detail.getResults().get(0);
                    Intent intent = new Intent(mContext, DetailActivity.class);
                    intent.putExtra(Config.NEWS,resultsBean);
                    mContext.startActivity(intent);
                    Log.e("xxxxxxx",resultsBean.content);
                }else{
                    Toast.makeText(mContext,"数据为空!",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Detail> call, Throwable t) {
                Toast.makeText(mContext,"请求失败!",Toast.LENGTH_SHORT).show();
                Log.e("-------------",t.getMessage().toString());
            }
        });
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
