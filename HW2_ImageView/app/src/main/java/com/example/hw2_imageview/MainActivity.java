package com.example.hw2_imageview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    //陣列儲存圖片，可匯入多個圖片檔供程式使用
    int[] picture={R.drawable.paper, R.drawable.scissor, R.drawable.stone};  //int[] 陣列名稱 = {R.drawable.圖片名稱, ...};

    int count=picture.length;  //picture.length為picture陣列的長度

    int random;

    ImageView fig1;
    Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fig1 = (ImageView)findViewById(R.id.imageView);
        btn1 = (Button)findViewById(R.id.button);
        btn1.setOnClickListener(btnclick);
    }
    private View.OnClickListener btnclick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            random = (int) (Math.random()*3);
            fig1.setImageResource(picture[random]);  //設定要顯示的圖片，imageview物件名稱.setImageResource(陣列名稱[指標]);
        }
    };

}
