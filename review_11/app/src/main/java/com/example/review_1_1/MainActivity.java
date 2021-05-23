package com.example.review_1_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //宣告物件名稱
    //private 物件型態 物件名稱;
    private TextView mtxtAns;
    private Button mbtnCal;
    private int ans = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //利用findViewById()取得介面元件，並設定給對應的物件
        //程式宣告時的命名 = (根據不同元件打不同的程式碼)findViewById(R.id.layout設計的元件的id);
        //物件名稱 = (介面元件型態)findViewById(R.id.介面元件名稱id);
        mtxtAns = (TextView)findViewById(R.id.textView);
        mbtnCal = (Button)findViewById(R.id.button);

        //按鈕物件名稱.setOnClickListener(函數名稱);
        mbtnCal.setOnClickListener(clickCal);

    }

    //按下按鈕後要執行的動作
    //private View.OnClickListener 函數名稱 = new View.OnClickListener
    private View.OnClickListener clickCal = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //按下按鈕後要執行的動作
            for (int i=1; i<=10; i++) {
                ans = ans + i;

                //要更改之變數.設定文字函數("答案=" + 將整數型態ans轉型為string型態並顯示出來);
                //textview之物件名稱.setText("文字" + Integer.toString(integer變數));
                //比較2EditText的//result的textview物件變數.setText("" + new DecimalFormat("0.000").format(double變數))
                mtxtAns.setText("答案=" + Integer.toString(ans));
            }

        }
    };

}
