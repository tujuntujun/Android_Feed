package com.hunau.feed_android.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hunau.feed_android.R;
import com.hunau.feed_android.cmd.DBUtils;

import java.util.HashMap;


public class DataFragment extends Fragment {

    private TextView tv_data;
    private TextView tv_dataHumi;
    private TextView tv_dataAir;
    private TextView tv_dataLight;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){
                case 0x11:
                    String s = (String) msg.obj;
                    //String h = (String) msg.obj;
                    tv_data.setText(s);
                    //tv_dataHumi.setText(h);
                    break;
                case 0x14:
                    //String s = (String) msg.obj;
                    String h = (String) msg.obj;
                    //tv_data.setText(s);
                    tv_dataHumi.setText(h);
                    break;
                case 0x13:
                    //String s = (String) msg.obj;
                    String hh = (String) msg.obj;
                    //tv_data.setText(s);
                    tv_dataHumi.setText(hh);
                    break;
                case 0x12:
                    String ss = (String) msg.obj;
                    //String hh = (String) msg.obj;
                    tv_data.setText(ss);
                    //tv_dataHumi.setText(hh);
                    break;
                case 0x15:
                    //String s = (String) msg.obj;
                    String aa = (String) msg.obj;
                    //tv_data.setText(s);
                    tv_dataAir.setText(aa);
                    break;
                case 0x16:
                    String a = (String) msg.obj;
                    //String hh = (String) msg.obj;
                    tv_dataAir.setText(a);
                    //tv_dataHumi.setText(hh);
                    break;
                case 0x17:
                    //String s = (String) msg.obj;
                    String ll = (String) msg.obj;
                    //tv_data.setText(s);
                    tv_dataLight.setText(ll);
                    break;
                case 0x18:
                    String l = (String) msg.obj;
                    //String hh = (String) msg.obj;
                    tv_dataLight.setText(l);
                    //tv_dataHumi.setText(hh);
                    break;

            }

/*            switch (msg.what){
                case 0x11:
                    String h = (String) msg.obj;
                    tv_dataHumi.setText(h);
                    break;
                case 0x12:
                    String hh = (String) msg.obj;
                    tv_dataHumi.setText(hh);
                    break;
            }*/

        }


    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // 控件的初始化
        /*btn_get_data = findViewById(R.id.btn_get_data);*/
        FragmentActivity view = getActivity();
        tv_data = view.findViewById(R.id.tv_data);
        tv_dataHumi = view.findViewById(R.id.tv_dataHumi);
        tv_dataAir = view.findViewById(R.id.tv_dataAir);
        tv_dataLight = view.findViewById(R.id.tv_dataLight);
        setListener();
        setListenerHumi();
        setListenerAir();
        setListenerLight();
    }

    /**
     * 设置监听
     */
    private void setListener() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String, Object> map = DBUtils.getInfoByName("Charger9527");
                Message message = handler.obtainMessage();
                if (map != null) {
                    String s = "";
                    for (String key : map.keySet()) {
                        System.out.println(map.get(key));
                        s += "当前温度值："+ map.get(key) + "\n";
                    }
                    message.what = 0x12;
                    message.obj = s;
                } else {
                    message.what = 0x11;
                    message.obj = "查询结果为空";
                }

                // 发消息通知主线程更新UI
                handler.sendMessage(message);

            }
        }).start();
    }

    private void setListenerHumi(){
        new Thread(new Runnable() {
            @Override
            public void run() {

                HashMap<String, Object> humi = DBUtils.getInfoByNameHumi("Charger");
                Message messageHumi = handler.obtainMessage();
                if (humi != null) {
                    String h = "";
                    for (String key : humi.keySet()) {
                        h += "当前湿度值：" + humi.get(key) + "\n";
                    }
                    messageHumi.what = 0x14;
                    messageHumi.obj = h;
                } else {
                    messageHumi.what = 0x13;
                    messageHumi.obj = "查询结果为空";
                }
                // 发消息通知主线程更新UI

                handler.sendMessage(messageHumi);

            }
        }).start();

    }

    private void setListenerAir(){
        new Thread(new Runnable() {
            @Override
            public void run() {

                HashMap<String, Object> air = DBUtils.getInfoByNameAir("Charger");
                Message messageAir = handler.obtainMessage();
                if (air != null) {
                    String h = "";
                    for (String key : air.keySet()) {
                        h += "当前氨气值：" + air.get(key) + "\n";
                    }
                    messageAir.what = 0x15;
                    messageAir.obj = h;
                } else {
                    messageAir.what = 0x16;
                    messageAir.obj = "查询结果为空";
                }
                // 发消息通知主线程更新UI

                handler.sendMessage(messageAir);

            }
        }).start();

    }

    private void setListenerLight(){
        new Thread(new Runnable() {
            @Override
            public void run() {

                HashMap<String, Object> light = DBUtils.getInfoByNameLight("Charger");
                Message messageLight = handler.obtainMessage();
                if (light != null) {
                    String h = "";
                    for (String key : light.keySet()) {
                        h += "当前光照值：" + light.get(key) + "\n";
                    }
                    messageLight.what = 0x17;
                    messageLight.obj = h;
                } else {
                    messageLight.what = 0x18;
                    messageLight.obj = "查询结果为空";
                }
                // 发消息通知主线程更新UI

                handler.sendMessage(messageLight);

            }
        }).start();

    }
}
