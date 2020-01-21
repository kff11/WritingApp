package com.Winner.writingapp.PostsList;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.Winner.writingapp.R;
import com.Winner.writingapp.SharedPreference.Post;
import com.Winner.writingapp.SharedPreference.SP_Main;

import java.util.ArrayList;

public class PostActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private String title;
    private ViewGroup viewGroup1, viewGroup2; // 수정할 레이아웃
    private EditText editText_Title, editText_Write; // 수정할 글
    private TextView textView_Title, textView_Write; // 수정된 글
    private ImageView imageView_edit, imageView_apply;
    private ArrayList<String> arrayList;
    private Post post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        setTitle();
        init();
        setToolbar();
    }

    void setTitle() {
        Intent intent = getIntent();
        // Post 객체를 어댑터에서 가져옴
        post = (Post) intent.getSerializableExtra("post");
        // 키값 설정
        title = post.getTitle();
    }

    void init() {
        viewGroup1 = (ViewGroup) findViewById(R.id.post_Layout_1);
        viewGroup2 = (ViewGroup) findViewById(R.id.post_Layout_2);
        textView_Title = (TextView) findViewById(R.id.post_Title);
        textView_Write = (TextView) findViewById(R.id.post_Write);
        editText_Title = (EditText) findViewById(R.id.post_Title_edit);
        editText_Write = (EditText) findViewById(R.id.post_Write_edit);
        editText_Title.setText(title);
        textView_Title.setText(title);
        editText_Write.setText(post.getWrite());
        textView_Write.setText(post.getWrite());

        viewGroup1.setVisibility(View.VISIBLE);
        viewGroup2.setVisibility(View.INVISIBLE);
    }

    // 툴바 설정
    void setToolbar() {
        toolbar = (Toolbar) findViewById(R.id.post_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false); // 타이틀 제거
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼 생성
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_post_menu, menu);
        final CheckBox checkBox = (CheckBox) menu.findItem(R.id.toolbar_Bookmark).getActionView();
        checkBox.setButtonDrawable(R.drawable.toolbar_bookmark_btn);

        checkBox.setChecked(post.isBookmark());

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
                // 북마크에 저장
                post.setBookmark(getApplicationContext(), check);
            }
        });
        return true;
    }

    void setTextGravity(int i) {
        textView_Title.setGravity(i);
        textView_Write.setGravity(i);
        editText_Title.setGravity(i);
        editText_Write.setGravity(i);
    }

    void setPost() {
        if (!textView_Title.getText().toString().equals(title)) { // 제목이 바뀌었을 때
            String trimTitle = textView_Title.getText().toString().trim();
            if (post.sameTitle(this, trimTitle) || title.equals(trimTitle)) {
                Log.d("제발1", textView_Title.getText().toString().trim());
                Log.d("되어라2", title);
                // 현재 내용을 잠시 저장
                String nowTitle = textView_Title.getText().toString();
                String nowWrite = textView_Write.getText().toString();

                // post객체의 내용을 수정
                post.editPost(this, title, nowTitle, nowWrite);
                Toast.makeText(this, nowTitle + "(으)로 수정되었습니다.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, trimTitle + "와(과) 다른 제목을 지어주세요.", Toast.LENGTH_LONG).show();
            }
        } else {
            post.editPost(this, textView_Write.getText().toString());
            Toast.makeText(this, post.getTitle() + "(이)가 수정되었습니다.", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClick(View view) {
        imageView_apply = (ImageView) findViewById(R.id.post_apply);
        imageView_edit = (ImageView) findViewById(R.id.post_edit);

        switch (view.getId()) {
            // 정렬 부분
            case R.id.post_center_gravity:
                setTextGravity(Gravity.CENTER_HORIZONTAL);
                break;
            case R.id.post_top_gravity:
                setTextGravity(Gravity.TOP);
                break;
            case R.id.post_right_gravity:
                setTextGravity(Gravity.RIGHT);
                break;

            // 수정 부분 (누르면 텍스트뷰를 숨김)
            case R.id.post_edit:
                viewGroup1.setVisibility(View.INVISIBLE);
                viewGroup2.setVisibility(View.VISIBLE);
                imageView_edit.setVisibility(View.INVISIBLE);
                imageView_apply.setVisibility(View.VISIBLE);
                break;

            // 적용 부분 (누르면 에딧텍스트를 숨김)
            case R.id.post_apply:
                viewGroup1.setVisibility(View.VISIBLE);
                viewGroup2.setVisibility(View.INVISIBLE);
                imageView_edit.setVisibility(View.VISIBLE);
                imageView_apply.setVisibility(View.INVISIBLE);
                textView_Title.setText(editText_Title.getText());
                textView_Write.setText(editText_Write.getText());

                // 수정한 내용 저장
                setPost();
                break;
        }
    }

    public void onClickHandler(View view) {
        // 삭제 여부 묻는 창 생성
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title + "을(를) 삭제하시겠습니까?");
        builder.setPositiveButton("아니요", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
            }
        });

        builder.setNegativeButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                post.delPost(getApplicationContext());
                finish();
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
                Toast.makeText(getApplicationContext(), title + "이(가) 삭제되었습니다!", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
    }
}
