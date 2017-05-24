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

package com.example.bjlz.loginbyzhiwen.views.ViewPagerTab;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bjlz.loginbyzhiwen.R;

import java.util.List;

/**
 * 项目名称：LoginByZhiWen
 * 类描述：ViewPagerIndeCator 自定义ViewPagerIndeCator
 * 创建人：slj
 * 创建时间：2016-9-18 15:29
 * 修改人：slj
 * 修改时间：2016-9-18 15:29
 * 修改备注：
 * 邮箱:slj@bjlingzhuo.com
 */
public class ViewPagerIndeCator extends LinearLayout {
    private Paint mPaint;//画笔
    private Path mPath;//路径
    //三角形的宽高
    private int mTrangleWidth;//宽
    private int mTrangleHeight;//高
    //三角形比例
    private static final float TRANGLE_WIDTH = 1 / 6f;
    //    三角形的位置
    private int initTrangleLocationX;
    //    三角形移动的位置
    private int scorllTrangleLocationX;
    //可显示tab数量
    private int mVisibleTab;
    //默认显示数量
    private static final int VISIBLE_TAB_DEFULT = 4;
    //设置tab的titles
    private List<String> mTitles;
    //设置tab的字体颜色 默认为白色
    private int DEFULT_TEXT_COLOR = 0x55FFFFFF;
    //设置tab的字体颜色 默认为白色
    private int DEFULT_TEXT_HIGHT_COLOR = 0xFFFFFFFF;
    //默认画笔颜色为白色
    private String DEFULT_PAINT_COLOR = "#FFFFFFFF";
    //默认的viewpager
    private ViewPager mViewPager;
    //接口
    private OnPagerChangedListener mOnPagerChangedListener;
    //对外提供的接口
    public interface OnPagerChangedListener{
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) ;
        public void onPageSelected(int position) ;
        public void onPageScrollStateChanged(int state);
    }

    /**
     * 设置页面切换监听
     * @param mOnPagerChangedListener
     */
    public void setOnPagerChangedListener(OnPagerChangedListener mOnPagerChangedListener){
        if (mOnPagerChangedListener !=null)
            this.mOnPagerChangedListener = mOnPagerChangedListener;
    }


    public ViewPagerIndeCator(Context context) {
        this(context, null);
    }

    public ViewPagerIndeCator(Context context, AttributeSet attrs) {
        super(context, attrs);
        //获取自定义数据
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ViewPagerIndeCator);
        //获取可见tab数量
        mVisibleTab = ta.getInt(R.styleable.ViewPagerIndeCator_visible_tab, VISIBLE_TAB_DEFULT);
        if (mVisibleTab < 0) mVisibleTab = VISIBLE_TAB_DEFULT;//如果为0 就设置为默认
        ta.recycle();//回收
        mPaint = new Paint();//初始化画笔
        mPaint.setAntiAlias(true);//设置抗锯齿
        mPaint.setColor(Color.parseColor(DEFULT_PAINT_COLOR));//设置颜色
        mPaint.setStyle(Paint.Style.FILL);//设置样式
        mPaint.setPathEffect(new CornerPathEffect(3));//设置尖锐程度
    }

    /**
     * 设置画笔颜色  默认为白色
     * @param color 格式如:String color = "#FFFFFFFF";
     */
    public void setPaintColor(String color){
        if (color !=null)
            this.DEFULT_PAINT_COLOR = color;
    }

    /**
     * 设置可见的tab数量
     * @param count 在设置title之前调用
     */
    public void setVisibleTabCounts(int count){
        if (count !=0)
            this.mVisibleTab = count;
    }
    /**
     * 结束初始化view时
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int mCount = getChildCount();
        if (mCount == 0) return;
        for (int i = 0; i < mCount; i++) {
            View view = getChildAt(i);//获取子view
            LinearLayout.LayoutParams lp = (LayoutParams) view.getLayoutParams();//获取控件布局参数
            lp.weight = 0;//强制设置为0
            lp.width = getScreenWidth() / mVisibleTab;//设置每个tab宽度
            view.setLayoutParams(lp);//设置布局参数
        }

    }

    /**
     * 获取屏幕宽度
     *
     * @return
     */
    private int getScreenWidth() {
        //获取窗口管理器
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        //获取屏幕管理器
        DisplayMetrics metrics = new DisplayMetrics();
        //获取显示区域
        wm.getDefaultDisplay().getMetrics(metrics);
        //返回宽度
        return metrics.widthPixels;
    }

    /**
     * 绘制
     *
     * @param canvas
     */
    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.save();//保存
        canvas.translate(initTrangleLocationX + scorllTrangleLocationX, getHeight() + 2);//移动距离
        canvas.drawPath(mPath, mPaint);//绘制画笔跟路径
        canvas.restore();//重绘
        super.dispatchDraw(canvas);
    }

    /**
     * 当尺寸变化时
     *
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTrangleWidth = (int) (w / mVisibleTab * TRANGLE_WIDTH);//设置三角形宽度
        initTrangleLocationX = w / mVisibleTab / 2 - mTrangleWidth / 2;//设置三角形初始化值
        initTriangle();//初始化三角形
    }

    /**
     * 初始化三角形
     */
    private void initTriangle() {
        mTrangleHeight = mTrangleWidth / 2;
        mPath = new Path();
        mPath.moveTo(0, 0);
        mPath.lineTo(mTrangleWidth, 0);
        mPath.lineTo(mTrangleWidth / 2, -mTrangleHeight);
        mPath.close();
    }

    /**
     * 三角形随着手指移动
     *
     * @param position
     * @param offset
     */
    public void scroll(int position, float offset) {
        int tabWidth = getWidth() / mVisibleTab;//获取
        scorllTrangleLocationX = (int) (tabWidth * (position + offset));//获取移动位置
        //容器移动  在tab处于最后一个时  让tab移动并
        if (position >= (mVisibleTab - 2) && offset > 0 && getChildCount() > mVisibleTab) {
            if (mVisibleTab != 1) {
                this.scrollTo((position - (mVisibleTab - 2)) * tabWidth + (int) (tabWidth * offset), 0);
            } else {
                this.scrollTo(position * tabWidth + (int) (tabWidth * offset), 0);
            }
        }
        invalidate();//重新绘制
    }

    public void setTabTitles(List<String> titles) {
        if (titles != null && titles.size() > 0)
            this.removeAllViews();
        this.mTitles = titles;
        for (String title : mTitles) {
            addView(getView(title));
        }
    }

    /**
     * 根据title获取子view
     *
     * @param title
     * @return
     */
    private View getView(String title) {
        TextView tv = new TextView(getContext());//创建textview
        //获取布局参数
        LinearLayout.LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        lp.width = getScreenWidth() / mVisibleTab;//设置布局参数的宽
        tv.setText(title);//设置显示的数据
        tv.setGravity(Gravity.CENTER);//设置居中
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);//设置字体大小
        tv.setTextColor(DEFULT_TEXT_COLOR);//设置字体颜色
        tv.setLayoutParams(lp);//设置布局参数
        return tv;
    }

    /**
     * 设置tab字体颜色
     * @param color 如: int color = 0xFFFFFFFF;
     */
    public void setTextColor(int color){
        if (color !=0)
            this.DEFULT_TEXT_COLOR = color;
    }

    /**
     * 设置关联的viewPager
     * @param pager
     * @param position
     */
    public void setViewPager(ViewPager pager,int position){
        mViewPager = pager;
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (mOnPagerChangedListener!=null)
                    mOnPagerChangedListener.onPageScrolled(position,positionOffset,positionOffsetPixels);

                scroll(position,positionOffset);//滑动到某一位置
                setHightTextColor(position);//设置字体高亮
            }

            @Override
            public void onPageSelected(int position) {
                if (mOnPagerChangedListener !=null)
                    mOnPagerChangedListener.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (mOnPagerChangedListener !=null)
                    mOnPagerChangedListener.onPageScrollStateChanged(state);
            }
        });
        mViewPager.setCurrentItem(position);
        setHightTextColor(position);
    }
    private void setNomalTextColor(){
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view instanceof TextView)
                ((TextView) view).setTextColor(DEFULT_TEXT_COLOR);
        }
    }
    /**
     * 设置选中tab文本高亮
     * @param position
     */
    private void setHightTextColor(int position){
        setNomalTextColor();
        View view = getChildAt(position);
        if (view instanceof TextView)
            ((TextView) view).setTextColor(DEFULT_TEXT_HIGHT_COLOR);
    }
}
