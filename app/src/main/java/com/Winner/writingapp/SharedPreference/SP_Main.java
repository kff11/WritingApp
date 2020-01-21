package com.Winner.writingapp.SharedPreference;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.logging.Logger;

public class SP_Main {
    private SharedPreferences sharedPreferences;
    private Context context;
    private static final String XML_FILE_NAME = "WritingApp"; // 저장할 XML 파일 이름

    public SP_Main(Context context) {
        this.context = context;
    }

    public String getSharedString(String key) {
        sharedPreferences = context.getSharedPreferences(XML_FILE_NAME, Activity.MODE_PRIVATE);
        String string = sharedPreferences.getString(key, null);
        return string;
    }

    public void setSharedString(String key, String string) {
        sharedPreferences = context.getSharedPreferences(XML_FILE_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, string);
        editor.commit();
    }

    public boolean getSharedboolean(String key) {
        sharedPreferences = context.getSharedPreferences(XML_FILE_NAME, Activity.MODE_PRIVATE);
        boolean aBoolean = sharedPreferences.getBoolean(key, false);
        return aBoolean;
    }

    public void setSharedboolean(String key, boolean aboolean) {
        sharedPreferences = context.getSharedPreferences(XML_FILE_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, aboolean);
        editor.commit();
    }

    public void delShared(String key) {
        sharedPreferences = context.getSharedPreferences(XML_FILE_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.commit();
    }


    // json 형식으로 ArrayList 저장
    public void setStringArrayList(String key, ArrayList<String> valueList) {
        sharedPreferences = context.getSharedPreferences(XML_FILE_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        JSONArray jsonArray = new JSONArray();
        // 받아온 ArrayList를 jsonArray에 입력
        for (int i = 0; i < valueList.size(); i++) {
            jsonArray.put(valueList.get(i));
        }
        if (!valueList.isEmpty()) {
            // json 형식, String 문자열로 저장.
            editor.putString(key, jsonArray.toString());
        } else {
            editor.putString(key, null);
        }
        editor.commit();
    }

    // ArrayList 불러오기
    public ArrayList<String> getStringArrayList(String key) {
        sharedPreferences = context.getSharedPreferences(XML_FILE_NAME, Activity.MODE_PRIVATE);
        String json = sharedPreferences.getString(key, null);
        ArrayList<String> valueList = new ArrayList<>();
        if (json != null) {
            try {
                JSONArray jArray = new JSONArray(json);
                for (int i = 0; i < jArray.length(); i++) {
                    String data = jArray.optString(i);
                    valueList.add(data);
                }
            } catch (JSONException e) {
            }
        }
        return valueList;
    }

}
