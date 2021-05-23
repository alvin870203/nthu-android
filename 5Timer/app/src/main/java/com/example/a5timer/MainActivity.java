package com.example.a5timer;

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

public class MainActivity extends AppCompatActivity {

    private Button btn;
    private SeekBar sb;
    private TextView read;
    int on=0;
    float result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button) findViewById(R.id.button);
        sb = (SeekBar) findViewById(R.id.seekBar);
        read = (TextView) findViewById(R.id.textView);
        Timer con = new Timer();

        btn.setOnClickListener(btnclick);
        TimerTask send = new TimerTask() {
            @Override
            public void run() {
                Message msg = new Message();
                work.sendMessage(msg);
            }
        };
        con.schedule(send,1000,100);
    }

    final Handler work = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (on == 1) {
                result=sb.getProgress();
                read.setText(""+result);
            }
        }
    };

    private View.OnClickListener btnclick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            on=1;
        }
    };

}
