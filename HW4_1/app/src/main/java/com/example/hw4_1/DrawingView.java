package com.example.hw4_1;

import android.graphics.Color;
import android.view.View;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import java.util.ArrayList;

//畫布繪製
public class DrawingView extends View {

    //用來儲存各條path的ArrayList，名為paths
    private ArrayList<Path> paths = new ArrayList<>();

    //建構元，物件初始化設定的函數
    public DrawingView(Context context) {
        super(context);
    }
    public DrawingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    public DrawingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public DrawingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    //將TouchListener.java中產生的新的path儲存至paths的ArrayList裡
    public void addPath(Path path) {
        paths.add(path);
    }

    //回傳paths的ArrayList裡最新的path, 就是目前正在畫的path
    public Path getLastPath() {
        if (paths.size() > 0) {
            return paths.get(paths.size() - 1);  //回傳ArrayList裡最後一個path
        }
        return null;
    }

    //畫布設定
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //對於paths的ArrayList裡每條path
        for (Path path : paths) {
            Paint paint = new Paint();  //建立畫筆
            paint.setColor(Color.RED);  //設定畫筆顏色
            paint.setStyle(Paint.Style.STROKE);  //設定畫筆類型
            paint.setStrokeWidth(10f); //設定畫筆粗細
            canvas.drawPath(path, paint);  //以畫筆paint在畫布畫出此path
        }
    }

    //清除畫布筆跡
    public void erase() {
        paths.clear();  //將所有的path從paths的ArrayList裡刪除
        invalidate();  //清除後準備重畫
    }

}

