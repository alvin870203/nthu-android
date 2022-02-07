package com.example.exam_105033110;

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
    private String[] ylabel = new String[900/yscale];  //y座標的數字
    protected void onDraw(Canvas canvas)
    {
        final Paint paint = new Paint();    //建立畫筆
        paint.setColor(Color.WHITE);    //設定畫筆顏色
        canvas.drawRect(0,0,1000,1000,paint);   //繪製1000*1000白底

        paint.setStyle(Paint.Style.STROKE);

        //paint.setStrokeJoin(Paint.Join.ROUND);

        //paint.setStrokeCap(Paint.Cap.ROUND);

        paint.setColor(Color.RED);
        paint.setStrokeWidth(3);


        canvas.drawCircle(500,500, data.canvasR, paint);

    }


}
