package com.Winner.writingapp;

import android.graphics.drawable.Drawable;

public class RecyclerViewItem {
    private String title;
    private String write;
    private String date;
    private boolean bookmark;
    public void setTitle(String title) {
        this.title = title;
    }

    public void setWrite(String write) {
        this.write = write;
    }

    public void setDate(String nickStr) {
        this.date = nickStr;
    }

    public void setBookmark(boolean bookmark) {
        this.bookmark = bookmark;
    }

    public String getTitle() {
        return this.title;
    }

    public String getWrite() {
        return write;
    }

    public String getDate() {
        return date;
    }

    public boolean isBookmark() {
        return bookmark;
    }
}
