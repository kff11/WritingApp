package com.Winner.writingapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.Winner.writingapp.Bookmark.BookmarkActivity;
import com.Winner.writingapp.PostsList.PostsActivity;
import com.Winner.writingapp.SharedPreference.Post;
import com.Winner.writingapp.SharedPreference.SP_Main;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private long pressedTime; // 뒤로가기 대기시간
    private DrawerLayout drawerLayout; // 메뉴바
    private SP_Main sp_main; // 쉐어드프리퍼런스
    private EditText editText_Write, editText_Title; // 제목 및 내용
    private Intent intent;
    private Toolbar toolbar;
    private String title, write;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp_main = new SP_Main(this);

        Init();
        setToolbar();
        setDrawerLayout();
    }

    void setToolbar(){
        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_menu);
    }

    void setDrawerLayout(){
        // 네비게이션 드로어
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.main_menu_list:
                        intent = new Intent(getApplicationContext(), PostsActivity.class);
                        break;
                    case R.id.main_menu_bookmark:
                        intent = new Intent(getApplicationContext(), BookmarkActivity.class);
                        break;
                    case R.id.main_menu_setting:
                        intent = new Intent(getApplicationContext(), SettingActivity.class);
                        break;
                }
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                return false;
            }
        });
    }


    void Init() {
        editText_Write = (EditText) findViewById(R.id.main_write);
        editText_Title = (EditText) findViewById(R.id.main_title);
        drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);

    }

    void postSave() {
        title = editText_Title.getText().toString(); // 제목
        write = editText_Write.getText().toString(); // 내용
        if (title.trim().length() == 0) { // 제목이 없을 때
            Toast.makeText(this, "제목을 적어주세요!", Toast.LENGTH_LONG).show();
        } else if (write.trim().length() == 0) { // 내용이 없을 때
            Toast.makeText(this, "내용을 적어주세요!", Toast.LENGTH_LONG).show();
        } else if (sp_main.getSharedString(title.trim()) != null) { // 제목이 같을 때
            Toast.makeText(this, title + "와(과) 다른 제목을 지어주세요.", Toast.LENGTH_LONG).show();
        } else {
            Post post = new Post(title, write);
            post.setPost(this);
            Toast.makeText(this, title + "이(가) 저장되었습니다!", Toast.LENGTH_LONG).show();
            // 글쓰기 초기화
            editText_Title.setText("");
            editText_Write.setText("");
        }
    }

    void setTextGravity(int i) {
        editText_Title.setGravity(i);
        editText_Write.setGravity(i);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_save:
                postSave();
                break;
            // 정렬 부분
            case R.id.main_center_gravity:
                setTextGravity(Gravity.CENTER_HORIZONTAL);
                break;
            case R.id.main_top_gravity:
                setTextGravity(Gravity.TOP);
                break;
            case R.id.main_right_gravity:
                setTextGravity(Gravity.RIGHT);
                break;
        }

    }

    // 뒤로가기 한번에 안되게
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (pressedTime == 0) {
            Toast.makeText(MainActivity.this, " 한 번 더 누르면 종료됩니다.", Toast.LENGTH_LONG).show();
            pressedTime = System.currentTimeMillis();
        } else {
            int seconds = (int) (System.currentTimeMillis() - pressedTime);
            if (seconds > 2000) {
                Toast.makeText(MainActivity.this, " 한 번 더 누르면 종료됩니다.", Toast.LENGTH_LONG).show();
                pressedTime = 0;
            } else {
                super.onBackPressed();
                finish();
            }
        }
    }
}
