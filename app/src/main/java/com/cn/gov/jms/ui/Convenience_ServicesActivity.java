package com.cn.gov.jms.ui;

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

public class Convenience_ServicesActivity extends BaseActivity {

    //公共服务 网格布局
    @BindView(R.id.public_service_gridview)
    GridView mGridview;
    private List<Map<String, Object>> data_list;
    private SimpleAdapter sim_adapter;
    // 图片封装为一个数组
    private int[] icon = { R.drawable.convenience_services_icon_01, R.drawable.convenience_services_icon_02, R.drawable.convenience_services_icon_03,
            R.drawable.convenience_services_icon_04, R.drawable.convenience_services_icon_05, R.drawable.convenience_services_icon_06,
            R.drawable.convenience_services_icon_07, R.drawable.convenience_services_icon_08,R.drawable.convenience_services_icon_09,
            R.drawable.convenience_services_icon_10, R.drawable.convenience_services_icon_11, R.drawable.convenience_services_icon_12};
    private String[] iconName = { "", "", "", "", "", "", "", "", "", "", "", ""};

    @BindView(R.id.bmcx_gridview)
    GridView bmcx_gridview;
    private List<Map<String, Object>> data_list_bmcx;
    private SimpleAdapter sim_adapter_bmcx;
    // 图片封装为一个数组
    private int[] icon_bmcx = { R.drawable.convenience_services_icon_a01, R.drawable.convenience_services_icon_a02, R.drawable.convenience_services_icon_a03,
            R.drawable.convenience_services_icon_a04, R.drawable.convenience_services_icon_a05, R.drawable.convenience_services_icon_a06,
            R.drawable.convenience_services_icon_a07, R.drawable.convenience_services_icon_a08,R.drawable.convenience_services_icon_a09,
            R.drawable.convenience_services_icon_a10};
    private String[] iconName_bmcx = { "", "", "", "", "", "", "", "", "", ""};

    @BindView(R.id.bmjf_gridview)
    GridView bmjf_gridview;
    private List<Map<String, Object>> data_list_bmjf;
    private SimpleAdapter sim_adapter_bmjf;
    // 图片封装为一个数组
    private int[] icon_bmjf = { R.drawable.convenience_services_icon_b01, R.drawable.convenience_services_icon_b02, R.drawable.convenience_services_icon_b03,
            R.drawable.convenience_services_icon_b04, R.drawable.convenience_services_icon_b05, R.drawable.convenience_services_icon_b06,
            R.drawable.convenience_services_icon_b07, R.drawable.convenience_services_icon_b08,R.drawable.convenience_services_icon_b09};
    private String[] iconName_bmjf = { "", "", "", "", "", "", "", "", ""};

    @Override
    protected int getLayoutId() {
        return R.layout.activity_convenience__services;
    }

    @Override
    protected void initView() {
        initViewPublicServices();
        initViewBmcx();
        initViewBmjf();
    }


    private void initViewPublicServices(){
        //网格布局
        // 新建List
        data_list = new ArrayList<Map<String, Object>>();
        //获取数据
        getData();
        //新建适配器
        String [] from ={"image","text"};
        int [] to = {R.id.image,R.id.text};
        sim_adapter = new SimpleAdapter(this, data_list, R.layout.item_gridview_public_services, from, to);
        //配置适配器
        mGridview.setAdapter(sim_adapter);
        mGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(Convenience_ServicesActivity.this,"点击了第"+(i+1)+"个条目",Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void initViewBmcx(){
        //网格布局
        // 新建List
        data_list_bmcx = new ArrayList<Map<String, Object>>();
        //获取数据
        getDataBmcx();
        //新建适配器
        String [] from ={"image","text"};
        int [] to = {R.id.image,R.id.text};
        sim_adapter_bmcx = new SimpleAdapter(this, data_list_bmcx, R.layout.item_gridview_bmcx, from, to);
        //配置适配器
        bmcx_gridview.setAdapter(sim_adapter_bmcx);
        bmcx_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(Convenience_ServicesActivity.this,"点击了第"+(i+1)+"个条目",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initViewBmjf(){
        //网格布局
        // 新建List
        data_list_bmjf = new ArrayList<Map<String, Object>>();
        //获取数据
        getDataBmjf();
        //新建适配器
        String [] from ={"image","text"};
        int [] to = {R.id.image,R.id.text};
        sim_adapter_bmjf = new SimpleAdapter(this, data_list_bmjf, R.layout.item_gridview_bmjf, from, to);
        //配置适配器
        bmjf_gridview.setAdapter(sim_adapter_bmjf);
        bmjf_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(Convenience_ServicesActivity.this,"点击了第"+(i+1)+"个条目",Toast.LENGTH_SHORT).show();
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

    /**
     * 网格布局初始化数据
     * @return
     */
    public List<Map<String, Object>> getDataBmcx(){
        //cion和iconName的长度是相同的，这里任选其一都可以
        for(int i=0;i<icon_bmcx.length;i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", icon_bmcx[i]);
            map.put("text", iconName_bmcx[i]);
            data_list_bmcx.add(map);
        }
        return data_list_bmcx;
    }
    /**
     * 网格布局初始化数据
     * @return
     */
    public List<Map<String, Object>> getDataBmjf(){
        //cion和iconName的长度是相同的，这里任选其一都可以
        for(int i=0;i<icon_bmjf.length;i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", icon_bmjf[i]);
            map.put("text", iconName_bmjf[i]);
            data_list_bmjf.add(map);
        }
        return data_list_bmjf;
    }

    @OnClick({ R.id.iv_back})
    public void onClick(View view) {finish();
    }
}
