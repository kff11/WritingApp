package com.Winner.writingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.Winner.writingapp.SharedPreference.SP_Main;

public class SettingActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText editText; // 처음 이름 설정
    private SP_Main SPMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        SPMain = new SP_Main(this);

        editText = (EditText) findViewById(R.id.setting_EditText);

        setToolbar();

    }

    // 툴바 설정
    void setToolbar() {
        toolbar = (Toolbar) findViewById(R.id.setting_Toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false); // 타이틀 제거
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼 생성
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right); // 뒤로가기 애니메이션
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting_Summit:
                if (editText.getText().toString().length() != 0) {
                    SPMain.setSharedString("userName", editText.getText().toString());
                    Toast.makeText(this, editText.
                            getText().toString() + "님 저장되었습니다!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "이름을 적어주세요.", Toast.LENGTH_LONG).show();
                }
                break;
        }

    }
}
