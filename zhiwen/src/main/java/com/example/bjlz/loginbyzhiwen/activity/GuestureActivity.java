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

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bjlz.loginbyzhiwen.R;
import com.example.bjlz.loginbyzhiwen.application.Address;
import com.example.bjlz.loginbyzhiwen.application.MyApplication;
import com.example.bjlz.loginbyzhiwen.tools.tools.PreferencesUtils;
import com.example.bjlz.loginbyzhiwen.tools.tools.ToastUtil;
import com.example.bjlz.loginbyzhiwen.tools.tools.UserUtils;
import com.example.bjlz.loginbyzhiwen.views.TitleBarView;
import com.example.bjlz.loginbyzhiwen.views.gesturelock.Drawl;
import com.example.bjlz.loginbyzhiwen.views.gesturelock.GuestureLockView;

/**
 * 项目名称：LoginByZhiWen
 * 类描述：
 * 创建人：slj
 * 创建时间：2016-9-8 13:22
 * 修改人：slj
 * 修改时间：2016-9-8 13:22
 * 修改备注：
 * 邮箱:slj@bjlingzhuo.com
 */
public class GuestureActivity extends BaseActivity {
    private TitleBarView title_bar;//标题
    private String userName;//xingming
    private FrameLayout mFrameLayout;//手势密码识别
    private GuestureLockView mGuestureLockView;//手势密码识别区
    private boolean isGuestureLock = false;//没有开启
    private String pwd;//密码
    private int inputTimes = 0;//输入错误次数
    // 弹出框
    private ProgressDialog mDialog;
    private TextView tv_reset_gesture, tv_forget_gesture, tv_set_guesture;//忘记/重置密码//显示
    private LinearLayout ll_gesture;//显示忘记/重置密码

    @Override
    public void onCreateBundle(Bundle savedInstanceState) {
        setContentView(R.layout.activity_guesture);
        MyApplication.getInstance().addActivity(this);
        getData();
        initView();
        initData();
        setOnClick();
    }

    @Override
    protected void onResume() {
        super.onResume();
        pwd = PreferencesUtils.getString(getApplicationContext(), "gusetureLock", null);
        isGuestureLock = PreferencesUtils.getBoolean(getApplicationContext(), "isGuestureLock", false);
    }

    @Override
    public void getData() {
        Bundle bundle = getIntent().getBundleExtra("bundle");
        if (bundle != null) {
            userName = bundle.getString("userName");
        }
        pwd = PreferencesUtils.getString(getApplicationContext(), "gusetureLock", null);
        isGuestureLock = PreferencesUtils.getBoolean(getApplicationContext(),"isGuestureLock", false);
    }

    @Override
    public void initView() {
        this.title_bar = (TitleBarView) findViewById(R.id.title_bar);
        mFrameLayout = (FrameLayout) findViewById(R.id.frame_layout);
        tv_forget_gesture = (TextView) findViewById(R.id.tv_forget_gesture);
        tv_reset_gesture = (TextView) findViewById(R.id.tv_reset_gesture);
        tv_set_guesture = (TextView) findViewById(R.id.tv_set_guesture);
        ll_gesture = (LinearLayout) findViewById(R.id.ll_gesture);
    }

