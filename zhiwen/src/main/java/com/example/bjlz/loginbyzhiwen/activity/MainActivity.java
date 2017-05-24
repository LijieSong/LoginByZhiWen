package com.example.bjlz.loginbyzhiwen.activity;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bjlz.loginbyzhiwen.R;
import com.example.bjlz.loginbyzhiwen.application.MyApplication;
import com.example.bjlz.loginbyzhiwen.fragments.ViewPagerSimpaleFragment;
import com.example.bjlz.loginbyzhiwen.tools.tools.PreferencesUtils;
import com.example.bjlz.loginbyzhiwen.tools.tools.ToastUtil;
import com.example.bjlz.loginbyzhiwen.views.TitleBarView;
import com.example.bjlz.loginbyzhiwen.views.ViewPagerTab.ViewPagerIndeCator;
import com.umeng.socialize.Config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends BaseActivity {
    private long exitTime = 0;//记录点击的时间
    private TitleBarView title_bar;//标题
    private String userName;//用户名
    private boolean isLogin = false;//记录是否登录
    private boolean isGuestureLock = false;//是否开启手势登录
    private boolean isFinger = false;//是否开启指纹登录
    private boolean isJump = false;//是否跳过
    private Button btn_share;//分享
    private int REQUEST_PERM = 10;
    private TextView tv_main,tv_main_chek;//展开
    private int dates;//表示展示开还是不开始
    private ViewPagerIndeCator viewpagerindecator;//展示图匹配器
    private ViewPager viewPager;//展示图
    private List<String> mTitles = Arrays.asList("短信","电话","通讯录","通讯录2","通讯录3","通讯录4");//标题
    private List<ViewPagerSimpaleFragment> fragments = new ArrayList<>();//fragment
    private FragmentPagerAdapter mAdapter;//适配器
    @Override
    public void onCreateBundle(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        MyApplication.getInstance().addActivity(this);
        getIsLogin();
        getData();
        initView();
        initData();
        setOnClick();
    }

    private void getIsLogin() {
        isLogin = PreferencesUtils.getBoolean(getApplicationContext(), "isLogin", false);
        isFinger = PreferencesUtils.getBoolean(getApplicationContext(), "isFinger", false);
        isJump = PreferencesUtils.getBoolean(getApplicationContext(), "isJump", false);
        isGuestureLock = PreferencesUtils.getBoolean(getApplicationContext(), "isGuestureLock", false);
    }
    @Override
    public void initView() {
        if (isJump != false){
            Bundle bundle = new Bundle();
            bundle.putString("userName", userName);
            MyApplication.startActivity(MainActivity.this, LoginSettingActivity.class, bundle);
        }else{
            if (isGuestureLock == false && isFinger == false ){
                Bundle bundle = new Bundle();
                bundle.putString("userName", userName);
                MyApplication.startActivity(MainActivity.this, LoginSettingActivity.class, bundle);
            }
        }
        this.title_bar = (TitleBarView) findViewById(R.id.title_bar);
        btn_share = (Button) findViewById(R.id.btn_share);
        tv_main = (TextView) findViewById(R.id.tv_main);
        tv_main_chek = (TextView) findViewById(R.id.tv_main_chek);
        viewpagerindecator = (ViewPagerIndeCator) findViewById(R.id.viewpagerindecator);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
    }

    @Override
    public void initData() {
        this.title_bar.setTitleText(R.string.app_name);
        this.title_bar.setCommonVisible(View.GONE,View.VISIBLE,View.VISIBLE);
        this.title_bar.setRightBtnText(R.string.exit);
        userName = PreferencesUtils.getUserId(getApplicationContext());
        if (isLogin == true) {
            this.title_bar.setTitleText(userName);
        }
        viewpagerindecator.setVisibleTabCounts(3);//设置可见tab数量
        viewpagerindecator.setTabTitles(mTitles);//设置标题
        for (String mTitle: mTitles) {
            ViewPagerSimpaleFragment simpaleFragment = ViewPagerSimpaleFragment.newInstance(mTitle);
            fragments.add(simpaleFragment);
        }
        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        };
        viewPager.setAdapter(mAdapter);
        viewpagerindecator.setViewPager(viewPager,0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getIsLogin();
        userName = PreferencesUtils.getUserId(getApplicationContext());
    }

    @Override
    public void getData() {
        if(Build.VERSION.SDK_INT>=23){
            String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.CALL_PHONE,Manifest.permission.READ_LOGS,Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.SET_DEBUG_APP,Manifest.permission.USE_FINGERPRINT,Manifest.permission.SYSTEM_ALERT_WINDOW,Manifest.permission.GET_ACCOUNTS,Manifest.permission.WRITE_APN_SETTINGS};
            ActivityCompat.requestPermissions(this,mPermissionList,REQUEST_PERM);
        }
        Bundle bundle = getIntent().getBundleExtra("bundle");
        if (bundle != null) {
            userName = bundle.getString("userName");
        }
        Config.dialogSwitch = true;
    }

    @Override
    public void setOnClick() {
        this.title_bar.setRightBtnOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferencesUtils.clear(getApplicationContext());
                MyApplication.getInstance().close();
                finish();
            }
        });
        btn_share.setOnClickListener(this);
        tv_main.setOnClickListener(this);
    }

    @Override
    public void WeightOnClick(View v) {
        switch(v.getId()){
            case R.id.tv_main:
                dates++;
                if (dates%2 == 1){
                    tv_main_chek.setEllipsize(null);
                    tv_main.setText("点击收起");
                    tv_main_chek.setMaxLines(Integer.MAX_VALUE);
                }else{
                    tv_main_chek.setLines(1);
                    tv_main.setText("点击展开");
                    tv_main_chek.setEllipsize(TextUtils.TruncateAt.END);//收缩
                }
                break;
        }
    }
    /**
     * 点击2次返回键关闭本界面
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //退出的判断
    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
//            Toast.makeText(MainActivity.this,"再按一次退出程序", Toast.LENGTH_SHORT).show();
            ToastUtil.shortDiyToast(MainActivity.this, "再按一次退出程序");
            exitTime = System.currentTimeMillis();
        } else {
            MyApplication.getInstance().close();
            finish();
        }
    }
}
