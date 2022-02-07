package com.example.a4intent;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity2 extends MainActivity {

    private TextView mtxtShow2, mtxtAnswer;
    private Button mbtnReturn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page2);
        find2();
        mbtnReturn.setOnClickListener(Return);
        Intent it = getIntent();
        Bundle bun2 = it.getExtras();
        double x_result = bun2.getDouble("DATA_x");
        double y_result = bun2.getDouble("DATA_y");
        mtxtAnswer.setText(Double.toString(x_result*y_result));
    }

    private void find2()
    {
        mtxtShow2 = (TextView)findViewById(R.id.txtShow2);
        mbtnReturn = (Button)findViewById(R.id.btnReturn);
        mtxtAnswer = (TextView)findViewById(R.id.txtAnswer);
    };

    private View.OnClickListener Return = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent it2 = new Intent();
            it2.setClass(MainActivity2.this,MainActivity.class);
            startActivity(it2);
            finish();
        }
    };

}
