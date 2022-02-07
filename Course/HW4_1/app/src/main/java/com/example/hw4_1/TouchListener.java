package com.example.hw4_1;

import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;

//處理使用者觸碰螢幕的觸發事件
public class TouchListener implements View.OnTouchListener {

    //觸碰螢幕執行的動作
    @Override
    public boolean onTouch(View view, MotionEvent event) {
        float x = event.getX();  //讀取觸碰點之x座標
        float y = event.getY();  //讀取觸碰點之y座標
        DrawingView drawingView = (DrawingView) view;  //設置引入DrawingView.java
        Path path;  //設置Path

        //MotionEvent的各種動作
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:  //觸碰到螢幕
                path = new Path();  //建立初始化一個新的path
                path.moveTo(x, y);  //將path座標原點移至觸碰到螢幕的x,y座標
                drawingView.addPath(path);  //將此新的path儲存至paths的ArrayList裡
                break;
            case MotionEvent.ACTION_MOVE:  //在螢幕上滑動
                path = drawingView.getLastPath();  //取得paths的ArrayList裡最新的path, 就是目前正在畫的path
                if (path != null) { //若paths的ArrayList有存在path
                    path.lineTo(x, y);  //由上一點繪製至目前螢幕上滑動的x,y座標
                }
                break;
        }

        drawingView.invalidate();  //手移開螢幕，未觸碰後，便準備下一次重畫一條新的path

        return true;
    }
}

