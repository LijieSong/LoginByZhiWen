package com.example.bjlz.loginbyzhiwen.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.bjlz.loginbyzhiwen.R;
import com.example.bjlz.loginbyzhiwen.application.Address;
import com.example.bjlz.loginbyzhiwen.application.MyApplication;
import com.example.bjlz.loginbyzhiwen.tools.tools.StringUtil;
import com.example.bjlz.loginbyzhiwen.tools.tools.ToastUtil;
import com.example.bjlz.loginbyzhiwen.views.ClearEditText;
import com.example.bjlz.loginbyzhiwen.views.TimeButton;
import com.example.bjlz.loginbyzhiwen.views.TitleBarView;

/**
 * 项目名称：PumpDown
 * 类描述：RegisterActivity 注册页面
 * 创建人：slj
 * 创建时间：2016-6-1 11:44
 * 修改人：slj
 * 修改时间：2016-6-1 11:44
 * 修改备注：
 * 邮箱:slj@bjlingzhuo.com
 */
public class RegisterActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener {
    private TitleBarView title_bar;//标题
    private Button btn_registered;//按钮
    private TimeButton get_security_code;//timebutton
    private CheckBox cb_xie_yi;//选择协议
    private TextView tv_xie_yi;//协议
    private LinearLayout ll_edit_pwd; //显示密码
    private String mobile = null;
    // 弹出框
    private ProgressDialog mDialog;
    private ClearEditText cdt_mobile,cet_security_code,cdt_pwd,cdt_me_pwd;//手机号/验证码/密码/确认密码
    @Override
    public void onCreateBundle(Bundle savedInstanceState) {
        setContentView(R.layout.activity_register);
        MyApplication.getInstance().addActivity(this);
        getData();
        initView();
        get_security_code.onCreate(savedInstanceState);
        get_security_code.setTextAfter("秒后重新获取").setTextBefore("点击获取验证码").setLenght(60 * 1000);
        initData();
        setOnClick();
    }

    @Override
    public void initView() {
        this.title_bar = (TitleBarView) findViewById(R.id.title_bar);
        cdt_mobile = (ClearEditText) findViewById(R.id.cdt_mobile);
        cet_security_code = (ClearEditText) findViewById(R.id.cet_security_code);
        cdt_pwd = (ClearEditText) findViewById(R.id.cdt_pwd);
        cdt_me_pwd = (ClearEditText) findViewById(R.id.cdt_me_pwd);
        btn_registered = (Button) findViewById(R.id.btn_registered);
        get_security_code = (TimeButton) findViewById(R.id.get_security_code);
        cb_xie_yi = (CheckBox) findViewById(R.id.cb_xie_yi);
        tv_xie_yi = (TextView) findViewById(R.id.tv_xie_yi);
        ll_edit_pwd = (LinearLayout) findViewById(R.id.ll_edit_pwd);

    }

    @Override
    public void initData() {
        this.title_bar.setTitleText(R.string.registered);
    }

    @Override
    public void getData() {

    }

    @Override
    public void setOnClick() {
        btn_registered.setOnClickListener(this);
        get_security_code.setOnClickListener(this);
        cb_xie_yi.setOnCheckedChangeListener(this);
        cb_xie_yi.setChecked(false);
        tv_xie_yi.setOnClickListener(this);
    }

