package com.example.exam_105033110;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText medt;
    private TextView mtxt;
    private Button mbtnSort;
    private Button mbtnChange;
    private int input, radiusInt;
    private double radius=0;
    private char A[];
    private char Ainvert[];
    private int n0=0, n1=0, n2=0,n3=0,n4=0, n5=0, n6=0, n7=0, n8=0, n9=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        medt = (EditText)findViewById(R.id.editText);
        mtxt = (TextView)findViewById(R.id.textView);
        mbtnSort = (Button)findViewById(R.id.button);
        mbtnChange = (Button)findViewById(R.id.button2);

        mbtnSort.setOnClickListener(clickSort);
        mbtnChange.setOnClickListener(clickChange);
    }

    private View.OnClickListener clickSort = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            input = Integer.parseInt(medt.getText().toString());
            String inputString = Integer.toString(input);
            A = inputString.toCharArray();
            Ainvert = inputString.toCharArray();
            int length=0;
            length = A.length;
            n0=0;
            n1=0;
            n2=0;
            n3=0;
            n4=0;
            n5=0;
            n6=0;
            n7=0;
            n8=0;
            n9=0;
            radius = 0;
            for (int i=0; i<length; i++) {

                Integer a = new Integer(A[i]);
                if (a == 48)
                    n0++;
                if (a == 49)
                    n1++;
                if (a == 50)
                    n2++;
                if (a == 51)
                    n3++;
                if (a == 52)
                    n4++;
                if (a == 53)
                    n5++;
                if (a == 54)
                    n6++;
                if (a == 55)
                    n7++;
                if (a == 56)
                    n8++;
                if (a == 57)
                    n9++;

                //Ainvert[i] = A[length-1-i];
            }
            //mtxt.setText(""+Integer.toString(n5));

            for (int j=length-1; j>=0; j--) {
                if (n9 > 0){
                    radius = radius + 9*Math.pow(10,length-1-j);
                    n9--;
                }
                else if (n8 > 0){
                    radius = radius + 8*Math.pow(10,length-1-j);
                    n8--;
                }
                else if (n7 > 0){
                    radius = radius + 7*Math.pow(10,length-1-j);
                    n7--;
                }
                else if (n6 > 0){
                    radius = radius + 6*Math.pow(10,length-1-j);
                    n6--;
                }
                else if (n5 > 0){
                    radius = radius + 5*Math.pow(10,length-1-j);
                    n5--;
                }
                else if (n4 > 0){
                    radius = radius + 4*Math.pow(10,length-1-j);
                    n4--;
                }
                else if (n3 > 0){
                    radius = radius + 3*Math.pow(10,length-1-j);
                    n3--;
                }
                else if (n2 > 0){
                    radius = radius + 2*Math.pow(10,length-1-j);
                    n2--;
                }
                else if (n1 > 0){
                    radius = radius + 1*Math.pow(10,length-1-j);
                    n1--;
                }
                else if (n0 > 0){
                    radius = radius + 0*Math.pow(10,length-1-j);
                    n0--;
                }
            }
            //String outputString = new  String(Ainvert);
            //mtxt.setText(""+outputString);
            //radius = Integer.parseInt(outputString);
            radiusInt = (int)radius;
            mtxt.setText(""+Integer.toString(radiusInt));
        }
    };


    private View.OnClickListener clickChange = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent it = new Intent();
            it.setClass(MainActivity.this, MainActivity2.class);
            double rValue = new Double(radius);
            //-------------
            //建立一個Bundle物件，然後呼叫putXXX()方法把資料和資料名稱放到Bundle中，再用putExtras將Bundle放入Intent
            Bundle bun = new Bundle();  //Bundle bundle物件名稱 = new Bundle();
            bun.putDouble("DATA_r", rValue); //bundle物件名稱.put資料型態("資料名稱", 資料變數);
            it.putExtras(bun);  //intent物件名稱.putExtras(bundle物件名稱);
            //-------------
            startActivity(it);
            finish();
        }
    };

}
