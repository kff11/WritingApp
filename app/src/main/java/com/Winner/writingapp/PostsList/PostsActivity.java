package com.Winner.writingapp.PostsList;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.Winner.writingapp.R;
import com.Winner.writingapp.RecyclerViewItem;
import com.Winner.writingapp.SharedPreference.SP_Main;

import java.util.ArrayList;

public class PostsActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView textView, textView_Num;
    private SP_Main sp_main;
    private PostsRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);
        sp_main = new SP_Main(this);

        setTextView();
        setToolbar();

        //setRecyclerView();

    }

    void setRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.posts_RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new PostsRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);

        getItem();
    }

    void getItem() {
        textView_Num = (TextView) findViewById(R.id.posts_Num);
        ArrayList<String> titleList = new ArrayList<String>();
        if (!sp_main.getStringArrayList("posts").isEmpty()) {
            titleList = sp_main.getStringArrayList("posts");
            textView_Num.setText(titleList.size() + "개의 조각글이 있습니다.");
            for (int i = titleList.size() - 1; i >= 0; i--) {
                RecyclerViewItem item = new RecyclerViewItem();
                item.setTitle(titleList.get(i));
                item.setWrite(sp_main.getSharedString(titleList.get(i)));
                item.setDate(sp_main.getSharedString(titleList.get(i) + "_date"));
                item.setBookmark(sp_main.getSharedboolean(titleList.get(i) + "_bookmark"));
                adapter.addItem(item);
            }
        }
        adapter.notifyDataSetChanged();
    }


    // 툴바 설정
    void setToolbar() {
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false); // 타이틀 제거
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼 생성
    }

    void setTextView() {
        textView = (TextView) findViewById(R.id.posts_nameWithPost);
        // 이름이 안들어가 있을 경우 오류 잡기
        if (sp_main.getSharedString("userName") != null) {
            textView.setText(sp_main.getSharedString("userName") + "님이 쓴 조각글");
        }
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

    @Override
    public void onResume() {
        super.onResume();
        setRecyclerView();
    }
}
