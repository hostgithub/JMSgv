package com.cn.gov.jms.ui;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cn.gov.jms.Config;
import com.cn.gov.jms.adapter.DeptAdapter;
import com.cn.gov.jms.base.BaseActivity;
import com.cn.gov.jms.model.DeptBean;
import com.cn.gov.jms.model.ResponseBean;
import com.cn.gov.jms.services.Api;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FanyingQuestionActivity extends BaseActivity {

    //    @BindView(R.id.spinner_kind)
//    Spinner spinner_kind;
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
    @BindView(R.id.imageview)
    ImageView imageview;

    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.rb1)
    RadioButton rb1;
    @BindView(R.id.rb2)
    RadioButton rb2;
    private String isOpen="1";

    @BindView(R.id.tv_number)
    TextView tv_number;
    @BindView(R.id.edt_number)
    EditText edt_number;

    private List<DeptBean.ResultsBean> list;
    private List<String> deptNmaeList;
    private String fileName;
    private static final int REQUEST_TAKE_PHOTO = 1;
    private String mCurrentPhotoPath;
    private File file;
    private String deptId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_fanying_question;
    }

    @Override
    protected void initView() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);// 让输入框不自动打开输入法
        list=new ArrayList<>();
        deptNmaeList=new ArrayList<>();
//        initKindData();
        initBumenData();
    }

    @OnClick({ R.id.iv_back,R.id.btn_submit,R.id.iv_upload,R.id.rb1,R.id.rb2})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.rb1:
                isOpen="1";
                tv_number.setVisibility(View.GONE);edt_number.setVisibility(View.GONE);
                break;
            case R.id.rb2:
                isOpen="0";
                tv_number.setVisibility(View.VISIBLE);edt_number.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_submit://提交
                if(isOpen=="0"){
                    if(edt_theme.getText().toString().trim().equals("")){
                        Toast.makeText(this,"请您输入查询码!",Toast.LENGTH_SHORT).show();
                    }
                }
                if(edt_theme.getText().toString().trim().equals("")||edt_name.getText().toString().trim().equals("")||edt_phone.getText().toString().trim().equals("")
                        || edt_address.getText().toString().trim().equals("")||edt_email.getText().toString().trim().equals("")||edt_content.getText().toString().trim().equals("")){
                    Toast.makeText(this,"请您将信息填写完整!",Toast.LENGTH_SHORT).show();
                }else {
                    post();
                }
                break;
            case R.id.iv_upload://浏览文件
                applyWritePermission();
                break;
            default:
                break;
        }
    }

    private void initBumenData(){

        //使用retrofit配置api
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Config.BANNER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api =retrofit.create(Api.class);
        Call<DeptBean> call=api.getTypeAndDeptData();
        call.enqueue(new Callback<DeptBean>() {
            @Override
            public void onResponse(Call<DeptBean> call, Response<DeptBean> response) {
                if(response!=null){
                    DeptBean deptBean=response.body();
                    list=deptBean.getResults();
                    Log.e("---------部门名称-----",list.get(0).deptName);
                    DeptAdapter deptAdapter=new DeptAdapter(deptBean.getResults(),FanyingQuestionActivity.this);
                    Log.e("-------------",list.toString());
                    //绑定 Adapter到控件
                    spinner_bumen .setAdapter(deptAdapter);
                    spinner_bumen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view,
                                                   int pos, long id) {
                            deptId=list.get(0).deptId;
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            // Another interface callback
                        }
                    });
                }else{
                    Toast.makeText(FanyingQuestionActivity.this,"数据为空!",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DeptBean> call, Throwable t) {
                Toast.makeText(FanyingQuestionActivity.this,"请求失败!",Toast.LENGTH_SHORT).show();
                Log.e("-------------",t.getMessage().toString());
            }
        });

        // 本地建立Adapter并且绑定数据源
        //final String[] mItems = getResources().getStringArray(R.array.kind);
//        ArrayAdapter adapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,mItems);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

    }


//    private void initKindData(){
//        // 本地建立数据源
//        final String[] mItems = getResources().getStringArray(R.array.kind);
//        // 建立Adapter并且绑定数据源
//        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, mItems);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        //绑定 Adapter到控件
//        spinner_kind .setAdapter(adapter);
//        spinner_kind.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view,
//                                       int pos, long id) {
//                String strBumen=mItems[pos];
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                // Another interface callback
//            }
//        });
//    }

    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobiles) {
    /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        String telRegex = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles)) return false;
        else return mobiles.matches(telRegex);
    }


    /**
     * 用onActivityResult()接收传回的图像，当用户拍完照片，或者取消后，系统都会调用这个函数
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            Log.e("TAG", "---------" + FileProvider.getUriForFile(this, "com.cn.gov.jms.MyApplication.fileprovider", file));
            imageview.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
        }
    }


    public void applyWritePermission() {

        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

        if (Build.VERSION.SDK_INT >= 23) {
            int check = ContextCompat.checkSelfPermission(this, permissions[0]);
            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
            if (check == PackageManager.PERMISSION_GRANTED) {
                //调用相机
                useCamera();
            } else {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        } else {
            useCamera();
        }
    }

    /**
     * 使用相机
     */
    private void useCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/test/" + System.currentTimeMillis() + ".jpg");
        file.getParentFile().mkdirs();

        //改变Uri  com.xykj.customview.fileprovider注意和xml中的一致
        Uri uri = FileProvider.getUriForFile(this, "com.cn.gov.jms.MyApplication.fileprovider", file);
        //添加权限
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, REQUEST_TAKE_PHOTO);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            useCamera();
        } else {
            // 没有获取 到权限，从新请求，或者关闭app
            Toast.makeText(this, "需要存储权限", Toast.LENGTH_SHORT).show();
        }
    }

    private void post(){
        Gson gson=new Gson();
        HashMap<String,String> paramsMap=new HashMap<>();
        paramsMap.put("id","10030002");
        paramsMap.put("dept",deptId);
        paramsMap.put("title",edt_theme.getText().toString());
        paramsMap.put("contents",edt_content.getText().toString());
        paramsMap.put("isOpen",isOpen);
        if(isOpen=="0"){
            paramsMap.put("hiddenCode",edt_number.getText().toString().trim());
        }
        paramsMap.put("name",edt_name.getText().toString());
        paramsMap.put("phone",edt_phone.getText().toString());
        paramsMap.put("address",edt_address.getText().toString());
        paramsMap.put("email",edt_email.getText().toString());
        //paramsMap.put("imagePath","");//图片路径
        String route= gson.toJson(paramsMap);
        Log.e("ssssssss",route.toString());

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Config.BANNER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api =retrofit.create(Api.class);
        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),route);
        Log.e("ssssssss",body.toString());
        Call<ResponseBean> call=api.addProblem(body);
        call.enqueue(new Callback<ResponseBean>() {
            @Override
            public void onResponse(Call<ResponseBean> call, Response<ResponseBean> response) {
                Log.e("sssss","-----------------------"+response.body().success);
                if(response.body().success=="true"){
                    AlertDialog.Builder builder=new AlertDialog.Builder(FanyingQuestionActivity.this);
                    builder.setTitle("反映问题");//设置对话框的标题
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
                Toast.makeText(FanyingQuestionActivity.this,"请求失败!",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
