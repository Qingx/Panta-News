package com.zonghe.one;

public class UserData {
    private String userName; //用户名
    private String userPwd; //用户密码
    private int userId; //用户ID号
    private int userSex; //用户性别
    public  int pwdresetFlag=0;

    // /获取用户名
    public String getUserName() {  //获取用户名
        return userName;
    }

    //设置用户名
    public void setUserName(String userName) {  //输入用户名
        this.userName = userName;
    }

    //获取用户密码
    public String getUserPwd() {  //输入用户密码
        return userPwd;
    }

    //获取用户id
    public int getUserId() {  //获取用户ID
        return userId;
    }

    //设置用户id
    public void setUserId(int userId) {  //设置用户ID
        this.userId = userId;
    }

    public  UserData(String userName, String userPwd){  //只采用用户名和密码
        super();
        this.userName = userName;
        this.userPwd = userPwd;
    }
}
