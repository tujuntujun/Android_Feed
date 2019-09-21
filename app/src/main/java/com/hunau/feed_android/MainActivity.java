package com.hunau.feed_android;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hunau.feed_android.cmd.Command;

import transsocket.TcpClient;
import transsocket.TcpClientFactory;

public class MainActivity extends Fragment {
    private TcpClient tcpClient;
    private TextView textView;
    private String FAN = Command.CLOSEFAN;
    private String FAN02 = Command.CLOSEFANTWO;
    private String GUA = Command.CLOSEGUA;
    private String CHUANG = Command.CLOSECHUANG;
    private String TOU = Command.CLOSETOU;
    private String SHUI = Command.CLOSESHUI;
    private Button b3;
    private Button b4;
    private Button b5;
    private Button b8;
    private Button b9;
    private Button b10;
    private Button b11;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        tcpClient = TcpClientFactory.createTcpClient();
        return inflater.inflate(R.layout.activity_main, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentActivity view = getActivity();
        this.b3 = view.findViewById(R.id.button3);
        this.b4 = view.findViewById(R.id.button4);
        this.b5 = view.findViewById(R.id.button5);
        this.b8 = view.findViewById(R.id.button8);
        this.b9 = view.findViewById(R.id.button9);
        this.b10 = view.findViewById(R.id.button10);
        this.b11 = view.findViewById(R.id.button11);
        this.textView = view.findViewById(R.id.textView);
        try {
            this.link();
            this.fan();
            this.fanTwo();
            this.gua();
            this.chuang();
            this.tou();
            this.shui();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void link() {
       b3.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               tcpClient = TcpClientFactory.createTcpClient();
               if (tcpClient == null) {
                   textView.setText("建立连接失败!");
                   Toast.makeText(getActivity().getApplicationContext(), "连接失败", Toast.LENGTH_SHORT).show();
               }
               else {
                   textView.setText("建立连接成功!");
                   Toast.makeText(getActivity().getApplicationContext(), "打开连接", Toast.LENGTH_SHORT).show();
               }
           }
       });
    }

    public void fan() {
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tcpClient != null) {
                    FAN = FAN.equals(Command.CLOSEFAN) ? Command.OPENFAN : Command.CLOSEFAN;
                    try {
                        tcpClient.sendMsg(FAN);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (FAN.equals(Command.OPENFAN)) {
                        textView.setText("已打开风扇1!");
                        Toast.makeText(getActivity().getApplicationContext(), "打开风扇1", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        textView.setText("已关闭风扇1!");
                        Toast.makeText(getActivity().getApplicationContext(), "关闭风扇1", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    System.out.println("请先建立连接!");
                    textView.setText("请先建立连接!");
                }
            }
        });
    }

    public void fanTwo() {
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tcpClient != null) {
                    FAN02 = FAN02.equals(Command.CLOSEFANTWO) ? Command.OPENFANTWO : Command.CLOSEFANTWO;
                    try {
                        tcpClient.sendMsg(FAN02);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (FAN02.equals(Command.OPENFANTWO)) {
                        textView.setText("已打开风扇2!");
                        Toast.makeText(getActivity().getApplicationContext(), "打开风扇2", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        textView.setText("已关闭风扇2!");
                        Toast.makeText(getActivity().getApplicationContext(), "关闭风扇2", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    System.out.println("请先建立连接!");
                    textView.setText("请先建立连接!");
                }
            }
        });
    }

    public void gua() {
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tcpClient != null) {
                    GUA = GUA.equals(Command.CLOSEGUA) ? Command.OPENGUA : Command.CLOSEGUA;
                    try {
                        tcpClient.sendMsg(GUA);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (GUA.equals(Command.OPENGUA)) {
                        textView.setText("已打开刮粪板!");
                        Toast.makeText(getActivity().getApplicationContext(), "打开刮粪板", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        textView.setText("已关闭刮粪板!");
                        Toast.makeText(getActivity().getApplicationContext(), "关闭刮粪板", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    System.out.println("请先建立连接!");
                    textView.setText("请先建立连接!");
                }
            }
        });
    }

    public void chuang()  {
        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tcpClient != null) {
                    CHUANG = CHUANG.equals(Command.CLOSECHUANG) ? Command.OPENCHUANG : Command.CLOSECHUANG;
                    try {
                        tcpClient.sendMsg(CHUANG);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (CHUANG.equals(Command.OPENCHUANG)) {
                        textView.setText("已打开窗帘!");
                        Toast.makeText(getActivity().getApplicationContext(), "打开水帘", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        textView.setText("已关闭窗帘!");
                        Toast.makeText(getActivity().getApplicationContext(), "关闭水帘", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    System.out.println("请先建立连接!");
                    textView.setText("请先建立连接!");
                }
            }
        });
    }

    public void tou() {
        b10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tcpClient != null) {
                    TOU = TOU.equals(Command.CLOSETOU) ? Command.OPENTOU : Command.CLOSETOU;
                    try {
                        tcpClient.sendMsg(TOU);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (TOU.equals(Command.OPENTOU)) {
                        textView.setText("已打开投食!");
                        Toast.makeText(getActivity().getApplicationContext(), "打开投食", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        textView.setText("已关闭投食!");
                        Toast.makeText(getActivity().getApplicationContext(), "关闭投食", Toast.LENGTH_SHORT).show();
                    }
                } else
                    System.out.println("请先建立连接!");
            }
        });
    }

    public void shui() {
        b11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tcpClient != null) {
                    SHUI = SHUI.equals(Command.CLOSESHUI) ? Command.OPENSHUI : Command.CLOSESHUI;
                    try {
                        tcpClient.sendMsg(SHUI);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (SHUI.equals(Command.OPENSHUI)) {
                        textView.setText("已打开喂水!");
                        Toast.makeText(getActivity().getApplicationContext(), "打开喂水", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        textView.setText("已关闭喂水!");
                        Toast.makeText(getActivity().getApplicationContext(), "关闭喂水", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    System.out.println("请先建立连接!");
                    textView.setText("请先建立连接!");
                }
            }
        });
    }


}
