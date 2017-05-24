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

package com.example.bjlz.loginbyzhiwen.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 项目名称：LoginByZhiWen
 * 类描述：ViewPagerSimpaleFragment 通用fragment
 * 创建人：slj
 * 创建时间：2016-9-18 15:47
 * 修改人：slj
 * 修改时间：2016-9-18 15:47
 * 修改备注：
 * 邮箱:slj@bjlingzhuo.com
 */
public class ViewPagerSimpaleFragment extends Fragment {
    private String mTitle;//标题
    private static final String BUNDLE_TITLE = "title";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle !=null) mTitle =  bundle.getString(BUNDLE_TITLE);
        TextView textView = new TextView(getActivity());
        textView.setGravity(Gravity.CENTER);
        textView.setText(mTitle);

        return textView;
    }

    public static ViewPagerSimpaleFragment newInstance(String title){
       Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_TITLE,title);
        ViewPagerSimpaleFragment simpaleFragment = new ViewPagerSimpaleFragment();
        simpaleFragment.setArguments(bundle);
        return  simpaleFragment;
    }
    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
    }
}
