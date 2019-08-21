package com.zonghe.one;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class bottom_fragment_me extends Fragment {
    private static Context context;
    View view;
    TextView me_name,me_picture;
    private static String strUserName,str;
    RelativeLayout criclerelativelayout;
    Boolean pd=false;
    public int ranColor;

    public static bottom_fragment_me createFragment(Context main_me){
        context = main_me;
        return new bottom_fragment_me();
    }
    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        view=inflater.inflate(R.layout.main_bottom_me,container,false);

        me_name=(TextView)view.findViewById(R.id.me_name_enter);
        me_picture=(TextView)view.findViewById(R.id.me_picture);
        criclerelativelayout=(RelativeLayout)view.findViewById(R.id.circlerelativelayout);

        Intent intent1=getActivity().getIntent();
        if (intent1 != null){
            Bundle data=intent1.getExtras();
            strUserName=data.getString("username");
            ranColor=data.getInt("ranColor");
            me_name.setText(strUserName);
            str=strUserName.substring(0,1);
            me_picture.setText(str);
            me_picture.setTextSize(20);
            GradientDrawable drawable=(GradientDrawable)criclerelativelayout.getBackground();
            drawable.setColor(ranColor);
        }
        return view;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    /*
    public static String stringtounicode(String strUserName){
        char[] chars=strUserName.toCharArray();
        StringBuilder sb=new StringBuilder("");
        byte[] ba=strUserName.getBytes();
        int bit;
        for (int i=0;i<ba.length;i++){
            bit=(ba[i]&0x0f0)>>4;
            sb.append(chars[bit]);
            bit=ba[i]&0x0f;
            sb.append(chars[bit]);
            sb.append(' ');
        }
        return sb.toString().trim();
    }
    */
}
