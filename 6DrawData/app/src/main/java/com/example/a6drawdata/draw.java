package com.example.a6drawdata;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class draw extends View {
    public draw(Context context) {
        super(context);
    }

    public draw(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public draw(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private int yscale = 100;
    private String[] ylabel = new String[900/yscale];
    protected void onDraw(Canvas canvas)
    {
        final Paint paint = new Paint();    //建立畫筆
        paint.setColor(Color.WHITE);    //設定畫筆顏色
        canvas.drawRect(0,0,1000,1000,paint);   //繪製1000*1000白底
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(3);    //設定畫筆粗度
        canvas.drawLine(100,900,900,900,paint); //繪製x軸
        canvas.drawLine(100,100,100,900,paint); //繪製y軸
        for (int i = 0; i<9; i++){
            canvas.drawLine(90,100+i*yscale,100,100+i*yscale,paint);
            ylabel[i]=""+(8-i)*100;
            paint.setTextSize(30f); //設定y座標文字大小
            canvas.drawText(ylabel[i],30,105+i*yscale,paint);   //畫y座標數值
        }
        paint.setColor(Color.RED);
        paint.setStrokeWidth(3);

        for (int j=0; j<data.num-1; j++){   //繪製xy曲線
            canvas.drawLine(data.xvalue[j],data.yvalue[j],data.xvalue[j+1],data.yvalue[j+1],paint);
        }
    }

}
