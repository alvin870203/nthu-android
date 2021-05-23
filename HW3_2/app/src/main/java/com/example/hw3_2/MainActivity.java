package com.example.hw3_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private int[] picture={R.drawable.rock, R.drawable.scissor, R.drawable.paper}; //匯入石頭剪刀布的圖
    private int random=3; //random為電腦隨機出拳的指標，預設為輸入錯誤
    private TextView mtxtResult; //輸贏結果的文字
    private Button mbtnRock, mbtnPaper, mbtnScissor; //玩家出拳按鈕
    private Button mbtnReplay; //再來一局按鈕
    private ImageView mimgCompShoot; //電腦出拳結果圖片
    private Button mbtnShow; //統計局數按鈕
    int num=0, win=0, flat=0, loss=0; //局數

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //介面連結
        mtxtResult = (TextView)findViewById(R.id.txtResult);
        mbtnRock = (Button)findViewById(R.id.btnRock);
        mbtnPaper = (Button)findViewById(R.id.btnPaper);
        mbtnScissor = (Button)findViewById(R.id.btnScissor);
        mbtnReplay = (Button)findViewById(R.id.btnReplay);
        mimgCompShoot = (ImageView)findViewById(R.id.imgCompShoot);
        mbtnShow = (Button)findViewById(R.id.btnShow);
        //玩家按按鈕，決定出剪刀石頭布
        mbtnRock.setOnClickListener(btnclickRock);
        mbtnPaper.setOnClickListener(btnclickPaper);
        mbtnScissor.setOnClickListener(btnclickScissor);
        //重啟遊戲
        mbtnReplay.setOnClickListener(btnclickReplay);
        //統計局數
        mbtnShow.setOnClickListener(btnclickShow);
    }

    //玩家出石頭
    private View.OnClickListener btnclickRock = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            random = (int) (Math.random()*3); //產生電腦隨機出拳指標，剪刀(1), 石頭(0), 布(2)
            mimgCompShoot.setImageResource(picture[random]); //顯示電腦出拳圖案
            //判斷輸贏並輸出結果文字
            switch (random) {
                case 0: //電腦出石頭
                    mtxtResult.setText("玩家出石頭~雙方平手!");
                    flat = flat + 1;
                    break;
                case 1: //電腦出剪刀
                    mtxtResult.setText("玩家出石頭~玩家勝利!");
                    win = win + 1;
                    break;
                case 2: //電腦出布
                    mtxtResult.setText("玩家出石頭~電腦勝利!");
                    loss = loss + 1;
                    break;
                default:
                    mtxtResult.setText("輸入錯誤");
                    break;
            }
            num = num + 1; //出拳局數加一
        }
    };

    //玩家出剪刀
    private View.OnClickListener btnclickScissor = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            random = (int) (Math.random()*3); //產生電腦隨機出拳指標，剪刀(1), 石頭(0), 布(2)
            mimgCompShoot.setImageResource(picture[random]); //顯示電腦出拳圖案
            //判斷輸贏並輸出結果文字
            switch (random) {
                case 0: //電腦出石頭
                    mtxtResult.setText("玩家出剪刀~電腦勝利!");
                    loss = loss + 1;
                    break;
                case 1: //電腦出剪刀
                    mtxtResult.setText("玩家出剪刀~雙方平手!");
                    flat = flat + 1;
                    break;
                case 2: //電腦出布
                    mtxtResult.setText("玩家出剪刀~玩家勝利!");
                    win = win + 1;
                    break;
                default:
                    mtxtResult.setText("輸入錯誤");
                    break;
            }
            num = num + 1; //出拳局數加一
        }
    };

    //玩家出布
    private View.OnClickListener btnclickPaper = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            random = (int) (Math.random()*3); //產生電腦隨機出拳指標，剪刀(1), 石頭(0), 布(2)
            mimgCompShoot.setImageResource(picture[random]); //顯示電腦出拳圖案
            //判斷輸贏並輸出結果文字
            switch (random) {
                case 0: //電腦出石頭
                    mtxtResult.setText("玩家出布~玩家勝利!");
                    win = win + 1;
                    break;
                case 1: //電腦出剪刀
                    mtxtResult.setText("玩家出布~電腦勝利!");
                    loss = loss + 1;
                    break;
                case 2: //電腦出布
                    mtxtResult.setText("玩家出布~雙方平手!");
                    flat = flat + 1;
                    break;
                default:
                    mtxtResult.setText("輸入錯誤");
                    break;
            }
            num = num + 1; //出拳局數加一
        }
    };

    //重啟，重啟app畫面
    private View.OnClickListener btnclickReplay = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            recreate();
        }
    };

    //統計局數，介面切換與資料傳送
    private Button.OnClickListener btnclickShow = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent it = new Intent();   //建立Intent物件
            it.setClass(MainActivity.this, MainActivity2.class);    //設定擁有者和要啟動的Activity

            Bundle bun = new Bundle();  //建立Bundle物件
            bun.putInt("Number", num);  //putXXX()把資料和資料名稱放到Bundle中
            bun.putInt("WinNum", win);
            bun.putInt("FlatNum", flat);
            bun.putInt("LossNum", loss);
            it.putExtras(bun);  //putExtras將Bundle放入Intent

            startActivity(it);  //啟動Intent
            //finish();   //結束這個Activity
        }
    };


}
