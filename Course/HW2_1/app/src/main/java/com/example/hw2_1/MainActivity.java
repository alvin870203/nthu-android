package com.example.hw2_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button mbtnStart; //確認輸入答案的按鈕
    private EditText medtNum; //玩家猜的輸入
    private TextView mtxtAns; //提示範圍或恭喜成功的文字
    private int random; //開啟遊戲時電腦隨機產生的正確答案
    private int guess, low=1, high=99; //玩家猜的整數；初始提示範圍low=0 ~ high=99

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //介面連結
        mbtnStart = findViewById(R.id.btnStart);
        medtNum = findViewById(R.id.edtNum);
        mtxtAns = findViewById(R.id.txtAns);
        startrandom(); //開啟遊戲隨機產生1~99某整數為正確答案
        mbtnStart.setOnClickListener(btnclickstart); //按按鈕後執行的動作
    }

    //開啟遊戲隨機產生1~99某整數為正確答案
    private void startrandom() {
        random = (int) (Math.random()*99+1); //產生1~99的整數亂數
    };

    //按下確認按鈕後執行的動作
    private View.OnClickListener btnclickstart = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            guess = Integer.parseInt(medtNum.getText().toString()); //讀入EditText輸入，資料轉型為整數guess

            //比較猜的數字與答案之大小相等與否
            if (guess == random) { //猜中答案，輸出恭喜答對之文字
                mtxtAns.setText("恭喜答對!"); //TextView的文字
                medtNum.setHint("恭喜答對!"); //EditText的Hint文字
                mbtnStart.setText("新的一局"); //猜中後，按鈕文字由"確認"改為"新的一局"
                mbtnStart.setOnClickListener(btnclickreplay); //按下"新的一局"按鈕後執行的動作
            } else if (guess > random && guess <= high) { //猜的數字比答案大
                high = guess; //提示範圍的最大值改為所猜的數字
                mtxtAns.setText("小一點，在"+Integer.toString(low)+"~"+Integer.toString(high)+"之間"); //更新新的提示範圍
                medtNum.setHint("輸入"+Integer.toString(low)+"~"+Integer.toString(high)); //更新EditText中Hint的提示文字範圍
            } else if (guess <random && guess >= low) { //猜的數字比答案小
                low = guess; //提示範圍的最小值改為所猜的數字
                mtxtAns.setText("大一點，在"+Integer.toString(low)+"~"+Integer.toString(high)+"之間"); //更新新的提示範圍
                medtNum.setHint("輸入"+Integer.toString(low)+"~"+Integer.toString(high)); //更新EditText中Hint的提示文字範圍
            } else { //其他狀況，代表輸入錯誤，可能不在提示範圍內，要求玩家重新輸入
                mtxtAns.setText("未在提示範圍內，請輸入"+Integer.toString(low)+"~"+Integer.toString(high)+"之間"); //更新新的提示範圍
            }

            medtNum.setText(""); //按確認後，清除EditText輸入數字
        }
    };

    //按下"新的一局"Button後執行的動作
    private View.OnClickListener btnclickreplay = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            recreate(); //重新啟動遊戲，回到初始狀態
        }
    };

}
