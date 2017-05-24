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

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.bjlz.loginbyzhiwen.R;
import com.example.bjlz.loginbyzhiwen.application.MyApplication;
import com.example.bjlz.loginbyzhiwen.tools.tools.PreferencesUtils;
import com.example.bjlz.loginbyzhiwen.tools.tools.ToastUtil;
import com.example.bjlz.loginbyzhiwen.tools.tools.UserUtils;
import com.example.bjlz.loginbyzhiwen.views.ClearEditText;
import com.example.bjlz.loginbyzhiwen.views.TitleBarView;
import com.example.bjlz.loginbyzhiwen.views.gesturelock.GuestureLockView;

import java.util.Map;

/**
 * 项目名称：LoginByZhiWen
 * 类描述：
 * 创建人：slj
 * 创建时间：2016-9-8 15:22
 * 修改人：slj
 * 修改时间：2016-9-8 15:22
 * 修改备注：
 * 邮箱:slj@bjlingzhuo.com
 */
public class ResetGuestureActivity extends BaseActivity {
    private TitleBarView title_bar;//标题
    private String pwd;
    private ClearEditText cdt_input_pwd;//输入密码
    private Button btn_ok;//确认按钮
    private Map<String, String> map = null;//存储用户名密码
    private String name;//姓名
    @Override
    public void onCreateBundle(Bundle savedInstanceState) {
        setContentView(R.layout.activity_reset_guesture);
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
            pwd = map.get("word");
        }
    }

    @Override
    public void initView() {
        this.title_bar = (TitleBarView) findViewById(R.id.title_bar);
        cdt_input_pwd = (ClearEditText) findViewById(R.id.cdt_input_pwd);
        btn_ok = (Button) findViewById(R.id.btn_ok);
    }

    @Override
    public void initData() {
        this.title_bar.setTitleText(R.string.check_login_pwd);
    }

    @Override
    public void setOnClick() {
        btn_ok.setOnClickListener(this);
    }

    @Override
    public void WeightOnClick(View v) {
    switch(v.getId()){
        case R.id.btn_ok:
            String inputPwd = cdt_input_pwd.getText().toString().trim();
            if (TextUtils.isEmpty(inputPwd)|| inputPwd.equals("") || inputPwd==null)
                ToastUtil.shortToast(getBaseContext(),"登录密码不能为空,请输入!");
            else
                if (pwd.equals(inputPwd) || inputPwd==pwd) {
                    ToastUtil.shortToast(getBaseContext(),"密码重置成功，请设置新手势密码");
                    PreferencesUtils.putString(getApplicationContext(), "gusetureLock", "");
                    MyApplication.startActivity(ResetGuestureActivity.this, GuestureActivity.class);
                    finish();
                }else
                    ToastUtil.shortToast(getBaseContext(),"登录密码验证不通过,无法重置手势密码");
                    finish();
            break;
        }
    }
}
