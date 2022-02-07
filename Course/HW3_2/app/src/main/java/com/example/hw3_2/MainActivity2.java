package com.example.hw3_2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity2 extends MainActivity {

    private TextView mtxtNum;
    private Button mbtnBack;

    //onCreate初始畫面
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page2);
        mtxtNum = (TextView)findViewById(R.id.txtNum);
        mbtnBack = (Button)findViewById(R.id.btnBack);
        mbtnBack.setOnClickListener(Back);

        //資料傳送
        Intent it = getIntent();    //getIntent()取得傳送過來的Intent物件
        Bundle bun2 = it.getExtras();   //從Intent物件中取出Bundle物件
        int num_result = bun2.getInt("Number"); //根據資料名稱取出Bundle物件中的資料
        int win_result = bun2.getInt("WinNum");
        int flat_result = bun2.getInt("FlatNum");
        int loss_result = bun2.getInt("LossNum");
        mtxtNum.setText("玩家: 贏"+Integer.toString(win_result)+", 平手"+Integer.toString(flat_result)+", 輸"+Integer.toString(loss_result)+", 共"+Integer.toString(num_result)+"局");

    }

    //Intent換頁，介面切換
    private View.OnClickListener Back = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent it2 = new Intent();
            it2.setClass(MainActivity2.this, MainActivity.class);
            startActivity(it2);
            finish();
        }
    };

}
