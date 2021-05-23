package com.example.hw4_1;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

import java.util.Timer;

public class MainActivity extends AppCompatActivity {

    DrawingView drawingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawingView = findViewById(R.id.canvas);  //介面連結
        drawingView.setOnTouchListener(new TouchListener());  //觸碰螢幕觸發TochListener.java
    }

    //按下"Clear"按鈕，執行清除畫面動作
    public void deleteDrawing (View view) {
        drawingView.erase();
    }

}

