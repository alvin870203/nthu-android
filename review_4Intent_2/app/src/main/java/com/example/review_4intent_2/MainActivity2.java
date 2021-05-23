package com.example.review_4intent_2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity2 extends MainActivity {

    private TextView mtxtAns;
    private Button mbtnReturn;
    private double n1_result, n2_result;

    //使用onCreate()方法初始化畫面內容
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page2);

        mtxtAns = (TextView)findViewById(R.id.textView);
        mbtnReturn = (Button)findViewById(R.id.button2);

        mbtnReturn.setOnClickListener(clickReturn);

        //----------
        //呼叫getIntent()方法取得傳送過來的Intent物件
        Intent it = getIntent();  //Intent 來源的intent名稱 = getIntent();
        //Intent物件中取出Buncle物件
        Bundle bun2 = it.getExtras();  //Bundle 此處的bundle名稱 = 來源的intent名稱.getExtras();
        //根據資料名稱取出Bundle物件中的資料
        n1_result = bun2.getDouble("DATA_n1");  //變數名稱 = 此處的bundle名稱.get變數型態("資料名稱")
        n2_result = bun2.getDouble("DATA_n2");
        //----------

        mtxtAns.setText(Double.toString(n1_result*n2_result));  //將double轉為string，Double.toString(double變數)

    }

    private View.OnClickListener clickReturn = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent it2 = new Intent();
            it2.setClass(MainActivity2.this, MainActivity.class);
            startActivity(it2);
            finish();
        }
    };

}
