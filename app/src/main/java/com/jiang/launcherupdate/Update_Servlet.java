package com.jiang.launcherupdate;

import android.app.Activity;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by  jiang
 * on 2017/6/19.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:TODO 检查更新请求
 * update：
 */
public class Update_Servlet extends AsyncTask<String, Integer, UpdateEntity> {
    private static final String TAG = "Update_Servlet";
    Activity activity;

    public Update_Servlet(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected UpdateEntity doInBackground(String... strings) {
        UpdateEntity entity;
        Map map = new HashMap();
        if (!TextUtils.isEmpty(MyAppliaction.ID)) {
            map.put("serialNum", MyAppliaction.ID);
        } else {
            entity = new UpdateEntity();
            entity.setErrorcode(-3);
            entity.setErrormsg("数据缺失");
            return entity;
        }
        map.put("versionNum", Tools.getVersionName(MyAppliaction.context));
        map.put("buildNum", String.valueOf(Tools.getVersionCode(MyAppliaction.context)));

        String res = HttpUtil.doPost(Const.URL + "cms/appVersionController/findNewVersion.do", map);


        if (res != null) {
            try {
                entity = new Gson().fromJson(res, UpdateEntity.class);
            } catch (Exception e) {
                entity = new UpdateEntity();
                entity.setErrorcode(-2);
                entity.setErrormsg("数据解析失败");
            }
        } else {
            entity = new UpdateEntity();
            entity.setErrorcode(-1);
            entity.setErrormsg("连接服务器失败");
        }
        return entity;
    }

    @Override
    protected void onPostExecute(UpdateEntity entity) {
        super.onPostExecute(entity);
        if (entity.getErrorcode() == 1000) {
            if (entity.getResult().getBuildNum() > Tools.getVersionCode(MyAppliaction.context)) {
                Loading.show(activity, "更新中");
                new DownUtil(activity).downLoad(entity.getResult().getDownloadUrl(), "Feekr" + entity.getResult().getVersionNum() + ".apk", true);
            }
        } else if (entity.getErrorcode() == 15) {
//            Toast.makeText(activity, TAG+entity.getErrormsg(), Toast.LENGTH_SHORT).show();
        } else {

        }
    }
}
