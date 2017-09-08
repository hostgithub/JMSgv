package com.cn.gov.jms.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;
import com.cn.gov.jms.Config;
import com.cn.gov.jms.adapter.TopAdapter;
import com.cn.gov.jms.base.BaseFragment;
import com.cn.gov.jms.ui.R;
import com.zanlabs.widget.infiniteviewpager.InfiniteViewPager;
import com.zanlabs.widget.infiniteviewpager.indicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by wangjiawei on 2017/7/24.
 */

public class LanmuFragment extends BaseFragment
{

    private static LanmuFragment lanmuFragment;//单例模式
    public static LanmuFragment getInstance() {
        if(lanmuFragment==null){
            lanmuFragment = new LanmuFragment();
        }
        return lanmuFragment;
    }
    @BindView(R.id.home_recycler)
    RecyclerView mHomeRecycler;
    @BindView(R.id.home_viewpager)
    InfiniteViewPager mHomeViewpager;
    @BindView(R.id.home_indicator)
    CirclePageIndicator mHomeIndicator;
    @BindView(R.id.home_header)
    RecyclerViewHeader mHomeHeader;
    @BindView(R.id.home_refresh)
    SwipeRefreshLayout mHomeRefresh;

    private Unbinder mUnbinder;
    private boolean connect = false;//判断网络是否连接正常

    private List<String> banner_img;
    private List<String> banner_url;


    @BindView(R.id.situation)
    ImageView situation;
    @BindView(R.id.newcenter)
    ImageView newcenter;
    @BindView(R.id.gove_public)
    ImageView gove_public;
    @BindView(R.id.announcement)
    ImageView announcement;

    @BindView(R.id.online_services)
    ImageView online_services;
    @BindView(R.id.convenience_services)
    ImageView convenience_services;
    @BindView(R.id.interaction)
    ImageView interaction;
    @BindView(R.id.investment_guide)
    ImageView investment_guide;

    @Override
    public int getLayoutId()
    {
        checkNet();
        return R.layout.fragment_lanmu;
    }

    @Override
    public void initVariables()
    {
        banner_img = new ArrayList<>();
        banner_url = new ArrayList<>();
        banner_img = Arrays.asList(Config.BANNER_IMGS);
        banner_url = Arrays.asList(Config.BANNER_URL);
    }


    @Override
    public void initViews(View view, Bundle savedInstanceState)
    {
        mUnbinder = ButterKnife.bind(this, view);

        mHomeRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        //广告窗体
        mHomeHeader.attachTo(mHomeRecycler,true);
        TopAdapter adapter = new TopAdapter(getActivity(), banner_img,banner_url);
        mHomeViewpager.setAdapter(adapter);
        mHomeViewpager.setAutoScrollTime(3000);
        mHomeViewpager.startAutoScroll();
        mHomeIndicator.setViewPager(mHomeViewpager);
    }


    @Override
    public void initLoadData()
    {

    }

    @Override
    protected void lazyFetchData() {

    }


    @OnClick({ R.id.situation, R.id.newcenter, R.id.gove_public, R.id.announcement,
            R.id.online_services, R.id.convenience_services, R.id.interaction, R.id.investment_guide })
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.situation:
                Toast.makeText(getActivity(),"市情概况",Toast.LENGTH_SHORT).show();
                break;
            case R.id.newcenter:
                Toast.makeText(getActivity(),"新闻中心",Toast.LENGTH_SHORT).show();
                break;
            case R.id.gove_public:
                Toast.makeText(getActivity(),"政务公开",Toast.LENGTH_SHORT).show();
                break;
            case R.id.announcement:
                Toast.makeText(getActivity(),"公告公示",Toast.LENGTH_SHORT).show();
                break;
            case R.id.online_services:
                Toast.makeText(getActivity(),"在线服务",Toast.LENGTH_SHORT).show();
                break;
            case R.id.convenience_services:
                Toast.makeText(getActivity(),"便民服务",Toast.LENGTH_SHORT).show();
                break;
            case R.id.interaction:
                Toast.makeText(getActivity(),"政民互动",Toast.LENGTH_SHORT).show();
                break;
            case R.id.investment_guide:
                Toast.makeText(getActivity(),"投资指南",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    @Override
    public void onResume() {
        mHomeViewpager.startAutoScroll();
        super.onResume();
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    public void checkNet()//网络是否已经连接上
    {
        ConnectivityManager manager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        connect = info != null && info.isConnected();
    }
}
