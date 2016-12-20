package com.yummyteam.fastcampus.forkit.view.logo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.yummyteam.fastcampus.forkit.R;
import com.yummyteam.fastcampus.forkit.model.TokenCache;
import com.yummyteam.fastcampus.forkit.view.login.LoginActivity;
import com.yummyteam.fastcampus.forkit.view.main.MainView;

import java.io.IOException;

public class Logo extends AppCompatActivity {

    private static final long SPLASH_DISPLAY_LENGTH = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);


        initData();

    }


    private void initData() {
        TokenCache tokenCache = TokenCache.getInstance();
        tokenCache.getCacheDir(this);

        try {
            if (tokenCache.read().equals("")) {
                loadMain(LoginActivity.class);
            } else {
                loadMain(MainView.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void loadMain(final Class type) {
        // 여기서 메인액티비티 호출
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //메인 엑티비티를 실행하고 로딩화면을 죽인다.
                Intent intent = new Intent(Logo.this, type);
                Logo.this.startActivity(intent);
                //애니매이션 제공
                Logo.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                Logo.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);


    }

}
