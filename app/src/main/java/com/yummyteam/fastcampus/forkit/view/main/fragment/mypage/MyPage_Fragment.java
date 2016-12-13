package com.yummyteam.fastcampus.forkit.view.main.fragment.mypage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.yummyteam.fastcampus.forkit.R;
import com.yummyteam.fastcampus.forkit.model.TokenCache;
import com.yummyteam.fastcampus.forkit.networks.ConnectFork;
import com.yummyteam.fastcampus.forkit.view.login.LoginActivity;
import com.yummyteam.fastcampus.forkit.view.main.ActivityConnectInterface;
import com.yummyteam.fastcampus.forkit.view.main.MainView;
import com.yummyteam.fastcampus.forkit.view.main.fragment.eatery.ELAdapter;

import java.io.IOException;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

public class MyPage_Fragment extends Fragment implements View.OnClickListener {

    private TokenCache cache;
    private String token;
    private ActivityConnectInterface anInterface;
    public MyPage_Fragment() {
        // Required empty public constructor
        cache = TokenCache.getInstance();
        try {
            token = cache.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setInterface(ActivityConnectInterface anInterface){
        this.anInterface = anInterface;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    private ImageView iv_profile_myPage;
    private TextView tv_profile_myPage;
    private Button btn_sign;
    private RecyclerView mylist;
    private ConnectFork connectFork;
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
        mylist = (RecyclerView)view.findViewById(R.id.mylist);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        mylist.setLayoutManager(manager);
        //connectFork = new ConnectFork();
        try {
            initAdapters();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setMyReviews();
        setMyFavorite();
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

    private void initAdapters() throws IOException {
        ELAdapter elAdapter = new ELAdapter();
        elAdapter.setInterface((MainView)getActivity());
        mylist.setAdapter(elAdapter);
        MyReviewAdapter mrAdapter = new MyReviewAdapter(getActivity());
    }

    private void setMyFavorite() {

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
        switch (view.getId()){
            case R.id.btn_sign:
                setSignButton(view);
                break;
            case R.id.tv_my_reviews:
                setMyReviews();
                break;
            case R.id.tv_my_favorites:
                break;
        }

    }

    private void setMyReviews() {
        if(token.equals("")){
            Toast.makeText(getContext(),"로그인 해주세요",Toast.LENGTH_SHORT).show();
        }else{

        }
    }

    private void setSignButton(View view){
        Button temp_btn = (Button)view;
        if(temp_btn.getText().toString().equals(LOGIN)){
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }else if(temp_btn.getText().toString().equals(LOGOUT)){
            try {
                cache.delete();
                anInterface.refresh();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
