package com.example.administrator.androiddemo.viewpager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.administrator.androiddemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gx on 2018/5/24 0024
 */

public class SecondFragment extends Fragment implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private SecondFragmentSub1 secondFragmentSub1;
    private SecondFragmentSub2 secondFragmentSub2;
    private SecondFragmentSub3 secondFragmentSub3;

    private Button sub1;
    private Button sub2;
    private Button sub3;

    private ViewPager subViewPager;

    private List<Fragment> fragmentList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);

    }

    private void initView(View view) {
        subViewPager = (ViewPager) view.findViewById(R.id.subViewPager);

        secondFragmentSub1 = new SecondFragmentSub1();
        secondFragmentSub2 = new SecondFragmentSub2();
        secondFragmentSub3 = new SecondFragmentSub3();

        fragmentList.add(secondFragmentSub1);
        fragmentList.add(secondFragmentSub2);
        fragmentList.add(secondFragmentSub3);

        // 这里注意 外层ViewPager 已经使用了 getSupportFragmentManager()
        // 因此，这里如果再使用 getActivity().getSupportFragmentManager() 就会报错：IllegalStateException: FragmentManager is already executing transactions
        // 需要改成 getChildFragmentManager()
        FragmentPagerAdapter fragmentPagerAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        };

        subViewPager.setAdapter(fragmentPagerAdapter);
        subViewPager.setOffscreenPageLimit(fragmentList.size());
        subViewPager.addOnPageChangeListener(this);

        sub1 = (Button) view.findViewById(R.id.sub1);
        sub1.setOnClickListener(this);

        sub2 = (Button) view.findViewById(R.id.sub2);
        sub2.setOnClickListener(this);

        sub3 = (Button) view.findViewById(R.id.sub3);
        sub3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sub1:
                subViewPager.setCurrentItem(0, true);
                break;
            case R.id.sub2:
                subViewPager.setCurrentItem(1, true);
                break;
            case R.id.sub3:
                subViewPager.setCurrentItem(2, true);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (position == 0) {
            sub1.setText("Sub1+C");
            sub2.setText("Sub2");
            sub3.setText("Sub3");
        } else if (position == 1) {
            sub1.setText("Sub1");
            sub2.setText("Sub2+C");
            sub3.setText("Sub3");
        } else {
            sub1.setText("Sub1");
            sub2.setText("Sub2");
            sub3.setText("Sub3+C");
        }
    }

    @Override
    public void onPageSelected(int position) {
        Log.e("onPageSelected", "position:" + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        Log.e("onPageScrollStateChange", "state:" + state);
    }
}
