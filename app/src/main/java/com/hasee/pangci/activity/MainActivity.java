package com.hasee.pangci.activity;

import android.content.Intent;
import android.content.SharedPreferences;
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

import com.hasee.pangci.Common.DataCleanManagerUtils;
import com.hasee.pangci.Common.DateFormat;
import com.hasee.pangci.Common.MessageEvent;
import com.hasee.pangci.R;
import com.hasee.pangci.adapter.MyFragmentPagerAdapter;
import com.hasee.pangci.bean.User;
import com.hasee.pangci.fragment.MemberFragment;
import com.hasee.pangci.fragment.MovieFragment;
import com.hasee.pangci.fragment.RecommendFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

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
    private CircleImageView mHeadCIV;
    private User mUserInfo = new User();//用户信息
    private boolean isLogin;//判断用户是否登录
    private SharedPreferences mLogin_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initView();
        initData();
        checkIsLogin();//判断之前是否登录，如果登录直接进
    }

    public void initView() {
        mNavigationView.setNavigationItemSelectedListener(this);
        mFloatingActionButton.setOnClickListener(this);
    }

    public void initData() {
        mToolbar.setTitle("胖次");
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
        mHeadCIV = (CircleImageView) headerView.findViewById(R.id.navigation_header_icon_civ);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_menu_item_about:
                Toast.makeText(MainActivity.this, "顶着一切为了满足用户需求的目的,致力于打造最齐全,最完善的资源平台,感谢您的支持!", Toast.LENGTH_SHORT).show();
                break;

            case R.id.navigation_menu_item_cache:
                try {
                    DataCleanManagerUtils.clearAllCache(this);
                    Toast.makeText(MainActivity.this, "缓存清除成功!", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case R.id.navigation_menu_item_exit:
                Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                //清除sp内容退出
                DataCleanManagerUtils.cleanSharedPreference(this);

                mNavigationMemberInfoLl.setVisibility(View.GONE);//隐藏会员信息布局
                mNavigationAccountTv.setText("点击登录");
                mHeadCIV.setImageResource(R.drawable.normal_login);
                isLogin = false;
                if (mLogin_info != null) {//注销--》更新登录状态
                    SharedPreferences.Editor edit = mLogin_info.edit();
                    edit.putBoolean("isLogin", false);
                    edit.apply();
                }
                break;

            case R.id.navigation_menu_item_flock:
                if (mLogin_info != null&&mLogin_info.getBoolean("isLogin",false)){
                    if (mLogin_info.getString("memberLevel","青铜").equals("钻石")) {
                        Toast.makeText(MainActivity.this, "尊贵的会员,请在微信公众后台联系管理员!", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(this, "钻石会员才能加入云群!", Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            case R.id.navigation_menu_item_version:
                Toast.makeText(MainActivity.this, "当前版本是最新的!", Toast.LENGTH_SHORT).show();
                break;

            case R.id.navigation_menu_item_member:
                Intent intent = new Intent(MainActivity.this, MemberCenterActivity.class);
                if (!isLogin) {
                    //未登录
                    intent.setFlags(0);//未登录
                } else {
                    intent.setFlags(1);//登录
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("user", mUserInfo);
                    intent.putExtras(bundle);
                }
                startActivity(intent);
                break;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.navigation_account_tv:
                if (mNavigationAccountTv.getText().toString().equals("点击登录")) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    mDrawerLayout.closeDrawers();
                } else {
                    Toast.makeText(this, "您已经登录!", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.main_fab:
                mDrawerLayout.openDrawer(Gravity.START);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)//默认优先级为0
    public void handleEvent(MessageEvent event) {
        User user = event.getUser();
        mUserInfo = user;
        isLogin = mLogin_info.getBoolean("isLogin", false);
        mNavigationMemberInfoLl.setVisibility(View.VISIBLE);
        if (user.getMemberLevel().equals("青铜")) {
            //普通会员
            mHeadCIV.setImageResource(user.getUserHeadImg());
            mNavigationAccountTv.setText(user.getUserAccount());
            mNavigationMemberLevelTv.setText("会员等级:" + user.getMemberLevel());
            mNavigationResidueTv.setVisibility(View.GONE);
        } else {
            //充值会员
            mHeadCIV.setImageResource(user.getUserHeadImg());
            mNavigationAccountTv.setText(user.getUserAccount());
            mNavigationMemberLevelTv.setText("会员等级:" + user.getMemberLevel());
            mNavigationResidueTv.setVisibility(View.VISIBLE);
            int residueDays = DateFormat.differentDaysByMillisecond(getCurrentDate(), user.getMemberEndDate().getDate());
            mNavigationResidueTv.setText("会员剩余天数:" + residueDays + "天");
            Log.i("TAGEventBus-", mLogin_info.getString("memberEndDate", "") + "--" + getCurrentDate());
        }
    }

    private void checkIsLogin() {
        mLogin_info = getSharedPreferences("LOGIN_INFO", MODE_PRIVATE);
        isLogin = mLogin_info.getBoolean("isLogin", false);
        if (!mLogin_info.getString("account", "").equals("")) {
            //说明里面有记录
            //判断会员等级
            mNavigationMemberInfoLl.setVisibility(View.VISIBLE);//显示布局会员信息布局
            if (mLogin_info.getString("memberLevel", "青铜").equals("青铜")) {
                //普通会员
                mHeadCIV.setImageResource(mLogin_info.getInt("headImg", R.drawable.normal_login));
                mNavigationAccountTv.setText(mLogin_info.getString("account", ""));
                mNavigationMemberLevelTv.setText("会员等级:" + mLogin_info.getString("memberLevel", "青铜"));
                mNavigationResidueTv.setVisibility(View.GONE);
                mUserInfo.setUserHeadImg(mLogin_info.getInt("headImg", R.drawable.normal_login));
                mUserInfo.setMemberLevel(mLogin_info.getString("memberLevel", "青铜"));
                mUserInfo.setUserAccount(mLogin_info.getString("account", ""));
            } else {
                //充值会员
                mHeadCIV.setImageResource(mLogin_info.getInt("headImg", R.drawable.normal_login));
                mNavigationAccountTv.setText(mLogin_info.getString("account", ""));
                mNavigationMemberLevelTv.setText("会员等级:" + mLogin_info.getString("memberLevel", "青铜"));
                int residueDays = DateFormat.differentDaysByMillisecond(getCurrentDate(), mLogin_info.getString("memberEndDate", ""));
                mNavigationResidueTv.setText("会员剩余天数:" + residueDays + "天");
                mUserInfo.setUserHeadImg(mLogin_info.getInt("headImg", R.drawable.normal_login));
                mUserInfo.setMemberLevel(mLogin_info.getString("memberLevel", "青铜"));
                mUserInfo.setUserAccount(mLogin_info.getString("account", ""));
            }
        }
    }

    private String getCurrentDate() {
        //获取当前时间 扣除会员天数
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }

    private long tempTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //先关闭侧滑
            if (mDrawerLayout != null && mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                mDrawerLayout.closeDrawers();
                return false;
            }
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
    protected void onDestroy() {
        super.onDestroy();
        //取消注册eventBus
        EventBus.getDefault().unregister(this);
    }
}
