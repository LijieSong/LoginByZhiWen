package com.example.bjlz.loginbyzhiwen.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.example.bjlz.loginbyzhiwen.R;
import com.example.bjlz.loginbyzhiwen.application.MyApplication;
import com.example.bjlz.loginbyzhiwen.tools.tools.PreferencesUtils;
import com.example.bjlz.loginbyzhiwen.tools.tools.UserUtils;

import java.util.Map;

/**
 * 项目名称：qianshandoctor
 * 类描述：SplashActivity :欢迎动画
 * 创建人：slj
 * 创建时间：2016-5-28 17:24
 * 修改人：slj
 * 修改时间：2016-5-28 17:24
 * 修改备注：
 * 邮箱:slj@bjlingzhuo.com
 */
public class SplashActivity extends BaseActivity {
    private Handler handler = new Handler();
    private boolean isLogin = false;//是否登录
    private boolean isGuestureLock = false;//手否开启手势登录
    private boolean isFinger = false;//是否开启指纹登录
    @Override
    public void onCreateBundle(Bundle savedInstanceState) {
        setContentView(R.layout.activity_welcome);
        getData();
        initView();
        initData();
        setOnClick();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isLogin == false) {
                    MyApplication.startActivity(SplashActivity.this, LoginActivity.class);
                } else {
                    if (isGuestureLock == true){
                        MyApplication.startActivity(SplashActivity.this, GuestureActivity.class);
                    } else if (isFinger == true){
                        MyApplication.startActivity(SplashActivity.this, LoginBackGroundActivity.class);
                    }else{
                        MyApplication.startActivity(SplashActivity.this, MainActivity.class);
                    }
                }
                finish();
            }
        }, 50);
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void getData() {
        isLogin = PreferencesUtils.getBoolean(getApplicationContext(), "isLogin", false);
        isFinger = PreferencesUtils.getBoolean(getApplicationContext(), "isFinger", false);
        isGuestureLock = PreferencesUtils.getBoolean(getApplicationContext(), "isGuestureLock", false);
    }

    @Override
    public void setOnClick() {

    }

    @Override
    public void WeightOnClick(View v) {

    }
}