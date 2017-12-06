package com.cn.gov.jms.ui;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.cn.gov.jms.Config;
import com.cn.gov.jms.adapter.XinWenTitleAdapter;
import com.cn.gov.jms.base.BaseActivity;
import com.cn.gov.jms.utils.RecyclerViewSpacesItemDecoration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class XinWenCenterActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView imageView;

    @BindView(R.id.recyerview)
    RecyclerView mRecyclerView;
    LinearLayoutManager linearLayoutManager;
    private boolean connect = false;//判断网络是否连接正常
    private List<String> new_title;
    private XinWenTitleAdapter xinWenTitleAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_xin_wen_center;
    }

    @Override
    protected void initView() {
        new_title = new ArrayList<>();
        new_title = Arrays.asList(Config.XINWEN_TITLE);

        linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        HashMap<String, Integer> stringIntegerHashMap = new HashMap<>();
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.TOP_DECORATION,10);//top间距
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.BOTTOM_DECORATION,10);//底部间距
        //stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.LEFT_DECORATION,10);//左间距
        //stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.RIGHT_DECORATION,10);//右间距
        mRecyclerView.addItemDecoration(new RecyclerViewSpacesItemDecoration(stringIntegerHashMap));

        xinWenTitleAdapter=new XinWenTitleAdapter(this,new_title);
        mRecyclerView.setAdapter(xinWenTitleAdapter);

        xinWenTitleAdapter.setOnItemClickLitener(new XinWenTitleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                switch (position){
                    case 0:
                        Toast.makeText(XinWenCenterActivity.this,new_title.get(position),Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(XinWenCenterActivity.this,new_title.get(position),Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(XinWenCenterActivity.this,new_title.get(position),Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(XinWenCenterActivity.this,new_title.get(position),Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        startActivity(new Intent(XinWenCenterActivity.this, NewsCenterActivity.class));
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
                        break;
                    case 8:
                        break;
                    case 9:
                        startActivity(new Intent(XinWenCenterActivity.this, JmsDayReportActivity.class));
                        break;
                    default:
                        break;
                }
            }
        });

    }

    @OnClick({ R.id.iv_back})
    public void onClick(View view) {
        finish();
    }
}
