package com.hasee.pangci.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.hasee.pangci.R;
import com.hasee.pangci.adapter.MyFragmentPagerAdapter;
import com.hasee.pangci.fragment.MemberFragment;
import com.hasee.pangci.fragment.MovieFragment;
import com.hasee.pangci.fragment.RecommendFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private TabLayout mTabLayout;
    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    private FloatingActionButton mFloatingActionButton;
    private MemberFragment memberFragment = new MemberFragment();
    private RecommendFragment recommendFragment = new RecommendFragment();
    private MovieFragment movieFragment = new MovieFragment();
    private String[] tabTitles = {"推荐", "影视", "VIP专区"};
    private Fragment[] fragments = {recommendFragment, movieFragment, memberFragment};
    private ArrayList<Fragment> fragmentArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initEvent();
    }

    public void initView() {
        mToolbar = (Toolbar) findViewById(R.id.main_tool_bar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        mTabLayout = (TabLayout) findViewById(R.id.main_tab_layout);
//        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.main_fab);
        mNavigationView = (NavigationView) findViewById(R.id.main_navigation_view);
        mViewPager = (ViewPager) findViewById(R.id.main_view_pager);
        mNavigationView.setNavigationItemSelectedListener(this);
    }

    public void initData() {
        mToolbar.setTitle("胖次");
        mToolbar.setTitleTextColor(Color.WHITE);
        for (int i = 0; i < tabTitles.length; i++) {
            fragmentArrayList.add(fragments[i]);
        }
        //关联彼此
        MyFragmentPagerAdapter myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentArrayList);
        mViewPager.setAdapter(myFragmentPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        //初始化数据
        for (int i = 0; i < tabTitles.length; i++) {
            mTabLayout.getTabAt(i).setText(tabTitles[i]);
        }
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(Gravity.LEFT);
            }
        });
    }

    public void initEvent() {

    }

    private long tempTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            if(System.currentTimeMillis()-tempTime>2000){
                Toast.makeText(this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
                tempTime=System.currentTimeMillis();
                return false;
            }else {
                finish();
                System.exit(0);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_menu_item_about:
                Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                break;

            case R.id.navigation_menu_item_cache:
                Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                break;

            case R.id.navigation_menu_item_exit:
                Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                break;

            case R.id.navigation_menu_item_flock:
                Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                break;

            case R.id.navigation_menu_item_version:
                Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                break;

            case R.id.navigation_menu_item_member:
                Intent intent = new Intent(MainActivity.this,MemberActivity.class);
                startActivity(intent);
                break;
        }
        return false;
    }
}
