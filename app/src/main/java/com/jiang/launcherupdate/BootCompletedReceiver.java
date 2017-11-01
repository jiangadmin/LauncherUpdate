package com.jiang.launcherupdate;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


/**
 * Created by  jiang
 * on 2017/6/19.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:TODO 开机广播接收
 * update：
 */
public class BootCompletedReceiver extends BroadcastReceiver {
        static final String ACTION = "android.intent.action.BOOT_COMPLETED";  
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ACTION)) {  
            Intent mainActivityIntent = new Intent(context, Main_Activity.class);  // 要启动的Activity
            mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
            context.startActivity(mainActivityIntent);  
        }  
    }
}