    @Override
    public void initData() {
        if (isGuestureLock != true){
            this.title_bar.setTitleText(R.string.bind_gesture);
        }  else {
            this.title_bar.setTitleText(R.string.login);
            this.title_bar.getLeftBtn().setVisibility(View.GONE);
            tv_set_guesture.setText(R.string.please_input_gesture);
        }

        mGuestureLockView = new GuestureLockView(this, new Drawl.GestureCallBack() {
            @Override
            public void checkedSuccess(String password) {

                //首先判断一下用户是否已经设置密码
                if (TextUtils.isEmpty(pwd)) {
                    //如果为空，代码没有设置密码，需要设置新的密码；
                    // 设置新密码需要设置两遍，防止用户误操作；
                    // 第一遍设置的新密码保存在Variate类的一个变量中，这个变量默认为null
                    if (TextUtils.isEmpty(Address.PASSWORD)) {
                        //如果这个变量为null，第一次将密码保存在Variate.PASSWORD提示再次输入密码，
                        Address.PASSWORD = password;
                        ToastUtil.shortToast(getBaseContext(), "请再次输入密码");
                        // 并且刷新当前页面
                        refresh();
                    } else {
                        //如果Variate.PASSWORD不为空代表是第二次输入新密码，判断两次输入密码是否相同
                        if (password.equals(Address.PASSWORD)) {
                            //如果相同，将密码保存在当地sp中
                            PreferencesUtils.putString(getApplicationContext(),"gusetureLock", password);
                            //保存是否开启了手势密码
                            PreferencesUtils.putBoolean(getApplicationContext(), "isGuestureLock", true);
                            PreferencesUtils.putBoolean(getApplicationContext(), "isJump", false);
                            // 进入主页面，点击输入密码，输入设置的密码进入主页面
                            new LoginTask().execute();
//                            finish();
                        } else {
                            //如果两次输入密码不一样，将Variate.PASSWORD设为null,提示密码设置失败
                            Address.PASSWORD = null;
                            ToastUtil.shortToast(getBaseContext(), "密码设置失败");
                            // 跳回主页面需重新设置密码  显示重设或者忘记密码
//                            refresh();
                            ll_gesture.setVisibility(View.VISIBLE);
                        }
                    }

                } else {
                    //如果已经设置密码，判断输入密码和保存密码是否相同
                    if (pwd.equals(password)) {
                        //如果相同，密码正确，进入主页面
                        ToastUtil.shortToast(getBaseContext(), "欢迎回来");
                        Bundle bundle = new Bundle();
                        bundle.putString("userName", userName);
                        MyApplication.startActivity(GuestureActivity.this, MainActivity.class, bundle);
                        finish();
                    } else {
                        inputTimes++;
                        //如果不相同，密码错误，刷新当前activity，需重新输入密码
                        ToastUtil.shortToast(getBaseContext(), "手势密码错误");
                        refresh();
                        if (inputTimes == 5) {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(GuestureActivity.this);
                            dialog.setTitle("温馨提示");
                            dialog.setMessage("密码错误大于5次,请重置手势密码!");
                            dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    MyApplication.startActivity(GuestureActivity.this, ResetGuestureActivity.class);
                                    finish();
                                }
                            });
                            dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            dialog.create();
                            dialog.show();
                        }
                    }
                }
            }

            @Override
            public void checkedFail() {

            }
        });
        mGuestureLockView.setParentView(mFrameLayout);
    }

    @Override
    public void setOnClick() {
        tv_forget_gesture.setOnClickListener(this);
        tv_reset_gesture.setOnClickListener(this);
    }

    @Override
    public void WeightOnClick(View v) {
//        Bundle bundle = new Bundle();
        switch (v.getId()) {
            case R.id.tv_forget_gesture:
                MyApplication.startActivity(GuestureActivity.this, ResetGuestureActivity.class);
                finish();
                break;
            case R.id.tv_reset_gesture:
                MyApplication.startActivity(GuestureActivity.this, ResetGuestureActivity.class);
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //当前页面关闭时将Variate.PASSWORD设为null；防止用户第二次输入密码的时候退出当前activity
        Address.PASSWORD = null;
    }

    public void refresh() {
        getData();
        initView();
        initData();
        setOnClick();
    }

    private class LoginTask extends AsyncTask<Object, Void, Object> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // 登录是耗时过程，所以要显示一个dialog来提示下用户
            mDialog = new ProgressDialog(GuestureActivity.this);
            mDialog.setMessage("手势密码设置成功,即将进入主页面");
            mDialog.show();
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
            Bundle bundle = new Bundle();
            bundle.putString("userName", userName);
            MyApplication.startActivity(GuestureActivity.this, MainActivity.class, bundle);
        }
    }
}
