package com.example.hw2_2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int[] picture={R.drawable.rock, R.drawable.scissor, R.drawable.paper}; //匯入石頭剪刀布的圖
    private int rnadom=3; //random為電腦隨機出拳的指標，預設為輸入錯誤
    private TextView mtxtResult; //輸贏結果的文字
    private Button mbtnRock, mbtnPaper, mbtnScissor; //玩家出拳按鈕
    private Button mbtnReplay; //再來一局按鈕
    private ImageView mimgCompShoot; //電腦出拳結果圖片

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
        //玩家按按鈕，決定出剪刀石頭布
        mbtnRock.setOnClickListener(btnclickRock);
        mbtnPaper.setOnClickListener(btnclickPaper);
        mbtnScissor.setOnClickListener(btnclickScissor);
        //重新遊戲
        mbtnReplay.setOnClickListener(btnclickReplay);
    }

    //玩家出石頭
    private View.OnClickListener btnclickRock = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            rnadom = (int) (Math.random()*3); //產生電腦隨機出拳指標，剪刀(1), 石頭(0), 布(2)
            mimgCompShoot.setImageResource(picture[rnadom]); //顯示電腦出拳圖案
            //判斷輸贏並輸出結果文字
            switch (rnadom) {
                case 0: //電腦出石頭
                    mtxtResult.setText("玩家出石頭~雙方平手!");
                    break;
                case 1: //電腦出剪刀
                    mtxtResult.setText("玩家出石頭~玩家勝利!");
                    break;
                case 2: //電腦出布
                    mtxtResult.setText("玩家出石頭~電腦勝利!");
                    break;
                default:
                    mtxtResult.setText("輸入錯誤");
                    break;
            }
        }
    };

    //玩家出剪刀
    private View.OnClickListener btnclickScissor = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            rnadom = (int) (Math.random()*3); //產生電腦隨機出拳指標，剪刀(1), 石頭(0), 布(2)
            mimgCompShoot.setImageResource(picture[rnadom]); //顯示電腦出拳圖案
            //判斷輸贏並輸出結果文字
            switch (rnadom) {
                case 0: //電腦出石頭
                    mtxtResult.setText("玩家出剪刀~電腦勝利!");
                    break;
                case 1: //電腦出剪刀
                    mtxtResult.setText("玩家出剪刀~雙方平手!");
                    break;
                case 2: //電腦出布
                    mtxtResult.setText("玩家出剪刀~玩家勝利!");
                    break;
                default:
                    mtxtResult.setText("輸入錯誤");
                    break;
            }
        }
    };

    //玩家出布
    private View.OnClickListener btnclickPaper = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            rnadom = (int) (Math.random()*3); //產生電腦隨機出拳指標，剪刀(1), 石頭(0), 布(2)
            mimgCompShoot.setImageResource(picture[rnadom]); //顯示電腦出拳圖案
            //判斷輸贏並輸出結果文字
            switch (rnadom) {
                case 0: //電腦出石頭
                    mtxtResult.setText("玩家出布~玩家勝利!");
                    break;
                case 1: //電腦出剪刀
                    mtxtResult.setText("玩家出布~電腦勝利!");
                    break;
                case 2: //電腦出布
                    mtxtResult.setText("玩家出布~雙方平手!");
                    break;
                default:
                    mtxtResult.setText("輸入錯誤");
                    break;
            }
        }
    };

    //再來一局，重啟app畫面
    private View.OnClickListener btnclickReplay = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            recreate();
        }
    };

}
