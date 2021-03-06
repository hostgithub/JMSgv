package com.cn.gov.jms.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.cn.gov.jms.App;
import com.cn.gov.jms.base.BaseActivity;
import com.cn.gov.jms.fragments.CollectFragment;
import com.cn.gov.jms.fragments.CopyFragment;
import com.cn.gov.jms.fragments.HomeFragment;
import com.cn.gov.jms.fragments.LanmuFragment;
import com.cn.gov.jms.fragments.MineFragment;
import com.cn.gov.jms.model.ApplicationEntity;
import com.cn.gov.jms.presenter.MainContract;
import com.cn.gov.jms.presenter.MainPresenter;
import com.cn.gov.jms.utils.AppInfoUtil;
import com.cn.gov.jms.utils.LogUtil;
import com.cn.gov.jms.utils.UpdateDialog;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements MainContract.View
{

    @BindView(R.id.edt_search)
    EditText edt_search;
    @BindView(R.id.iv_search)
    ImageView iv_search;
    @BindView(R.id.main_frame)
    FrameLayout mMainFrame;
    @BindView(R.id.fragment_bottom_home)
    RadioButton fragment_bottom_home;
    @BindView(R.id.fragment_bottom_lanmu)
    RadioButton fragment_bottom_lanmu;
    @BindView(R.id.fragment_bottom_coll)
    RadioButton fragment_bottom_coll;
    @BindView(R.id.fragment_bottom_mine)
    RadioButton fragment_bottom_mine;
//    @BindView(R.id.main_bottomBar)
//    BottomNavigationBar mMainBottomBar;//  特别的底部 按钮切换的组件 网上可以查到具体用法

    private Context mContext;
    private FragmentManager fm;

    private HomeFragment mHomeFragment;
    private LanmuFragment mLanmuFragment;
    private CollectFragment mCollectFragment;
    private MineFragment mMineFragment;
    private CopyFragment copyFragment;
    private boolean isExit;


    private MainContract.Presenter mPresenter;

//    @Override
//    protected void onCreate(Bundle savedInstanceState)
//    {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        ButterKnife.bind(this);
//        mContext = this;
//        fm = getSupportFragmentManager();
//        initBottom();
//    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }


    @Override
    protected void initView() {

        mPresenter = new MainPresenter(this);
        //ButterKnife.bind(this);          //BaseActivity已经绑定了
        mContext = this;
        fm = getSupportFragmentManager();
        initBottom();
//        mPresenter.checkUpdate("http://api.fir.im/apps/latest/58f87d50959d6904280005a3?api_token=9f2408863ff25abccca986e5d4d9d6ba");
        mPresenter.checkUpdate("http://www.jms.gov.cn/app/update.json");
    }

    private void initBottom()
    {

        changeImageSize();
//        mMainBottomBar.setMode(BottomNavigationBar.MODE_FIXED);
//        mMainBottomBar.setBarBackgroundColor(R.color.vpi__background_holo_dark);//set background color for navigation bar，设置底部导航栏颜色
//        //mMainBottomBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
//        mMainBottomBar.addItem(new BottomNavigationItem(R.drawable.cb_icon_discover_selected, "")
//                        .setActiveColorResource(R.color.colorBG).setInactiveIcon(mContext.getResources().getDrawable(R.drawable.cb_icon_discover_normal)))
//                .addItem(new BottomNavigationItem(R.drawable.cb_icon_guanzhu_selected, "")
//                        .setActiveColorResource(R.color.colorBG).setInactiveIcon(mContext.getResources().getDrawable(R.drawable.cb_icon_guanzhu_normal)))
//                .addItem(new BottomNavigationItem(R.drawable.cb_icon_tixing_selected, "")
//                        .setActiveColorResource(R.color.colorBG).setInactiveIcon(mContext.getResources().getDrawable(R.drawable.cb_icon_tixing_normal)))
//                .addItem(new BottomNavigationItem(R.drawable.cb_icon_more_selected, "")
//                        .setActiveColorResource(R.color.colorBG).setInactiveIcon(mContext.getResources().getDrawable(R.drawable.cb_icon_more_normal)))
//                .setFirstSelectedPosition(0)
//                .initialise();
//
//        mMainBottomBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener()
//        {
//            @Override
//            public void onTabSelected(int position)
//            {
//                switch (position)
//                {
//                    case 0:
//                        showFragment(0);
//                        break;
//                    case 1:
//                        showFragment(1);
//                        break;
//                    case 2:
//                        showFragment(2);
//                        break;
//                    case 3:
//                        showFragment(3);
//                        break;
//                    default:
//                        break;
//                }
//            }
//
//            @Override
//            public void onTabUnselected(int position)
//            {
//            }
//
//            @Override
//            public void onTabReselected(int position)
//            {
//            }
//        });
        showFragment(0);
        fragment_bottom_home.setChecked(true);
    }

    @OnClick({ R.id.fragment_bottom_home, R.id.fragment_bottom_lanmu, R.id.fragment_bottom_coll , R.id.fragment_bottom_mine,R.id.edt_search,R.id.iv_search})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fragment_bottom_home:
                showFragment(0);
                break;
            case R.id.fragment_bottom_lanmu:
                showFragment(1);
                break;
            case R.id.fragment_bottom_coll:
                showFragment(2);
                break;
            case R.id.fragment_bottom_mine:
                showFragment(3);
                break;
            case R.id.edt_search:
                startActivity(new Intent(this,SearchActivity.class));
                break;
            case R.id.iv_search:
                startActivity(new Intent(this,SearchActivity.class));
                break;
            default:
                break;
        }
    }

    /**
     * 展示fragment
     *
     * @param position
     */
    private void showFragment(int position)
    {
        FragmentTransaction ft = fm.beginTransaction();
        hideFragments(ft);
        switch (position)
        {
            case 0:
                if (mHomeFragment != null)
                {
                    ft.show(mHomeFragment);
                } else
                {
                    mHomeFragment = HomeFragment.getInstance();
                    ft.add(R.id.main_frame, mHomeFragment);
                }
                break;
            case 1:
                if (mLanmuFragment != null)
                {
                    ft.show(mLanmuFragment);
                } else
                {
                    mLanmuFragment = LanmuFragment.getInstance();
                    ft.add(R.id.main_frame, mLanmuFragment);
                }
                break;
            case 2:
                if (copyFragment != null)
                {
                    ft.show(copyFragment);
                } else
                {
                    copyFragment = CopyFragment.getInstance();
                    ft.add(R.id.main_frame, copyFragment);
                }
                break;
            case 3:
                if (mMineFragment != null)
                {
                    ft.show(mMineFragment);
                } else
                {
                    mMineFragment = MineFragment.getInstance();
                    ft.add(R.id.main_frame, mMineFragment);
                }
                break;
            default:
                break;
        }
        ft.commit();
    }


    private void changeImageSize() {
        //定义底部标签图片大小
        Drawable drawableFirst = getResources().getDrawable(R.drawable.btn_1);
        drawableFirst.setBounds(0, 0, 45, 45);//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
        fragment_bottom_home.setCompoundDrawables(null, drawableFirst, null, null);//只放上面

        Drawable drawableLanmu = getResources().getDrawable(R.drawable.btn_2);
        drawableLanmu.setBounds(0, 0, 45, 45);//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
        fragment_bottom_lanmu.setCompoundDrawables(null, drawableLanmu, null, null);//只放上面

        Drawable drawableSearch = getResources().getDrawable(R.drawable.btn_3);
        drawableSearch.setBounds(0, 0, 45, 45);//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
        fragment_bottom_coll.setCompoundDrawables(null, drawableSearch, null, null);//只放上面

        Drawable drawableMe = getResources().getDrawable(R.drawable.btn_4);
        drawableMe.setBounds(0, 0, 45, 45);//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
        fragment_bottom_mine.setCompoundDrawables(null, drawableMe, null, null);//只放上面
    }

    private void hideFragments(FragmentTransaction ft)
    {
        if (mHomeFragment != null)
        {
            ft.hide(mHomeFragment);
        }
        if (mLanmuFragment != null)
        {
            ft.hide(mLanmuFragment);
        }
        if (copyFragment != null)
        {
            ft.hide(copyFragment);
        }
        if (mMineFragment != null)
        {
            ft.hide(mMineFragment);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            exitByDoubleClick();
        }
        return false;
    }

    private void exitByDoubleClick() {
        Timer tExit=null;
        if(!isExit){
            isExit=true;
            Toast.makeText(MainActivity.this,"再按一次退出程序", Toast.LENGTH_SHORT).show();
            tExit=new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit=false;//取消退出
                }
            },2000);// 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
        }else{
            finish();
            System.exit(0);
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
                UpdateDialog.show(MainActivity.this, content, new UpdateDialog.OnUpdate() {
                    @Override
                    public void cancel() {

                    }

                    @Override
                    public void ok() {
                        mPresenter.update(entity);
                    }
                });
            }
        } catch (Exception e) {
            LogUtil.d("数字转化出错");
        }
    }
}
