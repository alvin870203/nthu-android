package com.example.a7stream;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    private Button mbtnWrite, mbtnRead, mbtnClear;
    private TextView mtxtData;
    private EditText medtData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mbtnWrite = (Button)findViewById(R.id.btnWrite);
        mbtnRead = (Button)findViewById(R.id.btnRead);
        mbtnClear = (Button)findViewById(R.id.btnClear);
        mtxtData = (TextView)findViewById(R.id.txtData);
        medtData = (EditText)findViewById(R.id.edtData);

        mbtnWrite.setOnClickListener(Write);
        mbtnRead.setOnClickListener(Read);
        mbtnClear.setOnClickListener(Clear);

    }

    private View.OnClickListener Write = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            FileOutputStream fileOut = null;
            BufferedOutputStream bufFileOut = null;
            try {
                fileOut = openFileOutput("DATA", MODE_APPEND);
                bufFileOut = new BufferedOutputStream(fileOut);
                bufFileOut.write(medtData.getText().toString().getBytes());
                bufFileOut.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private View.OnClickListener Read = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            try {
                FileInputStream fileIn = openFileInput("DATA");
                BufferedInputStream bufFileIn = new BufferedInputStream(fileIn);

                byte[] bufBytes = new byte[10];
                mtxtData.setText("");
                do {
                    int c = bufFileIn.read(bufBytes);
                    if (c == -1)
                        break;
                    else
                        mtxtData.append(new String(bufBytes));
                }while (true);
                bufFileIn.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };

    private View.OnClickListener Clear = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            FileOutputStream fileOut = null;
            try {
                fileOut = openFileOutput("DATA", MODE_PRIVATE);
                fileOut.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    };


}
