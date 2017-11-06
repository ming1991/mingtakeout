package com.heima.takeout31.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by lidongzhi on 2016/12/8.
 */

public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "Jpush";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            // SDK 向 JPush Server 注册所得到的注册 全局唯一的 ID ，可以通过此 ID 向对应的客户端发送消息和通知。
            String id = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            //保存服务器推送下来的消息的标题。
            String title = bundle.getString(JPushInterface.EXTRA_TITLE);
            //保存服务器推送下来的消息内容
            String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
            Log.e(TAG, message);
            //保存服务器推送下来的附加字段。这是个 JSON 字符串。
            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
            Log.e(TAG, extras);
            //保存服务器推送下来的内容类型。
            String type = bundle.getString(JPushInterface.EXTRA_CONTENT_TYPE);

            processExtra(extras);
        }
    }

    private void processExtra(String extras) {
        try {
            JSONObject jsonObject = new JSONObject(extras);
            String orderId = jsonObject.getString("orderId");
            String type = jsonObject.getString("type");
            Map<String,String> data = new HashMap<>();
            data.put("orderId", orderId);
            data.put("type", type);
            OrderObservable.getInstance().changeData(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
