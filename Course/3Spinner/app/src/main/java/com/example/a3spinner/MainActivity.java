package com.example.a3spinner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Spinner mSpnTea,mSpnSugar;
    private TextView mShowOrder;
    private Button mBtnFinish;
    private String msTea,msSugar;  //用來儲存選單所選的string

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSpnTea=(Spinner)findViewById(R.id.spnTea);
        mSpnSugar=(Spinner)findViewById(R.id.spnSugar);
        mShowOrder=(TextView)findViewById(R.id.showOrder);
        mBtnFinish=(Button)findViewById(R.id.button);

        //spinner物件名稱.setOnItemSelectedListener(函數名稱);
        mSpnTea.setOnItemSelectedListener(spnTeaOnItemSelected);
        mSpnSugar.setOnItemSelectedListener(spnSugarOnItemSelected);
        mBtnFinish.setOnClickListener(btnFinish);

    }

    //先輸入private AdapterView.OnItemSelectedListener 函數名稱 = new AdapterView.OnItemSelectedListener() {}
    //完成後會出現語法錯誤
    //之後點選紅色驚嘆號 -> Implement methods，就會自動加入需要的方法
    private AdapterView.OnItemSelectedListener spnTeaOnItemSelected = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                 //parent,  View view,  int position,  long id

            //string名稱 = parent.getSelectedItem().toString();
            msTea = parent.getSelectedItem().toString();
        }

        //用不到
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };


    private AdapterView.OnItemSelectedListener spnSugarOnItemSelected = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            msSugar = parent.getSelectedItem().toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };


    private View.OnClickListener btnFinish = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String s1="飲料名稱：", s2="甜度：";

            //比較字串是否相等，字串1.equals(字串2)
            if (msTea.equals(getString(R.string.green_tea)))
                s1+=getString(R.string.green_tea);
            if (msTea.equals(getString(R.string.black_tea)))
                s1+=getString(R.string.black_tea);
            if (msTea.equals(getString(R.string.milk_tea)))
                s1+=getString(R.string.milk_tea);

            if (msSugar.equals(getString(R.string.no_sugar)))
                s2+=getString(R.string.no_sugar);
            if (msSugar.equals(getString(R.string.thirty_percent)))
                s2+=getString(R.string.thirty_percent);
            if (msSugar.equals(getString(R.string.half)))
                s2+=getString(R.string.half);
            if (msSugar.equals(getString(R.string.seventy_percent)))
                s2+=getString(R.string.seventy_percent);
            if (msSugar.equals(getString(R.string.all_sugar)))
                s2+=getString(R.string.all_sugar);

            mShowOrder.setText(s1+"\n"+s2);  //換行，+"\n"+

        }
    };

}
