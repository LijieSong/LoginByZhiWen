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

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.bjlz.loginbyzhiwen.R;
import com.example.bjlz.loginbyzhiwen.application.MyApplication;
import com.example.bjlz.loginbyzhiwen.views.TitleBarView;

/**
 * 项目名称：LoginByZhiWen
 * 类描述：
 * 创建人：slj
 * 创建时间：2016-9-8 10:14
 * 修改人：slj
 * 修改时间：2016-9-8 10:14
 * 修改备注：
 * 邮箱:slj@bjlingzhuo.com
 */
public class WebViewActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{
    private Context mContext = null;//上下文对象
    private TitleBarView title_bar;//标题
    private WebView webView_userInfor;//显示患者详情
    private String url,name,title = null;
    private ProgressBar webView_ProgressBar;//进度条显示进度
    private SwipeRefreshLayout refreshLayout;//刷新的控制
    @Override
    public void onCreateBundle(Bundle savedInstanceState) {
        setContentView(R.layout.activity_webview);
        MyApplication.getInstance().addActivity(this);
        mContext = this;
        getData();
        initView();
        initData();
        setOnClick();
    }

    @Override
    public void getData() {
        Bundle bundle = getIntent().getBundleExtra("bundle");
        if (bundle !=null){
            name = bundle.getString("name");
            url = bundle.getString("url");
            title = bundle.getString("title");
        }
    }

    @Override
    public void initView() {
        this.title_bar = (TitleBarView) findViewById(R.id.title_bar);
        webView_ProgressBar = (ProgressBar) findViewById(R.id.webView_ProgressBar);
        this.refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
        webView_userInfor = (WebView) findViewById(R.id.webView_userInfor);
    }

    @Override
    public void initData() {
        if (name !=null){
            this.title_bar.setTitleText(name+"的"+title);
        }else{
            this.title_bar.setTitleText(title);
        }
        // 设置JS交互数据
        webView_userInfor.getSettings().setJavaScriptEnabled(true);
        webView_userInfor.getSettings().setSupportZoom(true);
        webView_userInfor.getSettings().setBuiltInZoomControls(true);
        webView_userInfor.getSettings().setDisplayZoomControls(false);
        webView_userInfor.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    webView_ProgressBar.setVisibility(View.GONE);
                } else {
                    if (View.INVISIBLE == webView_ProgressBar.getVisibility()) {
                        webView_ProgressBar.setVisibility(View.VISIBLE);
                    }
                    webView_ProgressBar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }

        });
        // 加载web资源
        webView_userInfor.loadUrl(url);
        this.refreshLayout.setRefreshing(false);
    }

    @Override
    public void setOnClick() {
        refreshLayout.setOnRefreshListener(this);//设置刷新监听
        // 设置webview的点击事件
        webView_userInfor.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    public void WeightOnClick(View v) {

    }

    @Override
    public void onRefresh() {
        //下拉刷新
        initData();
    }
}
