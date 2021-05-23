package com.example.review_4intent1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //Intent物件是當成需要幫手的時候，對Android系統發出的訊號。
    //當Android系統收到Intent物件的時候，會根據其中的描述，啟動適合的程式來處理。
    //Intent使用方式(1)：介面切換

    private TextView mtxtPage1;
    private Button mbtnChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        find();  //所有findViewById的函數
        mbtnChange.setOnClickListener(clickCange);
    }

    //自訂無輸入輸出的函數，private void 函數名稱() {};
    private void find() {
        mtxtPage1 = (TextView)findViewById(R.id.textView);
        mbtnChange = (Button)findViewById(R.id.button);
    };

    private Button.OnClickListener clickCange = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {

            //建立Intent物件
            //Intent intent物件名稱 = new Intent();
            Intent it = new Intent();

            //設定啟動的Activity之類別
            //intent物件名稱.setClass(當前activity名稱.this, 欲啟動之activity名稱.class);
            it.setClass(MainActivity.this, MainActivity2.class);

            //啟動Intent
            //startActivity(intent物件名稱);
            startActivity(it);

            //結束這個Activity
            //finish();
            finish();

        }
    };

}
