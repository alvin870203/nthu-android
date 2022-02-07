package com.example.review_5timer;

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

    //Timer()
    //功能：不斷重複執行同一動作
    //例如：Button按一次可執行特定程式A，但要連續執行的話就必須連續按好幾次Button
    //透過Timer，按一次Button即可不斷執行程式A

    //Timer：利用此類別設定工作schedule，並設定多久要第一次執行schedule內的任務以及執行任務的頻率
    //Handler：當街收到message後執行功能(演算法寫在此部分)
    //TimerTask：搭配timer，任務是送message給handler

    private SeekBar msb;
    private TextView mtxt;
    private Button mbtn;
    private int on=0;
    private float progressNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        msb = (SeekBar)findViewById(R.id.seekBar);
        mtxt = (TextView)findViewById(R.id.textView);
        mbtn = (Button)findViewById(R.id.button);

        //建立一個Tmer物件
        //Timer timer物件名稱 = new Timer();
        Timer con = new Timer();

        mbtn.setOnClickListener(click);

        //建立TimerTask：發配任務，任務為將message傳到Handler
        //TimerTask timertask物件名稱 = new TimerTask() {
        TimerTask send = new TimerTask() {
            @Override
            public void run() {

                //宣告Message
                //Message message物件名稱 = new Message();
                Message msg = new Message();

                //handler物件名稱.sendMessage(message物件名稱);
                work.sendMessage(msg);
            }
        };

        //Timer物件名稱.schedule(timertask物件名稱, 首次執行時間, 執行週期);    單位：ms
        con.schedule(send, 1000, 100);

    }


    //建立handler
    //final Handler handler物件名稱 = new Handler() {
    //    public void handleMessage(Message message物件名稱) {
    //        super.handleMessage(message物件名稱);
    //        每週期要執行的動作演算法
    //     }
    //};
    final Handler work = new Handler() {
      public void handleMessage(Message msg) {
          super.handleMessage(msg);
          if (on == 1) {
              //讀取seekBar數值
              progressNum = msb.getProgress();  //變數名稱 = seekbar物件名稱.setProgress();
              mtxt.setText(""+progressNum);
          }
      }
    };
    //在打這段程式碼的時候，如果遇到Handler底部是紅線或是handleMessage為灰色未定義型態請到最上面點開import查看
    //將import.java.util.logging.Handler;
    //改成import android.os.Handler;
    //因為handlerMessage是定義在android.os.Handler這個類別


    private View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            on = 1;
            //Timer()一直都在執行，在於on是否為1，來決定是否有動作產生
        }
    };

}
