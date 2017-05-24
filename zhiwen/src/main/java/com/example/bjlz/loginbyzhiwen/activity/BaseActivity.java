package com.example.bjlz.loginbyzhiwen.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;

import com.example.bjlz.loginbyzhiwen.tools.tools.StatusBarUtils;


/**
 * 项目名称：qianshandoctor
 * 类描述：BaseActivity :基类
 * 创建人：slj
 * 创建时间：2016-5-28 17:24
 * 修改人：slj
 * 修改时间：2016-5-28 17:24
 * 修改备注：
 * 邮箱:slj@bjlingzhuo.com
 */
public abstract class BaseActivity extends FragmentActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//设置没有标题
        StatusBarUtils.setStatusBar(BaseActivity.this);//设置透明状态栏
        onCreateBundle(savedInstanceState);
    }

    /**
     * 设置convertview
     * @param savedInstanceState
     */
    public abstract void onCreateBundle(Bundle savedInstanceState);
    /**
     * 获取传递过来的数据或者存储的数据
     */
    public abstract void getData();
    /**
     * 初始化UI
     */
    public abstract void initView();

    /**
     * 初始化数据
     */
    public abstract void initData();

    /**
     * 设置监听事件
     */
    public abstract void setOnClick();

    /**
     * 处理点击事件
     *
     * @param v
     */
    public abstract void WeightOnClick(View v);


    @Override
    public void onClick(View v) {
        WeightOnClick(v);
    }

    /**
     * 返回键关闭本页面
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN
                && event.getRepeatCount() == 0) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        /**
         * 设置为竖屏
         */
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        super.onResume();
    }
}
