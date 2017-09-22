package com.cn.gov.jms.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.cn.gov.jms.Config;
import com.cn.gov.jms.base.BaseFragment;
import com.cn.gov.jms.model.AboutAndContact;
import com.cn.gov.jms.services.Api;
import com.cn.gov.jms.ui.AboutusActivity;
import com.cn.gov.jms.ui.ContactusActivity;
import com.cn.gov.jms.ui.R;
import com.cn.gov.jms.ui.SettingActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2016/12/2.
 */

public class MineFragment extends BaseFragment
{
    private static MineFragment mineFragment;//单例模式
    public static MineFragment getInstance() {
        if(mineFragment==null){
            mineFragment = new MineFragment();
        }
        return mineFragment;
    }

    @BindView(R.id.about_us)
    RelativeLayout about_us;
    @BindView(R.id.contact_us)
    RelativeLayout contact_us;
    @BindView(R.id.setting)
    RelativeLayout setting;
    private Unbinder mUnbinder;

    @Override
    public int getLayoutId()
    {
        return R.layout.fragment_mine;
    }

    @Override
    public void initVariables()
    {
    }


    @Override
    public void initViews(View view, Bundle savedInstanceState)
    {
        mUnbinder = ButterKnife.bind(this, view);
    }

    @OnClick({ R.id.about_us, R.id.contact_us, R.id.setting })
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.about_us:
                getAboutData();
                //getActivity().startActivity(new Intent(getActivity(), AboutusActivity.class));
                break;
            case R.id.contact_us:
                getContactData();
                //getActivity().startActivity(new Intent(getActivity(), ContactusActivity.class));
                break;
            case R.id.setting:
                getActivity().startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    public void initLoadData()
    {
    }

    @Override
    protected void lazyFetchData() {

    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        mUnbinder.unbind();
    }


    private void getAboutData(){
        //使用retrofit配置api
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Config.BANNER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api =retrofit.create(Api.class);
        Call<AboutAndContact> call=api.getAboutAndContactData();
        call.enqueue(new Callback<AboutAndContact>() {
            @Override
            public void onResponse(Call<AboutAndContact> call, Response<AboutAndContact> response) {
                if(response!=null){
                    AboutAndContact detail=response.body();
                    AboutAndContact.ResultsBean resultsBean=detail.getResults().get(0);
                    Intent intent = new Intent(getActivity(),AboutusActivity.class);
                    intent.putExtra(Config.NEWS,resultsBean.id);
                    startActivity(intent);
                    Log.e("xxxxxxx",resultsBean.id);
                    Log.e("xxxxxxx",resultsBean.title);
                }else{
                    Toast.makeText(getActivity(),"数据为空!",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AboutAndContact> call, Throwable t) {
                Toast.makeText(getActivity(),"请求失败!",Toast.LENGTH_SHORT).show();
                Log.e("-------------",t.getMessage().toString());
            }
        });
    }


    private void getContactData(){
        //使用retrofit配置api
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Config.BANNER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api =retrofit.create(Api.class);
        Call<AboutAndContact> call=api.getAboutAndContactData();
        call.enqueue(new Callback<AboutAndContact>() {
            @Override
            public void onResponse(Call<AboutAndContact> call, Response<AboutAndContact> response) {
                if(response!=null){
                    AboutAndContact detail=response.body();
                    AboutAndContact.ResultsBean resultsBean=detail.getResults().get(1);
                    Intent intent = new Intent(getActivity(),ContactusActivity.class);
                    intent.putExtra(Config.NEWS,resultsBean.id);
                    startActivity(intent);
                    Log.e("xxxxxxx",resultsBean.id);
                    Log.e("xxxxxxx",resultsBean.title);
                }else{
                    Toast.makeText(getActivity(),"数据为空!",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AboutAndContact> call, Throwable t) {
                Toast.makeText(getActivity(),"请求失败!",Toast.LENGTH_SHORT).show();
                Log.e("-------------",t.getMessage().toString());
            }
        });
    }

}
