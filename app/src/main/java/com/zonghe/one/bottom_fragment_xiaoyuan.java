package com.zonghe.one;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class bottom_fragment_xiaoyuan extends Fragment {
    private static Context context;
    View view;

    public static bottom_fragment_xiaoyuan createFragment(Context main_xiaoyuan) {
        context = main_xiaoyuan;
        return new bottom_fragment_xiaoyuan();
    }

    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main_bottom_xiaoyuan, container, false);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}



