package com.cn.gov.jms.ui;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
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

import com.bumptech.glide.Glide;
import com.cn.gov.jms.Config;
import com.cn.gov.jms.adapter.DeptAdapter;
import com.cn.gov.jms.base.BaseActivity;
import com.cn.gov.jms.model.DeptBean;
import com.cn.gov.jms.model.ResponseBean;
import com.cn.gov.jms.services.Api;
import com.cn.gov.jms.utils.LQRPhotoSelectUtils;
import com.cn.gov.jms.utils.PhotoBitmapUtils2;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Consulting_problemsActivity extends BaseActivity {

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
    private String fileName;
    private static final int REQUEST_TAKE_PHOTO = 1;
    private String mCurrentPhotoPath;
    private File file;
    private String deptId;
    private LQRPhotoSelectUtils mLqrPhotoSelectUtils;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_consulting_problems;
    }

    @Override
    protected void initView() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);// 让输入框不自动打开输入法
        list=new ArrayList<>();
        initBumenData();//从网络获取部门数据

        rb1.setChecked(true);
        init();
    }

    private void init() {
        // 1、创建LQRPhotoSelectUtils（一个Activity对应一个LQRPhotoSelectUtils）
        mLqrPhotoSelectUtils = new LQRPhotoSelectUtils(this, new LQRPhotoSelectUtils.PhotoSelectListener() {
            @Override
            public void onFinish(File outputFile, Uri outputUri) {
                // 4、当拍照或从图库选取图片成功后回调
                //tv_imgUrl.setText(outputFile.getAbsolutePath());//图片路径
                tv_imgUrl.setText(PhotoBitmapUtils2.amendRotatePhoto(outputFile.getAbsolutePath(),Consulting_problemsActivity.this,""));//修复后的图片路径
                file=new File(PhotoBitmapUtils2.amendRotatePhoto(outputFile.getAbsolutePath(),Consulting_problemsActivity.this,""));
                Log.e("======上传的文件名===",file.getName());
                //mTvUri.setText(outputUri.toString());//图片Uri
                Glide.with(Consulting_problemsActivity.this).load(outputUri).into(imageview);
            }
        }, false);//true裁剪，false不裁剪

        //        mLqrPhotoSelectUtils.setAuthorities("com.lqr.lqrnativepicselect.fileprovider");
        //        mLqrPhotoSelectUtils.setImgPath(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + String.valueOf(System.currentTimeMillis()) + ".jpg");
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
                    postOpen();
                }
                break;
            case R.id.iv_upload://浏览文件
                // 3、调用从图库选取图片方法
                PermissionGen.needPermission(Consulting_problemsActivity.this,
                        LQRPhotoSelectUtils.REQ_SELECT_PHOTO,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE}
                );
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
                    DeptAdapter deptAdapter=new DeptAdapter(deptBean.getResults(),Consulting_problemsActivity.this);
                    Log.e("-------------",list.toString());
                    //绑定 Adapter到控件
                    spinner_bumen .setAdapter(deptAdapter);

                    deptAdapter.setOnItemClickLitener(new DeptAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            Toast.makeText(Consulting_problemsActivity.this,position,Toast.LENGTH_SHORT).show();
                        }
                    });
                    spinner_bumen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view,
                                                   int pos, long id) {
                            deptId=list.get(pos).deptId;
                            Log.e("======选择的deptId===",deptId);
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            // Another interface callback
                        }
                    });
                }else{
                    Toast.makeText(Consulting_problemsActivity.this,"数据为空!",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DeptBean> call, Throwable t) {
                Toast.makeText(Consulting_problemsActivity.this,"请求失败!",Toast.LENGTH_SHORT).show();
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


//    /**
//     * 用onActivityResult()接收传回的图像，当用户拍完照片，或者取消后，系统都会调用这个函数
//     */
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
//            Log.e("TAG", "---------" + FileProvider.getUriForFile(this, "com.cn.gov.jms.MyApplication.fileprovider", file));
//            imageview.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
//        }
//    }

    @PermissionSuccess(requestCode = LQRPhotoSelectUtils.REQ_TAKE_PHOTO)
    private void takePhoto() {
        mLqrPhotoSelectUtils.takePhoto();
    }

    @PermissionSuccess(requestCode = LQRPhotoSelectUtils.REQ_SELECT_PHOTO)
    private void selectPhoto() {
        mLqrPhotoSelectUtils.selectPhoto();
    }

    @PermissionFail(requestCode = LQRPhotoSelectUtils.REQ_TAKE_PHOTO)
    private void showTip1() {
        //        Toast.makeText(getApplicationContext(), "不给我权限是吧，那就别玩了", Toast.LENGTH_SHORT).show();
        showDialog();
    }

    @PermissionFail(requestCode = LQRPhotoSelectUtils.REQ_SELECT_PHOTO)
    private void showTip2() {
        //        Toast.makeText(getApplicationContext(), "不给我权限是吧，那就别玩了", Toast.LENGTH_SHORT).show();
        showDialog();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 2、在Activity中的onActivityResult()方法里与LQRPhotoSelectUtils关联
        mLqrPhotoSelectUtils.attachToActivityForResult(requestCode, resultCode, data);
    }

    public void showDialog() {
        //创建对话框创建器
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //设置对话框显示小图标
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        //设置标题
        builder.setTitle("权限申请");
        //设置正文
        builder.setMessage("在设置-应用-权限 中开启相机、存储权限，才能正常使用拍照或图片选择功能");

        //添加确定按钮点击事件
        builder.setPositiveButton("去设置", new DialogInterface.OnClickListener() {//点击完确定后，触发这个事件

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //这里用来跳到手机设置页，方便用户开启权限
                Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + Consulting_problemsActivity.this.getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        //添加取消按钮点击事件
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        //使用构建器创建出对话框对象
        AlertDialog dialog = builder.create();
        dialog.show();//显示对话框
    }
    private void postOpen(){
        Gson gson=new Gson();
        HashMap<String,Object> paramsMap=new HashMap<>();
        paramsMap.put("id","10030001");
        Log.e("======上传的deptId===",deptId);
        paramsMap.put("dept",deptId);
        paramsMap.put("title",edt_theme.getText().toString().trim());
        paramsMap.put("contents",edt_content.getText().toString().trim());
        paramsMap.put("isOpen",isOpen);
        if(isOpen=="0"){
            paramsMap.put("hiddenCode",edt_number.getText().toString().trim());
        }
        paramsMap.put("name",edt_name.getText().toString().trim());
        paramsMap.put("phone",edt_phone.getText().toString().trim());
        paramsMap.put("address",edt_address.getText().toString().trim());
        paramsMap.put("email",edt_email.getText().toString().trim());

//        HashMap<String,RequestBody> bodyMap=new HashMap<>();
//        bodyMap.put("file"+"\";filename=\""+file.getName(),RequestBody.create(MediaType.parse("image/png"),file));
//        paramsMap.put("key_service"+"\";filename=\""+file.getName(),RequestBody.create(MediaType.parse("image/jpg"),file));
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
                    AlertDialog.Builder builder=new AlertDialog.Builder(Consulting_problemsActivity.this);
                    builder.setTitle("咨询问题");//设置对话框的标题
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
