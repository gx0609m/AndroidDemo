package com.example.administrator.androiddemo.viewpager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import com.example.administrator.androiddemo.R;
import com.example.administrator.androiddemo.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * ViewPager + Fragment
 * <p>
 * Created by gx on 2018/5/24 0024
 */

public class ViewPagerWithFragmentLearningActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout linearLayoutFirst;
    private LinearLayout linearLayoutSecond;
    private LinearLayout linearLayoutThird;
    private LinearLayout linearLayoutFourth;

    private FirstFragment firstFragment;
    private SecondFragment secondFragment;
    private ThirdFragment thirdFragment;
    private FourthFragment fourthFragment;

    private ViewPager viewPager;

    private List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager_fragment_learning);

        initView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tab_home:
                viewPager.setCurrentItem(0, true);
                break;
            case R.id.tab_finance:
                viewPager.setCurrentItem(1, true);
                break;
            case R.id.tab_account:
                viewPager.setCurrentItem(2, false);
                break;
            case R.id.tab_setting:
                viewPager.setCurrentItem(3, false);
                break;
        }
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        firstFragment = new FirstFragment();
        secondFragment = new SecondFragment();
        thirdFragment = new ThirdFragment();
        fourthFragment = new FourthFragment();

        fragmentList.add(firstFragment);
        fragmentList.add(secondFragment);
        fragmentList.add(thirdFragment);
        fragmentList.add(fourthFragment);

        FragmentPagerAdapter fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        };

        viewPager.setAdapter(fragmentPagerAdapter);
        viewPager.setOffscreenPageLimit(fragmentList.size());

        linearLayoutFirst = (LinearLayout) findViewById(R.id.tab_home);
        linearLayoutFirst.setOnClickListener(this);

        linearLayoutSecond = (LinearLayout) findViewById(R.id.tab_finance);
        linearLayoutSecond.setOnClickListener(this);

        linearLayoutThird = (LinearLayout) findViewById(R.id.tab_account);
        linearLayoutThird.setOnClickListener(this);

        linearLayoutFourth = (LinearLayout) findViewById(R.id.tab_setting);
        linearLayoutFourth.setOnClickListener(this);
    }
}
