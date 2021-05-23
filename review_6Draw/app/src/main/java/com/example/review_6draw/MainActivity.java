package com.example.review_6draw;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;


//(1)畫布設定 draw.java
//(2)數據儲存 data.java
//(3)介面布局 activity_main.xml
//(4)動態曲線繪圖 MainActivity.java
public class MainActivity extends AppCompatActivity {

    private Button btn;
    private SeekBar sb;
    private TextView read;
    private draw draw_view;
    private int on=0;
    private float result;
    private boolean signal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button)findViewById(R.id.button);
        sb = (SeekBar)findViewById(R.id.seekBar);
        read = (TextView)findViewById(R.id.textView);
        draw_view = (draw)findViewById(R.id.view);

        Timer con = new Timer();

        btn.setOnClickListener(btnclick);

        for (int k=0; k<800; k++) { //設定data初始的x值
            data.xvalue[k]=k+101;
        }
        for (int i=0; i<800; i++) { //設定data初始的y值
            data.yvalue[i]=900;
        }

        final Handler work = new Handler()
        {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (on == 1) {
                    drawView();  //
                    draw_view.invalidate();  //讀新資料後重畫
                }
            }
        };

        final TimerTask send = new TimerTask() {
            @Override
            public void run() {
                Message msg = new Message();
                work.sendMessage(msg);
            }
        };

        con.schedule(send,1000,100);
    }


    private View.OnClickListener btnclick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (on == 0) {
                btn.setText("OFF");
                signal = true;
                on = 1;
            }
            else {
                signal = false;
                on = 0;
                btn.setText("START");
            }
        }
    };

    private void drawView()
    {
        if (signal) {
            result = sb.getProgress();
            read.setText(""+result);
            int k = 900 - (int) result;
            for (int s = data.num-1; s>0; s--) {
                data.yvalue[s] = data.yvalue[s-1];
            }
            data.yvalue[0] = k;
        }
    }

}
