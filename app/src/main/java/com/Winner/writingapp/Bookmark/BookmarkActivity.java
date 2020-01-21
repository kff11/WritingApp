package com.Winner.writingapp.Bookmark;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.Winner.writingapp.R;
import com.Winner.writingapp.RecyclerViewItem;
import com.Winner.writingapp.SharedPreference.SP_Main;

import java.util.ArrayList;

public class BookmarkActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private BookmarkRecyclerViewAdapter adapter;
    private SP_Main sp_main;
    private ArrayList<String> titleList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

        sp_main = new SP_Main(this);

        setToolbar();
        //setRecyclerView();
    }

    // 툴바 설정
    void setToolbar() {
        toolbar = (Toolbar) findViewById(R.id.bookmark_Toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false); // 타이틀 제거
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼 생성
    }

    void setRecyclerView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        RecyclerView recyclerView = findViewById(R.id.bookmark_RecyclerView);
        recyclerView.setLayoutManager(gridLayoutManager);

        adapter = new BookmarkRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);

        getItem();
    }

    void getItem() {
        if (!sp_main.getStringArrayList("bookmarks").isEmpty()) {
            titleList = sp_main.getStringArrayList("bookmarks");
            for (int i = titleList.size() - 1; i >= 0; i--) {
                RecyclerViewItem item = new RecyclerViewItem();
                item.setTitle(titleList.get(i));
                item.setWrite(sp_main.getSharedString(titleList.get(i)));
                item.setBookmark(sp_main.getSharedboolean(titleList.get(i) + "_bookmark"));
                adapter.addItem(item);
            }
        }
        adapter.notifyDataSetChanged();
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
    public void onResume(){
        super.onResume();
        setRecyclerView();
    }
}
