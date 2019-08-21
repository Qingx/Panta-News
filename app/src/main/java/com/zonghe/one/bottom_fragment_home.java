package com.zonghe.one;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class bottom_fragment_home extends Fragment{
    private static Context context;
    View view;
    private List<Fragment> home_list;
    private ViewPager home_viewpager;
    private TabLayout home_tablayout;
    private bottom_fragment_home.MyAdapter home_adapter;
    private String[] home_title = {"头条","娱乐","军事","体育","科技"};

    public static bottom_fragment_home createFragment(Context main_home){
        context = main_home;
        return new bottom_fragment_home();
    }
    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        view=inflater.inflate(R.layout.main_bottom_home,container,false);

        home_viewpager=(ViewPager)view.findViewById(R.id.home_viewpager);
        home_tablayout=(TabLayout)view.findViewById(R.id.home_tablayout);

        home_list=new ArrayList<>();
        home_list.add(home_toutiao.createFragment(this));
        home_list.add(home_yule.createFragment(this));
        home_list.add(home_junshi.createFragment(this));
        home_list.add(home_tiyu.createFragment(this));
        home_list.add(home_keji.createFragment(this));
        home_adapter = new MyAdapter(getChildFragmentManager());
        home_viewpager.setAdapter(home_adapter);
        home_viewpager.setOffscreenPageLimit(5);
        home_tablayout.setupWithViewPager(home_viewpager);
        return view;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    public class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return home_list.get(i);
        }

        @Override
        public int getCount() {
            return home_list.size();
        }
        @Override
        public CharSequence getPageTitle(int i){
            return home_title[i];
        }
    }
}
