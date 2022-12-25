package com.example.artist_m;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {

    private Button startbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.brown2));
        setContentView(R.layout.activity_main2);

        startbtn = findViewById(R.id.button8);

        startbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity2.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    // Step01-設定目前時間變數(使用long是因為System.currentTimeMillis()方法的型態是long):
    private long timeSave = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        // Step02-判斷是否按下按鍵，並且確認該按鍵是否為返回鍵:
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            // Step03-判斷目前時間與上次按下返回鍵時間是否間隔2000毫秒(2秒):
            if((System.currentTimeMillis()-timeSave) > 2000){
                Toast.makeText(this, "再按一次退出!!", Toast.LENGTH_SHORT).show();
                // Step04-紀錄第一次案返回鍵的時間:
                timeSave = System.currentTimeMillis();
            }
            else {
                // Step05-結束Activity與關閉APP:
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}