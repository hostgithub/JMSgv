package com.cn.gov.jms.ui;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.cn.gov.jms.base.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class ZhengWuGongKaiActivity extends BaseActivity {

    //网格布局
    @BindView(R.id.gridview)
    GridView mGridview;
    private List<Map<String, Object>> data_list;
    private SimpleAdapter sim_adapter;
    // 图片封装为一个数组
    private int[] icon = { R.drawable.icon_a01, R.drawable.icon_a02, R.drawable.icon_a03, R.drawable.icon_a04, R.drawable.icon_a05,
            R.drawable.icon_a06, R.drawable.icon_a07, R.drawable.icon_a08,R.drawable.icon_a09,R.drawable.icon_a10,R.drawable.icon_a11,
            R.drawable.icon_a12,R.drawable.icon_a13,R.drawable.icon_a14,R.drawable.icon_a15,R.drawable.icon_a16,R.drawable.icon_a17,
            R.drawable.icon_a18,R.drawable.icon_a19,R.drawable.icon_a20,R.drawable.icon_a21};
    private String[] iconName = { "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};

    @Override
    protected int getLayoutId() {
        return R.layout.activity_zheng_wu_gong_kai;
    }

    @Override
    protected void initView() {

        //网格布局
        // 新建List
        data_list = new ArrayList<Map<String, Object>>();
        //获取数据
        getData();
        //新建适配器
        String [] from ={"image","text"};
        int [] to = {R.id.image,R.id.text};
        sim_adapter = new SimpleAdapter(this, data_list, R.layout.item_gridview_zwgk, from, to);
        //配置适配器
        mGridview.setAdapter(sim_adapter);
        mGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(ZhengWuGongKaiActivity.this,"点击了第"+(i+1)+"个条目",Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * 网格布局初始化数据
     * @return
     */
    public List<Map<String, Object>> getData(){
        //cion和iconName的长度是相同的，这里任选其一都可以
        for(int i=0;i<icon.length;i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", icon[i]);
            map.put("text", iconName[i]);
            data_list.add(map);
        }
        return data_list;
    }

    @OnClick({ R.id.iv_back,R.id.system_of_government_information_publicity,R.id.government_information_public_directory,
            R.id.guide_to_government_information_disclosure,R.id.open_in_accordance_with_the_application,R.id.government_information_public_briefing,
            R.id.annual_report_on_government_information_work,R.id.important_area})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.system_of_government_information_publicity:
                startActivity(new Intent(ZhengWuGongKaiActivity.this,ListZwgkActivity.class));
                break;
            case R.id.government_information_public_directory:finish();
                break;
            case R.id.guide_to_government_information_disclosure:
                startActivity(new Intent(ZhengWuGongKaiActivity.this,ListGkznActivity.class));
                break;
            case R.id.open_in_accordance_with_the_application:finish();
                break;
            case R.id.government_information_public_briefing:finish();
                break;
            case R.id.annual_report_on_government_information_work:finish();
                break;
            case R.id.important_area:finish();
                break;
            default:
                break;
        }
    }



}
