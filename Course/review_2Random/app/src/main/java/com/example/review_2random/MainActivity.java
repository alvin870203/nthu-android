package com.example.review_2random;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mtxtNum;
    private Button mbtnStart;
    private int random;  //int變數

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mtxtNum = (TextView)findViewById(R.id.textView);
        mbtnStart = (Button)findViewById(R.id.button);

        mbtnStart.setOnClickListener(clickStart);

    }

    private View.OnClickListener clickStart = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            //1-999中隨機取一個數
            //int變數 = (int) (Math.random()*幾個+從幾開始);
            random = (int) (Math.random()*999+1);

            mtxtNum.setText(""+Integer.toString(random));

        }
    };

}
