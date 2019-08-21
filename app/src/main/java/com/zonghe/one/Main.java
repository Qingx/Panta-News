package com.zonghe.one;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Main extends AppCompatActivity implements View.OnClickListener {
    public static Main instance;
    private BottomNavigationView main_bottomnavigation;
    private ViewPager main_viewPager;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private TextView home_name;
    private ImageButton main_search;
    SharedPreferences sprfMain;
    SharedPreferences.Editor editorMain;

    private ICallBack iCallBack;
    private BCallBack bCallBack;
    private Context mContext;
    private AlertDialog alertDialog;
    private NetWork mNetWork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this.getApplicationContext();
        instance=this;
        main_search=(ImageButton)findViewById(R.id.main_search);
        main_search.setOnClickListener(this);
        //底部导航栏
        main_viewPager=(ViewPager)findViewById(R.id.main_viewpager);
        main_viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                main_bottomnavigation.getMenu().getItem(position).setChecked(true);

            }

            @Override
            public void onPageScrollStateChanged(int position) {

            }
        });

        final ArrayList<Fragment>fgLists=new ArrayList<>(4);
        fgLists.add(bottom_fragment_home.createFragment(this));
        fgLists.add(bottom_fragment_xiaoyuan.createFragment(this));
        fgLists.add(bottom_fragment_shipin.createFragment(this));
        fgLists.add(bottom_fragment_me.createFragment(this));

        FragmentPagerAdapter mPagerAdapter=new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fgLists.get(position);
            }

            @Override
            public int getCount() {
                return fgLists.size();
            }
        };

        main_viewPager.setAdapter(mPagerAdapter);
        main_viewPager.setOffscreenPageLimit(4);

        main_bottomnavigation=(BottomNavigationView)findViewById(R.id.main_bottomnavigation);
        //BottomNavigationViewHelper.disableShiftMode(main_bottomnavigation);
        main_bottomnavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.bottom_home:
                        main_viewPager.setCurrentItem(0);
                        break;
                    case R.id.bottom_xiaoyuan:
                        main_viewPager.setCurrentItem(1);
                        break;
                    case R.id.bottom_shipin:
                        main_viewPager.setCurrentItem(2);
                        break;
                    case R.id.bottom_me:
                        main_viewPager.setCurrentItem(3);
                        break;
                }
                return false;
            }
        });

        //侧滑导航栏
        drawerLayout=(DrawerLayout)findViewById(R.id.main);
        navigationView=(NavigationView)findViewById(R.id.main_side_left);
        View side_main=navigationView.getHeaderView(0);
        home_name =(TextView)findViewById(R.id.main_name);
        home_name.setOnClickListener(this);

        /*
        navigationView.findViewById(R.id.text12).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        */

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.side_account:
                        //startActivity(new Intent(Main.this, Side_account.class));
                        break;
                    case R.id.side_score:
                        //startActivity(new Intent(Main.this, Side_score.class));
                        break;
                    case R.id.side_notifications:
                        //startActivity(new Intent(Main.this, Side_notifications.class));
                        break;
                    case R.id.side_privacy:
                        //startActivity(new Intent(Main.this, Side_privacy.class));
                        break;
                    case R.id.side_general:
                        //startActivity(new Intent(Main.this, Side_general.class));
                        break;
                    case R.id.side_about:
                        //startActivity(new Intent(Main.this, Side_about.class));
                        Toast.makeText(Main.this,"暂不支持",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.side_help:
                        //startActivity(new Intent(Main.this, Side_help.class));
                        Toast.makeText(Main.this,"暂不支持",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.side_out:

                        //resetSprfMain();

                        Intent intent=new Intent(Main.this,Login.class);
                        startActivity(intent);
                        Main.this.finish();
                        break;
                }
                drawerLayout.closeDrawer(navigationView);
                return true;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_name:
                if(drawerLayout.isDrawerOpen(navigationView)){
                    drawerLayout.closeDrawer(navigationView);
                }else{
                    drawerLayout.openDrawer(navigationView);
                }
                break;
            case R.id.main_search:
                search_dialog(v);
                break;
        }
    }
    @Override
    public void  onPointerCaptureChanged(boolean hasCapture){
    }

    public void search_dialog(View view){
        /*
        TextView title=new TextView(this);
        title.setText("搜索");
        title.setPadding(10,30,10,10);
        title.setTextSize(18);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(getResources().getColor(R.color.colorPrimary));
        */
        final EditText_Clear editText=new EditText_Clear(this);
        editText.setTextSize(14);
        editText.setSingleLine(true);
        editText.setHint("  实时热点搜索");
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        editText.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (!mNetWork.isNetConnected(mContext)){
                        Toast.makeText(mContext,"请连接网络！",Toast.LENGTH_SHORT).show();
                        return false;
                    }

                    String searchString="热点";
                    if (editText.getText()!=null){
                         searchString =editText.getText().toString();
                    }
                    Intent intent = new Intent(mContext,SearchResultActivity.class);
                    intent.putExtra("searchString",searchString);
                    startActivity(intent);
                    if (!(iCallBack == null)){
                        iCallBack.SearchAciton(editText.getText().toString());
                    }

                    //startActivity(new Intent(Main.this,));

                    //Toast.makeText(Main.this, "需要搜索的是" + editText.getText(), Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        //builder.setCustomTitle(title);
        builder.setView(editText);
        //builder.setPositiveButton("确定",null);
         alertDialog=builder.create();
        Window window=alertDialog.getWindow();
        WindowManager.LayoutParams layoutParams=alertDialog.getWindow().getAttributes();
        window.setGravity(Gravity.TOP);
        layoutParams.height=40;
        layoutParams.y=280;
        alertDialog.getWindow().setAttributes(layoutParams);
        alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        alertDialog.show();
    }

    // 搜索按键回调接口
    public interface ICallBack {
        void SearchAciton(String string);
    }
    // 返回按键接口回调
    public void setOnClickBack(BCallBack bCallBack){
        this.bCallBack = bCallBack;
    }

    @Override
    protected void onDestroy() {
        if(alertDialog != null) {
            alertDialog.dismiss();
        }
        super.onDestroy();
    }
}
