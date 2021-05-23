package com.example.a2editview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    private TextView textView3,textView2;
    private Button button;
    private EditText editText,editText2;
    private double BMI,H,W;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView3 = (TextView) findViewById(R.id.textView2);
        textView2 = (TextView) findViewById(R.id.textView3);
        button = (Button) findViewById(R.id.button);
        editText = (EditText) findViewById(R.id.editText4);
        editText2 = (EditText) findViewById(R.id.editText5);
        button.setOnClickListener(btnclick);
    }

    private View.OnClickListener btnclick=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            H = Double.parseDouble(editText.getText().toString());
            W = Double.parseDouble(editText2.getText().toString());
            BMI = W / (H * H);
            textView3.setText("" + new DecimalFormat("0.00").format(BMI));
            if (BMI > 24) {
                textView2.setText("該減肥囉!!");
            }
            if (BMI < 18.5) {
                textView2.setText("太瘦了 多吃點");
            }
            if (BMI > 18.5 && BMI < 24) {
                textView2.setText("BMI正常 繼續保持喔");
            }
        }
    };

}
