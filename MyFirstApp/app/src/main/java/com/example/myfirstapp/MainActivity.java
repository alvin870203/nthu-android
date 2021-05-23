package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button mButton;
    private TextView mTextview;
    private int sum=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton=(Button)findViewById(R.id.button);
        mTextview=(TextView)findViewById(R.id.textView);
        mButton.setOnClickListener(btnClick);

    }

    private View.OnClickListener btnClick=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            for(int i=1;i<=10;i++)
            {
                sum=sum+i;
                mTextview.setText("答案="+Integer.toString(sum));
            }
        }
    };
}
