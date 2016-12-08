package com.yummyteam.fastcampus.forkit.view.main.fragment.mypage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yummyteam.fastcampus.forkit.R;
import com.yummyteam.fastcampus.forkit.model.TokenCache;
import com.yummyteam.fastcampus.forkit.view.login.LoginActivity;

import java.io.IOException;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class MyPage_Fragment extends Fragment implements View.OnClickListener {

    private TokenCache cache;
    private String token;
    public MyPage_Fragment() {
        // Required empty public constructor
        cache = TokenCache.getInstance();
        try {
            token = cache.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    private ImageView iv_profile_myPage;
    private TextView tv_profile_myPage;
    private Button btn_sign;

    private final String LOGIN = "로그인 하기";
    private final String LOGOUT = "로그아웃";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_page, container, false);
        iv_profile_myPage = (ImageView)view.findViewById(R.id.iv_profile_my);
        tv_profile_myPage = (TextView)view.findViewById(R.id.tv_profile_my);
        btn_sign  = (Button)view.findViewById(R.id.btn_sign);
        if(token.equals("")) {
            btn_sign.setText(LOGIN);
            tv_profile_myPage.setText("로그인 해주세요");
        }else{
            tv_profile_myPage.setText("로그인 되셨습니다.");
            btn_sign.setText(LOGOUT);
        }
        Glide.with(getContext())
                .load(R.mipmap.com_forkit_profile_picture_blank_portrait)
                .bitmapTransform(new CropCircleTransformation(getContext()))
                .into(iv_profile_myPage);

        btn_sign.setOnClickListener(this);

        return view;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_sign){
            Button temp_btn = (Button)view;
            if(temp_btn.getText().toString().equals(LOGIN)){
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }else if(temp_btn.getText().toString().equals(LOGOUT)){
                try {
                    cache.delete();
                    btn_sign.setText(LOGIN);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
