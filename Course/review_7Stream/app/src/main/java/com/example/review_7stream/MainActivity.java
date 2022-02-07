package com.example.review_7stream;

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

//Stream –由App建立檔案來儲存資料 - 寫入、讀取、清除
//資料寫入檔案→ FileOutputStream
//讀取檔案資料→ FileInputStream
//清除檔案資料→使用FileOutputStream清除

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

    //資料寫入檔案→ FileOutputStream
    //為了提升讀寫大型檔案的效率，通常會配合使用BufferedInputStream和BufferedOutputStream兩個類別
    private View.OnClickListener Write = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            FileOutputStream fileOut = null;

            BufferedOutputStream bufFileOut = null;

            try {  //1.呼叫openFileOutput()方法，取得一個FileOutputStream物件
                fileOut = openFileOutput("DATA", MODE_APPEND);  //寫入模式：[MODE：附加模式]
                bufFileOut = new BufferedOutputStream(fileOut);  //2.建立BufferedOutputStream物件
                bufFileOut.write(medtData.getText().toString().getBytes());  //3.將EditText內容轉成byte型態陣列，寫入Buffer
                bufFileOut.close();  //4.關閉檔案
            } catch (Exception e) {
                e.printStackTrace();  //例外處理  //處理錯誤的程式碼  //有關檔案寫入存取清除，皆須使用到例外處理
            }
        }
    };

    //讀取檔案資料→ FileInputStream
    private View.OnClickListener Read = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            try {
                FileInputStream fileIn = openFileInput("DATA");  //1.呼叫openFileInput()方法，取得一個FileInputStream物件
                                                       //"要開啟的檔案名稱"

                BufferedInputStream bufFileIn = new BufferedInputStream(fileIn);  //2.建立BufferedInputStream物件

                byte[] bufBytes = new byte[10];  //3.宣告一個byte陣列，後續用來存放讀取的資料
                mtxtData.setText("");
                do {
                    int c = bufFileIn.read(bufBytes);  //4.讀取InputStream的資料，並存入Byte陣列
                    if (c == -1)  //5.c傳回-1表示資料讀取完畢
                        break;
                    else
                        mtxtData.append(new String(bufBytes));  //6.將讀取結果顯示在TextView
                }while (true);
                bufFileIn.close();  //7.關閉檔案
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };


    //清除檔案資料→使用FileOutputStream清除
    //利用FileOutputStream的覆寫模式，開啟檔案後不另外寫入資料即被清除
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
