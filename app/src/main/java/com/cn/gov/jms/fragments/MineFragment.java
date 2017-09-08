package com.cn.gov.jms.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.cn.gov.jms.base.BaseFragment;
import com.cn.gov.jms.ui.AboutusActivity;
import com.cn.gov.jms.ui.ContactusActivity;
import com.cn.gov.jms.ui.R;
import com.cn.gov.jms.ui.SettingActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

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
                Toast.makeText(getActivity(),"关于我们", Toast.LENGTH_SHORT).show();
                getActivity().startActivity(new Intent(getActivity(), AboutusActivity.class));
                break;
            case R.id.contact_us:
                Toast.makeText(getActivity(),"联系我们", Toast.LENGTH_SHORT).show();
                getActivity().startActivity(new Intent(getActivity(), ContactusActivity.class));
                break;
            case R.id.setting:
                Toast.makeText(getActivity(),"设置", Toast.LENGTH_SHORT).show();
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

}
