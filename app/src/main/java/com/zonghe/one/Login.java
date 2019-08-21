package com.zonghe.one;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class Login extends AppCompatActivity {  //登录界面活动
    public static Login instance;
    private NetWork mNetWork;
    public int pwdresetFlag=0;
    private EditText mAccount;  //用户名
    private EditText mPwd;  //密码
    private Button mLoginButton; //登录按钮
    private TextView mSignButton; //注册按钮
    private CheckBox mRememberCheck;
    private SharedPreferences login_sp;
    private String userNameValue;
    private String passwordValue;
    private LoginNetConnectChangedReceiver mLoginNetConnectChangedReceiver;
    int ranColor;

    private UserDataManager mUserDataManager;

    //SharedPreferences sprfMain;
    //SharedPreferences.Editor editorMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        instance=this;

        mAccount = (EditText)findViewById(R.id.login_username);
        mPwd = (EditText)findViewById(R.id.login_password);
        mLoginButton = (Button)findViewById(R.id.login_login);
        mSignButton = (TextView)findViewById(R.id.login_sign);
        mRememberCheck =(CheckBox)findViewById(R.id.login_remember);

        login_sp = getSharedPreferences("userInfo",0);
        String name = login_sp.getString("USER_NAME","");
        String pwd = login_sp.getString("PASSWORD","");
        boolean choseRemember=login_sp.getBoolean("mRememberCheck",false);
        boolean choseAutoLogin=login_sp.getBoolean("mAutologinCheck",false);
        if(choseRemember){
            mAccount.setText(name);
            mPwd.setText(pwd);
            mRememberCheck.setChecked(true);
        }

        mLoginButton.setOnClickListener(mListener);
        mSignButton.setOnClickListener(mListener);

        if(mUserDataManager == null){
            mUserDataManager = new UserDataManager(this);
            mUserDataManager.openDataBase();
        }

    }
    OnClickListener mListener = new OnClickListener() {

        @Override
        public void onClick(View view) {
            CheckNetRegisterReceiver();
//            if (mNetWork.isNetConnected(Login.this)){
//
//            }
            switch (view.getId()){
                case R.id.login_login:
                    login();
                    break;
                case R.id.login_sign:
                    startActivity(new Intent(Login.this,Sign.class));
                    break;
            }

        }
    };
    public void login(){

        Random random=new Random();
        ranColor=0xff000000|random.nextInt(0x00ffffff);

        String userName = mAccount.getText().toString().trim();
        String userPwd = mPwd.getText().toString().trim();
        if(isUserNameAndPwdValid()){
            SharedPreferences.Editor editor = login_sp.edit();
            int result = mUserDataManager.findUserByNameAndPwd(userName,userPwd);
            if (result == 1) {
                editor.putString("USER_NAME", userName);
                editor.putString("PASSWORD", userPwd);
                editor.commit();

                if(mRememberCheck.isChecked()){
                    editor.putBoolean("mRememberCheck",true);
                }else {
                    editor.putBoolean("mRememberCheck",false);
                }
                editor.commit();

                Intent intent1=new Intent(Login.this,Main.class);

                //editorMain.putBoolean("main",true);
                //editorMain.commit();

                Bundle data=new Bundle();
                data.putString("username",userName);
                data.putInt("ranColor",ranColor);
                intent1.putExtras(data);

                startActivity(intent1);
                Toast.makeText(Login.this, "登陆成功", Toast.LENGTH_SHORT).show();
                Login.this.finish();
            }else if (result == 0){
                Toast.makeText(Login.this,"账号或密码不正确",Toast.LENGTH_SHORT).show();
            }
        }
    }
    public boolean isUserNameAndPwdValid(){
        if(mAccount.getText().toString().trim().equals("")){
            Toast.makeText(Login.this,"请输入你的账号",Toast.LENGTH_SHORT).show();
            return false;
        }else if (mPwd.getText().toString().trim().equals("")){
            Toast.makeText(Login.this,"请输入你的密码",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    @Override
    protected void onResume() {
        if (mUserDataManager == null) {
            mUserDataManager = new UserDataManager(this);
            mUserDataManager.openDataBase();
        }


        /*
        sprfMain= PreferenceManager.getDefaultSharedPreferences(this);
        editorMain=sprfMain.edit();

        if (sprfMain.getBoolean("main",false)){
            Intent intent1=new Intent(Login.this,Main.class);

            Bundle data=new Bundle();
            data.putString("username",userName);
            data.putInt("ranColor",ranColor);
            intent1.putExtras(data);

            startActivity(intent1);
            Toast.makeText(Login.this, "登陆成功", Toast.LENGTH_SHORT).show();
            Login.this.finish();
        }
        */

        super.onResume();
    }
    @Override
    protected void onDestroy() {
        unregisterReceiver(mLoginNetConnectChangedReceiver);
        super.onDestroy();
    }
    @Override
    protected void onPause() {
        if (mUserDataManager != null) {
            mUserDataManager.closeDataBase();
            mUserDataManager = null;
        }
        super.onPause();
    }
    private void CheckNetRegisterReceiver(){
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        mLoginNetConnectChangedReceiver =new LoginNetConnectChangedReceiver();
        registerReceiver(mLoginNetConnectChangedReceiver,filter);
    }
}
