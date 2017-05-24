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
import android.app.KeyguardManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v4.os.CancellationSignal;
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
public class FingerPrintLoginActivity extends BaseActivity {
    private TitleBarView title_bar;//标题
    private Button btn_start_finger,btn_ok,btn_cancle;//开始绑定指纹按钮/确定/取消
    private TextView tv_show_message,tv_show_title;//标题,内容
    private LinearLayout ll_finger;//标题
    KeyguardManager mKeyManager;
    FingerprintManager fingerprintManager;
    private FingerprintManagerCompat manager;
    private boolean isHardwareDetected ;//判断是否存在指纹解锁
    private final static int REQUEST_CODE_CONFIRM_DEVICE_CREDENTIALS = 0;
    private boolean isFinger = false;
    // 弹出框
    private ProgressDialog mDialog;
    private CancellationSignal mCancellationSignal = new CancellationSignal();

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LogUtils.debug("handleMessage: 重启指纹模块");
            manager.authenticate(null, 0, null, new MyCallBack(), handler);
        }
    };

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
        mKeyManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        fingerprintManager = (FingerprintManager) getSystemService(Context.FINGERPRINT_SERVICE);
        manager = FingerprintManagerCompat.from(getApplicationContext());
        isHardwareDetected = PreferencesUtils.getBoolean(getApplicationContext(),MyApplication.HAS_FINGERPRINT_API);
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
        if (isFinger()){
            startListening(null);
        }
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
                    if (Build.VERSION.SDK_INT >Build.VERSION_CODES.M){
                        if (mCancellationSignal.isCanceled()) {
                            mCancellationSignal = new CancellationSignal();
                        }
                        startListening(mCancellationSignal);
                    }else{
                        startListening(null);
                    }

            break;
            case R.id.btn_cancle:
                mCancellationSignal.cancel();
                PreferencesUtils.putBoolean(getApplicationContext(),"isFinger",isFinger);
                FingerPrintLoginActivity.this.finish();
                break;
            case R.id.btn_ok:
//                MyApplication.startActivity(FingerprintActivity.this,MainActivity.class);
                PreferencesUtils.putBoolean(getApplicationContext(),"isFinger",isFinger);
                PreferencesUtils.putBoolean(getApplicationContext(), "isJump", false);
                FingerPrintLoginActivity.this.finish();
                break;
        }
    }
    public void startListening(final CancellationSignal cancellationSignal) {
        //android studio 上，没有这个会报错
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "没有指纹识别权限", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.USE_FINGERPRINT},100);
            return;
        }
        /**
         * 开始验证，什么时候停止由系统来确定，如果验证成功，那么系统会关系sensor，如果失败，则允许
         * 多次尝试，如果依旧失败，则会拒绝一段时间，然后关闭sensor，过一段时候之后再重新允许尝试
         *
         * 第四个参数为重点，需要传入一个FingerprintManagerCompat.AuthenticationCallback的子类
         * 并重写一些方法，不同的情况回调不同的函数
         */
        if (Build.VERSION.SDK_INT >Build.VERSION_CODES.M){
            fingerprintManager.authenticate(null, null, 0, new FingerprintManager.AuthenticationCallback() {
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
                    FingerPrintLoginActivity.this.finish();
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
        }else{
            manager.authenticate(null, 0, cancellationSignal, new MyCallBack(), null);
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
        if (Build.VERSION.SDK_INT >Build.VERSION_CODES.M){
        //判断硬件是否支持指纹识别
        if (!fingerprintManager.isHardwareDetected()) {
            Toast.makeText(this, "没有指纹识别模块", Toast.LENGTH_SHORT).show();
            return false;
        }
        }else{
            //判断硬件是否支持指纹识别
            if (!isHardwareDetected) {
                Toast.makeText(this, "没有指纹识别模块", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
//        LogUtils.debug( "有指纹模块");
//        //判断 是否开启锁屏密码
//
//        if (!mKeyManager.isKeyguardSecure()) {
//            Toast.makeText(this, "没有开启锁屏密码", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//        LogUtils.debug( "已开启锁屏密码");
//        //判断是否有指纹录入
//        if (!manager.hasEnrolledFingerprints()) {
//            Toast.makeText(this, "没有录入指纹", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//        LogUtils.debug( "已录入指纹");
        return true;
    }
    private class FingerLoginTask extends AsyncTask<Object,Void,Object> {
        @Override
        protected void onPreExecute() {
            // 登录是耗时过程，所以要显示一个dialog来提示下用户
            mDialog = new ProgressDialog(FingerPrintLoginActivity.this);
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
            MyApplication.startActivity(FingerPrintLoginActivity.this, MainActivity.class);

            finish();
        }
    }
    public class MyCallBack extends FingerprintManagerCompat.AuthenticationCallback {
        private static final String TAG = "MyCallBack";

        // 当出现错误的时候回调此函数，比如多次尝试都失败了的时候，errString是错误信息
        @Override
        public void onAuthenticationError(int errMsgId, CharSequence errString) {
            //但多次指纹密码验证错误后，进入此方法；并且，不能短时间内调用指纹验证
            LogUtils.error("FingerPrintLoginActivity---errorCode:"+errMsgId+",---errString:"+errString);
            handler.sendMessageDelayed(new Message(), 1000 * 30);
            FingerPrintLoginActivity.this.finish();
            isFinger = false;
        }

        // 当指纹验证失败的时候会回调此函数，失败之后允许多次尝试，失败次数过多会停止响应一段时间然后再停止sensor的工作
        @Override
        public void onAuthenticationFailed() {
            isFinger = false;
            tv_show_title.setText("绑定失败");
            tv_show_message.setText("指纹绑定失败,请重试!");
            btn_ok.setVisibility(View.GONE);
            btn_cancle.setVisibility(View.VISIBLE);
            Toast.makeText(FingerPrintLoginActivity.this, "指纹识别失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
            LogUtils.error("onAuthenticationHelp:"+helpString);
        }

        // 当验证的指纹成功时会回调此函数，然后不再监听指纹sensor
        @Override
        public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {
            isFinger = true;
            tv_show_title.setText("绑定成功");
            tv_show_message.setText("指纹绑定成功");
            PreferencesUtils.putBoolean(getApplicationContext(),"isFinger",isFinger);
            PreferencesUtils.putBoolean(getApplicationContext(), "isJump", false);
            btn_ok.setVisibility(View.VISIBLE);
            btn_cancle.setVisibility(View.GONE);
            new FingerLoginTask().execute();
            FingerprintManagerCompat.CryptoObject object = result.getCryptoObject();
            if (object !=null){
                Signature signature = object.getSignature();
                Cipher cipher = object.getCipher();
                Mac mac = object.getMac();
                LogUtils.error("signature:"+signature +",----cipher:"+cipher+",----mac:"+mac);
            }
            Toast.makeText(FingerPrintLoginActivity.this, "指纹识别成功", Toast.LENGTH_SHORT).show();
        }
    }

}
