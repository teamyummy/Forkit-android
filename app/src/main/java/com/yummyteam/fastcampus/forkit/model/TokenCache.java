package com.yummyteam.fastcampus.forkit.model;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;

/**
 * Created by Dabin on 2016-12-08.
 */

public class TokenCache {
    static private Context context;
    static private TokenCache tokenCache;
    private SharedPreferences pref;

    private TokenCache() {

    }

    public static TokenCache getInstance() {
        if (tokenCache == null) {
            tokenCache = new TokenCache();
        }

        return tokenCache;
    }

    public void getCacheDir(Context context) {
        this.context = context;
        pref = context.getSharedPreferences("Token", Context.MODE_PRIVATE);
    }

    public void write(String obj) throws IOException {
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("Token", obj);
        editor.commit();
    }

    public String read() throws IOException {
        String text = pref.getString("Token", "");
        return text;
    }

    public String readID() throws IOException {
        String text = pref.getString("Id", "");
        return text;
    }

    public void delete() throws IOException {
        SharedPreferences.Editor editor = pref.edit();
        editor.remove("Token");
        editor.commit();
    }


    public void writeId(String obj) throws IOException {
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("Id", obj);
        editor.commit();
    }

    public String readURL() throws IOException {
        String url = "";
        String token = read();
        if(token.length()>0){
            url =  pref.getString(token,"");
        }



        return url;
    }
    public void writeULR(String url) throws IOException {
        SharedPreferences.Editor editor = pref.edit();
        String token = read();
        if(token.length()>0){
            editor.putString(token,url);
            editor.commit();
        }
    }
}
