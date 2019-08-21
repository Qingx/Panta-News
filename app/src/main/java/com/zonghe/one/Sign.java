package com.zonghe.one;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Sign extends AppCompatActivity {

    private EditText mAccount;
    private EditText mPwd;
    private EditText mPwdCheck;
    private Button mSureButton;
    private UserDataManager mUserDataManager;
    TextView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        mAccount = (EditText)findViewById(R.id.sign_username);
        mPwd = (EditText)findViewById(R.id.sign_password);
        mPwdCheck = (EditText)findViewById(R.id.sign_repassword);
        mSureButton = (Button)findViewById(R.id.sign_sign);
        mSureButton.setOnClickListener(m_sign_Listener);

        back=(TextView)findViewById(R.id.sign_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sign.this.finish();
            }
        });

        if (mUserDataManager == null){
            mUserDataManager = new UserDataManager(this);
            mUserDataManager.openDataBase();
        }
    }
    View.OnClickListener m_sign_Listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.sign_sign:
                    sign_check();
                    break;
            }
        }
    };
    public void  sign_check(){
        if (isUserNameAndPwdValid()){
            String userName = mAccount.getText().toString().trim();
            String userPwd = mPwd.getText().toString().trim();
            String userPwdCheck = mPwdCheck.getText().toString().trim();

            int count = mUserDataManager.findUserByName(userName);
            if (count>0){
                Toast.makeText(Sign.this,"账号已存在",Toast.LENGTH_SHORT).show();
                return;
            }
            if (userPwd.equals(userPwdCheck)==false){
                Toast.makeText(Sign.this,"请重复密码",Toast.LENGTH_SHORT).show();
                return;
            }else {
                UserData mUser = new UserData(userName,userPwd);
                mUserDataManager.openDataBase();
                long flag = mUserDataManager.insertUserData(mUser);
                if(flag == -1){
                    Toast.makeText(Sign.this,"注册失败",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(Sign.this,"注册成功",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(Sign.this,Login.class);
                    startActivity(intent);
                    Sign.this.finish();
                }
            }
        }
    }
    public boolean isUserNameAndPwdValid(){
        if (mAccount.getText().toString().trim().equals("")){
            Toast.makeText(Sign.this,"请输入你的账号",Toast.LENGTH_SHORT).show();
            return false;
        }else if (mPwd.getText().toString().trim().equals("")){
            Toast.makeText(Sign.this,"请输入你的密码",Toast.LENGTH_SHORT).show();
            return false;
        }else if (mPwdCheck.getText().toString().equals("")){
            Toast.makeText(Sign.this,"请重复密码",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
