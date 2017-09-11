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
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hasee.pangci.R;
import com.hasee.pangci.Utils.DateFormat;
import com.hasee.pangci.adapter.MyFragmentPagerAdapter;
import com.hasee.pangci.bean.User;
import com.hasee.pangci.fragment.MemberFragment;
import com.hasee.pangci.fragment.MovieFragment;
import com.hasee.pangci.fragment.RecommendFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    @BindView(R.id.main_tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.main_tool_bar)
    Toolbar mToolbar;
    @BindView(R.id.main_view_pager)
    ViewPager mViewPager;
    @BindView(R.id.main_navigation_view)
    NavigationView mNavigationView;
    @BindView(R.id.main_drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.main_fab)
    FloatingActionButton mFloatingActionButton;

    private MemberFragment memberFragment = new MemberFragment();
    private RecommendFragment recommendFragment = new RecommendFragment();
    private MovieFragment movieFragment = new MovieFragment();
    private String[] tabTitles = {"推荐", "影视", "VIP专区"};
    private Fragment[] fragments = {recommendFragment, movieFragment, memberFragment};
    private ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    private View mNavigationMemberInfoLl;
    private TextView mNavigationAccountTv;
    private TextView mNavigationMemberLevelTv;
    private TextView mNavigationResidueTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initView();
        initData();
        initEvent();
    }

    public void initView() {
        mNavigationView.setNavigationItemSelectedListener(this);
        mFloatingActionButton.setOnClickListener(this);
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

        //初始化导航栏的控件
        View headerView = mNavigationView.getHeaderView(0);
        mNavigationMemberInfoLl = headerView.findViewById(R.id.navigation_member_info_ll);//会员信息布局
        mNavigationAccountTv = (TextView) headerView.findViewById(R.id.navigation_account_tv);
        mNavigationAccountTv.setOnClickListener(this);
        mNavigationMemberLevelTv = (TextView) headerView.findViewById(R.id.navigation_member_level_tv);//会员等级
        //会员剩余天数
        mNavigationResidueTv = (TextView) headerView.findViewById(R.id.navigation_residue_tv);
    }

    public void initEvent() {

    }

    private long tempTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - tempTime > 2000) {
                Toast.makeText(this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
                tempTime = System.currentTimeMillis();
                return false;
            } else {
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
                item.setChecked(true);//高亮
                break;

            case R.id.navigation_menu_item_cache:
                Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                item.setChecked(true);//高亮
                break;

            case R.id.navigation_menu_item_exit:
                Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                item.setChecked(true);//高亮
                break;

            case R.id.navigation_menu_item_flock:
                Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                item.setChecked(true);//高亮
                break;

            case R.id.navigation_menu_item_version:
                Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                item.setChecked(true);//高亮
                break;

            case R.id.navigation_menu_item_member:
                item.setChecked(true);//高亮
                Intent intent = new Intent(MainActivity.this, MemberActivity.class);
                startActivity(intent);
                break;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.navigation_account_tv:
                Log.i("TAG", "onClick: "+mNavigationAccountTv.getText().toString());
                if (mNavigationAccountTv.getText().toString().equals("点击登录")) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    mDrawerLayout.closeDrawers();
                } else {
                    Toast.makeText(this, "您已经登录", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.main_fab:
                mDrawerLayout.openDrawer(Gravity.START);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleEventBus(User user) {
        Log.i("TAG", "handleEventBus: " + user.getUserAccount());
        mDrawerLayout.openDrawer(Gravity.START);
        mNavigationMemberInfoLl.setVisibility(View.VISIBLE);
        mNavigationAccountTv.setText(user.getUserAccount());
        mNavigationMemberLevelTv.setText("会员等级:" + user.getMemberLevel());
        int residueDays = DateFormat.differentDaysByMillisecond(user.getMemberStartDate().getDate(), user.getMemberEndDate().getDate());
        mNavigationResidueTv.setText(",会员剩余天数:" + residueDays + "天");
        Log.i("TAG", mNavigationAccountTv.getText().toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消注册eventBus
        EventBus.getDefault().unregister(this);
    }
}
