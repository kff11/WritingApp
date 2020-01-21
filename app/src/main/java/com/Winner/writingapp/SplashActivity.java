package com.Winner.writingapp;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.Winner.writingapp.SharedPreference.SP_Main;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SplashActivity extends AppCompatActivity {

    private SP_Main sp_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sp_main = new SP_Main(this);

        Date currentTime = Calendar.getInstance().getTime(); // 날짜 데이터 가져오기
        String date = new SimpleDateFormat("YYYY-MM-dd", Locale.getDefault()).format(currentTime); // 문자열로 변경

        sp_main.setSharedString("today", date); // 오늘 날짜 저장


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                overridePendingTransition(R.anim.anim_alpha_in, R.anim.anim_alpha_out);
                finish();
            }
        }, 2000);
    }

    // 뒤로가기 버튼 기능 없애기
    @Override
    public void onBackPressed() {
    }
}