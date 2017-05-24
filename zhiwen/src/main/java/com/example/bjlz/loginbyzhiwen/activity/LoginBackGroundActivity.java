/*
                   _ooOoo_
                  o8888888o
                  88" . "88
                  (| -_- |)
                  O\  =  /O
               ____/`---'\____
             .'  \\|     |//  `.
            /  \\|||  :  |||//  \
           /  _||||| -:- |||||-  \
           |   | \\\  -  /// |   |
           | \_|  ''\---/''  |   |
           \  .-\__  `-`  ___/-. /
         ___`. .'  /--.--\  `. . __
      ."" '<  `.___\_<|>_/___.'  >'"".
     | | :  `- \`.;`\ _ /`;.`/ - ` : | |
     \  \ `-.   \_ __\ /__ _/   .-` /  /
======`-.____`-.___\_____/___.-`____.-'======
                   `=---='
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
         佛祖保佑       永无BUG
*/
//          佛曰:
//                  写字楼里写字间，写字间里程序员；
//                  程序人员写程序，又拿程序换酒钱。
//                  酒醒只在网上坐，酒醉还来网下眠；
//                  酒醉酒醒日复日，网上网下年复年。
//                  但愿老死电脑间，不愿鞠躬老板前；
//                  奔驰宝马贵者趣，公交自行程序员。
//                  别人笑我忒疯癫，我笑自己命太贱；

package com.example.bjlz.loginbyzhiwen.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.bjlz.loginbyzhiwen.R;
import com.example.bjlz.loginbyzhiwen.application.MyApplication;
import com.example.bjlz.loginbyzhiwen.tools.tools.ToastUtil;
import com.example.bjlz.loginbyzhiwen.views.TitleBarView;

/**
 * 项目名称：LoginByZhiWen
 * 类描述：LoginBackGroundActivity 登录背景
 * 创建人：slj
 * 创建时间：2016-9-12 16:33
 * 修改人：slj
 * 修改时间：2016-9-12 16:33
 * 修改备注：
 * 邮箱:slj@bjlingzhuo.com
 */
@TargetApi(Build.VERSION_CODES.M)
public class LoginBackGroundActivity extends BaseActivity {
    private TitleBarView title_bar;
    private Button loginbyfinger,loginbyguesture,loginbypwd;
    private FingerprintManager manager;
    @Override
    public void onCreateBundle(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login_bg);
        MyApplication.getInstance().addActivity(this);
        getData();
        initView();
        initData();
        setOnClick();
    }

    @Override
    public void getData() {
        manager = (FingerprintManager) getSystemService(Context.FINGERPRINT_SERVICE);
    }

    @Override
    public void initView() {
        title_bar = (TitleBarView) findViewById(R.id.title_bar);
        title_bar.getLeftBtn().setVisibility(View.GONE);
        loginbyfinger = (Button) findViewById(R.id.loginbyfinger);
        loginbyguesture = (Button) findViewById(R.id.loginbyguesture);
        loginbypwd = (Button) findViewById(R.id.loginbypwd);
    }

    @Override
    public void initData() {
        title_bar.setTitleText(R.string.welcome_to_back);
        MyApplication.startActivity(LoginBackGroundActivity.this,FingerLoginActivity.class);
    }

    @Override
    public void setOnClick() {
        loginbyfinger.setOnClickListener(this);
        loginbyguesture.setOnClickListener(this);
        loginbypwd.setOnClickListener(this);
    }

    @Override
    public void WeightOnClick(View v) {
        switch (v.getId()){
            case R.id.loginbyfinger:
                //判断硬件是否支持指纹识别
                if (!manager.isHardwareDetected())
                    ToastUtil.shortToast(this, "该手机不支持指纹登录,请选择其他登录方式");
                else
                    MyApplication.startActivity(this,FingerLoginActivity.class);
                break;
            case R.id.loginbyguesture:
                MyApplication.startActivity(this,GuestureActivity.class);
                break;
            case R.id.loginbypwd:
                MyApplication.startActivity(this,LoginActivity.class);
                break;
        }
    }
}