    @Override
    public void WeightOnClick(View v) {
        switch(v.getId()){
            case R.id.btn_registered:
                final String mobile = cdt_mobile.getText().toString().trim();
//                final String securityCode = cet_security_code.getText().toString().trim();
                String pwd = cdt_pwd.getText().toString().trim();
                final String mepwd = cdt_me_pwd.getText().toString().trim();
                if (TextUtils.isEmpty(mobile) || mobile == null ){
                    ToastUtil.shortToastByRes(getApplicationContext(),R.string.mobile_is_not_null);
                    return;
                }
//                if (TextUtils.isEmpty(securityCode) || securityCode ==null){
//                    ToastUtil.shortToastByRes(getApplicationContext(),R.string.score_is_not_null);
//                    return;
//                }
                if (TextUtils.isEmpty(pwd) || pwd ==null){
                    ToastUtil.shortToastByRes(getApplicationContext(),R.string.pwd_is_not_allow_null);
                    return;
                }
                if (TextUtils.isEmpty(mepwd) || mepwd ==null){
                    ToastUtil.shortToastByRes(getApplicationContext(),R.string.me_pwd_is_not_allow_null);
                    return;
                }
                if (!pwd.equals(mepwd)){
                    ToastUtil.shortToastByRes(getApplicationContext(),R.string.pwd_not_equales_mepwd);
                    return;
                }
//                if (!StringUtil.isValidPassword(pwd) || !StringUtil.isValidPassword(mepwd)){
//                    ToastUtil.shortToastByRes(getApplicationContext(),R.string.pwd_is_not_valid);
//                    return;
//                }
                if (!cb_xie_yi.isChecked()){
                    ToastUtil.shortToastByRes(getApplicationContext(),R.string.tv_xieyi_toast);
                    return;
                } else{
//                    RegisterTask task = new RegisterTask();
//                    task.execute();


//                    // 注册是耗时过程，所以要显示一个dialog来提示下用户
//                    mDialog = new ProgressDialog(this);
//                    mDialog.setMessage("注册中，请稍后...");
//                    mDialog.show();
                    RegisterTask task = new RegisterTask();
                    task.execute();
//                    Bundle bundle = new Bundle();
//                    bundle.putString("userName","lxh");
//                    PreferencesUtils.putBoolean(getApplicationContext(),"isLogin",true);
//                    PreferencesUtils.putBoolean(getApplicationContext(),"isFirst",true);
//                    MyApplication.startActivity(RegisterActivity.this,MainActivity.class,bundle);
//                    finish();
                }
            break;
            case R.id.get_security_code:
                String mobile1 = cdt_mobile.getText().toString().trim();
                ll_edit_pwd.setVisibility(View.VISIBLE);
                if (TextUtils.isEmpty(mobile1) || mobile1 ==null){
                    ToastUtil.shortToastByRes(getApplicationContext(),R.string.mobile_is_not_null);
                    return;
                }
                if(!StringUtil.isMobileNO(mobile1)){
                    ToastUtil.shortToastByRes(getApplicationContext(),R.string.name_is_not_valid);
                    return;
                } else{
//                    GetScoreTask task1 = new GetScoreTask();
//                    task1.execute();
                    ToastUtil.shortToast(getApplicationContext(),"发送成功");
                }
                break;
            case R.id.tv_xie_yi:
                Bundle bundle = new Bundle();
                bundle.putString("title","用户协议");
                bundle.putString("url", Address.TEXT_URL);
                MyApplication.startActivity(RegisterActivity.this,WebViewActivity.class,bundle);
                break;
        }
    }
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (cb_xie_yi.isChecked()) {
            btn_registered.setEnabled(true);
        } else if (!cb_xie_yi.isChecked()) {
            btn_registered.setEnabled(false);
            ToastUtil.shortToastByRes(getApplicationContext(),R.string.tv_xieyi_toast);
        }
    }

    private class RegisterTask extends AsyncTask<Object,Void,Object> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mDialog = new ProgressDialog(RegisterActivity.this);
            mDialog.setMessage("注册中，请稍后...");
            mDialog.show();
        }

        @Override
        protected Object doInBackground(Object... params) {
//            mDialog.dismiss();
//            finish();
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
            MyApplication.startActivity(RegisterActivity.this,LoginActivity.class);
            finish();
        }
    }
    private class GetScoreTask extends AsyncTask<Object,Void,Object> {

        @Override
        protected Object doInBackground(Object... params) {
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
        }
    }
}
