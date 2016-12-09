package com.yummyteam.fastcampus.forkit.model;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Dabin on 2016-12-08.
 */

public class TokenCache {
    static private Context context;
    static private TokenCache tokenCache;

    private TokenCache() {

    }

    public static TokenCache getInstance() {
        if (tokenCache == null) {
            tokenCache = new TokenCache();
        }

        return tokenCache;
    }

    public File getCacheDir(Context context) {
        this.context = context;
        File cacheDir = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheDir = new File(Environment.getExternalStorageDirectory(), "tokenfolder");
            if (!cacheDir.isDirectory()) {
                cacheDir.mkdirs();
            }
        }
        if (!cacheDir.isDirectory()) {
            cacheDir = context.getCacheDir();
        }
        return cacheDir;
    }

    public void write(String obj) throws IOException {
        File cacheDir = getCacheDir(context);
        File cacheFile = new File(cacheDir, "Token.txt");
        if (!cacheFile.exists()) cacheFile.createNewFile();
        FileWriter fileWriter = new FileWriter(cacheFile);
        fileWriter.write(obj);
        fileWriter.flush();
        fileWriter.close();
    }

    public String read() throws IOException {
        File cacheDir = getCacheDir(context);
        File cacheFile = new File(cacheDir, "Token.txt");
        String text = "";
        if (cacheFile.exists()) {
            FileInputStream inputStream = new FileInputStream(cacheFile);
            Scanner s = new Scanner(inputStream);
            while (s.hasNext()) {
                text += s.nextLine();
            }
            inputStream.close();
        }

        return text;
    }

    public void delete() throws IOException {
        File cacheDir = getCacheDir(context);
        File cacheFile = new File(cacheDir, "Token.txt");
        if (cacheFile.exists()) {
            cacheFile.delete();
        }
    }


}
