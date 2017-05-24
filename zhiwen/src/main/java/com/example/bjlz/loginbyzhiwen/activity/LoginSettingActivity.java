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

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.TextView;

import com.example.bjlz.loginbyzhiwen.R;
import com.example.bjlz.loginbyzhiwen.application.MyApplication;
import com.example.bjlz.loginbyzhiwen.tools.tools.PreferencesUtils;
import com.example.bjlz.loginbyzhiwen.tools.tools.ToastUtil;

/**
 * 项目名称：LoginByZhiWen
 * 类描述：LoginSettingActivity 设置登录方式的界面
 * 创建人：slj
 * 创建时间：2016-9-8 10:52
 * 修改人：slj
 * 修改时间：2016-9-8 10:52
 * 修改备注：
 * 邮箱:slj@bjlingzhuo.com
 */
public class LoginSettingActivity extends BaseActivity {
    private TextView tv_zhi_wen, tv_guesture, tv_jump;//指纹/手势/跳过
    private String name;//姓名
    private boolean isHardwareDetected ;//判断是否存在指纹解锁
    @Override
    public void onCreateBundle(Bundle savedInstanceState) {
        setContentView(R.layout.activity_settings_login);
        MyApplication.getInstance().addActivity(this);
        getData();
        initView();
        initData();
        setOnClick();
    }

    @Override
    public void getData() {
        name = getIntent().getBundleExtra("bundle").getString("userName");
        isHardwareDetected= PreferencesUtils.getBoolean(getApplicationContext(),MyApplication.HAS_FINGERPRINT_API);
    }

    @Override
    public void initView() {
        tv_zhi_wen = (TextView) findViewById(R.id.tv_zhi_wen);
        tv_guesture = (TextView) findViewById(R.id.tv_guesture);
        tv_jump = (TextView) findViewById(R.id.tv_jump);
    }

    @Override
    public void initData() {

    }

    @Override
    public void setOnClick() {
        tv_zhi_wen.setOnClickListener(this);
        tv_guesture.setOnClickListener(this);
        tv_jump.setOnClickListener(this);
    }

    @Override
    public void WeightOnClick(View v) {
        Bundle bundle = new Bundle();
        bundle.putString("userName", name);
        switch (v.getId()) {
            case R.id.tv_zhi_wen://指纹
                if (!isHardwareDetected) {
                    ToastUtil.shortToast(this, "该手机不支持指纹登录");
                } else {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
//                    MyApplication.startActivity(LoginSettingActivity.this, FingerprintActivity.class, bundle);
                    MyApplication.startActivity(LoginSettingActivity.this, FingerPrintLoginActivity.class, bundle);
                    finish();
                }
                break;
            case R.id.tv_guesture://手势
                MyApplication.startActivity(LoginSettingActivity.this, GuestureActivity.class, bundle);
                finish();
                break;
            case R.id.tv_jump://跳过
                PreferencesUtils.putBoolean(getApplicationContext(), "isJump", true);
//                MyApplication.startActivity(LoginSettingActivity.this, MainActivity.class, bundle);
                finish();
                break;
        }
    }
//    @Override
//     public boolean onTouchEvent(MotionEvent event){
//               this.finish();
//        return true;
//    }
}
