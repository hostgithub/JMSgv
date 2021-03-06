package com.cn.gov.jms.ui;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cn.gov.jms.App;
import com.cn.gov.jms.base.BaseActivity;
import com.cn.gov.jms.model.ApplicationEntity;
import com.cn.gov.jms.presenter.MainContract;
import com.cn.gov.jms.presenter.MainPresenter;
import com.cn.gov.jms.utils.AppInfoUtil;
import com.cn.gov.jms.utils.DataCleanManagerUtils;
import com.cn.gov.jms.utils.FileUtil;
import com.cn.gov.jms.utils.LogUtil;
import com.cn.gov.jms.utils.UpdateDialog;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity implements MainContract.View{

    @BindView(R.id.iv_back)
    ImageView imageView;
    @BindView(R.id.about_app)
    RelativeLayout about_app;
    @BindView(R.id.clear)
    RelativeLayout clear;
    @BindView(R.id.update)
    RelativeLayout update;
    @BindView(R.id.tv_size)
    TextView tv_size;

    private MainContract.Presenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {

        mPresenter = new MainPresenter(this);
        try {
            tv_size.setText(DataCleanManagerUtils.getTotalCacheSize(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @OnClick({ R.id.iv_back,R.id.about_app,R.id.update,R.id.clear})
    public void onClick(View view) {
       switch (view.getId()){
           case R.id.iv_back:
               finish();
               break;
           case R.id.about_app:
               startActivity(new Intent(SettingActivity.this, AboutusDetailActivity.class));
               break;
           case R.id.clear:
               try {
                   DataCleanManagerUtils.clearAllCache(this);
                   tv_size.setText(DataCleanManagerUtils.getTotalCacheSize(this));
                   File file=new File(FileUtil.getSDCardPath()+"TestImage/imagePic/");
                   if (file.exists()) {
                      deleteFile(file);Toast.makeText(this,"清除缓存成功!", Toast.LENGTH_SHORT).show();
                   }else{
                       Toast.makeText(this,"清除缓存成功!", Toast.LENGTH_SHORT).show();
                   }
               } catch (Exception e) {
                   e.printStackTrace();
               }
               break;
           case R.id.update:
               mPresenter.checkUpdate("http://www.jms.gov.cn/app/update.json");
               //Toast.makeText(this,"已经是最新版本!", Toast.LENGTH_SHORT).show();
               break;
       }
    }

    /**
     * 删除文件夹所有内容
     *
     */
    public void deleteFile(File file) {

        if (file.exists()) { // 判断文件是否存在
            if (file.isFile()) { // 判断是否是文件
                file.delete(); // delete()方法 你应该知道 是删除的意思;
            } else if (file.isDirectory()) { // 否则如果它是一个目录
                File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
                for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
                    this.deleteFile(files[i]); // 把每个文件 用这个方法进行迭代
                }
            }
            file.delete();
        } else {
            //
        }
    }

    @Override
    public void showLoading() {
        startProgressDialog();
    }

    @Override
    public void stopLoading() {
        stopProgressDialog();
    }

    @Override
    public void showErrorTip(String msg) {
        showErrorHint(msg);
    }

    @Override
    public void retureResult(String result) {
        showToast(result);
    }

    @Override
    public void retureUpdateResult(final ApplicationEntity entity) {
        try {
            if (AppInfoUtil.getVersionCode(App.application) < Integer.parseInt(entity.getVersion())) {
                String content = String.format("最新版本：%1$s\napp名字：%2$s\n\n更新内容\n%3$s", entity.getVersionShort(), entity.getName(), entity.getChangelog());
                UpdateDialog.show(SettingActivity.this, content, new UpdateDialog.OnUpdate() {
                    @Override
                    public void cancel() {

                    }

                    @Override
                    public void ok() {
                        mPresenter.update(entity);
                    }
                });
            }else {
                Toast.makeText(this,"已经是最新版本!", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            LogUtil.d("数字转化出错");
        }
    }
}
