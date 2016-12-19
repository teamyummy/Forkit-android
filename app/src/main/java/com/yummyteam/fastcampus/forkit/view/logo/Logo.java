package com.yummyteam.fastcampus.forkit.view.logo;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.yummyteam.fastcampus.forkit.R;
import com.yummyteam.fastcampus.forkit.model.TokenCache;
import com.yummyteam.fastcampus.forkit.view.login.LoginActivity;
import com.yummyteam.fastcampus.forkit.view.main.MainView;

import java.io.IOException;

import static com.darsh.multipleimageselect.helpers.Constants.REQUEST_CODE;

public class Logo extends AppCompatActivity {

    private static final long SPLASH_DISPLAY_LENGTH = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);



        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            initData();
        else
            checkPermissions(); // 마시멜로우 이상일 경우는 런타임 권한을 체크해야 한다

    }

    @TargetApi(Build.VERSION_CODES.M)
    private void checkPermissions() {
        // 런타임 권한 체크 (디스크읽기권한)
        if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                &&checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            // 요청할 권한 배열생성
            String permissionArray[] = { Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE };
            // 런타임 권한요청을 위한 팝업창 출력
            requestPermissions( permissionArray , REQUEST_CODE );
        }else{
            // 런타임 권한이 이미 있으면 데이터를 세팅한다
            initData();
        }
    }

    private void initData() {
        TokenCache tokenCache = TokenCache.getInstance();
        tokenCache.getCacheDir(this);

        try {
            if(tokenCache.read().equals(""))
            {
                loadMain(LoginActivity.class);
            }else{
                loadMain(MainView.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void loadMain(final Class type){
        // 여기서 메인액티비티 호출
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //메인 엑티비티를 실행하고 로딩화면을 죽인다.
                Intent intent = new Intent(Logo.this,type);
                Logo.this.startActivity(intent);
                //애니매이션 제공
                Logo.this.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                Logo.this.finish();
            }
        },SPLASH_DISPLAY_LENGTH);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode) {
            case REQUEST_CODE: // 요청코드가 위의 팝업창에 넘겨준 코드와 같으면
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) { // 권한을 체크하고
                    // 권한이 있으면 데이터를 생성한다
                    initData();
                }
                break;
        }
    }


}
