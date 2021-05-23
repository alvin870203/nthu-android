package com.example.exam_105033110;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity2 extends MainActivity {

    private Button mbtnBack;
    private TextView mtxtRadius;
    public double radius2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page2);

        mbtnBack = (Button)findViewById(R.id.button3);
        mtxtRadius = (TextView)findViewById(R.id.textView2);

        mbtnBack.setOnClickListener(clickBack);

        //----------
        //呼叫getIntent()方法取得傳送過來的Intent物件
        Intent it = getIntent();  //Intent 來源的intent名稱 = getIntent();
        //Intent物件中取出Buncle物件
        Bundle bun2 = it.getExtras();  //Bundle 此處的bundle名稱 = 來源的intent名稱.getExtras();
        //根據資料名稱取出Bundle物件中的資料
        radius2 = bun2.getDouble("DATA_r");  //變數名稱 = 此處的bundle名稱.get變數型態("資料名稱")
        //----------

        mtxtRadius.setText("Radius："+Double.toString(radius2));  //將double轉為string，Double.toString(double變數)

        data.canvasR = new Float(radius2);

    }


    private View.OnClickListener clickBack = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent it2 = new Intent();
            it2.setClass(MainActivity2.this, MainActivity.class);
            startActivity(it2);
            finish();
        }
    };


}
