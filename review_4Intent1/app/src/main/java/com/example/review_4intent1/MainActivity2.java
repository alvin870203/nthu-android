//新增一個MainActivity2.java檔
//主程式資料夾 -> New -> Java Class

package com.example.review_4intent1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity2 extends MainActivity {
                          //補上 extends MainActivity
    private TextView mtxtPage2;
    private Button mbtnBack;

    //補上protected void onCreate(Bundle savedInstanceState) {
    //        super.onCreate(savedInstanceState);
    //        }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //建立想要顯示的介面布局檔
        //setContentView(R.layout.介面布局檔名稱);
        setContentView(R.layout.page2);

        find2();
        mbtnBack.setOnClickListener(clickBack);

    }

    private void find2() {
        mtxtPage2 = (TextView)findViewById(R.id.textView2);
        mbtnBack = (Button)findViewById(R.id.button2);
    };

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
