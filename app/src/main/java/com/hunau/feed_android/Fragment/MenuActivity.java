package com.hunau.feed_android.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;


import com.hunau.feed_android.MainActivity;
import com.hunau.feed_android.R;
import com.hunau.feed_android.monitoring.MonitoringActivity;
import com.hunau.feed_android.utils.BottomNavigationViewHelper;

import java.util.ArrayList;
import java.util.List;


public class MenuActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private BottomNavigationView mBottomNavigationView;

    private int lastIndex;
    List<Fragment> mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_main);
        initView();
        initBottomNavigation();
        initData();

    }

    public void initView() {
        mToolbar = findViewById(R.id.toolbar);

    }

    public void initData() {
        setSupportActionBar(mToolbar);
        mFragments = new ArrayList<>();
        mFragments.add(new MainActivity());
        mFragments.add(new MonitoringActivity());
        mFragments.add(new DataFragment());
        // 初始化展示MessageFragment
        setFragmentPosition(0);
    }

    public void initBottomNavigation() {
        mBottomNavigationView = findViewById(R.id.bv_bottomNavigation);
        // 解决当item大于三个时，非平均布局问题
        BottomNavigationViewHelper.disableShiftMode(mBottomNavigationView);
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
/*                    case R.id.menu_message:
                        setFragmentPosition(0);
                        break;*/
                    case R.id.menu_contacts:
                        setFragmentPosition(0);
                        break;
                    case R.id.menu_discover:
                        setFragmentPosition(1);
                        break;
                    case R.id.menu_me:
                        setFragmentPosition(2);
                        break;
                    default:
                        break;
                }
                // 这里注意返回true,否则点击失效
                return true;
            }
        });
    }


    private void setFragmentPosition(int position) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment currentFragment = mFragments.get(position);
        Fragment lastFragment = mFragments.get(lastIndex);
        lastIndex = position;
        ft.hide(lastFragment);
        if (!currentFragment.isAdded()) {
            getSupportFragmentManager().beginTransaction().remove(currentFragment).commit();
            ft.add(R.id.ll_frameLayout, currentFragment);
        }
        ft.show(currentFragment);
        ft.commitAllowingStateLoss();
    }
}
