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

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import com.example.bjlz.loginbyzhiwen.R;
import com.example.bjlz.loginbyzhiwen.application.MyApplication;
import com.example.bjlz.loginbyzhiwen.tools.tools.PreferencesUtils;
import com.example.bjlz.loginbyzhiwen.tools.tools.ToastUtil;
import com.example.bjlz.loginbyzhiwen.tools.tools.UserUtils;
import com.example.bjlz.loginbyzhiwen.views.ClearEditText;
import com.example.bjlz.loginbyzhiwen.views.TitleBarView;

import java.util.Map;

/**
 * 项目名称：LoginByZhiWen
 * 类描述：
 * 创建人：slj
 * 创建时间：2016-9-8 9:54
 * 修改人：slj
 * 修改时间：2016-9-8 9:54
 * 修改备注：
 * 邮箱:slj@bjlingzhuo.com
 */
public class LoginActivity extends BaseActivity {
    private ClearEditText user_name, user_password;
    private Button btn_login,btn_registered;//登录/注册按钮
    private String name, word, userName, userPwd;//用户名 密码
    private TitleBarView title_bar;
    private Map<String, String> map = null;//存储用户名密码
    // 弹出框
    private ProgressDialog mDialog;
    @Override
    public void onCreateBundle(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        MyApplication.getInstance().addActivity(this);
        getData();
        initView();
        initData();
        setOnClick();
    }

    @Override
    public void getData() {
        map = UserUtils.readInfo(getApplicationContext());
        if (map != null) {
            name = map.get("name");
            word = map.get("word");
        }
    }

    @Override
    public void initView() {
        this.title_bar = (TitleBarView) findViewById(R.id.title_bar);
        user_name = (ClearEditText) findViewById(R.id.user_name);
        user_password = (ClearEditText) findViewById(R.id.user_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_registered = (Button) findViewById(R.id.btn_registered);
    }

    @Override
    public void initData() {
        this.title_bar.setTitleText(R.string.login);
        this.title_bar.getTitleBarBg().setBackgroundColor(getResources().getColor(R.color.transparent));
        if (name != null || word != null) {
            user_name.setText(name);
            user_password.setText(word);
        }
    }

    @Override
    public void setOnClick() {
        btn_registered.setOnClickListener(this);
        btn_login.setOnClickListener(this);
    }

    @Override
    public void WeightOnClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                userName = user_name.getText().toString().trim();
                userPwd = user_password.getText().toString().trim();
                if (userName == null || TextUtils.isEmpty(userName)) {
                    ToastUtil.shortToastByRes(getApplicationContext(),R.string.name_is_not_allow_null);
                } else if (userPwd == null || TextUtils.isEmpty(userPwd)) {
                    ToastUtil.shortToastByRes(LoginActivity.this, R.string.pwd_is_not_allow_null);
                } else {
                    LoginTask task = new LoginTask();
                    task.execute();
//                    // 登录是耗时过程，所以要显示一个dialog来提示下用户
//                    mDialog = new ProgressDialog(this);
//                    mDialog.setMessage("登录中，请稍后...");
//                    mDialog.show();
//                    UserUtils.saveInfo(getApplicationContext(), LoginActivity.this.userName, userPwd);
//                    Bundle bundle = new Bundle();
//                    bundle.putString("userName", userName);
//                    mDialog.dismiss();
//                    MyApplication.startActivity(LoginActivity.this, MainActivity.class, bundle);
//                    PreferencesUtils.putBoolean(getApplicationContext(), "isLogin", true);
//                    PreferencesUtils.putUserId(getApplicationContext(),userName);
                }
                break;
            case R.id.btn_registered:
                MyApplication.startActivity(LoginActivity.this, RegisterActivity.class);
                break;
        }
    }
    private class LoginTask extends AsyncTask<Object,Void,Object>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // 登录是耗时过程，所以要显示一个dialog来提示下用户
            mDialog = new ProgressDialog(LoginActivity.this);
            mDialog.setMessage("登录中，请稍后...");
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
            UserUtils.saveInfo(getApplicationContext(), LoginActivity.this.userName, userPwd);
            Bundle bundle = new Bundle();
            bundle.putString("userName", userName);
            mDialog.dismiss();
            MyApplication.startActivity(LoginActivity.this, MainActivity.class, bundle);
//            MyApplication.startActivity(LoginActivity.this, LoginSettingActivity.class, bundle);
            PreferencesUtils.putBoolean(getApplicationContext(),"isLogin", true);
            PreferencesUtils.putUserId(getApplicationContext(),userName);
        }
    }
}
