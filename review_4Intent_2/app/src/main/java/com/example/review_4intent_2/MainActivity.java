package com.example.review_4intent_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    //Intent的AndroidManifest.xml一定要修
    //Intent使用方式(2)：資料傳送

    private EditText medt1;
    private EditText medt2;
    private Button mbtn;
    private double n1, n2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        medt1 = (EditText)findViewById(R.id.editText);
        medt2 = (EditText)findViewById(R.id.editText2);
        mbtn = (Button)findViewById(R.id.button);

        mbtn.setOnClickListener(click);

    }

    private View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent it = new Intent();
            it.setClass(MainActivity.this, MainActivity2.class);

            n1 = Double.parseDouble(medt1.getText().toString());
            n2 = Double.parseDouble(medt2.getText().toString());

            //-------------
            //建立一個Bundle物件，然後呼叫putXXX()方法把資料和資料名稱放到Bundle中，再用putExtras將Bundle放入Intent
            Bundle bun = new Bundle();  //Bundle bundle物件名稱 = new Bundle();
            bun.putDouble("DATA_n1", n1); //bundle物件名稱.put資料型態("資料名稱", 資料變數);
            bun.putDouble("DATA_n2", n2);  //一個bunble物件可加入多筆不同型態資料
            it.putExtras(bun);  //intent物件名稱.putExtras(bundle物件名稱);
            //-------------

            startActivity(it);
            finish();
        }
    };

}
