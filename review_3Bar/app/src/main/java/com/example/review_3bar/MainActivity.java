package com.example.review_3bar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private RatingBar mRatingBar;
    private SeekBar mSeekBar;
    private TextView mtxtSeekBarProgress, mtxtRatingBarProgress, mtxtRatingBarRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSeekBar = (SeekBar)findViewById(R.id.seekBar);
        mRatingBar = (RatingBar)findViewById(R.id.ratingBar);
        mtxtSeekBarProgress = (TextView)findViewById(R.id.textView);
        mtxtRatingBarRating = (TextView) findViewById(R.id.textView2);
        mtxtRatingBarProgress = (TextView)findViewById(R.id.textView3);

        //設定SeekBar與RatingBar的啟動事件
        //seekbar物件名稱.setOnSeekBarChangeListener(函數名稱);
        mSeekBar.setOnSeekBarChangeListener(changeSeekbar);
        //ratingbar物件名稱.setOnRatingBarChangeListener(函數名稱);
        mRatingBar.setOnRatingBarChangeListener(changeRatingbar);

    }



    //private  SeekBar.OnSeekBarChangeListener 函數名稱 = new SeekBar.OnSeekBarChangeListener
    private SeekBar.OnSeekBarChangeListener changeSeekbar = new SeekBar.OnSeekBarChangeListener() {

        //一般情況下只需要處理第一個方法，值儲存在 int  progress值的變數名稱
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                                                       //int  progress值的變數名稱
            //取得strings.xml裡的字串，並存在字串變數
            //String  字串變數名稱 = getString(R.string.strings檔中的字串name);
            String s = getString(R.string.seekbarProgress);

            //textview物件名稱.setText(字串變數名稱 + String.valueOf(progress值的變數名稱));
            mtxtSeekBarProgress.setText(s + String.valueOf(i));

        }

        //當控制按鈕被按下準備拖曳時執行，通常用不到此部分
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        //當控制按鈕被放下時執行，通常用不到此部分
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    };


    //private RatingBar.OnRatingBarChangeListener 函數名稱 = new RatingBar.OnRatingBarChangeListener
    private RatingBar.OnRatingBarChangeListener changeRatingbar = new RatingBar.OnRatingBarChangeListener() {
        @Override
        public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                                                         //float  rating值的變數名稱，rating就是星星數value

            String s = getString(R.string.ratingbarRating);
            mtxtRatingBarRating.setText(s + String.valueOf(v));

            s = getString(R.string.ratingbarProgress);
            //textview物件名稱.setText(字串變數名稱 + String.valueOf(ratingbar物件名稱.getProgress()));
            mtxtRatingBarProgress.setText(s + String.valueOf(mRatingBar.getProgress()));

        }
    };

}
