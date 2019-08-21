package com.zonghe.one;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import static android.support.constraint.Constraints.TAG;

public class LoginNetConnectChangedReceiver extends BroadcastReceiver {
    public AlertDialog mDialog;
    @Override
    public void onReceive(final Context context, Intent intent) {

        // 监听网络连接，包括wifi和移动数据的打开和关闭,以及连接上可用的连接都会接到监听
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            //获取联网状态的NetworkInfo对象
            NetworkInfo info = intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
            if (info != null)
            {
                //判断如果当前的网络连接成功并且网络连接可用
                if (NetworkInfo.State.CONNECTED == info.getState() && info.isAvailable())
                {
                    Log.e("网络已连接","info.getState()"+info.getState());
                    if(mDialog!=null&&mDialog.isShowing()){
                        mDialog.cancel();
                    }
//                    //判断当前是4G
//                    if (info.getType()==ConnectivityManager.TYPE_MOBILE) {
//                        ExoPlayerActivity.player.setPlayWhenReady(false);
//                        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
//                        dialog.setMessage("警告当前使用的是数据连接");
//                        dialog.setCancelable(false);
//                        dialog.setPositiveButton("我是土豪有钱", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                ExoPlayerActivity.player.setPlayWhenReady(true);
//                                dialog.cancel();
//                            }
//                        });
//                        dialog.setNegativeButton("设置网络", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
//                                context.startActivity(intent);
//                                dialog.cancel();
//                                //bug 不设网络返回自动播放。
//                            }
//                        });
//                        dialog.setNeutralButton("返回", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                ExoPlayerActivity.instance.finish();
//                                dialog.cancel();
//                            }
//                        });
//                        if (mDialog!=null&&mDialog.isShowing()){
//                            mDialog.dismiss();
//                            Log.e("mDialog","mDialog被消除");
//                        }
//                        mDialog = dialog.create();
//                        mDialog.show();
//                    }
                }else if (NetworkInfo.State.DISCONNECTED == info.getState() &&info.isAvailable()){
                    Log.e("没联网","info.getState()"+info.getState());
                    AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                    dialog.setMessage("未连接网络。");
                    dialog.setCancelable(false);
                    dialog.setPositiveButton("返回", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
//                    dialog.setNegativeButton("设置网络", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
//                            context.startActivity(intent);
//                            dialog.cancel();
//                        }
//                    });
                    if (mDialog!=null&&mDialog.isShowing()){
                        mDialog.cancel();
                    }
                    mDialog =dialog.create();
                    mDialog.show();
                }
            }
        }
    }
}

