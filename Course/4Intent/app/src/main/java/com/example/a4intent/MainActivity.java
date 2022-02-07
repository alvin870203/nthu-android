package com.example.a4intent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mtxtShow;
    private Button mChange;
    private EditText medtNUM1,medtNUM2;
    private double x,y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        find();
        mChange.setOnClickListener(Change);
    }

    private void find()
    {
        mtxtShow = (TextView)findViewById(R.id.txtShow);
        mChange = (Button)findViewById(R.id.btnChange);
        medtNUM1 = (EditText)findViewById(R.id.editText);
        medtNUM2 = (EditText)findViewById(R.id.editText2);
    };

    private Button.OnClickListener Change = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent it = new Intent();
            it.setClass(MainActivity.this,MainActivity2.class);

            x = Double.parseDouble(medtNUM1.getText().toString());
            y = Double.parseDouble(medtNUM2.getText().toString());

            Bundle bun = new Bundle();
            bun.putDouble("DATA_x", x);
            bun.putDouble("DATA_y", y);
            it.putExtras(bun);
            startActivity(it);
            finish();
        }

    };

}
