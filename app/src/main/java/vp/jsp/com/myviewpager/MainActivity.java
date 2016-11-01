package vp.jsp.com.myviewpager;

import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.lang.reflect.Field;
import java.util.ArrayList;

import vp.jsp.com.myviewpager.view.FixedSpeedScroller;
import vp.jsp.com.myviewpager.view.ViewPagerAnim;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout tab;
    private ArrayList<View> views;
    private ViewPager vp;
    private FixedSpeedScroller scroller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vp = (ViewPager) findViewById(R.id.vp);

        tab = (LinearLayout) findViewById(R.id.tab);
        initData();

        vp.setAdapter(new MyPagerAdapter());
        vp.setPageTransformer(true, new ViewPagerAnim());
        //第二种方式取消动画
//        setViewPagerScrollSpeed(vp);

    }

    private void initData(){
        views = new ArrayList<>();
        for(int i = 0; i<tab.getChildCount();i++){
            //监听底部按钮
            tab.getChildAt(i).setOnClickListener(this);

            ImageView imageView = new ImageView(MainActivity.this);
            imageView.setBackgroundResource(R.mipmap.ic_launcher);
            RelativeLayout layout = new RelativeLayout(MainActivity.this);
            layout.setBackgroundColor(Color.rgb((i+1)*50,(i+1)*50,(i+1)*50));
            layout.addView(imageView);
            RelativeLayout.LayoutParams ps = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
            ps.addRule(RelativeLayout.CENTER_IN_PARENT);//addRule参数对应RelativeLayout XML布局的属性
            ps.height = 100*(i+1);
            ps.width = 100*(i+1);
            imageView.setLayoutParams(ps);
            views.add(layout);
        }

    }

    /**
     * 设置ViewPager的滑动速度
    *
     * */
    private void setViewPagerScrollSpeed(ViewPager vp){
        try {
            Field mScroller = null;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            scroller = new FixedSpeedScroller( vp.getContext( ) );

            mScroller.set( vp, scroller);
        }catch(NoSuchFieldException e){

        }catch (IllegalArgumentException e){

        }catch (IllegalAccessException e){

        }
    }

    @Override
    public void onClick(View v) {
        for (int i = 0;i< tab.getChildCount();i++){
            if(v==tab.getChildAt(i)){
                // 第一种方式 false 取消 跳转时的动画
                vp.setCurrentItem(i,false);
                break;
            }
        }
    }

    class MyPagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return tab.getChildCount();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(views.get(position));
            return views.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(views.get(position));
        }
    }
}
