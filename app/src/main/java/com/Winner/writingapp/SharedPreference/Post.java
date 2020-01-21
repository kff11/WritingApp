package com.Winner.writingapp.SharedPreference;

import android.content.Context;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

public class Post implements Serializable {
    private String title, write, date; // 구성 요소
    private boolean bookmark; // 구성 요소
    private ArrayList<String> arrayList; // 임시 배열
    private SP_Main sp_main; // 쉐어드프리퍼런스

    public Post(String title, String write) {
        this.title = title;
        this.write = write;
        this.bookmark = false;
    }

    public Post(String title, String write, boolean bookmark){
        this.title = title;
        this.write = write;
        this.bookmark = bookmark;
    }

    // 내가 쓴 글을 핸드폰에 저장
    public void setPost(Context context) {
        sp_main = new SP_Main(context);
        this.date = sp_main.getSharedString("today"); // 날짜 저장
        if (!sp_main.getStringArrayList("posts").isEmpty()) { // 먼저 저장된 글이 있을 때
            arrayList = sp_main.getStringArrayList("posts");
            setSharedPref();
        } else {
            setSharedPref();
        }
    }

    public void setBookmark(Context context, boolean check) {
        sp_main = new SP_Main(context);
        if (!sp_main.getStringArrayList("bookmarks").isEmpty()) { // 먼저 북마크 된 글이 있을 때
            arrayList = sp_main.getStringArrayList("bookmarks");
        } else {
            sp_main.setStringArrayList("bookmarks", arrayList);
        }
        if (check == true) {
            sp_main.setSharedboolean(title + "_bookmark", true);
            arrayList.add(title);
        } else {
            sp_main.setSharedboolean(title + "_bookmark", false);
            arrayList.remove(title);
        }
        sp_main.setStringArrayList("bookmarks", arrayList);

    }

    public void delPost(Context context) {
        sp_main = new SP_Main(context);
        sp_main.delShared(title);
        sp_main.delShared(title + "_date");
        sp_main.delShared(title + "_bookmark");

        // 북마크 리스트에서 삭제
        arrayList = sp_main.getStringArrayList("bookmarks");
        arrayList.remove(title);
        sp_main.setStringArrayList("bookmarks", arrayList);
        // 내 글 목록에서 삭제
        arrayList = sp_main.getStringArrayList("posts");
        arrayList.remove(title);
        sp_main.setStringArrayList("posts", arrayList);
    }

    public void editPost(Context context, String write){
        sp_main = new SP_Main(context);
        sp_main.setSharedString(title, write);
    }

    public void editPost(Context context, String previousTitle, String nowTitle, String nowWrite) {
        sp_main = new SP_Main(context);
        arrayList = sp_main.getStringArrayList("posts");
        arrayList.set(arrayList.indexOf(previousTitle), nowTitle);
        sp_main.setStringArrayList("posts", arrayList);

        if (sp_main.getSharedboolean(previousTitle + "_bookmark")) { // 북마크에 저장되어 있다면 값을 가져옴
            arrayList = sp_main.getStringArrayList("bookmarks");
            arrayList.set(arrayList.indexOf(previousTitle), nowTitle);
            sp_main.setSharedboolean(nowTitle + "_bookmark", sp_main.getSharedboolean(previousTitle + "_bookmark"));
            sp_main.setStringArrayList("bookmarks", arrayList);
        }
        // 날짜도 가져옴
        sp_main.setSharedString(nowTitle + "_date", sp_main.getSharedString(previousTitle + "_date"));

        // 데이터를 다 가져왔으면 전 글 삭제
        sp_main.delShared(previousTitle);
        sp_main.delShared(previousTitle + "_bookmark");
        sp_main.delShared(previousTitle + "_date");
        sp_main.setSharedString(nowTitle, nowWrite);
    }

    private void setSharedPref() {
        sp_main.setSharedString(title, write); // 작성한 글 저장
        sp_main.setSharedString(title + "_date", date); // 날짜 저장
        sp_main.setSharedboolean(title + "_bookmark", false); // 북마크 초기화
        arrayList.add(title); // 임시 배열에 추가
        sp_main.setStringArrayList("posts", arrayList); // 임시 배열을 저장.
    }

    public boolean sameTitle(Context context, String title){
        sp_main = new SP_Main(context);
        if(sp_main.getSharedString(title.trim()) != null){
            return false;
        }
        return true;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWrite() {
        return write;
    }

    public void setWrite(String write) {
        this.write = write;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isBookmark() {
        return bookmark;
    }

    public void setBookmark(boolean bookmark) {
        this.bookmark = bookmark;
    }
}
