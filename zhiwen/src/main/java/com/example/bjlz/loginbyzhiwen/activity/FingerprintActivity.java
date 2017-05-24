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
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.KeyguardManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bjlz.loginbyzhiwen.R;
import com.example.bjlz.loginbyzhiwen.application.MyApplication;
import com.example.bjlz.loginbyzhiwen.tools.tools.LogUtils;
import com.example.bjlz.loginbyzhiwen.tools.tools.PreferencesUtils;
import com.example.bjlz.loginbyzhiwen.views.TitleBarView;

import java.security.Signature;

import javax.crypto.Cipher;
import javax.crypto.Mac;

/**
 * 项目名称：LoginByZhiWen
 * 类描述：FingerprintActivity 指纹绑定类
 * 创建人：slj
 * 创建时间：2016-9-12 13:38
 * 修改人：slj
 * 修改时间：2016-9-12 13:38
 * 修改备注：
 * 邮箱:slj@bjlingzhuo.com
 */
@TargetApi(Build.VERSION_CODES.M)
public class FingerprintActivity extends BaseActivity {
    private TitleBarView title_bar;//标题
    private Button btn_start_finger,btn_ok,btn_cancle;//开始绑定指纹按钮/确定/取消
    private TextView tv_show_message,tv_show_title;//标题,内容
    private LinearLayout ll_finger;//标题
    FingerprintManager manager;
    KeyguardManager mKeyManager;
    private final static int REQUEST_CODE_CONFIRM_DEVICE_CREDENTIALS = 0;
    private boolean isFinger = false;
    // 弹出框
    private ProgressDialog mDialog;
    @Override
    public void onCreateBundle(Bundle savedInstanceState) {
        setContentView(R.layout.activity_finger_print);
        MyApplication.getInstance().addActivity(this);
        getData();
        initView();
        initData();
        setOnClick();
    }

    @Override
    public void getData() {
        manager = (FingerprintManager) getSystemService(Context.FINGERPRINT_SERVICE);
        mKeyManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
    }

    @Override
    public void initView() {
        this.title_bar = (TitleBarView) findViewById(R.id.title_bar);
        btn_start_finger = (Button) findViewById(R.id.btn_start_finger);
        btn_ok = (Button) findViewById(R.id.btn_ok);
        btn_cancle = (Button) findViewById(R.id.btn_cancle);
        tv_show_message = (TextView) findViewById(R.id.tv_show_message);
        tv_show_title = (TextView) findViewById(R.id.tv_show_title);
        ll_finger = (LinearLayout) findViewById(R.id.ll_finger);
        if (isFinger())
            startListening(null);
    }

    @Override
    public void initData() {
        this.title_bar.setTitleText(R.string.bind_zhi_wen);
        btn_ok.setVisibility(View.GONE);
        btn_cancle.setVisibility(View.VISIBLE);
    }

    @Override
    public void setOnClick() {
        btn_start_finger.setOnClickListener(this);
        btn_ok.setOnClickListener(this);
        btn_cancle.setOnClickListener(this);
    }

    @Override
    public void WeightOnClick(View v) {
        switch (v.getId()){
            case  R.id.btn_start_finger:
                if (isFinger())
                    startListening(null);
            break;
            case R.id.btn_cancle:
                PreferencesUtils.putBoolean(getApplicationContext(),"isFinger",isFinger);
                FingerprintActivity.this.finish();
                break;
            case R.id.btn_ok:
//                MyApplication.startActivity(FingerprintActivity.this,MainActivity.class);
                PreferencesUtils.putBoolean(getApplicationContext(),"isFinger",isFinger);
                PreferencesUtils.putBoolean(getApplicationContext(), "isJump", false);
                FingerprintActivity.this.finish();
                break;
        }
    }
    CancellationSignal mCancellationSignal = new CancellationSignal();

    public void startListening(FingerprintManager.CryptoObject cryptoObject) {
        //android studio 上，没有这个会报错
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "没有指纹识别权限", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.USE_FINGERPRINT},100);
            return;
        }
        manager.authenticate(null, mCancellationSignal, 0, new FingerprintManager.AuthenticationCallback() {
            @Override
            public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
                isFinger = true;
                tv_show_title.setText("绑定成功");
                tv_show_message.setText("指纹绑定成功");
                PreferencesUtils.putBoolean(getApplicationContext(),"isFinger",isFinger);
                PreferencesUtils.putBoolean(getApplicationContext(), "isJump", false);
                btn_ok.setVisibility(View.VISIBLE);
                btn_cancle.setVisibility(View.GONE);
                new FingerLoginTask().execute();
                super.onAuthenticationSucceeded(result);
            }

            @Override
            public void onAuthenticationError(int errorCode, CharSequence errString) {
                LogUtils.error("FingerprintActivity---errorCode:"+errorCode+",---errString:"+errString);
                FingerprintActivity.this.finish();
                isFinger = false;
                super.onAuthenticationError(errorCode, errString);
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                isFinger = false;
                tv_show_title.setText("绑定失败");
                tv_show_message.setText("指纹绑定失败,请重试!");
                btn_ok.setVisibility(View.GONE);
                btn_cancle.setVisibility(View.VISIBLE);
            }
        }, null);
    }
    /**
     * 锁屏密码
     */
    private void showAuthenticationScreen() {
        Intent intent = mKeyManager.createConfirmDeviceCredentialIntent("LoginByFinger", "测试指纹识别");
        if (intent != null) {
            startActivityForResult(intent, REQUEST_CODE_CONFIRM_DEVICE_CREDENTIALS);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_CONFIRM_DEVICE_CREDENTIALS) {
            // Challenge completed, proceed with using cipher
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "识别成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "识别失败", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public boolean isFinger() {

        //android studio 上，没有这个会报错
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "没有指纹识别权限", Toast.LENGTH_SHORT).show();
            return false;
        }
        LogUtils.debug( "有指纹权限");
        //判断硬件是否支持指纹识别
        if (!manager.isHardwareDetected()) {
            Toast.makeText(this, "没有指纹识别模块", Toast.LENGTH_SHORT).show();
            return false;
        }
        LogUtils.debug( "有指纹模块");
        //判断 是否开启锁屏密码

        if (!mKeyManager.isKeyguardSecure()) {
            Toast.makeText(this, "没有开启锁屏密码", Toast.LENGTH_SHORT).show();
            return false;
        }
        LogUtils.debug( "已开启锁屏密码");
        //判断是否有指纹录入
        if (!manager.hasEnrolledFingerprints()) {
            Toast.makeText(this, "没有录入指纹", Toast.LENGTH_SHORT).show();
            return false;
        }
        LogUtils.debug( "已录入指纹");
        return true;
    }
    private class FingerLoginTask extends AsyncTask<Object,Void,Object> {
        @Override
        protected void onPreExecute() {
            // 登录是耗时过程，所以要显示一个dialog来提示下用户
            mDialog = new ProgressDialog(FingerprintActivity.this);
            mDialog.setMessage("绑定指纹成功,正在跳转,请稍候...");
            mDialog.show();
            super.onPreExecute();
        }

        @Override
        protected Object doInBackground(Object... params) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            mDialog.dismiss();
            MyApplication.startActivity(FingerprintActivity.this, MainActivity.class);

            finish();
        }
    }
}
