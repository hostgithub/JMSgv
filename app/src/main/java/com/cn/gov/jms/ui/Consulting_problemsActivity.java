package com.cn.gov.jms.ui;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cn.gov.jms.Config;
import com.cn.gov.jms.base.BaseActivity;
import com.cn.gov.jms.model.ResponseBean;
import com.cn.gov.jms.services.Api;
import com.google.gson.Gson;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Consulting_problemsActivity extends BaseActivity {

    @BindView(R.id.spinner_kind)
    Spinner spinner_kind;
    @BindView(R.id.spinner_bumen)
    Spinner spinner_bumen;
    @BindView(R.id.edt_theme)
    EditText edt_theme;
    @BindView(R.id.edt_name)
    EditText edt_name;
    @BindView(R.id.edt_phone)
    EditText edt_phone;
    @BindView(R.id.edt_address)
    EditText edt_address;
    @BindView(R.id.edt_email)
    EditText edt_email;
    @BindView(R.id.edt_content)
    EditText edt_content;

    @BindView(R.id.tv_imgUrl)
    TextView tv_imgUrl;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_consulting_problems;
    }

    @Override
    protected void initView() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);// 让输入框不自动打开输入法
        initKindData();
        initBumenData();
    }

    @OnClick({ R.id.iv_back,R.id.btn_submit,R.id.iv_upload})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_submit:
                if(edt_theme.getText().toString().trim().equals("")||edt_name.getText().toString().trim().equals("")||edt_phone.getText().toString().trim().equals("")
                        || edt_address.getText().toString().trim().equals("")||edt_email.getText().toString().trim().equals("")||edt_content.getText().toString().trim().equals("")){
                    Toast.makeText(this,"请您将信息填写完整!",Toast.LENGTH_SHORT).show();
                }else {
                    post();
                }
                break;
            case R.id.iv_upload:
                finish();
                break;
            default:
                break;
        }
    }

    private void initKindData(){
        // 建立数据源
        final String[] mItems = getResources().getStringArray(R.array.kind);
        // 建立Adapter并且绑定数据源
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, mItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //绑定 Adapter到控件
        spinner_kind .setAdapter(adapter);
        spinner_kind.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                String strKind=mItems[pos];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
    }


    private void initBumenData(){
        // 建立数据源
        final String[] mItems = getResources().getStringArray(R.array.bumen);
        // 建立Adapter并且绑定数据源
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, mItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //绑定 Adapter到控件
        spinner_bumen .setAdapter(adapter);
        spinner_bumen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                String strBumen=mItems[pos];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
    }

    private void post(){
        Gson gson=new Gson();
        HashMap<String,String> paramsMap=new HashMap<>();
        paramsMap.put("title",edt_theme.getText().toString());
        paramsMap.put("name",edt_name.getText().toString());
        paramsMap.put("phone",edt_phone.getText().toString());
        paramsMap.put("address",edt_address.getText().toString());
        paramsMap.put("mailbox",edt_email.getText().toString());
        paramsMap.put("contents",edt_content.getText().toString());
        String route= gson.toJson(paramsMap);
        Log.e("ssssssss",route.toString());

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Config.BANNER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api =retrofit.create(Api.class);
        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),route);
        Log.e("ssssssss",body.toString());
        Call<ResponseBean> call=api.postFlyRoute(body);
        call.enqueue(new Callback<ResponseBean>() {
            @Override
            public void onResponse(Call<ResponseBean> call, Response<ResponseBean> response) {
                Log.e("sssss","-----------------------"+response.body().success);
                if(response.body().success=="true"){
                    AlertDialog.Builder builder=new AlertDialog.Builder(Consulting_problemsActivity.this);
                    builder.setTitle("进言献策");//设置对话框的标题
                    builder.setMessage("您填写的信息已成功提交，请返回");//设置对话框的内容
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {  //这个是设置确定按钮

                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            //Toast.makeText(Some_suggestionsActivity.this, "确定", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
//                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {  //取消按钮
//
//                        @Override
//                        public void onClick(DialogInterface arg0, int arg1) {
//                            Toast.makeText(Some_suggestionsActivity.this, "取消",Toast.LENGTH_SHORT).show();
//                        }
//                    });
                    AlertDialog b=builder.create();
                    b.show();  //必须show一下才能看到对话框，跟Toast一样的道理
                }
            }

            @Override
            public void onFailure(Call<ResponseBean> call, Throwable t) {
                Log.e("sssss",t.getMessage());
                Toast.makeText(Consulting_problemsActivity.this,"请求失败!",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
