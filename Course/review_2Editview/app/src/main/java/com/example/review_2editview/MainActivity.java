package com.example.review_2editview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private EditText medtHeight;
    private EditText medtWeight;
    private Button mbtnCal;
    private TextView mtxtBMI;
    private TextView mtxtComment;
    private double H, W, BMI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        medtHeight = (EditText)findViewById(R.id.editText);
        medtWeight = (EditText)findViewById(R.id.editText2);
        mbtnCal = (Button)findViewById(R.id.button);
        mtxtBMI = (TextView)findViewById(R.id.textView2);
        mtxtComment = (TextView)findViewById(R.id.textView3);

        mbtnCal.setOnClickListener(clickCal);

    }

    private View.OnClickListener clickCal = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            //資料轉型
            //1. 先透過getTex()t抓取EditText之值
            //2. 利用toString()轉成字串
            //3. 透過Double.parseDouble將字串轉乘double
            //double變數 = Double.parseDouble(input的edittext物件名稱.getText().toString());
            H = Double.parseDouble(medtHeight.getText().toString());
            W = Double.parseDouble(medtWeight.getText().toString());

            BMI = W / (H * H);

            //資料轉型
            //透過decimalFormat將結果變成小數點後三位(string格式)
            //result的textview物件變數.setText("" + new DecimalFormat("0.000").format(double變數))
            //比較1-1的//textview之物件名稱.setText("文字" + Integer.toString(integer變數));
            mtxtBMI.setText("" + new DecimalFormat("0.000").format(BMI));

            if (BMI > 24) {
                mtxtComment.setText("該減肥囉!!");
            }
            if (BMI < 18.5) {
                mtxtComment.setText("太瘦了 多吃點");
            }
            //且&&
            if (BMI > 18.5 && BMI < 24) {
                mtxtComment.setText("BMI正常 繼續保持喔");
            }

        }
    };

}
