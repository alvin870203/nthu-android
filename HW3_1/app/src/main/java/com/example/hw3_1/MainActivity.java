package com.example.hw3_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private Button mbtnStart, mbtnPause, mbtnReset, mbtnEnd;    //開始,暫停,歸零,結束的按鈕
    private TextView mtxtTime;  //時間顯示文字
    int on=0;   //變數"on"決定Handler執行與否，也就是計時或暫停
    int totalsec=0, min=0, sec=0; //時間總秒數,換算分鐘,換算秒數的整數

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mbtnStart = (Button)findViewById(R.id.btnStart);     //開始按鈕
        mbtnPause = (Button)findViewById(R.id.btnPause);    //暫停按鈕
        mbtnReset = (Button)findViewById(R.id.btnReset);    //歸零按鈕
        mbtnEnd = (Button)findViewById(R.id.btnEnd);       //結束按鈕
        mtxtTime = (TextView)findViewById(R.id.txtTime);    //時間顯示文字
        Timer con = new Timer();    //建立Timer物件，名稱"con"

        mbtnStart.setOnClickListener(StartClick);   //按下"開始"按鈕
        mbtnPause.setOnClickListener(PauseClick);  //按下"暫停"按鈕
        mbtnReset.setOnClickListener(ResetClick);  //按下"歸零"按鈕
        mbtnEnd.setOnClickListener(EndClick);     //按下"結束"按鈕

        //建立TimerTask，名稱"send"，用以發配任務，任務為將message傳到Handler
        TimerTask send = new TimerTask() {
            @Override
            public void run() {
                Message msg = new Message();    //宣告Message，名稱"msg"
                work.sendMessage(msg);
            }
        };
        //Timer名稱.schedule(TimerTask名稱, 首次執行時間, 執行週期)，單位ms
        con.schedule(send,1000,1000);

    }

    //設Handler，名稱"work"，接收到message後執行的動作
    final Handler work = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (on == 1) {              //若變數"on"為1，則執行以下
                totalsec = totalsec + 1;  //每個Timer的執行週期(=1000ms)，時間總秒數便加1
                min = totalsec / 60;    //總秒數除以60的商數，即為換算的分鐘數"min"
                sec = totalsec % 60;     //總秒數除以60的餘數，即為換算的秒數"sec"
                //搭配資料轉型DecimalFormat，輸出時間顯示文字為[min:sec]，格式為[00:00]
                mtxtTime.setText("" + new DecimalFormat("00").format(min) + ":" + new DecimalFormat("00").format(sec));
            }
        }
    };

    //按下"開始"按鈕，變數"on"變為1，Handler執行，計時進行
    private View.OnClickListener StartClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            on = 1;
        }
    };

    //按下"暫停"按鈕，變數"on"變為0，Handler不執行，計時暫停
    private View.OnClickListener PauseClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            on = 0;
        }
    };

    //按下"結束"按鈕，幒時間秒數"totalsec"變為0，歸零計時，並設時間顯示文字為00:00
    private View.OnClickListener EndClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            totalsec = 0;
            mtxtTime.setText("00:00");
            //在此設時間顯示文字為00:00，是為了避免Timer經過一秒delay才顯示時間文字
            // 造成按下歸零未顯示00:00，而等待一秒後直接顯示00:01，此錯誤輸出
        }
    };

    //按下"歸零"按鈕，總時間秒數"totalsec"變為0，且變數"on"變為0，歸零並停止計時
    private View.OnClickListener ResetClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            totalsec = 0;
            mtxtTime.setText("00:00");
            on = 0;
        }
    };

}
